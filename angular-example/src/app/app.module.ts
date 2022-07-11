import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AdministradorComponent } from './administrador/administrador.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { OperadorComponent } from './operador/operador.component';
import {PruebaService} from "./http.service";
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdministradorComponent,
    NotFoundComponent,
    OperadorComponent,
    NotAuthorizedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [PruebaService],
  bootstrap: [AppComponent]
})
export class AppModule { }
