import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { CarePlanDto, CarePlanStepDto } from '../../shared/models/care-plan.model';

@Component({
  selector: 'ct-plans-list-page',
  templateUrl: './plans-list-page.component.html',
  styleUrls: ['./plans-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PlansListPageComponent {
  private readonly plansSignal = signal<CarePlanDto[]>([
    {
      id: 'plan-1',
      name: 'Réhabilitation post-infarctus',
      description: 'Programme structuré sur 6 semaines pour réhabiliter la fonction cardiaque.',
      status: 'ACTIVE',
      startDate: '2024-03-15',
      patientId: 'p-1',
      steps: [
        {
          id: 'step-1',
          title: 'Consultation diététique',
          description: 'Adapter le régime alimentaire',
          dueDate: '2024-04-12',
          status: 'UPCOMING'
        },
        {
          id: 'step-2',
          title: 'Test d’effort',
          description: 'Contrôle de la tolérance à l’effort',
          dueDate: '2024-04-18',
          status: 'UPCOMING'
        }
      ]
    },
    {
      id: 'plan-2',
      name: 'Suivi insuffisance respiratoire',
      description: 'Optimisation du traitement et séances de kinésithérapie respiratoire.',
      status: 'ACTIVE',
      startDate: '2024-03-28',
      patientId: 'p-2',
      steps: [
        {
          id: 'step-3',
          title: 'Contrôle gazométrie',
          description: 'Analyse des gaz sanguins artériels',
          dueDate: '2024-04-13',
          status: 'UPCOMING'
        }
      ]
    }
  ]);

  protected readonly patients = [
    { id: 'p-1', label: 'Claire Lambert' },
    { id: 'p-2', label: 'Hugo Morel' },
    { id: 'p-3', label: 'Louise Garnier' }
  ];

  protected readonly selectedPlanId = signal<string>('plan-1');
  protected readonly plans = this.plansSignal.asReadonly();
  protected readonly selectedPlan = computed(() =>
    this.plansSignal().find((plan) => plan.id === this.selectedPlanId()) ?? this.plansSignal()[0]
  );

  protected readonly planForm = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    description: ['', [Validators.required, Validators.minLength(10)]],
    patientId: ['', Validators.required]
  });

  protected readonly stepForm = this.fb.nonNullable.group({
    title: ['', Validators.required],
    description: ['', Validators.required],
    dueDate: ['', Validators.required]
  });

  protected readonly calendar = computed(() =>
    this.plansSignal()
      .flatMap((plan) =>
        plan.steps.map((step) => ({
          planId: plan.id,
          planName: plan.name,
          label: step.title,
          date: step.dueDate
        }))
      )
      .sort((a, b) => a.date.localeCompare(b.date))
  );

  public constructor(private readonly fb: FormBuilder) {}

  protected selectPlan(planId: string): void {
    this.selectedPlanId.set(planId);
  }

  protected createPlan(): void {
    if (this.planForm.invalid) {
      this.planForm.markAllAsTouched();
      return;
    }

    const value = this.planForm.getRawValue();
    const newPlan: CarePlanDto = {
      id: `plan-${Math.random().toString(36).slice(2, 7)}`,
      name: value.name,
      description: value.description,
      status: 'ACTIVE',
      startDate: new Date().toISOString(),
      patientId: value.patientId,
      steps: []
    };

    this.plansSignal.update((plans) => [newPlan, ...plans]);
    this.selectedPlanId.set(newPlan.id);
    this.planForm.reset();
  }

  protected addStep(): void {
    if (this.stepForm.invalid || !this.selectedPlan()) {
      this.stepForm.markAllAsTouched();
      return;
    }

    const value = this.stepForm.getRawValue();
    const newStep: CarePlanStepDto = {
      id: `step-${Math.random().toString(36).slice(2, 7)}`,
      title: value.title,
      description: value.description,
      dueDate: value.dueDate,
      status: 'UPCOMING'
    };

    this.plansSignal.update((plans) =>
      plans.map((plan) =>
        plan.id === this.selectedPlanId()
          ? { ...plan, steps: [...plan.steps, newStep] }
          : plan
      )
    );

    this.stepForm.reset();
  }
}
