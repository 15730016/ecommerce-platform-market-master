import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../_services/token-storage.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../_services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-two-fa-auth',
  templateUrl: './two-fa-auth.component.html',
  styleUrls: ['./two-fa-auth.component.scss']
})
export class TwoFaAuthComponent implements OnInit {

  twoFaOauthForm: FormGroup = new FormGroup({
    code: new FormControl()
  });
  isLoggedIn = false;
  isLoginFailed = false;
  submitted = false;
  errorMessage = '';
  currentUser: any;

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.twoFaOauthForm = this.formBuilder.group({
      code: ['',
        [
          Validators.required,
          Validators.minLength(6)
        ]
      ]
    })
    if (this.tokenStorage.getToken()) {
      // this.isLoggedIn = false;
      this.currentUser = this.tokenStorage.getUser();
    }
  }

  get formControl(): any {
    return this.twoFaOauthForm.controls;
  }

  clearForm(){
    this.twoFaOauthForm.reset();
  }

  onSubmit(): void {
    this.submitted = true;
    if(this.twoFaOauthForm.valid) {
      this.authService.verify(this.twoFaOauthForm.value).subscribe(
        (data: any) => {

          console.log('code ', this.twoFaOauthForm.value)
          this.tokenStorage.saveToken(data.accessToken);
          this.login(data.user);
          this.router.navigate(['/profile']).then();
        },
        (err: any) => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
  }

  login(user: any): void {
    this.tokenStorage.saveUser(user);
    this.isLoginFailed = false;
    this.isLoggedIn = true;
    this.currentUser = this.tokenStorage.getUser();
  }

}
