import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs";


interface LoginCredential{
  user:string;
  password:string;
}
interface SingupCredencial{
  user:string;
  password:string;
}

let loggedin=new BehaviorSubject<boolean>(false)
let admin=new BehaviorSubject<boolean>(false)
@Injectable({
  providedIn: 'root'
})
export class PruebaService {
   loggedin$ = loggedin.asObservable()
   admin$ = admin.asObservable()
  constructor(private http:HttpClient) { }


  public login(credential:LoginCredential){
    return this.http.post<any>('http://localhost:8080/login', credential,{ observe: 'response', withCredentials: true })
  }

  public loginSyn(credential:LoginCredential):Promise<any>{
    return this.login(credential).toPromise()
  }


  public signup(credential:SingupCredencial){
    return this.http.post<any>('http://localhost:8080/signup', credential,{ observe: 'response', withCredentials: true })
  }

  public signupSyn(credential:SingupCredencial) {
    return this.signup(credential).toPromise()
  }

  public isadmin(){
    return this.http.post<{login: boolean}>('http://localhost:8080/isadmin','', { observe: 'response', withCredentials: true })
  }

  public isadminSyn(){
    return this.isadmin().toPromise()
  }

  public session(){
      return this.http.post<{login: boolean}>('http://localhost:8080/session','', { observe: 'response', withCredentials: true })

  }

  public sessionSyn() {
    return this.session().toPromise()
  }

  public logout(){
    return this.http.post<any>('http://localhost:8080/logout','',{ observe: 'response', withCredentials: true })
  }

  public logoutSyn() {
    return this.logout().toPromise();
  }

public logoutbs(){
    loggedin.next(false)
}
public loginbs(){
     console.log(true)
    loggedin.next(true)
}
public admin(){
    admin.next(true)
}

public noadmin(){
  admin.next(false)
}

}
