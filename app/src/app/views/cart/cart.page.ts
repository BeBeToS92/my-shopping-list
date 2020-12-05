import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItem, IItemResponse } from 'src/app/models/item.model';
import { IShop, IShopResponse } from 'src/app/models/shop.model';

import { AlertService } from 'src/app/services/alert.service';
import { LanguageService } from 'src/app/services/language.service';
import { ItemService } from 'src/app/services/rest/item.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.page.html',
  styleUrls: ['./cart.page.scss'],
})
export class CartPage implements OnInit {

  id;
  newItem: string;
  searchItem: string;

  details: IShop = {
    name: '',
    items: []
  };

  constructor(private activatedRoute: ActivatedRoute, private itemService: ItemService,
    private alertService: AlertService, private langService: LanguageService) { }

  ngOnInit() { }

  ionViewWillEnter() {
    this.details = { name: '', items: [] }
    this.id = this.activatedRoute.snapshot.paramMap.get('id');

    this.getItems();
  }


  getItems() {

    this.alertService.showLoading().then(
      (loading) => {
        loading.present();

        this.itemService.getItems(this.id).subscribe(
          (response: IShopResponse) => {
            this.details = response.data;
            this.details.items.forEach(item => item.boughtBoolean = item.isBought == 1);
            this.sort();
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


  add() {

    this.alertService.showLoading().then(
      (loading) => {
        loading.present();

        this.itemService.addItem(this.newItem, this.id).subscribe(
          (response: IItemResponse) => {
            this.details.items.push(
              { id: response.data.id, name: response.data.name, isBought: 0, boughtBoolean: false, unavailable: 0, hide: false });
            this.newItem = '';

            this.sort();
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

  delete(item: IItem) {

    this.alertService.showLoading().then(
      (loading) => {

        const params = [item.id]
        this.itemService.deleteItem(params).subscribe(
          (response: IItemResponse) => {
            this.details.items = this.details.items.filter(obj => obj !== item);
            this.sort();
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

  askToDelete(){
    let msg = this.langService.getString('CART.delete');

    this.alertService.showConfirmAlert('', msg).then(
      (alert) => { alert.present();

        alert.onDidDismiss().then( (resp) => {
          this.emptyList();
        });
      });
  }


  emptyList() {

    this.alertService.showLoading().then(
      (loading) => {
        const params = this.details.items.map(item => item.id);

        this.itemService.deleteItem(params).subscribe(
          (response: IItemResponse) => {
            this.details.items = [];
            loading.dismiss();
          },
          (error) => {
            loading.dismiss();
            let title = this.langService.getString('ALERT.error');
            let msg = this.langService.getString('ERROR.delete_list');
            this.alertService.showAlert(title, msg).then(
              (alertBox) => { alertBox.present(); }
            );
          }
        );
      }
    );

  }

  notFound(item: IItem) {

    // For 0 or 1 value
    item.unavailable = 1 - item.unavailable
    
    this.itemService.updateItem(item).subscribe(
      (response: IItemResponse) => {
        this.sort();
      },
      (error) => {

      }
    );
  }


  bought(item: IItem) {
    // For 0 or 1 value
    item.isBought = 1 - item.isBought;

    this.itemService.updateItem(item).subscribe(
      (response: IItemResponse) => {
        this.sort();
      },
      (error) => {

      }
    );
  }

  search() {
    const query = this.searchItem.toLowerCase();

    this.details.items.forEach(item => {
      const show = item.name.toLowerCase().indexOf(query) > -1;
      item.hide = !show;
    });
  }

  sort() {
    this.details.items.sort((item1, item2) => {

      if (item1.isBought > item2.isBought) return 1;
      if (item1.isBought < item2.isBought) return -1;

      if (item1.unavailable > item2.unavailable) return 1;
      if (item1.unavailable < item2.unavailable) return -1;

      if (item1.name.toLowerCase() > item2.name.toLowerCase()) return 1;
      if (item1.name.toLowerCase() < item2.name.toLowerCase()) return -1;

    });
  }

}
