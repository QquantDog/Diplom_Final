import { Injectable } from '@angular/core';
import {State, Action, StateContext, Selector, Store} from '@ngxs/store';
import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { RidesService } from '../../services/rides.service';
import {
  LoadRides,
  SetRideFilters,
  ClearRideFilters,
  LoadRideDetails, UpdatePagination,
  // AcceptRide,
  // CompleteRide,
  // CancelRide
} from './rides.actions';
import {Ride, RideFilters} from '../../models/ride/ride.model';
import {AuthState} from '../auth/auth.state';
import {PageEvent} from '@angular/material/paginator';

export interface RidesStateModel {
  rides: Ride[];
  selectedRide: Ride | null;
  filters: RideFilters;
  loading: boolean;
  total: number;
  currentPage: number;
  pageSize: number;
  searchTerm: string;
  error: string | null;
}

@State<RidesStateModel>({
  name: 'rides',
  defaults: {
    rides: [],
    selectedRide: null,
    filters: {},
    loading: false,
    total: 0,
    currentPage: 1,
    pageSize: 10,
    searchTerm: '',
    error: null
  }
})
@Injectable()
export class RidesState {
  constructor(private ridesService: RidesService, private store: Store) {}

  @Selector()
  static rides(state: RidesStateModel): Ride[] {
    return state.rides;
  }

  @Selector()
  static selectedRide(state: RidesStateModel): Ride | null {
    return state.selectedRide;
  }

  @Selector()
  static filters(state: RidesStateModel): RideFilters {
    return state.filters;
  }

  @Selector()
  static loading(state: RidesStateModel): boolean {
    return state.loading;
  }

  @Selector()
  static pagination(state: RidesStateModel) {
    return {
      total: state.total,
      currentPage: state.currentPage,
      pageSize: state.pageSize
    };
  }

  @Selector()
  static hasActiveFilters(state: RidesStateModel): boolean {
    return Object.keys(state.filters).length > 0;
  }

  @Action(UpdatePagination)
  updatePagination(ctx: StateContext<RidesStateModel>, { pageBundle }: UpdatePagination): void {
    // console.warn("pageEvent: ");
    console.warn(pageBundle);
    ctx.patchState({
        currentPage: pageBundle.currentPage,
        pageSize: pageBundle.pageSize
    });
  }

  @Action(LoadRides)
  loadRides(ctx: StateContext<RidesStateModel>, action: LoadRides) {
    ctx.patchState({
      loading: true,
      error: null,
      currentPage: action.params.page,
      searchTerm: action.params.search || ''
    });

    if (this.store.selectSnapshot(AuthState.isCustomer)){
      console.warn("customer");
      return this.ridesService.getCustomerRides(action.params).pipe(
        tap((response) => {
          console.warn(response);
          ctx.patchState({
            rides: response.data,
            total: response.total || 0,
            loading: false
          });
        }),
        catchError((error) => {
          ctx.patchState({
            loading: false,
            error: error.error?.message || 'Failed to load rides'
          });
          return throwError(() => error);
        })
      );
    } else {
      console.warn("driver");
      return this.ridesService.getDriverRides(action.params).pipe(
        tap((response) => {
          console.warn(response);
          ctx.patchState({
            rides: response.data,
            total: response.total || 0,
            loading: false
          });
        }),
        catchError((error) => {
          ctx.patchState({
            loading: false,
            error: error.error?.message || 'Failed to load rides'
          });
          return throwError(() => error);
        })
      );
    }
  }

  @Action(SetRideFilters)
  setRideFilters(ctx: StateContext<RidesStateModel>, action: SetRideFilters) {
    ctx.patchState({
      filters: action.filters,
      currentPage: 1 // Reset to first page when filters change
    });
  }

  @Action(ClearRideFilters)
  clearRideFilters(ctx: StateContext<RidesStateModel>) {
    ctx.patchState({
      filters: {},
      currentPage: 1
    });
  }

  @Action(LoadRideDetails)
  loadRideDetails(ctx: StateContext<RidesStateModel>, action: LoadRideDetails) {
    ctx.patchState({ loading: true, error: null });

    return this.ridesService.getRideById(action.rideId).pipe(
      tap((ride) => {
        ctx.patchState({
          selectedRide: ride,
          loading: false
        });
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to load ride details'
        });
        return throwError(() => error);
      })
    );
  }

  // @Action(AcceptRide)
  // acceptRide(ctx: StateContext<RidesStateModel>, action: AcceptRide) {
  //   return this.ridesService.acceptRide(action.rideId).pipe(
  //     tap((updatedRide) => {
  //       const state = ctx.getState();
  //       const rides = state.rides.map(ride =>
  //         ride.id === updatedRide.id ? updatedRide : ride
  //       );
  //       ctx.patchState({ rides });
  //     })
  //   );
  // }
  //
  // @Action(CompleteRide)
  // completeRide(ctx: StateContext<RidesStateModel>, action: CompleteRide) {
  //   return this.ridesService.completeRide(action.rideId).pipe(
  //     tap((updatedRide) => {
  //       const state = ctx.getState();
  //       const rides = state.rides.map(ride =>
  //         ride.id === updatedRide.id ? updatedRide : ride
  //       );
  //       ctx.patchState({ rides });
  //     })
  //   );
  // }
  //
  // @Action(CancelRide)
  // cancelRide(ctx: StateContext<RidesStateModel>, action: CancelRide) {
  //   return this.ridesService.cancelRide(action.rideId).pipe(
  //     tap((updatedRide) => {
  //       const state = ctx.getState();
  //       const rides = state.rides.map(ride =>
  //         ride.id === updatedRide.id ? updatedRide : ride
  //       );
  //       ctx.patchState({ rides });
  //     })
  //   );
  // }
}


// @Action(LoadRides)
// loadRides(ctx: StateContext<RidesStateModel>, action: LoadRides) {
//   ctx.patchState({
//     loading: true,
//     error: null,
//     currentPage: action.params.page,
//     searchTerm: action.params.search || ''
//   });
//
//   if (this.store.selectSnapshot(AuthState.isCustomer)){
//     console.warn("customer");
//     return this.ridesService.getCustomerRides(action.params).pipe(
//       tap((response) => {
//         console.warn(response);
//         ctx.patchState({
//           rides: response.data,
//           total: response.total || 0,
//           loading: false
//         });
//       }),
//       catchError((error) => {
//         ctx.patchState({
//           loading: false,
//           error: error.error?.message || 'Failed to load rides'
//         });
//         return throwError(() => error);
//       })
//     );
//   } else {
//     console.warn("driver");
//     return this.ridesService.getDriverRides(action.params).pipe(
//       tap((response) => {
//         console.warn(response);
//         ctx.patchState({
//           rides: response.data,
//           total: response.total || 0,
//           loading: false
//         });
//       }),
//       catchError((error) => {
//         ctx.patchState({
//           loading: false,
//           error: error.error?.message || 'Failed to load rides'
//         });
//         return throwError(() => error);
//       })
//     );
//   }
// }
