import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { AuctionService } from '../../auction.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';


Chart.register(...registerables)

@Component({
  selector: 'app-sellerreport',
  standalone: true,
  imports: [CurrencyPipe,CommonModule,FormsModule],
  templateUrl: './sellerreport.component.html',
  styleUrl: './sellerreport.component.css'
})
export class SellerreportComponent implements OnInit{
  sellerdetails: any[] = [];



  constructor(private auctionservice:AuctionService){

  }

  ngOnInit(): void {
    this.auctionservice.getSellerBidCounts().subscribe((data) => {
      console.log(data);
      this.renderChart(data);
    });
    this.auctionservice.getSellerDetails().subscribe((data)=>{
      this.sellerdetails=data;
    });
  }


  private renderChart(data: any): void {
    const labels = data.map((d: any) => d.username);
    const bidCounts = data.map((d: any) => d.bidCount);

    new Chart('barChart', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Bid Counts',
            data: bidCounts,
            backgroundColor: 'rgba(75, 192, 192, 0.6)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: false }
        },
        scales: {
          x: { title: { display: true, text: 'Sellers' } },
          y: { title: { display: true, text: 'Bid Counts' } }
        }
      }
    });
  }





}
