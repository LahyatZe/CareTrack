import { Pipe, PipeTransform } from '@angular/core';

const STATUS_DICTIONARY: Record<string, string> = {
  ACTIVE: 'Actif',
  COMPLETED: 'Terminé',
  UPCOMING: 'À venir',
  OVERDUE: 'En retard',
  PENDING: 'En attente',
  ACKNOWLEDGED: 'Reconnu',
  RESOLVED: 'Résolu',
  CRITICAL: 'Critique',
  NORMAL: 'Normal',
  ELEVATED: 'Élevé',
  TAKEN: 'Confirmé',
  MISSED: 'Manqué'
};

@Pipe({ name: 'statusLabel', standalone: false })
export class StatusLabelPipe implements PipeTransform {
  public transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }

    const normalized = value.toUpperCase();
    return STATUS_DICTIONARY[normalized] ?? value;
  }
}
