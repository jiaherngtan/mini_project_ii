import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';
import { Subscription } from 'rxjs';

import { Movie, People } from '../models';

import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit, AfterViewInit {

  query!: string
  searchMovies: Movie[] = []
  searchPeople: People[] = []
  params$!: Subscription

  constructor(
    private activatedRoute: ActivatedRoute,
    private title: Title,
    private viewportScroller: ViewportScroller,
    private movieService: MovieService) { }

  ngOnInit(): void {
    // this.router.events.subscribe(event => {
    //   if (event instanceof NavigationEnd) {
    //     window.scrollTo(0, 0)
    //   }
    // })
    this.params$ = this.activatedRoute.queryParamMap.subscribe(
      (params) => {
        this.query = params.get('query')!
        this.title.setTitle(`${this.query} - Find`)
        console.info('>>> query: ', this.query)
        this.movieService.getMovieBySearch(this.query)
          .then(result => {
            console.info('>>> result search movies: ', result)
            this.searchMovies = result
          })
          .catch(error => {
            console.error('>>> error search movies: ', error)
          })
        this.movieService.getPeopleBySearch(this.query)
          .then(result => {
            console.info('>>> result search people: ', result)
            this.searchPeople = result
          })
          .catch(error => {
            console.error('>>> error search people: ', error)
          })
      }
    )
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

}
