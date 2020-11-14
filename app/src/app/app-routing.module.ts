import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './services/auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  
  { path: 'login', loadChildren: () => import('./views/login/login.module').then(m => m.LoginPageModule) },
  { path: 'register', loadChildren: () => import('./views/register/register.module').then( m => m.RegisterPageModule) },

  { path: 'settings', loadChildren: () => import('./views/settings/settings.module').then( m => m.SettingsPageModule) },

  { path: 'shops', loadChildren: () => import('./views/shops/shops.module').then(m => m.ShopsPageModule), canActivate: [AuthGuardService] },
  { path: 'cart/:id', loadChildren: () => import('./views/cart/cart.module').then(m => m.CartPageModule), canActivate: [AuthGuardService] },
  
  { path: 'change-password', loadChildren: () => import('./views/profile/change-password/change-password.module').then( m => m.ChangePasswordPageModule) },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
