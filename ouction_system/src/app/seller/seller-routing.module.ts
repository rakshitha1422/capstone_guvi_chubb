import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SellerListComponent } from './seller-list/seller-list.component';
import { RegisterComponent } from './register/register.component';
import { SellerreportComponent } from './sellerreport/sellerreport.component';

const routes: Routes = [
  {
    path:'',component:SellerListComponent
  },
  {
    path:'register',component:RegisterComponent
  },
  {
    path:'report',component:SellerreportComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellerRoutingModule { }
