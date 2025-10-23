import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MedicationsRoutingModule } from './medications-routing.module';
import { MedicationsListPageComponent } from './pages/medications-list-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [MedicationsListPageComponent],
  imports: [CommonModule, SharedModule, MedicationsRoutingModule]
})
export class MedicationsModule {}
