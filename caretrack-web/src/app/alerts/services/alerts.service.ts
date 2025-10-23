import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiConfigurationService } from '../../core/services/api-configuration.service';
import { AlertDto } from '../../shared/models/alert.model';

@Injectable({ providedIn: 'root' })
export class AlertsService {
  public constructor(private readonly http: HttpClient, private readonly api: ApiConfigurationService) {}

  public getAlerts(): Observable<AlertDto[]> {
    return this.http.get<AlertDto[]>(`${this.api.apiUrl}/alerts`);
  }

  public acknowledgeAlert(id: string): Observable<void> {
    return this.http.patch<void>(`${this.api.apiUrl}/alerts/${id}/acknowledge`, {});
  }
}
