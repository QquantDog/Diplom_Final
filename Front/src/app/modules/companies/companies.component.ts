import { Component, OnInit, OnDestroy } from '@angular/core';
import { Store } from '@ngxs/store';
import { Observable, Subject } from 'rxjs';
import { takeUntil, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { CompaniesState } from '../../store/companies/companies.state';
import {
  LoadCompanies,
  LoadCompanyVehicles,
  RegisterToCompany,
  UnregisterFromCompany
} from '../../store/companies/companies.actions';
import { Vehicle } from '../../models/vehicle.model';
import {TaxiCompanyShort} from '../../models/taxi-company.model';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.scss'],
  standalone: false
})
//
//
//  TaxiCompanyShort -> TaxiCompany
//
//
export class CompaniesComponent implements OnInit, OnDestroy {
  companies$: Observable<TaxiCompanyShort[]> | null = null;
  selectedCompanyVehicles$: Observable<Vehicle[]> | null = null;
  loading$: Observable<boolean> | null = null;
  pagination$!: Observable<{ total: number; currentPage: number; pageSize: number }>;

  searchControl = new FormControl('');
  currentPage = 1;
  pageSize = 10;
  selectedCompanyId: string | null = null;

  private destroy$ = new Subject<void>();

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.loadCompanies();
    this.setupSearch();

    this.companies$ = this.store.select(CompaniesState.companies);
    this.selectedCompanyVehicles$ = this.store.select(CompaniesState.selectedCompanyVehicles);
    this.loading$ = this.store.select(CompaniesState.loading);
    this.pagination$ = this.store.select(CompaniesState.pagination);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private setupSearch(): void {
    this.searchControl.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.currentPage = 1;
        this.loadCompanies();
      });
  }

  private loadCompanies(): void {
    this.store.dispatch(new LoadCompanies({
      page: this.currentPage,
      limit: this.pageSize,
      search: this.searchControl.value || ''
    }));
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex + 1;
    this.pageSize = event.pageSize;
    this.loadCompanies();
  }

  onRegisterToCompany(companyId: string): void {
    this.store.dispatch(new RegisterToCompany(companyId));
  }

  onUnregisterFromCompany(companyId: string): void {
    this.store.dispatch(new UnregisterFromCompany(companyId));
  }

  onViewCompanyVehicles(companyId: string): void {
    this.selectedCompanyId = companyId;
    this.store.dispatch(new LoadCompanyVehicles(companyId));
  }

  closeVehicleModal(): void {
    this.selectedCompanyId = null;
  }

  trackByCompanyId(index: number, company: TaxiCompanyShort): string {
    return company.company_id;
  }

  trackByVehicleId(index: number, vehicle: Vehicle): string {
    return vehicle.id;
  }

  getSelectedCompany(): TaxiCompanyShort | undefined {
    const companies = this.store.selectSnapshot(CompaniesState.companies);
    return companies.find(c => c.company_id === this.selectedCompanyId);
  }
}
