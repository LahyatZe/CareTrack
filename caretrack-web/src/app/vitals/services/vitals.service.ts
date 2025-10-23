import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiConfigurationService } from '../../core/services/api-configuration.service';
import { VitalSignDto } from '../../shared/models/vital.model';

@Injectable({ providedIn: 'root' })
export class VitalsService {
  public constructor(private readonly http: HttpClient, private readonly api: ApiConfigurationService) {}

  public getVitals(): Observable<VitalSignDto[]> {
    return this.http.get<VitalSignDto[]>(`${this.api.apiUrl}/vitals`);
  }
}
