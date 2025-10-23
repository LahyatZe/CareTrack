import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PatientsListPageComponent } from './pages/patients-list-page.component';

const routes: Routes = [{ path: '', component: PatientsListPageComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientsRoutingModule {}
