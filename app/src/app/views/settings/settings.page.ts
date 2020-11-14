import { Component, OnInit } from '@angular/core';
import { LanguageService } from 'src/app/services/language.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
})
export class SettingsPage implements OnInit {

  dark: boolean = false;
  language: string;
  languageCancelText: string;

  constructor(private storageService: StorageService,private languageService: LanguageService) { }

  async ngOnInit() {
    let style = await this.storageService.getThemeValue();
    if(style == 'dark'){
      this.dark = true;
    }

    this.language = await this.storageService.getLanguage();
    this.languageCancelText = this.languageService.getString('ALERT.cancel');
  }

  updateDarkMode() {
    if(this.dark){
      document.body.setAttribute('color-theme', 'dark');
      this.storageService.setThemeValue('dark');
    } else {
      document.body.setAttribute('color-theme', 'light');
      this.storageService.setThemeValue('light');
    }
  }

  changeLanguage(lang){
    this.languageService.setLanguage(lang);
  }

}
