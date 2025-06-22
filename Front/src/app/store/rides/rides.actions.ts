import {PaginationFilterRideParams} from '../../models/api-response.model';
import {RideFilters} from '../../models/ride/ride.model';
import {PageEvent} from '@angular/material/paginator';
import {PageBundle} from '../../modules/rides/rides.component';

export class LoadRides {
  static readonly type = '[Rides] Load Rides';
  constructor(public params: PaginationFilterRideParams) {}
}

export class SetRideFilters {
  static readonly type = '[Rides] Set Ride Filters';
  constructor(public filters: RideFilters) {}
}

export class ClearRideFilters {
  static readonly type = '[Rides] Clear Ride Filters';
}

export class LoadRideDetails {
  static readonly type = '[Rides] Load Ride Details';
  constructor(public rideId: string) {}
}

export class AcceptRide {
  static readonly type = '[Rides] Accept Ride';
  constructor(public rideId: number) {}
}

export class CompleteRide {
  static readonly type = '[Rides] Complete Ride';
  constructor(public rideId: number) {}
}

export class CancelRide {
  static readonly type = '[Rides] Cancel Ride';
  constructor(public rideId: number) {}
}

export class UpdatePagination{
  static readonly type = '[Rides] Update Pagination';
  constructor(public pageBundle: PageBundle) {}
}
