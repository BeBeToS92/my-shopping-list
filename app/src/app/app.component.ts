import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { LanguageService } from './services/language.service';
import { StorageService } from './services/storage.service';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  public appPages = [ ];

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private languageService: LanguageService,
    private storageService: StorageService,
    private authService: AuthenticationService
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      this.languageService.setInitialAppLanguage();
      this.initializeStyle();
      this.initializeMenu();
    });
  }


  initializeMenu() {
    this.appPages = [
      {
        title: 'SETTINGS.title',
        url: '/settings',
        icon: 'settings',
        visible: true,
      }
    ]
  }

  async initializeStyle(){
    let style = await this.storageService.getThemeValue();
    if(style == 'dark'){
      document.body.setAttribute('color-theme', 'dark');
    } else {
      document.body.setAttribute('color-theme', 'light');
    }
  }

  logout(){
    this.authService.logout();
  }

}
