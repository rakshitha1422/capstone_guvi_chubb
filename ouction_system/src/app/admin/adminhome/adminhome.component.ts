import { Component, OnInit } from '@angular/core';
import { AdditemComponent } from '../additem/additem.component';
import { RouterOutlet } from '@angular/router';
import { ItemsService } from '../../items.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-adminhome',
  standalone: true,
  imports: [AdditemComponent,RouterOutlet,FormsModule,CommonModule,HttpClientModule],
  templateUrl: './adminhome.component.html',
  styleUrl: './adminhome.component.css'
})
export class AdminhomeComponent implements OnInit{
  items: any[] = [];


  constructor(private itemsService: ItemsService) {}

  ngOnInit(): void {
    this.fetchItems();
  }

  fetchItems() {
    this.itemsService.getItems().subscribe(
      (response: any) => {
        this.items = response;
        console.log(response)
      },
      (error:any) => {
        console.error('Error fetching items:', error);
      }
    );
  }
}
