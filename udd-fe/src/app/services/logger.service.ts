import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LocationRequest } from '../model/location-request.model';

@Injectable({
  providedIn: 'root',
})
export class LoggerService {
  constructor(private http: HttpClient) {}

  logFormAccess(locationRequest: LocationRequest) {
    return this.http.post<void>(
      environment.apiEndpoint + '/log/form-access',
      locationRequest
    );
  }
}
