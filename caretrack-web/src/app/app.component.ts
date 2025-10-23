import { ChangeDetectionStrategy, Component } from '@angular/core';

import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'ct-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {
  protected title = 'CareTrack';

  public constructor(private readonly authService: AuthService) {}

  public logout(): void {
    this.authService.logout();
  }
}
