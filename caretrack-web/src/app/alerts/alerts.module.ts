import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlertsRoutingModule } from './alerts-routing.module';
import { AlertsListPageComponent } from './pages/alerts-list-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [AlertsListPageComponent],
  imports: [CommonModule, SharedModule, AlertsRoutingModule]
})
export class AlertsModule {}
