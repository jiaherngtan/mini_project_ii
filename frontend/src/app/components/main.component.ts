import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';

import { Movie } from '../models';

import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, AfterViewInit {

  popularMovies: Movie[] = []
  topRatedMovies: Movie[] = []
  nowPlayingMovies: Movie[] = []

  constructor(
    public afAuth: AngularFireAuth,
    private title: Title,
    private viewportScroller: ViewportScroller,
    private movieService: MovieService) { }

  async ngOnInit() {
    this.title.setTitle('The Movie Database')
    this.popularMovies = await this.movieService.getPopularMovies()
    this.topRatedMovies = await this.movieService.getTopRatedMovies()
    this.nowPlayingMovies = await this.movieService.getNowPlayingMovies()
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

}
