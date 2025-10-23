import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, UrlTree } from '@angular/router';

import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  public constructor(private readonly authService: AuthService, private readonly router: Router) {}

  public canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree {
    const allowedRoles = route.data['roles'] as string[] | undefined;
    const profile = this.authService.profile();

    if (!allowedRoles || !allowedRoles.length) {
      return true;
    }

    if (profile && allowedRoles.includes(profile.role)) {
      return true;
    }

    return this.router.createUrlTree(['/dashboard']);
  }
}
