import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AdministradorComponent} from "./administrador/administrador.component";
import {AuthGuard} from "./auth.guard";
import {OperadorComponent} from "./operador/operador.component";
import {RoleGuardGuard} from "./role-guard.guard";
import {NotFoundComponent} from "./not-found/not-found.component";
import {LoginGuard} from "./login.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent, canActivate:[LoginGuard]},
  {path: 'administrador', component: AdministradorComponent,canActivate:[RoleGuardGuard,AuthGuard]},
  {path: 'operador', component: OperadorComponent, canActivate: [AuthGuard]},
  {path: 'not-authorized', component: NotAuthorizedComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
