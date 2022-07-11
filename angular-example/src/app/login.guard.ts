import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {PruebaService} from "./http.service";

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate{
  constructor(private http: PruebaService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.islogged()) {
      this.router.navigate(['operador'])
      return false;
    }
    return true;
  }


  islogged() {
    let logged;
    const obser = this.http.loggedin$.subscribe(value => logged = value);
    obser.unsubscribe();
    console.log('can activate login ' + logged);
    return logged;
  }
}
