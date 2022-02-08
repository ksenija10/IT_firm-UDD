import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { SearchPage } from '../model/search-page.model';
import { environment } from 'src/environments/environment';
import { GeoLocationRequest } from '../model/geo-search-request.model';
import { AdvancedSearchRequest } from '../model/advanced-search-request.model';

@Injectable({
  providedIn: 'root',
})
export class JobApplicationService {
  constructor(private http: HttpClient) {}

  applyForJob(jobApplication: FormData) {
    return this.http.post<void>(
      environment.apiEndpoint + '/job-application',
      jobApplication
    );
  }
}
