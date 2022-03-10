import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConstants } from '../common/app.constants';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(credentials: any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'login', {
      email: credentials.email,
      password: credentials.password
    }, httpOptions);
  }

  register(user: any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'register', {
      displayName: user.displayName,
      email: user.email,
      password: user.password,
      matchingPassword: user.matchingPassword,
      socialProvider: 'LOCAL',
      using2FA: user.using2FA
    }, httpOptions);
  }

  verify(credentials: any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'verify', credentials.code, {
    	  headers: new HttpHeaders({ 'Content-Type': 'text/plain' })
    });
  }

  verifyToken(token: any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'token/verify', token, {
    	  headers: new HttpHeaders({ 'Content-Type': 'text/plain' })
    });
  }

  resendToken(token: any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'token/resend', token, {
    	  headers: new HttpHeaders({ 'Content-Type': 'text/plain' })
    });
  }
}
