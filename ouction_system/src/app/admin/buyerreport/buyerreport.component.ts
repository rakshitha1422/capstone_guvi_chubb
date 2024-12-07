import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-buyerreport',
  standalone: true,
  imports: [DatePipe,CurrencyPipe,CommonModule ,FormsModule],
  templateUrl: './buyerreport.component.html',
  styleUrls: ['./buyerreport.component.css'],
})
export class BuyerreportComponent implements OnInit {
  bids: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchBids();
  }

  fetchBids(): void {
    this.http.get<any[]>('http://localhost:8080/api/auctions/details').subscribe(
      (data) => {
        this.bids = data;
      },
      (error) => {
        console.error('Error fetching bids:', error);
      }
    );
  }
}
