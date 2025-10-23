import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';

interface MedicationSchedule {
  id: string;
  patient: string;
  medication: string;
  dose: string;
  scheduledAt: string;
  status: 'PENDING' | 'TAKEN' | 'MISSED';
  lastConfirmedAt?: string;
}

@Component({
  selector: 'ct-medications-list-page',
  templateUrl: './medications-list-page.component.html',
  styleUrls: ['./medications-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MedicationsListPageComponent {
  private readonly medicationsSignal = signal<MedicationSchedule[]>([
    {
      id: 'med-1',
      patient: 'Claire Lambert',
      medication: 'Bisoprolol',
      dose: '5 mg',
      scheduledAt: '11 avr. • 09h00',
      status: 'PENDING'
    },
    {
      id: 'med-2',
      patient: 'Hugo Morel',
      medication: 'Salbutamol',
      dose: '2 bouffées',
      scheduledAt: '11 avr. • 08h30',
      status: 'TAKEN',
      lastConfirmedAt: '11 avr. • 08h35'
    },
    {
      id: 'med-3',
      patient: 'Louise Garnier',
      medication: 'Apixaban',
      dose: '2,5 mg',
      scheduledAt: '11 avr. • 07h30',
      status: 'MISSED'
    }
  ]);

  protected readonly medications = this.medicationsSignal.asReadonly();
  protected readonly pendingCount = computed(() => this.medicationsSignal().filter((m) => m.status === 'PENDING').length);
  protected readonly takenToday = computed(() => this.medicationsSignal().filter((m) => m.status === 'TAKEN').length);

  protected confirmIntake(id: string): void {
    this.medicationsSignal.update((items) =>
      items.map((item) =>
        item.id === id
          ? {
              ...item,
              status: 'TAKEN',
              lastConfirmedAt: new Date().toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })
            }
          : item
      )
    );
  }
}
