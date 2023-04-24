import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';
import { Subscription } from 'rxjs';

import { Crew, Movie } from '../models';
import { WatchlistRequest } from '../WatchlistRequest';
import { WatchedRequest } from '../WatchedRequest';

import { MovieService } from '../services/movie.service';
import { WatchlistService } from '../services/watchlist.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit, AfterViewInit {

  id!: string
  movie!: Movie
  crews: Crew[] = []
  similarMovies: Movie[] = []
  params$!: Subscription
  email!: string
  form!: FormGroup

  constructor(
    private activatedRoute: ActivatedRoute,
    public afAuth: AngularFireAuth,
    private fb: FormBuilder,
    private router: Router,
    private title: Title,
    private viewportScroller: ViewportScroller,
    private movieService: MovieService,
    private watchlistService: WatchlistService
  ) {
    this.afAuth.authState.subscribe(user => {
      this.email = user?.email!
      console.info(this.email)
    })
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        window.scrollTo(0, 0)
      }
    })
    this.form = this.createForm()
    this.params$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.id = params['id']
        this.movieService.getMovie(this.id)
          .then(result => {
            console.info('>>> result movie: ', result)
            this.movie = result
            this.title.setTitle(this.movie.title)
          })
          .catch(error => {
            console.error('>>> error movie: ', error)
          })
        this.movieService.getCrewByMovie(this.id)
          .then(result => {
            console.info('>>> result crews: ', result)
            this.crews = result
          })
          .catch(error => {
            console.error('>>> error crews: ', error)
          })
        this.movieService.getSimilarMovies(this.id)
          .then(result => {
            console.info('>>> result similar: ', result)
            this.similarMovies = result
          })
          .catch(error => {
            console.error('>>> error similar: ', error)
          })
      }
    )
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

  private createForm(): FormGroup {
    return this.fb.group({
      rating: this.fb.control<number>(0, [Validators.required, Validators.min(1), Validators.max(10)]),
      review: this.fb.control<string>('', [Validators.required])
    })
  }

  addToWantToWatch() {
    if (null == this.email) {
      this.router.navigate(['/login'])
    } else {
      const watchlistRequest = new WatchlistRequest(this.email, this.movie)
      this.watchlistService.addToWantToWatch(watchlistRequest)
        .then(result => {
          console.info('>>> result adding to want to watch: ', result)
        })
        .catch(error => {
          console.error('>>> error adding to want to watch: ', error)
        })
    }
  }

  addToWatched() {
    if (null == this.email) {
      this.router.navigate(['/login'])
    } else {
      const watchedRequest = new WatchedRequest(this.email, this.movie, this.form.value["rating"], this.form.value["review"])
      this.watchlistService.addToWatched(watchedRequest)
        .then(result => {
          console.info('>>> result adding to watched: ', result)
        })
        .catch(error => {
          console.error('>>> error adding to watched: ', error)
        })
      this.form.reset()
    }
  }

}
