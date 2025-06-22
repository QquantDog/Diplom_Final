import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { DriverGuard } from './guards/driver.guard';

const routes: Routes = [
  { path: '', redirectTo: '/rides', pathMatch: 'full' },
  {
    path: 'auth',
    loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'rides',
    loadChildren: () => import('./modules/rides/rides.module').then(m => m.RidesModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'profile',
    loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'shifts',
    loadChildren: () => import('./modules/shifts/shifts.module').then(m => m.ShiftsModule),
    canActivate: [AuthGuard, DriverGuard]
  },
  // {
  //   path: 'companies',
  //   loadChildren: () => import('./modules/companies/companies.module').then(m => m.CompaniesModule),
  //   canActivate: [AuthGuard, DriverGuard]
  // },
  { path: '**', redirectTo: '/rides' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    enableTracing: false,
    scrollPositionRestoration: 'top'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
