import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { PatientsService } from '../services/patients.service';
import { PatientDto } from '../../shared/models/patient.model';

@Component({
  selector: 'ct-patients-list-page',
  templateUrl: './patients-list-page.component.html',
  styleUrls: ['./patients-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PatientsListPageComponent {
  private readonly patientsService = inject(PatientsService);
  protected readonly patients$: Observable<PatientDto[]> = this.patientsService.getPatients();
}
