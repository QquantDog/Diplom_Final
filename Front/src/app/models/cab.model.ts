import { TaxiCompanyShort} from './taxi-company.model';
import {Vehicle} from './vehicle.model';

export interface Cab {
  cab_id: string;
  vin: string;
  is_on_shift: boolean;
  manufacture_date: Date;
  color: string;
  license_plate: string;
  vehicle_info: Vehicle;
  company_info: TaxiCompanyShort
}
