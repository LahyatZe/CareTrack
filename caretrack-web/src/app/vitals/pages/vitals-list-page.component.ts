import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { VitalSignDto } from '../../shared/models/vital.model';

interface VitalTrend {
  label: string;
  unit: string;
  color: string;
  measurements: number[];
  timestamps: string[];
}

@Component({
  selector: 'ct-vitals-list-page',
  templateUrl: './vitals-list-page.component.html',
  styleUrls: ['./vitals-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class VitalsListPageComponent {
  private readonly vitalsSignal = signal<VitalSignDto[]>([
    {
      id: 'v-1',
      patientId: 'p-1',
      patientName: 'Claire Lambert',
      type: 'HEART_RATE',
      value: 110,
      unit: 'bpm',
      recordedAt: '2024-04-11T08:10:00Z',
      status: 'ELEVATED'
    },
    {
      id: 'v-2',
      patientId: 'p-1',
      patientName: 'Claire Lambert',
      type: 'SPO2',
      value: 96,
      unit: '%',
      recordedAt: '2024-04-11T08:05:00Z',
      status: 'NORMAL'
    },
    {
      id: 'v-3',
      patientId: 'p-2',
      patientName: 'Hugo Morel',
      type: 'RESPIRATORY_RATE',
      value: 20,
      unit: 'rpm',
      recordedAt: '2024-04-11T08:02:00Z',
      status: 'NORMAL'
    }
  ]);

  protected readonly trends: VitalTrend[] = [
    {
      label: 'Fréquence cardiaque',
      unit: 'bpm',
      color: '#ef4444',
      measurements: [98, 103, 108, 110, 105],
      timestamps: ['06h', '07h', '08h', '09h', '10h']
    },
    {
      label: 'SpO₂',
      unit: '%',
      color: '#22c55e',
      measurements: [95, 96, 97, 96, 95],
      timestamps: ['06h', '07h', '08h', '09h', '10h']
    }
  ];

  protected readonly vitalForm = this.fb.nonNullable.group({
    patientName: ['', Validators.required],
    type: ['HEART_RATE', Validators.required],
    value: [null as number | null, [Validators.required, Validators.min(0)]],
    unit: ['', Validators.required],
    recordedAt: [new Date().toISOString().slice(0, 16), Validators.required]
  });

  public constructor(private readonly fb: FormBuilder) {}

  protected get vitals(): VitalSignDto[] {
    return this.vitalsSignal();
  }

  protected chartPath(trend: VitalTrend, width = 280, height = 140): string {
    const { measurements } = trend;
    const max = Math.max(...measurements);
    const min = Math.min(...measurements);
    const range = max - min || 1;
    const stepX = width / Math.max(1, measurements.length - 1);

    return measurements
      .map((value, index) => {
        const x = index * stepX;
        const y = height - ((value - min) / range) * height;
        return `${x},${y}`;
      })
      .join(' ');
  }

  protected submitVital(): void {
    if (this.vitalForm.invalid) {
      this.vitalForm.markAllAsTouched();
      return;
    }

    const value = this.vitalForm.getRawValue();
    this.vitalsSignal.update((current) => [
      {
        id: `v-${Math.random().toString(36).slice(2, 7)}`,
        patientId: 'temp',
        patientName: value.patientName,
        type: value.type as VitalSignDto['type'],
        value: value.value ?? 0,
        unit: value.unit,
        recordedAt: new Date(value.recordedAt).toISOString(),
        status: 'NORMAL'
      },
      ...current
    ]);

    this.vitalForm.reset({
      patientName: '',
      type: 'HEART_RATE',
      value: null,
      unit: '',
      recordedAt: new Date().toISOString().slice(0, 16)
    });
  }
}
