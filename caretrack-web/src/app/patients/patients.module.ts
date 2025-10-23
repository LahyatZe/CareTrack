import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientsRoutingModule } from './patients-routing.module';
import { PatientsListPageComponent } from './pages/patients-list-page.component';
import { PatientDetailPageComponent } from './pages/patient-detail-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [PatientsListPageComponent, PatientDetailPageComponent],
  imports: [CommonModule, SharedModule, PatientsRoutingModule]
})
export class PatientsModule {}
