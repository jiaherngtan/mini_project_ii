import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

import { WatchedRequest } from '../WatchedRequest';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private httpClient: HttpClient) { }

  // for development
  addReviewArg = '/api/watchlist/add/review'
  editReviewArg = '/api/watchlist/update/review'
  getReviewsArg(id: string) {
    return `/api/watchlist/reviews/${id}`
  }
  getSortedReviewsArg(id: string, pattern: string) {
    return `/api/watchlist/reviews/${id}/${pattern}`
  }

  // for production
  // addReviewArg = 'http://tmdb.up.railway.app/api/watchlist/add/review'
  // editReviewArg = 'http://tmdb.up.railway.app/api/watchlist/update/review'
  // getReviewsArg(id: string) {
  //   return `http://tmdb.up.railway.app/api/watchlist/reviews/${id}`
  // }
  // getSortedReviewsArg(id: string, pattern: string) {
  //   return `https//tmdb.up.railway.app/api/watchlist/reviews/${id}/${pattern}`
  // }

  addReview(watchedRequest: WatchedRequest): Promise<WatchedRequest> {
    return firstValueFrom(
      this.httpClient.post<WatchedRequest>(this.addReviewArg, watchedRequest)
    )
  }

  editReview(watchedRequest: WatchedRequest): Promise<WatchedRequest> {
    return firstValueFrom(
      this.httpClient.put<WatchedRequest>(this.editReviewArg, watchedRequest)
    )
  }

  getReviews(id: string): Promise<WatchedRequest[]> {
    return firstValueFrom(
      this.httpClient.get<WatchedRequest[]>(this.getReviewsArg(id))
    )
  }

  getSortedReviews(id: string, pattern: string): Promise<WatchedRequest[]> {
    console.info(id)
    console.info(pattern)
    return firstValueFrom(
      this.httpClient.get<WatchedRequest[]>(this.getSortedReviewsArg(id, pattern))
    )
  }

}
