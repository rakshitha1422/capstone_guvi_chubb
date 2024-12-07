import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuctionService } from '../../auction.service';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(private http: HttpClient,private router: Router,private auctionservice:AuctionService) {}

  login() {
    if (!this.username.trim() || !this.password.trim()) {
      this.errorMessage = "Both fields are required.";
      return;
    }

    const loginData = { username: this.username, password: this.password };

    this.http.post('http://localhost:8080/api/login', loginData).subscribe(
      (response: any) => {
        this.errorMessage = null;
        this.successMessage = response.message;

        if (response.role === 'Admin') {
          this.router.navigate(['/admin']); // Navigate to SellerComponent
        }
        else if(response.role==='Seller'){
          this.router.navigate(['/seller'])
        }
        else if(response.role==='Buyer'){
          this.router.navigate(['/buyer'])
        }
      },
      (error: { error: { message: string; }; }) => {
        this.successMessage = null;
        this.errorMessage = error.error.message || 'An error occurred.';
      }
    );



    const logData = {
      username: this.username,
      action: "Logging",
      detail: `${this.username} logged in successfully`
    };

    this.auctionservice.createAuditLog(logData).subscribe(
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

}
