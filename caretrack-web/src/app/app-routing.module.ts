import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule)
  },
  {
    path: 'dashboard',
    canActivate: [AuthGuard],
    loadChildren: () => import('./dashboard/dashboard.module').then((m) => m.DashboardModule)
  },
  {
    path: 'patients',
    canActivate: [AuthGuard],
    loadChildren: () => import('./patients/patients.module').then((m) => m.PatientsModule)
  },
  {
    path: 'plans',
    canActivate: [AuthGuard],
    loadChildren: () => import('./plans/plans.module').then((m) => m.PlansModule)
  },
  {
    path: 'medications',
    canActivate: [AuthGuard],
    loadChildren: () => import('./medications/medications.module').then((m) => m.MedicationsModule)
  },
  {
    path: 'vitals',
    canActivate: [AuthGuard],
    loadChildren: () => import('./vitals/vitals.module').then((m) => m.VitalsModule)
  },
  {
    path: 'alerts',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ADMIN', 'CLINICIAN'] },
    loadChildren: () => import('./alerts/alerts.module').then((m) => m.AlertsModule)
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard'
  },
  {
    path: '**',
    redirectTo: 'dashboard'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
