import { Injectable } from '@angular/core';
import { UploadResponse } from './model/UploadResponse';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminProductImageService {

  constructor(private http: HttpClient) { }

  uploadImage(formData: FormData): Observable<UploadResponse>{
    return this.http.post<UploadResponse>('api/admin/products/upload-image', formData);
  }
}
