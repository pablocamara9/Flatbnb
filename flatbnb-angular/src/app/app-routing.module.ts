import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { AnnouncementDetailComponent } from './components/announcement-detail/announcement-detail.component';
import { PropietarioComponent } from './components/propietario/propietario-anuncios/propietario-anuncios.component';
import { PropietarioFormComponent } from './components/propietario/propietario-form/propietario-form.component';
import { PropietarioPisosComponent } from './components/propietario/propietario-pisos/propietario-pisos.component';
import { PropietarioPisoFormComponent } from './components/propietario/propietario-piso-form/propietario-piso-form.component';

const routes: Routes = [
  { path: 'main', component: MainPageComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'register', component: RegisterFormComponent },
  { path: 'anuncio/:id', component: AnnouncementDetailComponent },
  { path: 'propietario/:id', component: PropietarioComponent},
  { path: 'agregar-anuncio', component: PropietarioFormComponent },
  { path: 'editar-anuncio/:id', component: PropietarioFormComponent },
  { path: 'propietario-pisos/:id', component: PropietarioPisosComponent},
  { path: 'agregar-piso', component: PropietarioPisoFormComponent},
  { path: 'editar-piso/:id', component: PropietarioPisoFormComponent},
  { path: '', redirectTo: '/main', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
