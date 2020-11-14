import { Injectable } from '@angular/core';
import { LoadingController, AlertController, ToastController } from '@ionic/angular';
import { LanguageService } from './language.service';



@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(private alertController: AlertController, private toastController: ToastController, private loadingController: LoadingController,
    private language: LanguageService) { }


  // Show alert just with a message
  showAlert(title: string, msg: string, subTitle: string = null): Promise<HTMLIonAlertElement> {
    return this.alertController.create({
      header: title,
      subHeader: subTitle,
      message: msg,
      buttons: [this.language.getString('ALERT.ok')]
    });
  }


  // Show toast
  showToast(msg: string, duration: number = 2500, isError: boolean = false, position: string = 'bottom'): Promise<HTMLIonToastElement> {
    return this.toastController.create({
      message: msg,
      duration: duration,
      position: position == 'top' ? 'top' : 'bottom',
      cssClass: isError ? 'errorToast' : 'infoToast'
    });
  }


  showLoading(msg?: string): Promise<HTMLIonLoadingElement> {
    let message = this.language.getString('ALERT.loading');

    if (msg != null) message = msg;

    return this.loadingController.create({
      message: message
    });

  }
}
