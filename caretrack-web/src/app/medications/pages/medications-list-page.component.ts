import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { MedicationsService } from '../services/medications.service';
import { MedicationDto } from '../../shared/models/medication.model';

@Component({
  selector: 'ct-medications-list-page',
  templateUrl: './medications-list-page.component.html',
  styleUrls: ['./medications-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MedicationsListPageComponent {
  private readonly medicationsService = inject(MedicationsService);
  protected readonly medications$: Observable<MedicationDto[]> = this.medicationsService.getMedications();
}
