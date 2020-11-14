import { Component, OnInit } from '@angular/core';

import { IShop, IShopResponse, IShopsResponse } from 'src/app/models/shop.model';

import { AlertService } from 'src/app/services/alert.service';
import { LanguageService } from 'src/app/services/language.service';
import { ShopService } from 'src/app/services/rest/shop.service';

@Component({
  selector: 'app-shops',
  templateUrl: './shops.page.html',
  styleUrls: ['./shops.page.scss'],
})
export class ShopsPage implements OnInit {

  newItem: string;
  list: IShop[] = []

  constructor(private shopService: ShopService, private alertService: AlertService,
    private langService: LanguageService) { }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.getAllShops();
  }


  add() {
    this.alertService.showLoading().then(
      (loading) => {
        loading.present();

        this.shopService.addShop(this.newItem).subscribe(
          (response: IShopResponse) => {
            this.list.push({ id: response.data.id, name: response.data.name, total: 0, bought: 0 });
            this.newItem = '';
            loading.dismiss();
          },
          (error) => {
            loading.dismiss();
            let title = this.langService.getString('ALERT.error');
            let msg = this.langService.getString('ERROR.add_elem');
            this.alertService.showAlert(title, msg).then(
              (alertBox) => { alertBox.present(); }
            );
          }
        );
      }
    );
  }

  delete(item) {
    this.alertService.showLoading().then(
      (loading) => {
        loading.present();

        this.shopService.deleteShop(item.id).subscribe(
          (response: IShopResponse) => {
            this.list = this.list.filter(obj => obj !== item);
            loading.dismiss();
          },
          (error) => {
            loading.dismiss();
            let title = this.langService.getString('ALERT.error');
            let msg = this.langService.getString('ERROR.delete_elem');
            this.alertService.showAlert(title, msg).then(
              (alertBox) => { alertBox.present(); }
            );
          }
        );
      }
    );
  }

  getAllShops() {
    this.alertService.showLoading().then(
      (loading) => {
        loading.present();

        this.shopService.getShops().subscribe(
          (response: IShopsResponse) => {
            this.list = response.data;
            loading.dismiss();
          },
          (error) => {
            loading.dismiss();
            let title = this.langService.getString('ALERT.error');
            let msg = this.langService.getString('ERROR.retrieve_list');
            this.alertService.showAlert(title, msg).then(
              (alertBox) => { alertBox.present(); }
            );
          }
        );
      }
    );
  }

}
