import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

import { Crew, Genre, Movie, People } from '../models';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private httpClient: HttpClient) { }

  // for development
  getPopularMoviesArg = '/api/popular'
  getTopRatedMoviesArg = '/api/top'
  getNowPlayingMoviesArg = '/api/now'
  getSimilarMoviesArg(id: string) {
    return `/api/movie/${id}/similar`
  }
  getMovieByGenreArg(id: number, genre: string) {
    return `/api/genre/${id}/${genre}`
  }
  getMovieBySearchArg(query: string) {
    return `/api/search/${query}/movie`
  }
  getPeopleBySearchArg(query: string) {
    return `/api/search/${query}/people`
  }
  getMovieArg(id: string) {
    return `/api/movie/${id}`
  }
  getPeopleArg(id: string) {
    return `/api/people/${id}`
  }
  getPopularPeopleArg = '/api/people/popular'
  getCrewByMovieArg(id: string) {
    return `/api/movie/${id}/crew`
  }
  getGenresArg = '/api/genres'

  // for production
  // getPopularMoviesArg = 'http://tmdb.up.railway.app/api/popular'
  // getTopRatedMoviesArg = 'http://tmdb.up.railway.app/api/top'
  // getNowPlayingMoviesArg = 'http://tmdb.up.railway.app/api/now'
  // getSimilarMoviesArg(id: string) {
  //   return `http://tmdb.up.railway.app/api/movie/${id}/similar`
  // }
  // getMovieByGenreArg(id: number, genre: string) {
  //   return `http://tmdb.up.railway.app/api/genre/${id}/${genre}`
  // }
  // getMovieBySearchArg(query: string) {
  //   return `http://tmdb.up.railway.app/api/search/${query}/movie`
  // }
  // getPeopleBySearchArg(query: string) {
  //   return `http://tmdb.up.railway.app/api/search/${query}/people`
  // }
  // getMovieArg(id: string) {
  //   return `http://tmdb.up.railway.app/api/movie/${id}`
  // }
  // getPeopleArg(id: string) {
  //   return `http://tmdb.up.railway.app/api/people/${id}`
  // }
  // getPopularPeopleArg = 'http://tmdb.up.railway.app/api/people/popular'
  // getCrewByMovieArg(id: string) {
  //   return `http://tmdb.up.railway.app/api/movie/${id}/crew`
  // }
  // getGenresArg = 'http://tmdb.up.railway.app/api/genres'

  movies: Movie[] = []

  getPopularMovies(): Promise<Movie[]> {
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getPopularMoviesArg)
    )
  }

  getTopRatedMovies(): Promise<Movie[]> {
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getTopRatedMoviesArg)
    )
  }

  getNowPlayingMovies(): Promise<Movie[]> {
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getNowPlayingMoviesArg)
    )
  }

  getSimilarMovies(id: string): Promise<Movie[]> {
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getSimilarMoviesArg(id))
    )
  }

  getMoviesByGenre(id: number, genre: string): Promise<Movie[]> {
    console.info(id, genre)
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getMovieByGenreArg(id, genre))
    )
  }

  getMovieBySearch(query: string): Promise<Movie[]> {
    return firstValueFrom(
      this.httpClient.get<Movie[]>(this.getMovieBySearchArg(query))
    )
  }

  getPeopleBySearch(query: string): Promise<People[]> {
    return firstValueFrom(
      this.httpClient.get<People[]>(this.getPeopleBySearchArg(query))
    )
  }

  getMovie(id: string): Promise<Movie> {
    return firstValueFrom(
      this.httpClient.get<Movie>(this.getMovieArg(id))
    )
  }

  getPeople(id: string): Promise<People> {
    return firstValueFrom(
      this.httpClient.get<People>(this.getPeopleArg(id))
    )
  }

  getPopularPeople(): Promise<People[]> {
    return firstValueFrom(
      this.httpClient.get<People[]>(this.getPopularPeopleArg)
    )
  }

  getCrewByMovie(id: string): Promise<Crew[]> {
    return firstValueFrom(
      this.httpClient.get<Crew[]>(this.getCrewByMovieArg(id))
    )
  }

  getGenres(): Promise<Genre[]> {
    return firstValueFrom(
      this.httpClient.get<Genre[]>(this.getGenresArg)
    )
  }

}
