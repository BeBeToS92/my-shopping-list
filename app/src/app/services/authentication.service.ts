import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { filter } from 'rxjs/operators';
import { IConfiguration } from '../models/configuration.model';
import { IAccessInformations } from '../models/user.model';


@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<IAccessInformations>;
  public currentUser: Observable<IAccessInformations>;

  private configurationSource$: BehaviorSubject<IConfiguration> = new BehaviorSubject<IConfiguration>(null);
  configuration$: Observable<IConfiguration> = this.configurationSource$.asObservable().pipe(filter((source: IConfiguration) => (!! source)));

  set configuration(conf: IConfiguration) {       
      this.configurationSource$.next(conf);
  }

  get configuration(): IConfiguration {
      return this.configurationSource$.value;
  }

  constructor() {
    this.currentUserSubject = new BehaviorSubject<IAccessInformations>(null);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): IAccessInformations {
    return this.currentUserSubject.value;
  }

  public setCurrentUserValue(newValue: IAccessInformations){
    this.currentUserSubject.next(newValue);
  }

  logout() {
    this.currentUserSubject.next(null);
  }

  isAuthenticated(){
    return !!this.currentUserSubject.value;
  }


  getAccessToken(){
    if(this.currentUserSubject.value)
      return this.currentUserSubject.value.access_token;

    return null;
  }

  getRefreshToken(){
    if(this.currentUserSubject.value)
      return this.currentUserSubject.value.refresh_token;

    return null;
  }
}