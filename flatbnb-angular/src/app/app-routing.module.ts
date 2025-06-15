import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { AnnouncementDetailComponent } from './components/announcement-detail/announcement-detail.component';
import { PropietarioComponent } from './components/propietario/propietario-main-page/propietario.component';

const routes: Routes = [
  { path: 'main', component: MainPageComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'register', component: RegisterFormComponent },
  { path: 'anuncio/:id', component: AnnouncementDetailComponent },
  { path: 'propietario/:id', component: PropietarioComponent},
  { path: '', redirectTo: '/main', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
