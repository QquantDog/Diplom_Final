import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import {Ride, RideFilters} from '../models/ride/ride.model';
import {ApiResponse, PaginationFilterRideParams} from '../models/api-response.model';
import {PaginationParams} from '../models/pagination.model';
import {Store} from '@ngxs/store';
import {AuthState} from '../store/auth/auth.state';

@Injectable({
  providedIn: 'root'
})
export class RidesService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private store: Store) {}

  getCustomerRides(params: PaginationFilterRideParams): Observable<ApiResponse<Ride[]>> {
    let httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('limit', params.limit.toString());

    if (params.filters) {
      httpParams = this.addFilterParams(httpParams, params.filters);
    }

    return this.http.get<ApiResponse<Ride[]>>(`${this.apiUrl}/rides/customer/my`, { params: httpParams });
  }

  getDriverRides(params: PaginationFilterRideParams): Observable<ApiResponse<Ride[]>> {
    let httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('limit', params.limit.toString());

    if (params.filters) {
      httpParams = this.addFilterParams(httpParams, params.filters);
    }

    return this.http.get<ApiResponse<Ride[]>>(`${this.apiUrl}/rides/driver/my`, { params: httpParams });
  }

  getRideById(id: string): Observable<Ride> {
    return this.http.get<Ride>(`${this.apiUrl}/rides/${id}`);
  }

  // createRide(ride: Partial<Ride>): Observable<Ride> {
  //   return this.http.post<Ride>(`${this.apiUrl}/rides`, ride);
  // }
  //
  // acceptRide(rideId: string): Observable<Ride> {
  //   return this.http.patch<Ride>(`${this.apiUrl}/rides/${rideId}/accept`, {});
  // }
  //
  // completeRide(rideId: string): Observable<Ride> {
  //   return this.http.patch<Ride>(`${this.apiUrl}/rides/${rideId}/complete`, {});
  // }
  //
  // cancelRide(rideId: string): Observable<Ride> {
  //   return this.http.patch<Ride>(`${this.apiUrl}/rides/${rideId}/cancel`, {});
  // }

  private addFilterParams(params: HttpParams, filters: RideFilters): HttpParams {
    let httpParams = params;

    if (filters.minPrice !== undefined) {
      httpParams = httpParams.set('minPrice', filters.minPrice.toString());
    }
    if (filters.maxPrice !== undefined) {
      httpParams = httpParams.set('maxPrice', filters.maxPrice.toString());
    }
    if (filters.startTimeFrom) {
      httpParams = httpParams.set('startTimeFrom', filters.startTimeFrom);
    }
    if (filters.startTimeTo) {
      httpParams = httpParams.set('startTimeTo', filters.startTimeTo);
    }
    if (filters.endTimeFrom) {
      httpParams = httpParams.set('endTimeFrom', filters.endTimeFrom);
    }
    if (filters.endTimeTo) {
      httpParams = httpParams.set('endTimeTo', filters.endTimeTo);
    }
    if (filters.minDistance !== undefined) {
      httpParams = httpParams.set('minDistance', filters.minDistance.toString());
    }
    if (filters.maxDistance !== undefined) {
      httpParams = httpParams.set('maxDistance', filters.maxDistance.toString());
    }
    if (filters.status && filters.status.length > 0) {
      httpParams = httpParams.set('status', filters.status.join(','));
    }
    if (filters.minTip !== undefined) {
      httpParams = httpParams.set('minTip', filters.minTip.toString());
    }
    if (filters.maxTip !== undefined) {
      httpParams = httpParams.set('maxTip', filters.maxTip.toString());
    }
    if (filters.paymentMethod && filters.paymentMethod.length > 0) {
      httpParams = httpParams.set('paymentMethod', filters.paymentMethod.join(','));
    }

    return httpParams;
  }
}
