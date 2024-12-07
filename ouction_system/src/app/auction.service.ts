import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuctionService {
  private apiUrl = 'http://localhost:8080/api/auctions';
  private logUrl = 'http://localhost:8080/api/audit-logs';

  constructor(private http: HttpClient) {}

  createAuction(auctionData: any) {
    return this.http.post(`${this.apiUrl}/register`, auctionData, { responseType: 'text' });
  }

  
  // Fetch buyer ID by username
  getBuyerIdByUsername(username: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/getBuyerIdByUsername?username=${username}`);
  }

  getAvailableAuctions(buyerLoginTime: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/available-items?buyerLoginTime=${buyerLoginTime}`);
  }

  placeBid(bidRequest: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/placeBid`, bidRequest, { responseType: 'text' });
}

  closeAuction(auctionId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/close/${auctionId}`, { responseType: 'text' });
  }


  getLastBidder(auctionId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${auctionId}/lastBidder`);
}



getAuctionsByPopularity(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/filter/popularity`);
}

getAuctionsEndingSoon(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/filter/ending-soon`);
}


submitDispute(auctionId: number, username: string, issue: string): Observable<any> {
  const payload = { auctionId, username, issue };
  return this.http.post(`${this.apiUrl}/submitDispute`, payload, { responseType: 'text' });
}


getDisputes(): Observable<any> {
  return this.http.get(`${this.apiUrl}/disputes`);
}


resolveDispute(disputeId: number): Observable<any> {
  return this.http.put(`${this.apiUrl}/resolveDispute`, { disputeId }, { responseType: 'text' });
}





getSellerBidCounts(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/seller-bid-counts`);
}

getSellerDetails():Observable<any[]>{
  return this.http.get<any[]>(`${this.apiUrl}/getsellerdetails`);
}


createAuditLog(logData: { username: string; action: string; detail: string }): Observable<string> {
  return this.http.post<string>(`${this.logUrl}/create`, logData);
}


}
