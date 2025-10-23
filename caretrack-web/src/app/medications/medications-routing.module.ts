import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MedicationsListPageComponent } from './pages/medications-list-page.component';

const routes: Routes = [{ path: '', component: MedicationsListPageComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicationsRoutingModule {}
