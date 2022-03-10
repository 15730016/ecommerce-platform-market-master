import { Component, OnInit } from '@angular/core';
import Validation from "../interface/validation";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../_services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup = new FormGroup({
    email: new FormControl(),
    displayName: new FormControl(),
    // phoneNumber: new FormControl(),
    password: new FormControl(),
    matchingPassword: new FormControl(),
    socialProvider: new FormControl('LOCAL'),
    using2FA: new FormControl(false)
  });
  submitted = false;
  isUsing2FA: boolean = false;
  isSuccessful: boolean = false;
  isSignUpFailed: boolean = false;
  emailPattern = '^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$';
  phonePattern = '(0[3|5|7|8|9])+([0-9]{8})';
  errorMessage: string = '';
  qrCodeImage: string = '';

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router         ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      displayName: ['',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20),
        ]
      ],
      email: ['',
        [
          Validators.required,
          Validators.pattern(this.emailPattern)
        ]
      ],
      // phoneNumber: ['',
      //   [
      //     Validators.required,
      //     Validators.pattern(this.phonePattern),
      //   ]
      // ],
      password: ['',
        [
          Validators.required,
          Validators.minLength(6)
        ]
      ],
      matchingPassword: ['',
        [
          Validators.required,

        ]
      ],
      socialProvider: ['LOCAL'],
      using2FA: [false]
    }, {
      validators: [Validation.match('password', 'matchingPassword')]
    });
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
    return this.registerForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    if(this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe(
        (data: any) => {
          console.log(data);
          if(data.using2FA){
            this.isUsing2FA = true;
            this.qrCodeImage = data.qrCodeImage;
          }
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        },
        (err: any) => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        }
      );
    }
  }

  clearForm(){
    // this.form.reset();
    this.router.navigate(['']).then();
  }

}
