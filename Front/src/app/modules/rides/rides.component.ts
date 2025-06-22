import { Component, OnInit, OnDestroy } from '@angular/core';
import { Store } from '@ngxs/store';
import { Observable, Subject } from 'rxjs';
import {takeUntil, debounceTime, distinctUntilChanged, take, tap} from 'rxjs/operators';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { RidesState } from '../../store/rides/rides.state';
import { AuthState } from '../../store/auth/auth.state';
import {
  LoadRides,
  SetRideFilters,
  ClearRideFilters,
  AcceptRide,
  CompleteRide,
  CancelRide, UpdatePagination
} from '../../store/rides/rides.actions';
import {Ride, RideFilters} from '../../models/ride/ride.model';

@Component({
  selector: 'app-rides',
  templateUrl: './rides.component.html',
  styleUrls: ['./rides.component.scss'],
  standalone: false
})
export class RidesComponent implements OnInit, OnDestroy {
  rides$!: Observable<Ride[]>;
  loading$!: Observable<boolean>;
  pagination$!: Observable<{ total: number; currentPage: number; pageSize: number }>;
  filters$!: Observable<RideFilters>;
  hasActiveFilters$!: Observable<boolean>;
  isDriver$!: Observable<boolean>;

  searchForm: FormGroup;
  filterForm: FormGroup;
  showFilters = false;
  currentPage = 1;
  pageSize = 10;

  statusOptions = [
    { value: 'PENDING', label: 'Pending' },
    { value: 'ACCEPTED', label: 'Accepted' },
    { value: 'IN_PROGRESS', label: 'In Progress' },
    { value: 'COMPLETED', label: 'Completed' },
    { value: 'CANCELLED', label: 'Cancelled' }
  ];

  paymentMethodOptions = [
    { value: 'cash', label: 'Cash' },
    { value: 'card', label: 'Card' },
    { value: 'wallet', label: 'Wallet' },
    { value: 'promo', label: 'Promo' }
  ];

  private destroy$ = new Subject<void>();

  constructor(
    private store: Store,
    private fb: FormBuilder
  ) {
    this.searchForm = this.fb.group({
      search: ['']
    });

    this.filterForm = this.fb.group({
      minPrice: [''],
      maxPrice: [''],
      startTimeFrom: [''],
      startTimeTo: [''],
      endTimeFrom: [''],
      endTimeTo: [''],
      minDistance: [''],
      maxDistance: [''],
      status: [[]],
      minTip: [''],
      maxTip: [''],
      paymentMethod: [[]]
    });
  }

  ngOnInit(): void {
    this.rides$ = this.store.select(RidesState.rides);
    this.loading$ = this.store.select(RidesState.loading);
    this.pagination$ = this.store.select(RidesState.pagination);
    this.filters$ = this.store.select(RidesState.filters);
    this.hasActiveFilters$ = this.store.select(RidesState.hasActiveFilters);
    this.isDriver$ = this.store.select(AuthState.isDriver);

    this.loadRides();
    this.setupSearch();
    this.loadExistingFilters();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private setupSearch(): void {
    this.searchForm.get('search')?.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.currentPage = 1;
        this.loadRides();
      });
  }

  private loadExistingFilters(): void {
    this.filters$.pipe(takeUntil(this.destroy$)).subscribe(filters => {
      this.filterForm.patchValue(filters, { emitEvent: false });
    });
  }

  private loadRides(): void {
    // const searchValue = this.searchForm.get('search')?.value || '';
    const filters = this.store.selectSnapshot(RidesState.filters);
    console.warn(this.currentPage)
    console.warn(this.pageSize)

    this.store.dispatch(new LoadRides({
      page: this.currentPage,
      limit: this.pageSize,
      filters: filters
    }));
  }

  onPageChange(event: PageEvent): void {
    const pb: PageBundle = {
      currentPage: event.pageIndex + 1,
      pageSize: event.pageSize,
    }
    this.pageSize = pb.pageSize;
    this.currentPage = pb.currentPage;
    this.store.dispatch(new UpdatePagination(pb));
    this.loadRides();
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }

  onSearch(): void {
    const filters = this.buildFilters();
    this.store.dispatch(new SetRideFilters(filters));
    this.currentPage = 1;
    this.loadRides();
  }

  onClearFilters(): void {
    this.filterForm.reset();
    this.store.dispatch(new ClearRideFilters());
    this.currentPage = 1;
    this.loadRides();
  }

  private buildFilters(): RideFilters {
    const formValue = this.filterForm.value;
    const filters: RideFilters = {};

    if (formValue.minPrice) filters.minPrice = formValue.minPrice;
    if (formValue.maxPrice) filters.maxPrice = formValue.maxPrice;
    if (formValue.startTimeFrom) filters.startTimeFrom = formValue.startTimeFrom;
    if (formValue.startTimeTo) filters.startTimeTo = formValue.startTimeTo;
    if (formValue.endTimeFrom) filters.endTimeFrom = formValue.endTimeFrom;
    if (formValue.endTimeTo) filters.endTimeTo = formValue.endTimeTo;
    if (formValue.minDistance) filters.minDistance = formValue.minDistance;
    if (formValue.maxDistance) filters.maxDistance = formValue.maxDistance;
    if (formValue.status && formValue.status.length > 0) filters.status = formValue.status;
    if (formValue.minTip) filters.minTip = formValue.minTip;
    if (formValue.maxTip) filters.maxTip = formValue.maxTip;
    if (formValue.paymentMethod && formValue.paymentMethod.length > 0) filters.paymentMethod = formValue.paymentMethod;

    return filters;
  }

  onAcceptRide(rideId: number): void {
    this.store.dispatch(new AcceptRide(rideId));
  }

  onCompleteRide(rideId: number): void {
    this.store.dispatch(new CompleteRide(rideId));
  }

  onCancelRide(rideId: number): void {
    this.store.dispatch(new CancelRide(rideId));
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'PENDING': return 'warn';
      case 'ACCEPTED': return 'accent';
      case 'IN_PROGRESS': return 'primary';
      case 'COMPLETED': return 'primary';
      case 'CANCELLED': return '';
      default: return '';
    }
  }

  // canAcceptRide(ride: Ride): boolean {
  //   return ride.status === 'PENDING';
  // }
  //
  // canCompleteRide(ride: Ride): boolean {
  //   return ride.status === 'ACCEPTED' || ride.status === 'IN_PROGRESS';
  // }
  //
  // canCancelRide(ride: Ride): boolean {
  //   return ride.status === 'PENDING' || ride.status === 'ACCEPTED';
  // }

  formatCoordinates(point: any): string {
    if (!point || !point.coordinates) return 'N/A';
    const [lng, lat] = point.coordinates;
    return `${lat.toFixed(4)}, ${lng.toFixed(4)}`;
  }

  formatDistance(meters: number | null): string {
    if (!meters) return 'N/A';
    if (meters < 1000) return `${meters}m`;
    return `${(meters / 1000).toFixed(2)}km`;
  }

  formatPrice(price: number | string | null): string {
    if (!price) return 'N/A';
    return `$${price}`;
  }

  formatDuration(startTime: string | null, endTime: string | null): string {
    if (!startTime || !endTime) return 'N/A';

    const start = new Date(startTime).getTime();
    const end = new Date(endTime).getTime();
    const diffMinutes = Math.floor((end - start) / 60000);

    if (diffMinutes < 60) return `${diffMinutes}min`;

    const hours = Math.floor(diffMinutes / 60);
    const minutes = diffMinutes % 60;
    return `${hours}h ${minutes}min`;
  }

  formatWaitingTime(seconds: number): string {
    if (seconds < 60) return `${seconds}s`;

    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;

    if (minutes < 60) {
      return remainingSeconds > 0 ? `${minutes}m ${remainingSeconds}s` : `${minutes}m`;
    }

    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;
    return `${hours}h ${remainingMinutes}m`;
  }

  // trackByRideId(index: number, ride: Ride): number {
  //   return ride.id;
  // }
}

export interface PageBundle{
  currentPage: number;
  pageSize: number;
}
