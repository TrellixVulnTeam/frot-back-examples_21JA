import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {PruebaService} from "./http.service";

@Injectable({
  providedIn: 'root'
})
export class RoleGuardGuard implements CanActivate {
  isadmin=this.http.admin$;
  constructor(private http:PruebaService, private router:Router) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.isAdmin()){
    return true;
    }
    this.router.navigate(['not-authorized']);
    return false;
  }
  isAdmin(){
    let rol;
    const obser=this.http.admin$.subscribe(value => rol=value);
    obser.unsubscribe();
    return rol;
  }
}
