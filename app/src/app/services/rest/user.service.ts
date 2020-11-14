import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';

import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { IAccessInformations, IAccessInformationsError, IUserInformations } from 'src/app/models/user.model';
import { MainService } from '../main.service';
import { AuthenticationService } from '../authentication.service';


@Injectable({
  providedIn: 'root'
})
export class UserService extends MainService {

  address: string;
  controller: string;

  constructor(private http: HttpClient, private authentication: AuthenticationService) {
    super()
    this.address = this.authentication.configuration.url;
  }


  login(email: string, password: string) {

    let params = new URLSearchParams();
    params.set('username', email);
    params.set('password', password);
    params.set('grant_type', 'password');


    return this.http.post<IAccessInformations>(this.address + 'oauth/token', params.toString(), { headers: this.headers }).pipe((
      catchError((httpError: HttpErrorResponse) => {
        console.log("err", httpError);
        let err: IAccessInformationsError = {
          error: httpError.error,
          message: httpError.error.error_description
        };

        return throwError(err);
      })
    ));
  }


  refreshToken(token: string) {

    let params = new URLSearchParams();
    params.set('grant_type', 'refresh_token');
    params.set('refresh_token', token);

    return this.http.post<IAccessInformations>(this.address + 'oauth/token', params).pipe((
      catchError((httpError: HttpErrorResponse) => {
        console.log("refresh", httpError)
        let err: IAccessInformationsError = {
          error: httpError.error,
          message: httpError.error.error_description
        };

        return throwError(err);
      })
    ));

  }


  register(email: string, password: string) {

    let params = {
      email: email,
      password: password
    }

    return this.http.post<IUserInformations>(this.address + 'auth/register', params).pipe((
      catchError((httpError: HttpErrorResponse) => {
        console.log("register", httpError)
        let err: IUserInformations = {
          message: httpError.error.error_description
        };

        return throwError(err);
      })
    ));
  }

}
