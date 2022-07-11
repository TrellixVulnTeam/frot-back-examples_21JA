import { Component, OnInit} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {PruebaService} from "../http.service";
import {AuthGuard} from "../auth.guard";
import {Router} from "@angular/router";



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [PruebaService]
})
export class LoginComponent implements OnInit{
  loginForm!:FormGroup;

  constructor(private formbuilder:FormBuilder, private http:PruebaService, private auth:AuthGuard, private router:Router) {}


  ngOnInit(): void {
    this.loginForm = this.formbuilder.group({
      name: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(15)]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(15)]],
    });
    this.revition();
  }

  async revition() {
    let data = await this.http.sessionSyn()
    if (data) {
      this.http.loginbs();
      this.router.navigate(['operador']);
    }
  }


 async onSubmit() {
   let userf: any;
   let passwordf: any;
   if (this.loginForm.invalid) {
     return;
   }
   userf = this.loginForm.controls['name'].value;
   passwordf = this.loginForm.controls['password'].value;
   let response= await this.http.loginSyn({user: userf, password: passwordf})
   if(response){
     console.log('Ingresaste bien las credenciales');
     this.router.navigate(['operador']);
     this.http.loginbs();
     let datarol = await this.http.isadminSyn();
     if(datarol){
       this.http.admin()
     }
   }
 }
}

