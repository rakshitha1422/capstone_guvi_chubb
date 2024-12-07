import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminhomeComponent } from './adminhome/adminhome.component';
import { AdditemComponent } from './additem/additem.component';
import { DisputesComponent } from './disputes/disputes.component';
import { BuyerreportComponent } from './buyerreport/buyerreport.component';
import { SellerreportComponent } from './sellerreport/sellerreport.component';

const routes: Routes = [
  {
    path:'',component:AdminhomeComponent
  },
  {
    path:'additem',component:AdditemComponent
  },
  {
    path:'disputes',component:DisputesComponent
  },
  {
    path:'buyerreport',component:BuyerreportComponent
  },
  {
    path:'sellerreport',component:SellerreportComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
