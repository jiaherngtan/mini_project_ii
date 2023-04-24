import { Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';

import { Genre } from './models';
import { Message } from './Message';

import { MessagingService } from './services/messaging.service';
import { MovieService } from './services/movie.service';
import { UserService } from './services/user.service';
import { environment } from 'src/environments/environment';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  form!: FormGroup
  query!: string
  genres: Genre[] = []
  email!: string
  username!: string
  authSubscription!: Subscription
  // message: any = null
  messages: Message[] = []

  constructor(
    public afAuth: AngularFireAuth,
    public afMessaging: AngularFireMessaging,
    private fb: FormBuilder,
    private router: Router,
    private messagingService: MessagingService,
    private movieService: MovieService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.movieService.getGenres()
      .then(result => {
        this.genres = result
      })
    // this.genres = this.movieService.getGenres()
    this.form = this.createForm()
    // subscribe to username
    this.authSubscription = this.afAuth.authState.subscribe(user => {
      this.email = user?.email!
      console.info('>>> user email: ', this.email)
      this.userService.getUserDetails(this.email)
        .then(result => {
          console.info('>>> result username: ', result.username)
          this.username = result.username
        })
        .catch(error => {
          console.error('>>> error username: ', error)
        })
      // firebase messaging
      this.messagingService.requestPermission()
      this.afMessaging.onMessage((payload) => {
        let notification = payload.notification
        this.messages.push({
          title: notification.title,
          body: notification.body,
          iconUrl: notification.icon
        })
      })
    })
    // this.message = this.messagingService.currentMessage
    // this.requestPermission()
    // this.listen()
  }

  private createForm(): FormGroup {
    console.info('>>> form created...')
    return this.fb.group({
      query: this.fb.control<string>('', [Validators.required])
    })
  }

  search(): void {
    console.info('>>> form submitted...')
    this.query = this.form.value["query"]
    this.router.navigateByUrl(`/search?query=${this.query}`)
    this.form.reset()
  }

  logout(): void {
    console.info(this.afAuth.user)
    this.afAuth.signOut()
    this.authSubscription.unsubscribe()
    this.username = ''
    this.router.navigate(['/'])
  }

  // firebase messaging
  // requestPermission() {
  //   const messaging = getMessaging()
  //   getToken(messaging,
  //     { vapidKey: environment.firebase.vapidKey })
  //     .then((currentToken) => {
  //       if (currentToken)
  //         console.info('currentToken: ', currentToken)
  //       else
  //         console.info('No token available')
  //     })
  //     .catch(error => {
  //       console.error('error: ', error)
  //     })
  // }

  // listen() {
  //   const messaging = getMessaging()
  //   onMessage(messaging, (payload) => {
  //     console.info('message received: ', payload)
  //     this.message = payload
  //   })
  // }

}
