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
export class SearchService {
  constructor(private http: HttpClient) {}

  simpleSearch(
    query: string,
    page: number,
    size: number
  ): Observable<SearchPage> {
    let params = new HttpParams();
    params = params.append('query', query);
    params = params.append('page', String(page));
    params = params.append('size', String(size));

    return this.http.get<SearchPage>(
      environment.apiEndpoint + '/search/simple',
      { params }
    );
  }

  advancedSearch(
    searchRequest: AdvancedSearchRequest,
    page: number,
    size: number
  ): Observable<SearchPage> {
    let params = new HttpParams();
    params = params.append('page', String(page));
    params = params.append('size', String(size));

    return this.http.post<SearchPage>(
      environment.apiEndpoint + '/search/advanced',
      searchRequest,
      { params }
    );
  }

  geoLocationSearch(
    searchRequest: GeoLocationRequest,
    page: number,
    size: number
  ): Observable<SearchPage> {
    let params = new HttpParams();
    params = params.append('page', String(page));
    params = params.append('size', String(size));

    return this.http.post<SearchPage>(
      environment.apiEndpoint + '/search/location',
      searchRequest,
      { params }
    );
  }

  openCv(cvId: number) {
    return this.http
      .get(environment.apiEndpoint + '/job-application/cv/' + cvId, {
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        map((res: any) => {
          return new Blob([res.body], { type: 'application/pdf' });
        })
      )
      .subscribe((result) => {
        const fileURL = URL.createObjectURL(result);
        window.open(fileURL, '_blank');
      });
  }
}
