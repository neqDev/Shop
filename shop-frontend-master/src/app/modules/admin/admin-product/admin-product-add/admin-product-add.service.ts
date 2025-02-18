import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AdminProductUpdate } from '../model/adminProductUpdate';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminProductAddService {

  constructor(private http: HttpClient) {

   }

  saveNewProduct(product: AdminProductUpdate) : Observable<AdminProductUpdate>{
    return this.http.post<AdminProductUpdate>("/api/admin/products", product);
  }
}
