import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'ct-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardPageComponent {}
