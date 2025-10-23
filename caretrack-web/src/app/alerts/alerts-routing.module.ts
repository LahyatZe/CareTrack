import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AlertsListPageComponent } from './pages/alerts-list-page.component';

const routes: Routes = [{ path: '', component: AlertsListPageComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AlertsRoutingModule {}
