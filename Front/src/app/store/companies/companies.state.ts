import { Injectable } from '@angular/core';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { CompaniesService } from '../../services/companies.service';
import { Vehicle } from '../../models/vehicle.model';
import {
  LoadCompanies,
  LoadCompanyVehicles,
  RegisterToCompany,
  UnregisterFromCompany
} from './companies.actions';
import {TaxiCompanyShort} from '../../models/taxi-company.model';

export interface CompaniesStateModel {
  companies: TaxiCompanyShort[];
  selectedCompanyVehicles: Vehicle[];
  loading: boolean;
  total: number;
  currentPage: number;
  pageSize: number;
  searchTerm: string;
  error: string | null;
}

@State<CompaniesStateModel>({
  name: 'companies',
  defaults: {
    companies: [],
    selectedCompanyVehicles: [],
    loading: false,
    total: 0,
    currentPage: 1,
    pageSize: 10,
    searchTerm: '',
    error: null
  }
})
@Injectable()
export class CompaniesState {
  constructor(private companiesService: CompaniesService) {}

  @Selector()
  static companies(state: CompaniesStateModel): TaxiCompanyShort[] {
    return state.companies;
  }

  @Selector()
  static selectedCompanyVehicles(state: CompaniesStateModel): Vehicle[] {
    return state.selectedCompanyVehicles;
  }

  @Selector()
  static loading(state: CompaniesStateModel): boolean {
    return state.loading;
  }

  @Selector()
  static pagination(state: CompaniesStateModel) {
    return {
      total: state.total,
      currentPage: state.currentPage,
      pageSize: state.pageSize
    };
  }

  @Action(LoadCompanies)
  loadCompanies(ctx: StateContext<CompaniesStateModel>, action: LoadCompanies) {
    ctx.patchState({
      loading: true,
      error: null,
      currentPage: action.params.page,
      searchTerm: action.params.search || ''
    });

    return this.companiesService.getCompanies(action.params).pipe(
      tap((response) => {
        ctx.patchState({
          companies: response.data,
          total: response.total || 0,
          loading: false
        });
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to load companies'
        });
        return throwError(() => error);
      })
    );
  }

  @Action(LoadCompanyVehicles)
  loadCompanyVehicles(ctx: StateContext<CompaniesStateModel>, action: LoadCompanyVehicles) {
    ctx.patchState({ loading: true, error: null });

    return this.companiesService.getCompanyVehicles(action.companyId).pipe(
      tap((vehicles) => {
        ctx.patchState({
          selectedCompanyVehicles: vehicles,
          loading: false
        });
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to load company vehicles'
        });
        return throwError(() => error);
      })
    );
  }

  // @Action(LoadAvailableVehicles)
  // loadAvailableVehicles(ctx: StateContext<CompaniesStateModel>) {
  //   return this.companiesService.getAvailableVehicles().pipe(
  //     tap((vehicles: Vehicle[]) => {
  //       // ctx.patchState({
  //       //   selectedCompanyVehicles: vehicles,
  //       //   loading: false
  //       // });
  //     }),
  //     catchError((error) => {
  //       ctx.patchState({
  //         loading: false,
  //         error: error.error?.message || 'Failed to load company vehicles'
  //       });
  //       return throwError(() => error);
  //     })
  //   );
  // }

  @Action(RegisterToCompany)
  registerToCompany(ctx: StateContext<CompaniesStateModel>, action: RegisterToCompany) {
    return this.companiesService.registerToCompany(action.companyId).pipe(
      tap(() => {
        const state = ctx.getState();
        const companies = state.companies.map(company =>
          company.company_id === action.companyId
            ? { ...company, isRegistered: true }
            : company
        );
        ctx.patchState({ companies });
      })
    );
  }

  @Action(UnregisterFromCompany)
  unregisterFromCompany(ctx: StateContext<CompaniesStateModel>, action: UnregisterFromCompany) {
    return this.companiesService.unregisterFromCompany(action.companyId).pipe(
      tap(() => {
        const state = ctx.getState();
        const companies = state.companies.map(company =>
          company.company_id === action.companyId
            ? { ...company, isRegistered: false }
            : company
        );
        ctx.patchState({ companies });
      })
    );
  }
}
