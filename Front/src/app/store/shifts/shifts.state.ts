import { Injectable } from '@angular/core';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ShiftsService } from '../../services/shifts.service';
import { Shift } from '../../models/shift/shift.model';
import {
  LoadCurrentShift,
  LoadShiftHistory,
  StartShift,
  EndShift,
  LoadAvailableCabs
} from './shifts.actions';
import {Cab} from '../../models/cab.model';

export interface ShiftsStateModel {
  currentShift: Shift | null;
  shiftHistory: Shift[];
  availableCabs: Cab[];
  loading: boolean;
  error: string | null;
}

@State<ShiftsStateModel>({
  name: 'shifts',
  defaults: {
    currentShift: null,
    shiftHistory: [],
    availableCabs: [],
    loading: false,
    error: null
  }
})
@Injectable()
export class ShiftsState {
  constructor(private shiftsService: ShiftsService) {}

  @Selector()
  static currentShift(state: ShiftsStateModel): Shift | null {
    return state.currentShift;
  }

  @Selector()
  static shiftHistory(state: ShiftsStateModel): Shift[] {
    return state.shiftHistory;
  }

  @Selector()
  static availableCabs(state: ShiftsStateModel): Cab[] {
    return state.availableCabs;
  }

  @Selector()
  static hasActiveShift(state: ShiftsStateModel): boolean {
    return !state?.currentShift?.end_time;
  }

  @Selector()
  static loading(state: ShiftsStateModel): boolean {
    return state.loading;
  }

  @Action(LoadCurrentShift)
  loadCurrentShift(ctx: StateContext<ShiftsStateModel>) {
    ctx.patchState({ loading: true, error: null });

    return this.shiftsService.getCurrentShift().pipe(
      tap((shift) => {
        ctx.patchState({
          currentShift: shift,
          loading: false
        });
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to load current shift'
        });
        return throwError(() => error);
      })
    );
  }

  @Action(LoadShiftHistory)
  loadShiftHistory(ctx: StateContext<ShiftsStateModel>, action: LoadShiftHistory) {
    return this.shiftsService.getShiftHistory(action.limit).pipe(
      tap((shifts) => {
        ctx.patchState({ shiftHistory: shifts });
      })
    );
  }

  @Action(LoadAvailableCabs)
  loadAvailableVehicles(ctx: StateContext<ShiftsStateModel>) {
    // ctx.patchState({ loading: true, error: null });

    return this.shiftsService.getAvailableCabs().pipe(
      tap((cabs) => {
        ctx.patchState({
          availableCabs: cabs
        });
      }),
      catchError((error) => {
        // ctx.patchState({
        //   loading: false,
        //   error: error.error?.message || 'Failed to load available vehicles'
        // });
        return throwError(() => error);
      })
    );
  }

  @Action(StartShift)
  startShift(ctx: StateContext<ShiftsStateModel>, action: StartShift) {
    ctx.patchState({ loading: true, error: null });

    return this.shiftsService.startShift({
      cab_id: action.cabId,
      city_id: "1",
      shift_start_point: {
        type: "Point",
        coordinates: [41.98543, 23.85325]
      }
    }).pipe(
      tap((shift) => {
        ctx.patchState({
          currentShift: shift,
          availableCabs: [],
          loading: false
        });
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to start shift'
        });
        return throwError(() => error);
      })
    );
  }

  @Action(EndShift)
  endShift(ctx: StateContext<ShiftsStateModel>) {
    ctx.patchState({ loading: true, error: null });

    return this.shiftsService.endShift().pipe(
      tap(() => {
        ctx.patchState({
          currentShift: null,
          loading: false
        });
        // Reload shift history
        ctx.dispatch(new LoadShiftHistory());
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to end shift'
        });
        return throwError(() => error);
      })
    );
  }
}
