import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiConfigurationService } from '../../core/services/api-configuration.service';
import { MedicationDto } from '../../shared/models/medication.model';

@Injectable({ providedIn: 'root' })
export class MedicationsService {
  public constructor(private readonly http: HttpClient, private readonly api: ApiConfigurationService) {}

  public getMedications(): Observable<MedicationDto[]> {
    return this.http.get<MedicationDto[]>(`${this.api.apiUrl}/medications`);
  }

  public getMedication(id: string): Observable<MedicationDto> {
    return this.http.get<MedicationDto>(`${this.api.apiUrl}/medications/${id}`);
  }
}
