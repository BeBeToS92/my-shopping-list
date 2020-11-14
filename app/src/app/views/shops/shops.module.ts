import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ShopsPageRoutingModule } from './shops-routing.module';

import { ShopsPage } from './shops.page';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ShopsPageRoutingModule,
    TranslateModule
  ],
  declarations: [ShopsPage]
})
export class ShopsPageModule {}
