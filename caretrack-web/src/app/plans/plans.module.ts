import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { PlansRoutingModule } from './plans-routing.module';
import { PlansListPageComponent } from './pages/plans-list-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [PlansListPageComponent],
  imports: [CommonModule, ReactiveFormsModule, SharedModule, PlansRoutingModule]
})
export class PlansModule {}
