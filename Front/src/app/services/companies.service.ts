import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Vehicle } from '../models/vehicle.model';
import { ApiResponse } from '../models/api-response.model';
import {PaginationParams} from '../models/pagination.model';
import {TaxiCompanyShort} from '../models/taxi-company.model';

@Injectable({
  providedIn: 'root'
})
export class CompaniesService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getCompanies(params: PaginationParams): Observable<ApiResponse<TaxiCompanyShort[]>> {
    let httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('limit', params.limit.toString());

    if (params.search) {
      httpParams = httpParams.set('search', params.search);
    }

    return this.http.get<ApiResponse<TaxiCompanyShort[]>>(`${this.apiUrl}/companies`, { params: httpParams });
  }

  getCompanyById(id: string): Observable<TaxiCompanyShort> {
    return this.http.get<TaxiCompanyShort>(`${this.apiUrl}/companies/${id}`);
  }

  registerToCompany(companyId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/companies/${companyId}/register`, {});
  }

  unregisterFromCompany(companyId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/companies/${companyId}/register`);
  }

  getCompanyVehicles(companyId: string): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.apiUrl}/companies/${companyId}/vehicles`);
  }
}
