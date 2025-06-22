import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Shift } from '../models/shift/shift.model';
import {Cab} from '../models/cab.model';
import {StartShift} from '../models/shift/start-shift.model';

@Injectable({
  providedIn: 'root'
})
export class ShiftsService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAvailableCabs(): Observable<Cab[]> {
    return this.http.get<Cab[]>(`${this.apiUrl}/cabs/possible`);
  }

  getCurrentShift(): Observable<Shift | null> {
    return this.http.get<Shift | null>(`${this.apiUrl}/shifts/my/active`);
  }

  getShiftHistory(limit: number = 5): Observable<Shift[]> {
    return this.http.get<Shift[]>(`${this.apiUrl}/shifts/my?limit=${limit}`);
  }

  startShift(model: StartShift): Observable<Shift> {
    return this.http.post<Shift>(`${this.apiUrl}/shifts/start`, { ...model });
  }

  endShift(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/shifts/finish`, {});
  }
}
