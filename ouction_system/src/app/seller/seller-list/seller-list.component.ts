import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';


import { Router } from '@angular/router';
import { ItemsService } from '../../items.service';



@Component({
  selector: 'app-seller-list',
  standalone: true,
  imports: [CommonModule, FormsModule,HttpClientModule],
  templateUrl: './seller-list.component.html',
  styleUrls: ['./seller-list.component.css']
})
export class SellerListComponent {
  items: any[] = [];

  constructor(private itemsService: ItemsService, private router: Router) {}

  ngOnInit(): void {
    this.fetchItems();
  }

  fetchItems() {
    this.itemsService.getItems().subscribe(
      (response: any) => {
        this.items = response;
      },
      (error:any) => {
        console.error('Error fetching items:', error);
      }
    );
  }

  navigateToRegister(item: any) {
    this.router.navigate(['/seller/register'], { state: { item } });
  }
}
