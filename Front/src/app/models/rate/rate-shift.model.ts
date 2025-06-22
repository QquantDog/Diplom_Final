import {TierName} from '../tier/tier.model';
import {City} from '../city.model';

export interface RateShift {
  id: string;
  city_info: City;
  tier_info: TierName;
}
