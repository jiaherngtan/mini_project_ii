import { NgModule, isDevMode } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { ServiceWorkerModule } from '@angular/service-worker';

// ng add @angular/fire
import { AngularFireModule } from '@angular/fire/compat';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { provideAuth, getAuth } from '@angular/fire/auth';

// npm install @google-pay/button-angular
import { GooglePayButtonModule } from '@google-pay/button-angular';

// npm install @angular/google-maps
import { GoogleMapsModule } from '@angular/google-maps';

// component list
import { AppComponent } from './app.component';
import { AboutComponent } from './components/about.component';
import { GenreComponent } from './components/genre.component';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';
import { MovieComponent } from './components/movie.component';
import { PeopleComponent } from './components/people.component';
import { SearchComponent } from './components/search.component';
import { UserComponent } from './components/user.component';
import { WatchlistComponent } from './components/watchlist.component';

// services and others
import { AuthGuard } from './services/auth.guard';
import { environment } from '../environments/environment';
import { MovieService } from './services/movie.service';
import { ReviewService } from './services/review.service';
import { UserService } from './services/user.service';
import { WatchlistService } from './services/watchlist.service';
import { MessagingService } from './services/messaging.service';

const appRoutes: Routes = [
  { path: '', component: MainComponent },
  { path: 'about', component: AboutComponent },
  { path: 'genre/:id/:genre', component: GenreComponent },
  { path: 'movie/:id', component: MovieComponent },
  { path: 'people/:id', component: PeopleComponent },
  { path: 'search', component: SearchComponent, data: { queryParams: 'query' } },
  { path: 'login', component: LoginComponent },
  { path: 'user', component: UserComponent },
  { path: 'watchlist', component: WatchlistComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    GenreComponent,
    LoginComponent,
    MainComponent,
    MovieComponent,
    PeopleComponent,
    SearchComponent,
    UserComponent,
    WatchlistComponent
  ],
  imports: [
    AngularFireModule.initializeApp(environment.firebase),
    BrowserModule,
    FormsModule,
    GoogleMapsModule,
    GooglePayButtonModule,
    HttpClientModule,
    HttpClientJsonpModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideAuth(() => getAuth()),
    BrowserAnimationsModule,
  ],
  providers: [MovieService, ReviewService, UserService, WatchlistService, MessagingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
