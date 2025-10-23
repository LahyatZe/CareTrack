import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'ct-forgot-password-page',
  templateUrl: './forgot-password-page.component.html',
  styleUrls: ['./forgot-password-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ForgotPasswordPageComponent {
  public readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]]
  });
  protected submitted = false;

  public constructor(private readonly fb: FormBuilder) {}

  public submit(): void {
    if (this.form.invalid) {
      return;
    }

    this.submitted = true;
  }
}
