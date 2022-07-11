import { Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {PruebaService} from "./http.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate{
  constructor(private http:PruebaService, private router:Router) {
    this.session();
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(!this.islogged()){
      this.router.navigate(['login'])
      return false;
    }
    return true;

  }

islogged(){
    let logged;
    const obser=this.http.loggedin$.subscribe(value => logged=value);
    obser.unsubscribe();
    return logged;
}
  async session() {
    let data = await this.http.sessionSyn()
    if (data) {
      this.http.loginbs();
      let datarol = await this.http.isadminSyn()
      if (datarol) {
        this.http.admin()
      }
    }
  }


}
