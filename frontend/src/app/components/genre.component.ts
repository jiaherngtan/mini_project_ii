import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';
import { Subscription } from 'rxjs';

import { Movie } from '../models';

import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-genre',
  templateUrl: './genre.component.html',
  styleUrls: ['./genre.component.css']
})
export class GenreComponent implements OnInit, AfterViewInit {

  id!: number
  genre!: string
  bannerUrl?: string
  genreMovies: Movie[] = []
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
    this.params$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.id = params['id']
        this.genre = params['genre']
        this.title.setTitle(`Top ${this.genre} Movies`)
        this.movieService.getMoviesByGenre(this.id, this.genre)
          .then(result => {
            console.info('>>> result movie: ', result)
            this.genreMovies = result
            this.bannerUrl = this.genreMovies.at(Math.floor(Math.random() * this.genreMovies.length))?.backdropUrl
          })
          .catch(error => {
            console.error('>>> error movie: ', error)
          })
      }
    )
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

}
