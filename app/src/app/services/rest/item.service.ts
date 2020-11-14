import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


import { IItem, IItemResponse } from 'src/app/models/item.model';
import { IShopResponse } from 'src/app/models/shop.model';

import { AuthenticationService } from '../authentication.service';


@Injectable({
  providedIn: 'root'
})
export class ItemService {

  url: string;
  address: string;
  controller: string;

  constructor(private http: HttpClient, private authentication: AuthenticationService) {

    this.address = this.authentication.configuration.url;
    this.controller = 'item/';
    this.url = this.address + this.controller;
  }

  getItems(id: number): Observable<IShopResponse> {

    return this.http.get<IShopResponse>(this.url + 'getAll?shopId=' + id).pipe((
      catchError((error: HttpErrorResponse) => {
        let err: IShopResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ))
  }


  addItem(name: string, shopId: number): Observable<IItemResponse> {

    const params = {
      name: name,
      shopId: shopId
    }

    return this.http.post<IItemResponse>(this.url + 'save', params).pipe((
      catchError((error: HttpErrorResponse) => {
        let err: IItemResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ));
  }


  updateItem(item: IItem): Observable<IItemResponse> {

    const params = {
      id: item.id,
      isBought: item.isBought,
      unavailable: item.unavailable
    }

    return this.http.post<IItemResponse>(this.url + 'update', params).pipe((
      catchError((error: HttpErrorResponse) => {
        let err: IItemResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ));
  }


  deleteItem(ids: number[]): Observable<IItemResponse> {

    const params = {
      ids: ids
    }

    return this.http.post<IItemResponse>(this.url + 'delete', params).pipe((
      catchError((error: HttpErrorResponse) => {
        let err: IItemResponse = null;
        err.status = error.status;
        err.message = error.error;
        return throwError(err);
      })
    ));
  }
}
