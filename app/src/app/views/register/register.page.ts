import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService } from 'src/app/services/alert.service';
import { LanguageService } from 'src/app/services/language.service';
import { UserService } from 'src/app/services/rest/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  email: string = '';
  password: string = '';
  repeatPassword: string = '';

  inputType = 'password';
  icon = 'eye-outline';

  constructor(private userService: UserService, private alertService: AlertService,
    private langService: LanguageService, private router: Router) {

  }

  ngOnInit() {
  }


  showHidePassword() {
    if (this.inputType == 'password') {
      this.inputType = 'text'
      this.icon = 'eye-off-outline'
    } else {
      this.inputType = 'password'
      this.icon = 'eye-outline'
    }
  }

  register() {

    if (this.password != this.repeatPassword) {

      let title = this.langService.getString('ALERT.error');
      let message = this.langService.getString('REGISTER.not_matching_password');

      this.alertService.showAlert(title, message).then(
        (alertBox) => { alertBox.present(); }
      );
      return;
    }

    this.alertService.showLoading().then(
      (loading) => {
        loading.present();
        this.userService.register(this.email, this.password).subscribe(
          (response) => {
            loading.dismiss();
            let msg = this.langService.getString('REGISTER.completed')
            this.alertService.showAlert('', msg).then(
              (alertBox) => { alertBox.present(); }
              );
              this.router.navigate(['/login']);
            },
          (error) => {
            loading.dismiss();
          }
        );
      }
    );

  }

}
