import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PlansListPageComponent } from './pages/plans-list-page.component';

const routes: Routes = [{ path: '', component: PlansListPageComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlansRoutingModule {}
