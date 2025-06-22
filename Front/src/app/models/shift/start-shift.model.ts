import {GeoPoint} from '../geo/geo-point.model';

export interface StartShift {
  cab_id: string;
  city_id: string;
  shift_start_point: GeoPoint;
}
