import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

import { Movie } from '../models';
import { WatchedRequest } from '../WatchedRequest';
import { WatchlistRequest } from '../WatchlistRequest';

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  constructor(private httpClient: HttpClient) { }

  // headers = new HttpHeaders({
  //   'Access-Control-Allow-Origin': '*',
  //   'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  //   'Access-Control-Allow-Headers': 'Origin, Content-Type, Accept, Authorization, X-Requested-With'
  // })

  // for development
  addToWantToWatchArg = '/api/watchlist/add/pend'
  addToWatchedArg = '/api/watchlist/add/done'
  updateToWatchedArg = '/api/watchlist/update'
  removeFromWatchlistArg(email: string, movieId: string) {
    return `/api/watchlist/remove/${email}/${movieId}`
  }
  getWatchlistArg(id: string, state: string) {
    return `/api/watchlist/${id}/${state}`
  }

  // for production
  // addToWantToWatchArg = 'http://tmdb.up.railway.app/api/watchlist/add/pend'
  // addToWatchedArg = 'http://tmdb.up.railway.app/api/watchlist/add/done'
  // updateToWatchedArg = 'http://tmdb.up.railway.app/api/watchlist/update'
  // removeFromWatchlistArg(email: string, movieId: string) {
  //   return `http://tmdb.up.railway.app/api/watchlist/remove/${email}/${movieId}`
  // }
  // getWatchlistArg(id: string, state: string) {
  //   return `http://tmdb.up.railway.app/api/watchlist/${id}/${state}`
  // }

  addToWantToWatch(watchlistRequest: WatchlistRequest): Promise<any> {
    // const headers = new HttpHeaders()
    //   .set("content-type", "application/json")
    //   .set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
    //   .set("Access-Control-Allow-Origin", "*")
    // console.info(watchlistRequest)
    return firstValueFrom(
      this.httpClient.post<any>(this.addToWantToWatchArg, watchlistRequest)
    )
  }

  addToWatched(watchedRequest: WatchedRequest): Promise<WatchedRequest> {
    return firstValueFrom(
      this.httpClient.post<WatchedRequest>(this.addToWatchedArg, watchedRequest)
    )
  }

  updateToWatched(watchedRequest: WatchedRequest): Promise<WatchedRequest> {
    return firstValueFrom(
      this.httpClient.put<WatchedRequest>(this.updateToWatchedArg, watchedRequest)
    )
  }

  removeFromWatchlist(email: string, movieId: string): Promise<any> {
    return firstValueFrom(
      this.httpClient.delete(this.removeFromWatchlistArg(email, movieId))
    )
  }

  getWatchlist(id: string, state: string): Promise<Movie[]> {
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getWatchlistArg(id, state))
    )
  }

}
