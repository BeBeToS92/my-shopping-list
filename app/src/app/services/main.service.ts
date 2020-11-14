import {HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import {Observable, of} from 'rxjs';


export abstract class MainService  {
   

    protected headers: HttpHeaders = new HttpHeaders();
    protected controller: string = "";

    constructor() {
        this.headers = this.headers.set("Authorization", "Basic " + btoa("mymoney-client:mymoney-secret-bebetos"));
        this.headers = this.headers.set("Content-Type", "application/x-www-form-urlencoded");
        this.controller = 'user/';
    }

    public handleError(errorResponse: HttpErrorResponse, method: "GET" | "POST" | "PUT" | "PATCH" | "DELETE", body?: any): Observable<any> {
        console.error({method, url: errorResponse.url, body, status: errorResponse.status, message: errorResponse.message, response: errorResponse.error});
        return of(null);
    }
}
