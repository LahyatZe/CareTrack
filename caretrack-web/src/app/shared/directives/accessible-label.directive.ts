import { Directive, ElementRef, HostBinding, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[ctAccessibleLabel]'
})
export class AccessibleLabelDirective {
  @HostBinding('attr.tabindex')
  public tabindex = 0;

  @HostBinding('attr.role')
  public role = 'button';

  private label = '';

  public constructor(private readonly elementRef: ElementRef<HTMLElement>) {}

  @HostBinding('attr.aria-label')
  public get ariaLabel(): string {
    return this.label;
  }

  @Input()
  public set ctAccessibleLabel(value: string) {
    this.label = value;
  }

  @HostListener('keydown.enter')
  @HostListener('keydown.space')
  public triggerClick(): void {
    const element = this.elementRef.nativeElement;
    if (element.tagName === 'BUTTON') {
      return;
    }

    element.click();
  }
}
