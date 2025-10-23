import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { VitalsService } from '../services/vitals.service';
import { VitalSignDto } from '../../shared/models/vital.model';

@Component({
  selector: 'ct-vitals-list-page',
  templateUrl: './vitals-list-page.component.html',
  styleUrls: ['./vitals-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class VitalsListPageComponent {
  private readonly vitalsService = inject(VitalsService);
  protected readonly vitals$: Observable<VitalSignDto[]> = this.vitalsService.getVitals();
}
