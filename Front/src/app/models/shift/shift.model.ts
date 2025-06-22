import {Cab} from '../cab.model';
import {RateShift} from '../rate/rate-shift.model';

export interface Shift {
  shift_id: string;
  start_time: Date;
  end_time?: Date;
  rate_info?: RateShift;
  cab_info?: Cab;
}
