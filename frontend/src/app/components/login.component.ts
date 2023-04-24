import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ViewportScroller } from '@angular/common';

import { LoginUser, User } from '../models';

import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {

  @ViewChild('myfile') imageData!: ElementRef

  loginForm!: FormGroup
  registrationForm!: FormGroup
  firebaseErrMsg!: string

  constructor(
    private fb: FormBuilder,
    private title: Title,
    private viewportScroller: ViewportScroller,
    private userService: UserService) { }

  ngOnInit(): void {
    this.title.setTitle('Become a Member')
    this.loginForm = this.createLoginForm()
    this.registrationForm = this.createRegistrationForm()
  }

  ngAfterViewInit() {
    this.viewportScroller.scrollToPosition([0, 0])
  }

  private createLoginForm(): FormGroup {
    return this.fb.group({
      loginEmail: this.fb.control<string>('', [Validators.required, Validators.email]),
      loginPassword: this.fb.control<string>('', [Validators.required, Validators.minLength(8)])
    })
  }

  private createTestLoginForm(): FormGroup {
    return this.fb.group({
      loginEmail: this.fb.control<string>('test@email.com', [Validators.required, Validators.email]),
      loginPassword: this.fb.control<string>('test1234', [Validators.required, Validators.minLength(8)])
    })
  }

  private createRegistrationForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(20)]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]),
      myfile: this.fb.control('', [Validators.required])
    })
  }

  processLoginForm() {
    // const formData = new FormData()
    // formData.set('username', this.loginForm.get('loginUsername')?.value)
    // formData.set('password', this.loginForm.get('loginPassword')?.value)
    const loginUser = this.loginForm.value as LoginUser

    this.userService.login(loginUser)
      .then(result => {
        window.location.href = '/'
      })
      .catch(error => {
        console.error('>>> login error: ', error)
      })
  }

  processRegistrationForm() {
    const formData = new FormData()
    formData.set('username', this.registrationForm.get('username')?.value)
    formData.set('email', this.registrationForm.get('email')?.value)
    formData.set('password', this.registrationForm.get('password')?.value)
    formData.set('myImage', this.imageData.nativeElement.files[0])

    const user = this.registrationForm.value as User
    // const loginUser = this.registrationForm.value as LoginUser
    this.userService.register(user, formData)
      .then(result => {
        console.info('>>> result: ', result)
        window.location.href = '/'
        // this.userService.login(loginUser)
      })
      .catch(error => {
        console.error('>>> error: ', error)
      })
  }

  useTest() {
    this.loginForm = this.createTestLoginForm()
  }
}
