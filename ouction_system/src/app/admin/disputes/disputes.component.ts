import { Component } from '@angular/core';
import { AuctionService } from '../../auction.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@Component({
  selector: 'app-disputes',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './disputes.component.html',
  styleUrl: './disputes.component.css'
})
export class DisputesComponent {
  disputes: any[] = []; // Holds the disputes fetched from the server

  constructor(private auctionService: AuctionService) {}

  ngOnInit(): void {
    this.loadDisputes();
  }

  loadDisputes(): void {
    this.auctionService.getDisputes().subscribe(
      (data) => {
        this.disputes = data;
      },
      (error) => {
        alert('Error fetching disputes');
      }
    );
  }

  resolveDispute(dispute: any): void {
    this.auctionService.resolveDispute(dispute.disputeId).subscribe(
      (response) => {
        alert('Dispute resolved successfully.');
        this.loadDisputes(); // Refresh the disputes list
      },
      (error) => {
        alert('Error resolving dispute.');
      }
    );
  }


}
