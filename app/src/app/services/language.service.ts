import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
 
import { StorageService } from './storage.service';


@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  selected = 'it';
 
  constructor(private translate: TranslateService, private storageService: StorageService) { }
 
  async setInitialAppLanguage() {
    let language = this.translate.getBrowserLang();
    this.translate.setDefaultLang(language);
 

    let val = await this.storageService.getLanguage();
    if(val != null){
      this.setLanguage(val);
      this.selected = val;
    }
  }
 
  setLanguage(lng) {
    this.translate.use(lng);
    this.selected = lng;
    this.storageService.setLanguage(lng);
  }

  getString(key){
    return this.translate.instant(key);
  }
}
