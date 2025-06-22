import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { CompaniesComponent } from './companies.component';
import {MatChip} from '@angular/material/chips';

const routes: Routes = [
  {
    path: '',
    component: CompaniesComponent
  }
];

@NgModule({
  declarations: [
    CompaniesComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes),
    MatChip
  ]
})
export class CompaniesModule {}
