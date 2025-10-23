import { ChangeDetectionStrategy, Component, Input, TemplateRef } from '@angular/core';

export interface TableColumn<T> {
  key: keyof T | string;
  header: string;
  width?: string;
  align?: 'start' | 'center' | 'end';
  template?: TemplateRef<unknown>;
}

@Component({
  selector: 'ct-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DataTableComponent<T extends Record<string, unknown>> {
  @Input() public columns: TableColumn<T>[] = [];
  @Input() public data: T[] = [];
  @Input() public trackBy?: (index: number, item: T) => unknown;
  @Input() public emptyState = 'Aucune donnée disponible';

  public getCellValue(row: T, column: TableColumn<T>): unknown {
    return column.key in row ? (row as Record<string, unknown>)[column.key as string] : '';
  }
}
