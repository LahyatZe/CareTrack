import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';

import { ApiConfigurationService } from './api-configuration.service';
import { TokenStorageService } from './token-storage.service';
import { LoginRequest, LoginResponse, UserProfile } from '../../shared/models/auth.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly profileSignal = signal<UserProfile | null>(null);
  public readonly isAuthenticated = computed(() => !!this.tokenStorage.getToken());
  public readonly profile = this.profileSignal.asReadonly();

  public constructor(
    private readonly http: HttpClient,
    private readonly api: ApiConfigurationService,
    private readonly tokenStorage: TokenStorageService
  ) {}

  public login(payload: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api.apiUrl}/auth/login`, payload).pipe(
      tap((response) => {
        this.tokenStorage.setToken(response.accessToken);
        this.profileSignal.set(response.user);
      })
    );
  }

  public loadProfile(): Observable<UserProfile> {
    return this.http
      .get<UserProfile>(`${this.api.apiUrl}/auth/me`)
      .pipe(tap((profile) => this.profileSignal.set(profile)));
  }

  public logout(): void {
    this.tokenStorage.clear();
    this.profileSignal.set(null);
  }
}
