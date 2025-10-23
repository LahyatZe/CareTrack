import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { CardComponent } from './components/card/card.component';
import { DataTableComponent } from './components/data-table/data-table.component';
import { StatusChipComponent } from './components/status-chip/status-chip.component';
import { StatusLabelPipe } from './pipes/status-label.pipe';
import { AccessibleLabelDirective } from './directives/accessible-label.directive';

@NgModule({
  declarations: [
    ToolbarComponent,
    CardComponent,
    DataTableComponent,
    StatusChipComponent,
    StatusLabelPipe,
    AccessibleLabelDirective
  ],
  imports: [CommonModule, RouterModule],
  exports: [
    CommonModule,
    RouterModule,
    ToolbarComponent,
    CardComponent,
    DataTableComponent,
    StatusChipComponent,
    StatusLabelPipe,
    AccessibleLabelDirective
  ]
})
export class SharedModule {}
