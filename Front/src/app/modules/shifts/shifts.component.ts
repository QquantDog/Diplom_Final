import { Component, OnInit, OnDestroy } from '@angular/core';
import { Store } from '@ngxs/store';
import { Observable, Subject, interval } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { ShiftsState } from '../../store/shifts/shifts.state';
import {
  LoadCurrentShift,
  LoadShiftHistory,
  LoadAvailableCabs,
  StartShift,
  EndShift
} from '../../store/shifts/shifts.actions';
import { Shift } from '../../models/shift/shift.model';
import { Vehicle } from '../../models/vehicle.model';
import {Cab} from '../../models/cab.model';
import {LoadCities} from '../../store/cities/city.action';
import {City} from '../../models/city.model';
import {CityState} from '../../store/cities/city.state';

@Component({
  selector: 'app-shifts',
  templateUrl: './shifts.component.html',
  styleUrls: ['./shifts.component.scss'],
  standalone: false
})
export class ShiftsComponent implements OnInit, OnDestroy {
  currentShift$!: Observable<Shift | null>;
  shiftHistory$!: Observable<Shift[]>;
  availableCabs$!: Observable<Cab[]>;
  cities$!: Observable<City[]>;
  loading$!: Observable<boolean>;

  showVehicleSelection = false;
  shiftDuration$ = new Observable<string>();

  private destroy$ = new Subject<void>();

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.loadData();
    this.setupShiftTimer();

    this.currentShift$ = this.store.select(ShiftsState.currentShift);
    this.shiftHistory$ = this.store.select(ShiftsState.shiftHistory);
    this.availableCabs$ = this.store.select(ShiftsState.availableCabs);
    // this.hasActiveShift$ = this.store.select(ShiftsState.hasActiveShift);
    this.cities$ = this.store.select(CityState.cities);
    this.loading$ = this.store.select(ShiftsState.loading);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private loadData(): void {
    this.store.dispatch(new LoadCurrentShift());
    this.store.dispatch(new LoadShiftHistory());
  }

  private setupShiftTimer(): void {
    this.shiftDuration$ = interval(1000).pipe(
      takeUntil(this.destroy$),
      map(() => {
        const currentShift = this.store.selectSnapshot(ShiftsState.currentShift);
        if (currentShift && !currentShift.end_time) {
          const start = new Date(currentShift.start_time).getTime();
          const now = new Date().getTime();
          const duration = Math.floor((now - start) / 1000);
          return this.formatDuration(duration);
        }
        return '00:00:00';
      })
    );
  }

  loadAvailableVehicles(): void {
    this.store.dispatch(new LoadAvailableCabs());
    this.store.dispatch(new LoadCities());
    this.showVehicleSelection = true;
  }

  startShift(cabId: string): void {
    this.store.dispatch(new StartShift(cabId));
    this.showVehicleSelection = false;
  }

  endShift(): void {
    this.store.dispatch(new EndShift());
  }

  cancelVehicleSelection(): void {
    this.showVehicleSelection = false;
  }

  formatDuration(seconds: number): string {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  }

  getShiftDuration(shift: Shift): string {
    if (!shift.end_time) return 'Active';

    const start = new Date(shift.start_time).getTime();
    const end = new Date(shift.end_time).getTime();
    const duration = Math.floor((end - start) / 1000);
    return this.formatDuration(duration);
  }

  trackByShiftId(index: number, shift: Shift): string {
    return shift.shift_id;
  }

  trackByVehicleId(index: number, vehicle: Vehicle): string {
    return vehicle.id;
  }
}
