import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LoginComponent } from './login/login.component';
import { BuyerModule } from '../buyer/buyer.module';


const routes: Routes = [
  { path: '', component: SignUpComponent }, // Default route for the Users module
  {path:'login',component:LoginComponent},
  {path:'signup',component:SignUpComponent},
  {
    path:'seller',
    loadChildren:() => import('../seller/seller.module').then((m) => m.SellerModule) 
  },
  {
    path:'admin',
    loadChildren:() => import('../admin/admin.module').then((m)=>m.AdminModule)

  },
  {
    path:'buyer',
    loadChildren:()=> import('../buyer/buyer.module').then((m)=>BuyerModule)
  }
 
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
