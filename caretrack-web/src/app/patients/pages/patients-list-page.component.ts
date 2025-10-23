import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  TemplateRef,
  ViewChild,
  ChangeDetectorRef,
  inject
} from '@angular/core';

import { PatientDto } from '../../shared/models/patient.model';
import { TableColumn } from '../../shared/components/data-table/data-table.component';

interface PatientTableView extends PatientDto {
  age: number;
  lastVital: string;
  nextAppointment: string;
}

@Component({
  selector: 'ct-patients-list-page',
  templateUrl: './patients-list-page.component.html',
  styleUrls: ['./patients-list-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PatientsListPageComponent implements AfterViewInit {
  @ViewChild('actionsColumn', { static: true })
  private actionsTemplate?: TemplateRef<unknown>;

  private readonly cdr = inject(ChangeDetectorRef);

  protected readonly patients: PatientTableView[] = [
    {
      id: 'p-1',
      firstName: 'Claire',
      lastName: 'Lambert',
      dateOfBirth: '1986-03-12',
      gender: 'FEMALE',
      phoneNumber: '06 44 90 12 67',
      email: 'claire.lambert@exemple.fr',
      age: 38,
      lastVital: 'TA 142/92 • 08:10',
      nextAppointment: 'Suivi cardio • 12 avr. 09h'
    },
    {
      id: 'p-2',
      firstName: 'Hugo',
      lastName: 'Morel',
      dateOfBirth: '1994-10-02',
      gender: 'MALE',
      phoneNumber: '06 51 88 03 78',
      email: 'hugo.morel@exemple.fr',
      age: 29,
      lastVital: 'SpO₂ 97% • 08:05',
      nextAppointment: 'Kinésithérapie • 11 avr. 11h'
    },
    {
      id: 'p-3',
      firstName: 'Louise',
      lastName: 'Garnier',
      dateOfBirth: '1972-07-21',
      gender: 'FEMALE',
      phoneNumber: '06 77 90 18 45',
      email: 'louise.garnier@exemple.fr',
      age: 51,
      lastVital: 'Temp 37.8°C • 07:55',
      nextAppointment: 'Pansement • 11 avr. 11h15'
    }
  ];

  protected columns: TableColumn<PatientTableView>[] = [];
  protected readonly trackByPatient = (_: number, patient: PatientDto) => patient.id;

  public ngAfterViewInit(): void {
    this.columns = [
      { key: 'lastName', header: 'Nom' },
      { key: 'firstName', header: 'Prénom' },
      { key: 'age', header: 'Âge', align: 'center' },
      { key: 'lastVital', header: 'Dernière constante' },
      { key: 'nextAppointment', header: 'Prochain soin' },
      { key: 'actions', header: '', width: '7rem', template: this.actionsTemplate }
    ];

    this.cdr.detectChanges();
  }
}
