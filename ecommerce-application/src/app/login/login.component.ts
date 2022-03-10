import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../_services/token-storage.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {UserAccountService} from "../_services/user-account.service";
import {AuthService} from "../_services/auth.service";
import {AppConstants} from "../common/app.constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({
    email: new FormControl(),
    password: new FormControl()
  });
  emailPattern = '^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$';

  submitted = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;
  googleURL = AppConstants.GOOGLE_AUTH_URL;
  facebookURL = AppConstants.FACEBOOK_AUTH_URL;
  returnUrl = '/home';


  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute,
    private userAccountService: UserAccountService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private router: Router) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['',
        [
          Validators.required,
          Validators.pattern(this.emailPattern)
        ]
      ],
      password: ['',
        [
          Validators.required,
          Validators.minLength(6)
        ]
      ]
    });

    const token = this.route.snapshot.queryParamMap.get('token');
    const error = this.route.snapshot.queryParamMap.get('error');
    if (this.tokenStorage.getToken()) {
      this.submitted = true;
      this.currentUser = this.tokenStorage.getUser();
    } else if (token) {
      this.tokenStorage.saveToken(token);
      console.log('token = ', token)
      this.userAccountService.getCurrentUser().subscribe(
        (data: any) => {
          this.login(data);
        },
        (err: any) => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    } else if (error) {
      this.errorMessage = error;
      this.isLoginFailed = true;
    }

  }

  validatorsMessage = {
    email : {
      required: 'Nhập thư điện tử người dùng!',
      pattern: 'Vui lòng nhập đúng thư điện tử người dùng!'
    },
    password: {
      required: 'Nhập mật khẩu người dùng!',
      minlength: 'Mật khẩu có ít nhất 6 ký tự'
    }
  }

  get formControl(): any {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(
        (data: any) => {
          this.tokenStorage.saveToken(data.accessToken);
          console.log('data.accessToken = ', data.accessToken)
          if (data.authenticated) {
            this.login(data.user);
            // this.toastr.success('Đăng nhập thành công tài khoản!', 'Xác thực tài khoản');
          } else {
            this.router.navigate(['/twoFaAuth']).then();
          }
        },
        (err: any) => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
  }

  clearForm(){
    this.loginForm.reset();
    this.router.navigate(['']).then();
  }

  login(user: any): void {
    this.tokenStorage.saveUser(user);
    this.isLoginFailed = false;
    this.currentUser = this.tokenStorage.getUser();
    this.router.navigate(['/home']).then(function (){
      window.location.reload();
    });
  }

}
