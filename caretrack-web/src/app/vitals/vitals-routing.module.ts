import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { VitalsListPageComponent } from './pages/vitals-list-page.component';

const routes: Routes = [{ path: '', component: VitalsListPageComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VitalsRoutingModule {}
