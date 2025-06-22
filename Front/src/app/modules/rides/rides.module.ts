import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { RidesComponent } from './rides.component';
import {MatChip} from '@angular/material/chips';

const routes: Routes = [
  {
    path: '',
    component: RidesComponent
  }
];

@NgModule({
  declarations: [
    RidesComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes),
    MatChip
  ]
})
export class RidesModule {}
