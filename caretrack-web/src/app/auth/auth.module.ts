import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginPageComponent } from './pages/login-page.component';
import { ForgotPasswordPageComponent } from './pages/forgot-password-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [LoginPageComponent, ForgotPasswordPageComponent],
  imports: [CommonModule, ReactiveFormsModule, SharedModule, AuthRoutingModule]
})
export class AuthModule {}
