import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { firstValueFrom } from 'rxjs';
import { User } from '../models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userLoggedIn!: boolean
  email!: string

  constructor(
    private afAuth: AngularFireAuth,
    private httpClient: HttpClient) {
    this.userLoggedIn = false;
    this.afAuth.onAuthStateChanged(user => {
      this.userLoggedIn = (user) ? true : false
      this.email = user?.email!
      console.info('>>> user logged in?: ', this.userLoggedIn)
      // console.info('>>> user email: ', user?.email)
    })
  }

  // for development
  getUserDetailsArg(email: string) {
    return `/api/user/${email}`
  }
  registerSqlArg = '/api/user/register'
  deleteSqlArg(email: string) {
    return `/api/user/delete/${email}`
  }

  // for production
  // getUserDetailsArg(email: string) {
  //   return `http://tmdb.up.railway.app/api/user/${email}`
  // }
  // registerSqlArg = 'http://tmdb.up.railway.app/api/user/register'
  // deleteSqlArg(email: string) {
  //   return `http://tmdb.up.railway.app/api/user/delete/${email}`
  // }

  getUserDetails(email: string): Promise<User> {
    return firstValueFrom(
      this.httpClient.get<User>(this.getUserDetailsArg(email))
    )
  }

  register(user: any, formData: any): Promise<any> {
    // register to firebase
    this.registerFireBase(user)
    // register to mysql
    return this.registerSql(formData)
      .then(result => {
        console.info('>>> sql registration result: ', result)
      })
      .catch(error => {
        console.error('>>> sql registration error: ', error)
      })
  }

  deleteSql(email: string): Promise<User> {
    return firstValueFrom(
      this.httpClient.delete<User>(this.deleteSqlArg(email))
    )
  }

  registerSql(user: User): Promise<User> {
    return firstValueFrom(
      this.httpClient.post<User>(this.registerSqlArg, user)
    )
  }

  registerFireBase(user: any): Promise<any> {
    return this.afAuth.createUserWithEmailAndPassword(user.email, user.password)
      .then(result => {
        console.info('>>> registration result: ', result)
        // send email to user
        result.user?.sendEmailVerification
      })
      .catch(error => {
        console.info('>>> registration error: ', error)
      })
  }

  login(loginUser: any): Promise<any> {
    return this.afAuth.signInWithEmailAndPassword(loginUser.loginEmail, loginUser.loginPassword)
      .then(result => {
        console.info('>>> login result: ', result)
      })
      .catch(error => {
        console.info('>>> login error: ', error)
      })
  }

}
