import {Component, OnInit} from '@angular/core';
import {PruebaService} from "./http.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  loggedin=false;
  isadmin=false;

  constructor(private http:PruebaService, private router:Router) {
  }

  ngOnInit() {
    this.session()
    this.http.loggedin$.subscribe(loggedin=> this.loggedin=loggedin);
    this.http.admin$.subscribe(valor => this.isadmin=valor)
  }

  async session(){
    let data = await this.http.sessionSyn()
    if(data){
      this.http.loginbs();
      let datarol = await this.http.isadminSyn()
      if(datarol){
        this.http.admin()
      }
    }
  }

  async logout() {
    let data = await this.http.logoutSyn()
    if(data){
      this.http.logoutbs();
      this.http.noadmin();
      window.location.reload()
    }
  }

}
