import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiConfigurationService } from '../../core/services/api-configuration.service';
import { CarePlanDto } from '../../shared/models/care-plan.model';

@Injectable({ providedIn: 'root' })
export class CarePlansService {
  public constructor(private readonly http: HttpClient, private readonly api: ApiConfigurationService) {}

  public getCarePlans(): Observable<CarePlanDto[]> {
    return this.http.get<CarePlanDto[]>(`${this.api.apiUrl}/care-plans`);
  }

  public getCarePlan(id: string): Observable<CarePlanDto> {
    return this.http.get<CarePlanDto>(`${this.api.apiUrl}/care-plans/${id}`);
  }
}
