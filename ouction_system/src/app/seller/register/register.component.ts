import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuctionService } from '../../auction.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  item: any;
  username: string = '';
  startPrice: number = 0;
  reservePrice: number = 0;
  startBidTime: string = '';
  endBidTime: string = '';

  constructor(private router: Router, private auctionsService: AuctionService,private http:HttpClient,private toastrservice:ToastrService) {
    const navState = this.router.getCurrentNavigation()?.extras.state;
    if (navState) {
      this.item = navState['item'];
    }
  }

  submitForm() {
    const auctionData = {
      itemId: this.item.itemId,
      username: this.username,
      startPrice: this.startPrice,
      reservePrice: this.reservePrice,
      startBidTime: this.startBidTime,
      endBidTime: this.endBidTime
    };
  
    this.auctionsService.createAuction(auctionData).subscribe(
      (response: any) => {
        //alert(response); // Success message from server
        this.toastrservice.success(response);
        this.username='';
        this.startPrice=0;
        this.reservePrice=0;
        this.startBidTime='';
        this.endBidTime='';
        

      },
      (error: any) => {
        if (error.status === 400) {
          //alert(error.error); // Error message from server
          this.toastrservice.error(error.error);
          this.username='';
        this.startPrice=0;
        this.reservePrice=0;
        this.startBidTime='';
        this.endBidTime='';
        } else {
          console.error('Error registering auction:', error);
          alert('An unexpected error occurred.');
          this.username='';
        this.startPrice=0;
        this.reservePrice=0;
        this.startBidTime='';
        this.endBidTime='';
        }
      }

        
    );
  }
  

}
