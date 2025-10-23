import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CarePlanDto } from '../../shared/models/care-plan.model';
import { VitalSignDto } from '../../shared/models/vital.model';
import { AlertDto } from '../../shared/models/alert.model';

interface PatientSummary {
  id: string;
  fullName: string;
  age: number;
  gender: string;
  diagnosis: string;
  primaryPhysician: string;
}

@Component({
  selector: 'ct-patient-detail-page',
  templateUrl: './patient-detail-page.component.html',
  styleUrls: ['./patient-detail-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PatientDetailPageComponent {
  protected readonly patient: PatientSummary;
  protected readonly plans: CarePlanDto[];
  protected readonly vitals: VitalSignDto[];
  protected readonly alerts: AlertDto[];
  protected activeTab: 'plans' | 'vitals' | 'alerts' = 'plans';

  public constructor(route: ActivatedRoute) {
    const patientId = route.snapshot.paramMap.get('id') ?? 'p-1';

    const patientDirectory: Record<string, PatientSummary> = {
      'p-1': {
        id: 'p-1',
        fullName: 'Claire Lambert',
        age: 38,
        gender: 'Femme',
        diagnosis: 'Insuffisance cardiaque modérée',
        primaryPhysician: 'Dr. Sophie Bernard'
      },
      'p-2': {
        id: 'p-2',
        fullName: 'Hugo Morel',
        age: 29,
        gender: 'Homme',
        diagnosis: 'Asthme sévère',
        primaryPhysician: 'Dr. Marc Petit'
      },
      'p-3': {
        id: 'p-3',
        fullName: 'Louise Garnier',
        age: 51,
        gender: 'Femme',
        diagnosis: 'Post-opération arthroplastie hanche',
        primaryPhysician: 'Dr. Clara Nguyen'
      }
    };

    this.patient = patientDirectory[patientId] ?? patientDirectory['p-1'];

    this.plans = [
      {
        id: 'cp-1',
        name: 'Programme cardio niveau 2',
        description: 'Optimisation du traitement médicamenteux et surveillance rapprochée.',
        status: 'ACTIVE',
        startDate: '2024-03-28',
        endDate: undefined,
        patientId: this.patient.id,
        steps: [
          {
            id: 'step-1',
            title: 'Bilan biologique',
            description: 'NFS + Ionogramme + BNP',
            dueDate: '2024-04-12',
            status: 'UPCOMING'
          },
          {
            id: 'step-2',
            title: 'Consultation cardiologie',
            description: 'Suivi hebdomadaire avec cardio',
            dueDate: '2024-04-15',
            status: 'UPCOMING'
          }
        ]
      }
    ];

    this.vitals = [
      {
        id: 'v-1',
        patientId: this.patient.id,
        patientName: this.patient.fullName,
        type: 'HEART_RATE',
        recordedAt: '2024-04-11T08:10:00Z',
        value: 112,
        unit: 'bpm',
        status: 'ELEVATED'
      },
      {
        id: 'v-2',
        patientId: this.patient.id,
        patientName: this.patient.fullName,
        type: 'BLOOD_PRESSURE',
        recordedAt: '2024-04-10T20:05:00Z',
        value: 138,
        unit: 'mmHg',
        status: 'ELEVATED'
      }
    ];

    this.alerts = [
      {
        id: 'al-1',
        patientId: this.patient.id,
        patientName: this.patient.fullName,
        message: 'Suivi tension artérielle : tendance haussière sur 24h',
        createdAt: '2024-04-11T07:35:00Z',
        status: 'ACKNOWLEDGED',
        type: 'VITAL_SIGN'
      }
    ];
  }

  protected selectTab(tab: 'plans' | 'vitals' | 'alerts'): void {
    this.activeTab = tab;
  }
}
