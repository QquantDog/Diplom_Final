import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { ShiftsComponent } from './shifts.component';
import {MatChip} from '@angular/material/chips';

const routes: Routes = [
  {
    path: '',
    component: ShiftsComponent
  }
];

@NgModule({
  declarations: [
    ShiftsComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes),
    MatChip
  ]
})
export class ShiftsModule {}
