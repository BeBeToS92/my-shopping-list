import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { IShopResponse, IShopsResponse } from '../../models/shop.model';
import { AuthenticationService } from '../authentication.service';


@Injectable({
  providedIn: 'root'
})
export class ShopService {

  url: string;
  address: string;
  controller: string;

  constructor(private http: HttpClient, private authentication: AuthenticationService) {
    
    this.address = this.authentication.configuration.url;
    this.controller = 'shop/';
    this.url = this.address + this.controller;
  }

  getShops(): Observable<IShopsResponse> {

    return this.http.get<IShopsResponse>(this.url + 'getAll').pipe((
      catchError((error: HttpErrorResponse) => {
        console.log("errore", error);
        let err: IShopsResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ))
  }


  addShop(name: string): Observable<IShopResponse> {

    const params = {
      name: name
    }

    return this.http.post<IShopResponse>(this.url + 'save', params).pipe((
      catchError((error: HttpErrorResponse) => {
        console.log("errore", error);
        let err: IShopResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ));
  }


  deleteShop(id: number): Observable<IShopResponse> {

    const params = {
      id: id
    }

    return this.http.post<IShopResponse>(this.url + 'delete', params).pipe((
      catchError((error: HttpErrorResponse) => {
        console.log("errore", error);
        let err: IShopResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ));
  }
}
