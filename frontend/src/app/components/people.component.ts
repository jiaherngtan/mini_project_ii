import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';
import { Subscription } from 'rxjs';

import { People } from '../models';

import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-people',
  templateUrl: './people.component.html',
  styleUrls: ['./people.component.css']
})
export class PeopleComponent implements OnInit, AfterViewInit {

  id!: string
  people!: People
  popularPeople: People[] = []
  params$!: Subscription

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private title: Title,
    private viewportScroller: ViewportScroller,
    private movieService: MovieService) { }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        window.scrollTo(0, 0)
      }
    })
    this.params$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.id = params['id']
        this.movieService.getPeople(this.id)
          .then(result => {
            console.info('>>> result people: ', result)
            this.people = result
            this.title.setTitle(this.people.name)
          })
          .catch(error => {
            console.error('>>> error people: ', error)
          })
        this.movieService.getPopularPeople()
          .then(result => {
            console.info('>>> result popular people: ', result)
            this.popularPeople = result
          })
          .catch(error => {
            console.error('>>> error people popular: ', error)
          })
      }
    )
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

}
