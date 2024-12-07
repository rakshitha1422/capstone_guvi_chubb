import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuyerhomeComponent } from './buyerhome/buyerhome.component';
import { BuyerstartComponent } from './buyerstart/buyerstart.component';
import { BuyerreportComponent } from './buyerreport/buyerreport.component';

const routes: Routes = [
  {
    path:'bidnow',component:BuyerhomeComponent
  },
  {
    path:'',component:BuyerstartComponent
  },
  {
    path:'buyerreport',component:BuyerreportComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BuyerRoutingModule { }
