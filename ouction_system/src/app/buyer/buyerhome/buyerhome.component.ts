import { Component } from '@angular/core';
import { AuctionService } from '../../auction.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { provideToastr, ToastrModule, ToastrService } from 'ngx-toastr';




@Component({
  selector: 'app-buyerhome',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule,ToastrModule],
  templateUrl: './buyerhome.component.html',
  styleUrl: './buyerhome.component.css'
})
export class BuyerhomeComponent {
  auctions: any[] = [];
  bidAmount: { [key: number]: number } = {};
  isBidEnabled: { [key: number]: boolean } = {};
  isBidDisabled: { [key: number]: boolean } = {};  // New state to disable the bid inputs
  buyerUsername: string = '';
  buyerId: number | null = null;
  isBuyerLoggedIn: boolean = false;
  updateInterval!: Subscription;
  //notifications: any[] = [];
  lastBidder: { [key: number]: { buyerId: number; bidAmount: number } } = {};
  is30MinMessageDisplayed:boolean=false;
  is20SecMessageDisplayed:boolean=false;
  filteredAuctions: any[] = [];
  searchTerm: string = '';
  selectedCategory: string = '';
  uniqueCategories: string[] = [];
  issearch:boolean=false;
  isfilter:boolean=false;
  dispute: { [key: number]: string } = {};
  isvisited:boolean=false;

  constructor(private toastr: ToastrService,private auctionService: AuctionService,private http:HttpClient) {}

  ngOnInit() {
     const buyerLoginTime = new Date().toISOString();
     console.log(buyerLoginTime)
    this.auctionService.getAvailableAuctions(buyerLoginTime).subscribe((data: any) => {
    this.auctions = data;
    console.log(this.auctions);
    this.filteredAuctions = [...this.auctions];
    this.uniqueCategories = [...new Set(this.auctions.map(auction => auction.itemCategory))];
    
   
   });



   this.lastBidder= {};

    //this.fetchNotifications();
    //setInterval(() => this.fetchNotifications(), 10000); // Poll every 10 seconds
   
    //setInterval(()=> this.refreshAuctions(),1000);

  

    setInterval(() => this.refreshAuctionsStatus(), 1000);
  }


  
filterAuctions() {
  this.issearch=true;
    this.filteredAuctions = this.auctions.filter(auction => {
      const matchesName = this.searchTerm
        ? auction.itemName.toLowerCase().includes(this.searchTerm.toLowerCase())
        : true;
      const matchesCategory = this.selectedCategory
        ? auction.itemCategory === this.selectedCategory
        : true;
      return matchesName && matchesCategory;
    });
  }

  

  filterByPopularity() {
    this.isfilter=true;
    this.auctionService.getAuctionsByPopularity().subscribe((data: any) => {
      this.filteredAuctions = data;
      console.log(this.filteredAuctions);
    });
  }

  filterByEndingSoon() {
    this.isfilter=true;
    this.auctionService.getAuctionsEndingSoon().subscribe((data: any) => {
      this.filteredAuctions = data;
      console.log(this.filteredAuctions);
    });
  }


  


  


  fetchBuyerId() {
    if (this.buyerUsername) {
      this.auctionService.getBuyerIdByUsername(this.buyerUsername).subscribe(
        (response: any) => {
          this.buyerId = response.buyerId;
          this.isBuyerLoggedIn = true; // Set buyer as logged in
          console.log(`Buyer ID: ${this.buyerId}`);
        },
        (error: any) => {
          console.error('Error fetching buyer ID:', error);
          alert('Invalid username. Please try again.');
        }
      );
    }
  }




  calculateTimeLeft(endTime: string, auctionId: number): string {
    const diff = new Date(endTime).getTime() - Date.now();
    if (diff <= 0) {
      this.isBidDisabled[auctionId] = true;  // Disable bidding if the auction is over
      return "Time Up";
    } else {
      const hours = Math.floor(diff / (1000 * 60 * 60));
      const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((diff % (1000 * 60)) / 1000);
      return `${hours}h ${minutes}m ${seconds}s`;
    }
  }

  refreshAuctionsStatus() {
    this.auctions.forEach((auction) => {
      const diff = new Date(auction.bidEndTime).getTime() - Date.now();
      if (diff <= 10000 && auction.status !== 'CLOSED') {
        // Close the auction
        this.closeAuction(auction.auctionId);
        auction.status = 'CLOSED';
  
        
        
          // Fetch the last bidder for the auction
          this.auctionService.getLastBidder(auction.auctionId).subscribe(
            (response: any) => {
              // Store the last bidder details for the auction
              this.lastBidder[auction.auctionId] = response;
              console.log('Last bidder:', this.lastBidder);
            },
            (error: any) => {
              console.error('Error fetching last bidder:', error);
            }
          );
        
      }

      // 30 minutes remaining
    if (diff <= 1800000 && diff > 1790000 && auction.status !== 'CLOSED') {
      this.toastr.info(`The bid on ${auction.itemName} ends in 30 mins`);
    }

    // 20 seconds remaining
    if (diff <= 20000 && diff > 19000 && auction.status !== 'CLOSED') {
      this.toastr.info(`The bid on ${auction.itemName} is going to end in 20 seconds`);
    }



      // Disable bid input and button if time is less than or equal to 10 seconds
      if (diff <= 10000) {
        this.isBidDisabled[auction.auctionId] = true;
      } else {
        this.isBidDisabled[auction.auctionId] = false;
      }

    });
  }







purchaseItem(auctionId: number) {
  const auction = this.auctions.find((a) => a.auctionId === auctionId); // Find the auction with the given ID

  if (auction && auction.itemName && auction.currentBid) {
    alert(`Purchased successfully ${auction.itemName} for ${auction.currentBid}`);
  } else {
    alert("Error: Item name or bid information is missing.");
  }


  const logData = {
    username: this.buyerUsername,
    action: "Purchase",
    detail: `${this.buyerUsername} purchased ${auction.itemName} successfully`
  };

  this.auctionService.createAuditLog(logData).subscribe(
    (response) => {
      //alert(response);
      // Reset form fields
    },
    (error) => {
      console.error('Error creating audit log:', error);
      //alert('Failed to create audit log.');
    }
  );

  

}

  closeAuction(auctionId: number) {
    this.auctionService.closeAuction(auctionId).subscribe(
      (response: any) => {
        console.log(`Auction ${auctionId} closed successfully.`);
      },
      (error: any) => {
        //console.error(`Failed to close auction ${auctionId}: ${error.message}`);
      }
    );
  }


  enableBidButton(auctionId: number, currentBid: number) {
    this.isBidEnabled[auctionId] = this.bidAmount[auctionId] > currentBid;
  }

  placeBid(auctionId: number) {
    const bidData = {
        auctionId: auctionId,
        buyerId: this.buyerId, // Replace with the actual logged-in buyer ID
        bidAmount: this.bidAmount[auctionId]
    };

    this.auctionService.placeBid(bidData).subscribe(
        (response: any) => {
            if (response && typeof response === 'string') {
                //alert(response); // Display success message
                this.toastr.success(response);
            }
            this.ngOnInit(); // Refresh auctions
            
        },
        (error: any) => {
            const errorMessage = error.error || "An unexpected error occurred.";
            if (error.status === 400) {
              //alert(error.error); // Error message from server
              this.toastr.error(error.error)
            }
        }
    );



    const logData = {
      username: this.buyerUsername,
      action: "Placing Bid",
      detail: `${this.buyerUsername} placed Bid successfully`
    };
  
    this.auctionService.createAuditLog(logData).subscribe(
      (response) => {
        //alert(response);
        // Reset form fields
      },
      (error) => {
        console.error('Error creating audit log:', error);
        //alert('Failed to create audit log.');
      }
    );



}



submitDispute(auctionId: number) {
  console.log(this.buyerUsername);
  const issue = this.dispute[auctionId];

  if (!issue || issue.trim() === '') {
    alert('Please enter a valid dispute.');
    return;
  }

  this.auctionService.submitDispute(auctionId, this.buyerUsername, issue).subscribe(
    (response) => {
      this.toastr.success('Dispute submitted successfully.');
      //alert('Dispute submitted successfully.');
      this.dispute[auctionId] = ''; // Clear the dispute input
    },
    (error) => {
      alert('Error submitting dispute.');
    }
  );
}




}
