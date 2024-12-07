import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {
  private apiUrl = 'http://localhost:8080/api/items/fetch';

  constructor(private http: HttpClient) {}

  getItems() {
    return this.http.get(`${this.apiUrl}`);
  }
}
