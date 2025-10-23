import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';

import { AlertDto } from '../../shared/models/alert.model';

@Component({
  selector: 'ct-alerts-list-page',
  templateUrl: './alerts-list-page.component.html',
  styleUrls: ['./alerts-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertsListPageComponent {
  private readonly alertsSignal = signal<AlertDto[]>([
    {
      id: 'a-1',
      patientId: 'p-1',
      patientName: 'Claire Lambert',
      type: 'VITAL_SIGN',
      message: 'SpO₂ < 90% durant 5 min',
      status: 'CRITICAL',
      createdAt: '2024-04-11T08:12:00Z'
    },
    {
      id: 'a-2',
      patientId: 'p-2',
      patientName: 'Hugo Morel',
      type: 'MEDICATION',
      message: 'Prise de bronchodilatateur confirmée',
      status: 'ACKNOWLEDGED',
      createdAt: '2024-04-11T08:05:00Z'
    },
    {
      id: 'a-3',
      patientId: 'p-3',
      patientName: 'Louise Garnier',
      type: 'PLAN_STEP',
      message: 'Inspection pansement planifiée dans 30 min',
      status: 'PENDING',
      createdAt: '2024-04-11T07:55:00Z'
    }
  ]);

  protected readonly statusFilter = signal<'ALL' | AlertDto['status']>('ALL');
  protected readonly typeFilter = signal<'ALL' | AlertDto['type']>('ALL');
  protected readonly filteredAlerts = computed(() =>
    this.alertsSignal().filter((alert) => {
      const statusOk = this.statusFilter() === 'ALL' || alert.status === this.statusFilter();
      const typeOk = this.typeFilter() === 'ALL' || alert.type === this.typeFilter();
      return statusOk && typeOk;
    })
  );

  protected setStatusFilter(value: 'ALL' | AlertDto['status']): void {
    this.statusFilter.set(value);
  }

  protected setTypeFilter(value: 'ALL' | AlertDto['type']): void {
    this.typeFilter.set(value);
  }

  protected acknowledgeAlert(id: string): void {
    this.alertsSignal.update((alerts) =>
      alerts.map((alert) => (alert.id === id ? { ...alert, status: 'ACKNOWLEDGED' } : alert))
    );
  }

  protected resolveAlert(id: string): void {
    this.alertsSignal.update((alerts) =>
      alerts.map((alert) => (alert.id === id ? { ...alert, status: 'RESOLVED' } : alert))
    );
  }
}
