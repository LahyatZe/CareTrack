import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { CarePlansService } from '../services/care-plans.service';
import { CarePlanDto } from '../../shared/models/care-plan.model';

@Component({
  selector: 'ct-plans-list-page',
  templateUrl: './plans-list-page.component.html',
  styleUrls: ['./plans-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PlansListPageComponent {
  private readonly carePlansService = inject(CarePlansService);
  protected readonly plans$: Observable<CarePlanDto[]> = this.carePlansService.getCarePlans();
}
