import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { provideHttpClient } from '@angular/common/http';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { FormsModule } from '@angular/forms';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { AnnouncementDetailComponent } from './components/announcement-detail/announcement-detail.component';
import { PropietarioComponent } from './components/propietario/propietario-anuncios/propietario-anuncios.component';
import { PropietarioFormComponent } from './components/propietario/propietario-form/propietario-form.component';
import { PropietarioPisosComponent } from './components/propietario/propietario-pisos/propietario-pisos.component';
import { PropietarioPisoFormComponent } from './components/propietario/propietario-piso-form/propietario-piso-form.component';
import { AdminMainComponent } from './components/admin/admin-main/admin-main.component';
import { AdminUserFormComponent } from './components/admin/admin-user-form/admin-user-form.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { PropietarioListComponent } from './components/propietario-list/propietario-list.component';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    HeaderComponent,
    FooterComponent,
    LoginFormComponent,
    RegisterFormComponent,
    AnnouncementDetailComponent,
    PropietarioComponent,
    PropietarioFormComponent,
    PropietarioPisosComponent,
    PropietarioPisoFormComponent,
    AdminMainComponent,
    AdminUserFormComponent,
    UserDetailComponent,
    PropietarioListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
