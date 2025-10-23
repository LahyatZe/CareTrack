import { ChangeDetectionStrategy, Component, Input } from '@angular/core';

@Component({
  selector: 'ct-status-chip',
  templateUrl: './status-chip.component.html',
  styleUrls: ['./status-chip.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StatusChipComponent {
  @Input() public status: string | null = null;
  @Input() public tone: 'neutral' | 'success' | 'warning' | 'danger' | 'info' = 'neutral';
  @Input() public icon?: string;
}
