import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';

import { Movie } from '../models';
import { WatchedRequest } from '../WatchedRequest';

import { MovieService } from '../services/movie.service';
import { ReviewService } from '../services/review.service';
import { UserService } from '../services/user.service';
import { WatchlistService } from '../services/watchlist.service';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit, AfterViewInit {

  email!: string
  id!: string
  form!: FormGroup
  addReviewForm!: FormGroup
  editReviewForm!: FormGroup
  topRatedMovies: Movie[] = []
  pendMovies: Movie[] = []
  doneMovies: Movie[] = []

  constructor(
    public afAuth: AngularFireAuth,
    private fb: FormBuilder,
    private title: Title,
    private viewportScroller: ViewportScroller,
    private movieService: MovieService,
    private reviewService: ReviewService,
    private userService: UserService,
    private watchlistService: WatchlistService
  ) {
    // this.afAuth.authState.subscribe(user => {
    //   this.email = user?.email!
    //   this.userService.getUserDetails(this.email)
    //     .then(result => {
    //       console.info('>>> result user id: ', result.id)
    //       this.id = result.id
    //     })
    //     .catch(error => {
    //       console.error('>>> error user id: ', error)
    //     })
    // })
  }

  async ngOnInit() {
    this.title.setTitle('Watchlist')
    this.form = this.createForm()
    this.addReviewForm = this.createForm()
    this.editReviewForm = this.createForm()
    this.afAuth.authState.subscribe(user => {
      this.email = user?.email!
      this.userService.getUserDetails(this.email)
        .then(result => {
          console.info('>>> result user id: ', result.id)
          this.id = result.id
          this.getWatchlist()
        })
        .catch(error => {
          console.error('>>> error user id: ', error)
        })
    })
    if (this.pendMovies.length == 0 && this.doneMovies.length == 0)
      this.topRatedMovies = await this.movieService.getTopRatedMovies()
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

  addToWatched(index: number) {
    let movie = this.pendMovies.at(index)
    const watchedRequest = new WatchedRequest(this.email, movie!, this.form.value["rating"], this.form.value["review"])
    this.watchlistService.addToWatched(watchedRequest)
      .then(result => {
        console.info('>>> result add to watched: ', result)
        this.getWatchlist()
      })
      .catch(error => {
        console.error('>>> error add to watched: ', error)
      })
    this.form.reset()
  }

  updateToWatched(index: number) {
    let movie = this.pendMovies.at(index)
    const watchedRequest = new WatchedRequest(this.email, movie!, this.form.value["rating"], this.form.value["review"])
    this.watchlistService.updateToWatched(watchedRequest)
      .then(result => {
        console.info('>>> result update to watched: ', result)
        this.getWatchlist()
      })
      .catch(error => {
        console.error('>>> error update to watched: ', error)
      })
    this.form.reset()
  }

  addReview(index: number) {
    console.info(index)
    let movie = this.doneMovies.at(index)
    const watchedRequest = new WatchedRequest(this.email, movie!, this.addReviewForm.value["rating"], this.addReviewForm.value["review"])
    this.reviewService.addReview(watchedRequest)
      .then(result => {
        console.info('>>> result update review: ', result)
        this.getWatchlist()
      })
      .catch(error => {
        console.error('>>> error update review: ', error)
      })
    this.addReviewForm.reset()
  }

  editReview(index: number) {
    let movie = this.doneMovies.at(index)
    const watchedRequest = new WatchedRequest(this.email, movie!, this.editReviewForm.value["rating"], this.editReviewForm.value["review"])
    this.reviewService.editReview(watchedRequest)
      .then(result => {
        console.info('>>> result edit review: ', result)
        this.getWatchlist()
      })
      .catch(error => {
        console.error('>>> error edit review: ', error)
      })
    this.editReviewForm.reset()
  }

  removeFromList(movieId: string) {
    this.watchlistService.removeFromWatchlist(this.email, movieId)
      .then(result => {
        console.info('>>> result removing from watchlist: ', result)
        this.getWatchlist()
      })
      .catch(error => {
        console.error('>>> error removing from watchlist: ', error)
      })
  }

  getWatchlist() {
    this.watchlistService.getWatchlist(this.id, 'pend')
      .then(result => {
        console.info('>>> result pend movie: ', result)
        this.pendMovies = result
      })
      .catch(error => {
        console.error('>>> error pend movie: ', error)
      })
    this.watchlistService.getWatchlist(this.id, 'done')
      .then(result => {
        console.info('>>> result done movie: ', result)
        this.doneMovies = result
      })
      .catch(error => {
        console.error('>>> error done movie: ', error)
      })
  }

}
