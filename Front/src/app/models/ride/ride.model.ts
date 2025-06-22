import {GeoPoint} from '../geo/geo-point.model';

export interface AcceptedRateInfo {
  id: string;
  init_price: number;
  rate_per_km: number;
  paid_waiting_per_minute: number;
  free_time: number; // seconds
}

export interface PaymentInfo {
  overall_price: string;
  method: 'cash' | 'card' | 'wallet' | 'promo';
}

export interface PromocodeInfo {
  id: string;
  code: string;
  discount_amount: number;
  discount_type: 'fixed' | 'percentage';
}

export interface ShiftInfo {
  shift_id: string;
}

export interface Ride {
  id: string;
  accepted_at: string | null;
  accepted_rate_info: AcceptedRateInfo | null;
  actual_price: number | null;
  created_at: string;
  distance_actual_meters: number | null;
  distance_expected_meters: number | null;
  driver_waiting: string | null;
  end_point: GeoPoint;
  end_time: string | null;
  expected_price: number | null;
  payment_info: PaymentInfo | null;
  promocode_info: PromocodeInfo | null;
  ride_tip: number;
  shift_info: ShiftInfo | null;
  start_point: GeoPoint;
  start_time: string | null;
  status: 'PENDING' | 'ACCEPTED' | 'WAITING_CLIENT' | 'IN_WAY' | 'COMPLETED' | 'CANCELLED';
}

export interface RideFilters {
  minPrice?: number;
  maxPrice?: number;
  startTimeFrom?: string;
  startTimeTo?: string;
  endTimeFrom?: string;
  endTimeTo?: string;
  minDistance?: number;
  maxDistance?: number;
  status?: string[];
  minTip?: number;
  maxTip?: number;
  paymentMethod?: string[];
  search?: string;
}
