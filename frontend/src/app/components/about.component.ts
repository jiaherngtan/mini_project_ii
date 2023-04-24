import { AfterViewInit, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';
import { Observable, catchError, map, of } from 'rxjs';

import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit, AfterViewInit {

  apiLoaded: Observable<boolean>
  center!: google.maps.LatLngLiteral
  zoom = 12
  options: google.maps.MapOptions = {
    zoomControl: true,
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 15,
    minZoom: 8,
  }

  constructor(
    private httpClient: HttpClient,
    private title: Title,
    private viewportScroller: ViewportScroller
  ) {
    console.info("load api...")
    console.info(environment.mapAPI)
    this.apiLoaded = httpClient.jsonp(environment.mapAPI, 'callback')
      .pipe(
        map(() => true),
        catchError(() => of(false)),
      )
  }

  ngOnInit() {
    this.title.setTitle('About Us')
    this.center = {
      lat: 1.2930611406597132,
      lng: 103.77672874602465,
    }
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

  // google pay
  onLoadPaymentData(event: any): void {
    console.log("load payment data", event.detail);
  }

}
