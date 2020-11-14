import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { IConfiguration } from '../models/configuration.model';


@Injectable()
export class ConfigurationService {

    constructor(private authentication: AuthenticationService, private http: HttpClient) {}

   
    public initialize(): Promise<string> {
        return new Promise((resolve) => {
            this.getLocalConfiguration().subscribe((conf: IConfiguration) => {
                this.authentication.configuration = conf;
                console.log("conf",conf)
               resolve();             
            });                
        });
    }

    public getLocalConfiguration(): Observable<IConfiguration> {
        return this.http.get<IConfiguration>("/assets/json/configuration.json");
    }
   

}
