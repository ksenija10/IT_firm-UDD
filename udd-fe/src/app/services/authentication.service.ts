import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationRequest } from '../model/authentication-request.model';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient) {}

  login(
    authenticationRequest: AuthenticationRequest
  ): Observable<HttpResponse<void>> {
    return this.http.post<void>(
      environment.apiEndpoint + '/auth/login',
      authenticationRequest,
      {
        observe: 'response',
      }
    );
  }

  setLoggedInUser(response: any): void {
    const jwtToken = response.body.accessToken;
    const expiresIn = response.body.expiresIn;
    sessionStorage.setItem('jwtToken', jwtToken);
    sessionStorage.setItem('expiresIn', expiresIn);
  }
}
