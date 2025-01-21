import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AdminReview } from '../../common/model/adminReview';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminReviewService {

  constructor(private http: HttpClient) { }

  getReviews(): Observable<Array<AdminReview>> {
    return this.http.get<Array<AdminReview>>("/api/admin/reviews");
  }
  moderate(id: number): Observable<void> {
    return this.http.put<void>(`/api/admin/reviews/${id}/moderate`, ''); // po co ''?
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`/api/admin/reviews/${id}`);
  }

}
