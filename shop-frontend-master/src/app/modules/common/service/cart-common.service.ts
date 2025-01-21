import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CartSummary } from '../model/cart/cartSummary';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartCommonService {

  constructor(private http: HttpClient) { }

  getCart(id: number): Observable<CartSummary> {
    return this.http.get<CartSummary>("/api/carts/" + id);
  }
}
