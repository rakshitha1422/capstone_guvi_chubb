import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-additem',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './additem.component.html',
  styleUrl: './additem.component.css'
})
export class AdditemComponent {
  auctionId: number=0;
  name: string="";
  description: string="";
  image: string="";
  category: string="";
  tags: string="";
  

  constructor(private http: HttpClient,private toastrservice:ToastrService) {}

  // Method to handle form submission
  submitForm() {
    let newItem= {
      auctionId: this.auctionId,
      name: this.name,
      description: this.description,
      image: this.image,
      category: this.category,
      tags: this.tags,
      
    };

    // Send the data to the backend API
    this.http.post(`http://localhost:8080/api/items/save`, newItem, { responseType: 'text' })
      .subscribe(
        (response:any) => {
          console.log('Item added successfully', response);
          //alert('item added successfully')
          this.toastrservice.success("item added successfully");

          this.auctionId=0,
          this.name="",
          this.category="",
          this.description="",
          this.image="",
          this.tags=""
          // Handle success (e.g., show a success message, reset form, etc.)
        },
        (error:any) => {
          console.error('Error adding item', error);
          // Handle error (e.g., show an error message)
        }
      );
  }
}
