import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { TitleCasePipe, ViewportScroller } from '@angular/common';

import { WatchedRequest } from '../WatchedRequest';

import { ReviewService } from '../services/review.service';
import { UserService } from '../services/user.service';
import { WatchlistService } from '../services/watchlist.service';
import { User } from '../models';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  providers: [TitleCasePipe]
})
export class UserComponent implements OnInit, AfterViewInit {

  id!: string
  email!: string
  username!: string
  created!: string
  imageUrl!: string
  pattern!: string
  watchedRequests: WatchedRequest[] = []
  pendMoviesLength!: number
  doneMoviesLength!: number

  constructor(
    public afAuth: AngularFireAuth,
    private router: Router,
    private title: Title,
    private titleCasePipe: TitleCasePipe,
    private viewportScroller: ViewportScroller,
    private reviewService: ReviewService,
    private userService: UserService,
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    this.afAuth.authState.subscribe(user => {
      this.email = user?.email!
      // get user details
      this.userService.getUserDetails(this.email)
        .then(result => {
          console.info('>>> result user id: ', result.id)
          this.id = result.id
          this.username = result.username
          this.username = this.titleCasePipe.transform(this.username)
          this.title.setTitle(this.username)
          this.created = result.created
          this.imageUrl = 'https://jhtan.sgp1.digitaloceanspaces.com/myobjects%2F' + result.imageUrl
          // get reviews
          this.reviewService.getReviews(this.id)
            .then(result => {
              console.info('>>> result reviews: ', result)
              console.info(result.at(0)?.movie)
              this.watchedRequests = result
            })
            .catch(error => {
              console.error('>>> error reviews: ', error)
            })
          // get watchlist length
          this.watchlistService.getWatchlist(this.id, 'pend')
            .then(result => {
              this.pendMoviesLength = result.length
            })
          this.watchlistService.getWatchlist(this.id, 'done')
            .then(result => {
              this.doneMoviesLength = result.length
            })
        })
        .catch(error => {
          console.error('>>> error user id: ', error)
        })
    })
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

  sort(pattern: string, patternName: string) {
    this.reviewService.getSortedReviews(this.id, pattern)
      .then(result => {
        console.info('>>> result sort reviews: ', result)
        this.pattern = patternName
        this.watchedRequests = result
      })
      .catch(error => {
        console.error('>>> error sort reviews: ', error)
      })
  }

  deleteUser() {
    this.afAuth.currentUser.then(user => user?.delete())
      .then(result => {
        console.info('>>> delete result: ', result)
        this.userService.deleteSql(this.email)
        this.router.navigate(['/'])
      })
      .catch(error => {
        console.info('>>> delete error: ', error)
      })
  }

  // google pay
  // onLoadPaymentData(event: any): void {
  //   console.log("load payment data", event.detail);
  // }

  // paymentRequest = {
  //   apiVersion: 2,
  //   apiVersionMinor: 0,
  //   allowedPaymentMethods: [
  //     {
  //       type: 'CARD',
  //       parameters: {
  //         allowedAuthMethods: ['PAN_ONLY', 'CRYPTOGRAM_3DS'],
  //         allowedCardNetworks: ['AMEX', 'VISA', 'MASTERCARD']
  //       },
  //       tokenizationSpecification: {
  //         type: 'PAYMENT_GATEWAY',
  //         parameters: {
  //           gateway: 'example',
  //           gatewayMerchantId: 'exampleGatewayMerchantId'
  //         }
  //       }
  //     }
  //   ],
  //   merchantInfo: {
  //     merchantId: '12345678901234567890',
  //     merchantName: 'Demo Merchant'
  //   },
  //   transactionInfo: {
  //     totalPriceStatus: 'FINAL',
  //     totalPriceLabel: 'Total',
  //     totalPrice: '100.00',
  //     currencyCode: 'USD',
  //     countryCode: 'US'
  //   }
  // }

}
