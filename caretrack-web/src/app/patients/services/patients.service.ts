import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { ApiConfigurationService } from '../../core/services/api-configuration.service';
import { PatientDto, PatientListResponse } from '../../shared/models/patient.model';

@Injectable({ providedIn: 'root' })
export class PatientsService {
  public constructor(private readonly http: HttpClient, private readonly api: ApiConfigurationService) {}

  public getPatients(): Observable<PatientDto[]> {
    return this.http
      .get<PatientListResponse>(`${this.api.apiUrl}/patients`)
      .pipe(map((response) => response.data));
  }

  public getPatient(id: string): Observable<PatientDto> {
    return this.http.get<PatientDto>(`${this.api.apiUrl}/patients/${id}`);
  }

  public createPatient(payload: Partial<PatientDto>): Observable<PatientDto> {
    return this.http.post<PatientDto>(`${this.api.apiUrl}/patients`, payload);
  }

  public updatePatient(id: string, payload: Partial<PatientDto>): Observable<PatientDto> {
    return this.http.put<PatientDto>(`${this.api.apiUrl}/patients/${id}`, payload);
  }
}
