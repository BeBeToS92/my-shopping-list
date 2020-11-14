import { Injectable } from '@angular/core';

import { Storage } from '@ionic/storage';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  keys = {
    THEME: "THEME",
    LANGUAGE: "SELECTED_LANGUAGE",
    CREDENTIALS: "CREDENTIALS"
  }

  constructor(private storage: Storage) { }

  setThemeValue(value: string) {
    this.storage.set(this.keys.THEME, value).then(
      (res) => { }
    ).catch(
      (err) => { console.log("setThemeValue-err", err) }
    );
  }

  async getThemeValue() {
    let value = 'light';
    await this.storage.get(this.keys.THEME).then(
      (res: string) => { value = res }
    ).catch(
      (err) => { console.log("getThemeValue-err", err) }
    );

    return value;
  }


  setLanguage(value: string) {
    this.storage.set(this.keys.LANGUAGE, value).then(
      (res) => { }
    ).catch(
      (err) => { console.log("setThemeValue-err", err) }
    );
  }

  async getLanguage() {
    let value = '';
    await this.storage.get(this.keys.LANGUAGE).then(
      (res: string) => { value = res }
    ).catch(
      (err) => { console.log("getLanguage-err", err) }
    );

    return value;
  }


  setCredentials(email: string, password: string) {
    let value = { email: email, password: password }

    this.storage.set(this.keys.CREDENTIALS, value).then(
      (res) => { }
    ).catch(
      (err) => { console.log("setThemeValue-err", err) }
    );
  }

  async getCredentials() {
    let value = null;
    await this.storage.get(this.keys.CREDENTIALS).then(
      (res) => { value = res }
    ).catch(
      (err) => { console.log("getCredentials-err", err) }
    );

    return value;
  }

  deleteCredentials() {

    this.storage.remove(this.keys.CREDENTIALS).then(
      (res) => { }
    ).catch(
      (err) => { console.log("setThemeValue-err", err) }
    );
  }

}
