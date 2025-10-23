import { Injectable, signal } from '@angular/core';

const ACCESS_TOKEN_KEY = 'caretrack.access_token';

@Injectable({ providedIn: 'root' })
export class TokenStorageService {
  private readonly tokenSignal = signal<string | null>(this.getPersistedToken());

  public get token() {
    return this.tokenSignal.asReadonly();
  }

  public setToken(token: string | null): void {
    if (token) {
      localStorage.setItem(ACCESS_TOKEN_KEY, token);
    } else {
      localStorage.removeItem(ACCESS_TOKEN_KEY);
    }
    this.tokenSignal.set(token);
  }

  public getToken(): string | null {
    return this.tokenSignal();
  }

  public clear(): void {
    this.setToken(null);
  }

  private getPersistedToken(): string | null {
    return globalThis.localStorage?.getItem(ACCESS_TOKEN_KEY) ?? null;
  }
}
