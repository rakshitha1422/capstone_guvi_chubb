import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuctionService } from '../../auction.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  username: string = "";
  password: string = "";
  email: string = "";
  role: string = "";
  uniqueCode: string = "";
  enteredUniqueCode: string = "";
  isVerified: boolean = false;;
  isUsernameAvailable: boolean | null = null; 

  constructor(private http: HttpClient,private auctionservice:AuctionService,private toastrservice:ToastrService) {}


  // Check if the username is unique
  checkUsernameAvailability() {
    if (!this.username.trim()) {
      this.isUsernameAvailable = null; // Reset when input is empty
      return;
    }
  
    this.http.get<{ available: boolean }>(`http://localhost:8080/api/check-username/${this.username}`)
      .subscribe(
        (response) => {
          this.isUsernameAvailable = response.available;
        },
        (error) => {
          console.error('Error checking username availability:', error);
          this.isUsernameAvailable = null; // Optional: Reset or handle differently
        }
      );
  }
  

  getUniqueCode() {
    if (!this.email.trim()) {
      alert("Please enter a valid email.");
      return;
    }
    
    const body = { email: this.email };
    
    this.http.post("http://localhost:8080/api/get-unique-code", body).subscribe((response: any) => {
      if (response && response.uniqueCode) {
        this.uniqueCode = response.uniqueCode;
        //alert(`Unique Code sent to your email: ${this.email}`);
        this.toastrservice.success(`Unique Code sent to your email: ${this.email}`)
      } else {
        //alert("Failed to generate unique code.");
        this.toastrservice.error("Failed to generate unique code.");
      }
    });
  }

  // Verify the entered unique code
  verifyCode() {
    const body = { enteredUniqueCode: this.enteredUniqueCode };
    console.log(this.enteredUniqueCode);
    this.http.post("http://localhost:8080/api/verify-unique-code", body).subscribe((response: any) => {
      if (response && response.verified) {
        this.isVerified = true;
        //alert("Code verified successfully");
        this.toastrservice.success("Code verified successfully");
      } else {
        //alert("Verification failed. Please try again.");
        this.toastrservice.error("Verification failed. Please try again.");
      }
    });
  }

  // Register the user
  register() {

    if (!this.isUsernameAvailable) {
      alert("Please choose a unique username before submitting.");
      return;
    }

    if (!this.isVerified) {
      alert("Please verify the unique code before submitting.");
      return;
    }

    let bodyData = {
      username: this.username,
      userpassword: this.password,
      useremail: this.email,
      userrole: this.role,
      userverify: this.isVerified
    };

    this.http.post("http://localhost:8080/api/usersignup/save", bodyData, { responseType: 'text' }).subscribe((resultData: any) => {
      console.log(resultData);
      //alert("User Registered Successfully");
      this.toastrservice.success("User Registered Successfully");

      // Reset fields
      this.username = '';
      this.email = '';
      this.password = '';
      this.role = '';
      this.uniqueCode = '';
      this.enteredUniqueCode = '';
      this.isVerified = false;
      this.isUsernameAvailable = null;
    });


    const logData = {
      username: this.username,
      action: "Signing in",
      detail: `${this.username} Signed in successfully`
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
