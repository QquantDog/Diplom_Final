import {City} from '../../models/city.model';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Action, Selector, State, StateContext} from '@ngxs/store';
import {CityService} from '../../services/city.service';
import {LoadCities} from './city.action';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';

export interface CityStateModel {
  cities: City[];
}

@State<CityStateModel>({
  name: "cities",
  defaults: {
    cities: [],
  }
})
@Injectable()
export class CityState {
  constructor(
    private cityService: CityService,
    private router: Router
  ) {}

  @Selector()
  static cities(state: CityStateModel): City[] {
    return state.cities;
  }

  @Action(LoadCities)
  loadCities(ctx: StateContext<CityStateModel>): Observable<City[]> {
    return this.cityService.getCities().pipe(
      tap((cities: City[]) => {
        ctx.setState({cities: cities});
      }),
      catchError((err) => {
        return throwError(() => err);
      })
    )
  }
}
