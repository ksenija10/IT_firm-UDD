import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ChartResponse } from '../model/chart.model';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  constructor(private http: HttpClient) {}

  getTimeReport(): Observable<ChartResponse[]> {
    return this.http.get<ChartResponse[]>(
      environment.apiEndpoint + '/report/time'
    );
  }

  getCityReport(): Observable<ChartResponse[]> {
    return this.http.get<ChartResponse[]>(
      environment.apiEndpoint + '/report/city'
    );
  }
}
