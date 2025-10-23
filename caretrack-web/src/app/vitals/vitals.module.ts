import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VitalsRoutingModule } from './vitals-routing.module';
import { VitalsListPageComponent } from './pages/vitals-list-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [VitalsListPageComponent],
  imports: [CommonModule, SharedModule, VitalsRoutingModule]
})
export class VitalsModule {}
