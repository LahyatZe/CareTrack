import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { AlertsService } from '../services/alerts.service';
import { AlertDto } from '../../shared/models/alert.model';

@Component({
  selector: 'ct-alerts-list-page',
  templateUrl: './alerts-list-page.component.html',
  styleUrls: ['./alerts-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertsListPageComponent {
  private readonly alertsService = inject(AlertsService);
  protected readonly alerts$: Observable<AlertDto[]> = this.alertsService.getAlerts();
}
