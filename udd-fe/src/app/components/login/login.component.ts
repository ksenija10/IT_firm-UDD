import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationRequest } from 'src/app/model/authentication-request.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  constructor(
    private authenticationService: AuthenticationService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

  login() {
    if (!this.loginForm.valid) {
      return;
    }

    const authenticationRequest = new AuthenticationRequest(
      this.loginForm.value.username,
      this.loginForm.value.password
    );

    this.authenticationService.login(authenticationRequest).subscribe(
      (response) => {
        this.toastr.success('Logged in successfully');
        this.authenticationService.setLoggedInUser(response);
        this.loginForm.reset();
        this.router.navigate(['search']);
      },
      (error) => {
        this.toastr.error('Username or password incorrect');
      }
    );
  }
}
