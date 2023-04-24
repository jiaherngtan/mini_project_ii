import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessagingService {

  // currentMessage = new BehaviorSubject<any>(null)

  constructor(private afMessaging: AngularFireMessaging, private httpClient: HttpClient) { }

  // request for notification permission
  requestPermission() {
    this.afMessaging.requestToken.subscribe(token => {
      console.info('token: ', token)
      this.httpClient.post('/api/notification', {
        target: token,
        title: 'Welcome to TMDB,',
        message: 'we are glad to have you here!'
      }).subscribe(() => { })
      console.info("sending to backend...")
    }, error => {
      console.info('unable to get notification permission', error)
    })
    // receive notification from firebase
    // this.afMessaging.messages.subscribe(payload => {
    //   console.info('message received: ', payload)
    //   this.currentMessage.next(payload)
    // })
  }

  // requestToken(): void {
  //   this.afMessaging.requestToken.subscribe({
  //     next: token => {
  //     },
  //     error: err => {
  //       console.error('token failed', err)
  //     }
  //   })
  //   this.afMessaging.messages.subscribe((message: any) => {
  //     console.info('foreground message: ', message)
  //   })
  // }

}