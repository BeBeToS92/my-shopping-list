import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { IAccessInformations, IAccessInformationsError } from 'src/app/models/user.model';

import { AlertService } from 'src/app/services/alert.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LanguageService } from 'src/app/services/language.service';
import { UserService } from 'src/app/services/rest/user.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  email: string = '';
  password: string = '';
  saveCredentials: boolean;

  constructor(private userService: UserService, private authService: AuthenticationService,
    private router: Router, private alertService: AlertService, private languageService: LanguageService,
    private storageService: StorageService) { }

  async ngOnInit() {

    let params = await this.storageService.getCredentials();
    console.log("params", params);
    if(params != null){
      this.email = params.email;
      this.password = params.password;
      this.saveCredentials = true;
    }

  }

  
  doLogin() {

    this.alertService.showLoading().then((loader)=>{
      loader.present();
      
      this.userService.login(this.email, this.password).subscribe((res: IAccessInformations) => {
        loader.dismiss();

        if(this.saveCredentials){
          this.storageService.setCredentials(this.email, this.password);
        } else {
          this.storageService.deleteCredentials();
        }

        this.authService.setCurrentUserValue(res);
        this.router.navigate(['/shops']);
  
      }, (error: IAccessInformationsError) => {
        loader.dismiss();
        this.alertService.showAlert('', this.languageService.getString('LOGIN.wrong_credentials')).then((resp)=>{
            resp.present();
        });
      });
    })
  }

}
