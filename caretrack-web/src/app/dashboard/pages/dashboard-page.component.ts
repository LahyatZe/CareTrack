import { ChangeDetectionStrategy, Component } from '@angular/core';

import { AlertDto } from '../../shared/models/alert.model';
import { VitalSignDto } from '../../shared/models/vital.model';

interface UpcomingTreatment {
  id: string;
  patient: string;
  treatment: string;
  scheduledAt: string;
  status: 'UPCOMING' | 'COMPLETED' | 'OVERDUE';
}

@Component({
  selector: 'ct-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardPageComponent {
  protected readonly criticalAlerts: AlertDto[] = [
    {
      id: 'alert-1',
      patientId: 'p-1',
      patientName: 'Claire Lambert',
      type: 'VITAL_SIGN',
      status: 'CRITICAL',
      createdAt: '2024-04-11T08:15:00Z',
      message: 'Saturation SpO₂ < 90% depuis 5 min'
    },
    {
      id: 'alert-2',
      patientId: 'p-3',
      patientName: 'Nicolas Henry',
      type: 'MEDICATION',
      status: 'OVERDUE',
      createdAt: '2024-04-11T07:40:00Z',
      message: 'Prise d’anticoagulant en retard de 25 min'
    }
  ];

  protected readonly upcomingTreatments: UpcomingTreatment[] = [
    {
      id: 't-1',
      patient: 'Lina Dupont',
      treatment: 'Perfusion antibiothérapie',
      scheduledAt: '11 avr. • 10h30',
      status: 'UPCOMING'
    },
    {
      id: 't-2',
      patient: 'Hugo Morel',
      treatment: 'Séance de kinésithérapie respiratoire',
      scheduledAt: '11 avr. • 11h00',
      status: 'UPCOMING'
    },
    {
      id: 't-3',
      patient: 'Louise Garnier',
      treatment: 'Inspection pansement post-op',
      scheduledAt: '11 avr. • 11h15',
      status: 'UPCOMING'
    }
  ];

  protected readonly recentVitals: VitalSignDto[] = [
    {
      id: 'v-1',
      patientId: 'p-1',
      patientName: 'Claire Lambert',
      type: 'HEART_RATE',
      recordedAt: '2024-04-11T08:10:00Z',
      value: 112,
      unit: 'bpm',
      status: 'ELEVATED'
    },
    {
      id: 'v-2',
      patientId: 'p-2',
      patientName: 'Hugo Morel',
      type: 'SPO2',
      recordedAt: '2024-04-11T08:05:00Z',
      value: 97,
      unit: '%',
      status: 'NORMAL'
    },
    {
      id: 'v-3',
      patientId: 'p-3',
      patientName: 'Louise Garnier',
      type: 'BLOOD_PRESSURE',
      recordedAt: '2024-04-11T08:00:00Z',
      value: 142,
      unit: 'mmHg',
      status: 'ELEVATED'
    }
  ];

  protected readonly treatmentTrackBy = (_: number, item: UpcomingTreatment) => item.id;
  protected readonly alertTrackBy = (_: number, item: AlertDto) => item.id;
  protected readonly vitalTrackBy = (_: number, item: VitalSignDto) => item.id;
}
