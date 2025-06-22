import {RideFilters} from './ride/ride.model';

export interface ApiResponse<T> {
  data: T;
  total?: number;
  page?: number;
  limit?: number;
  success: boolean;
  message?: string;
}

export interface PaginationFilterRideParams {
  page: number;
  limit: number;
  search?: string;
  filters?: RideFilters;
}
