import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserRegister } from '../../models/user.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register-form',
  standalone: false,
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css'
})
export class RegisterFormComponent {

  nombre: string = '';
  apellidos: string = '';
  email: string = '';
  telefono: string = '';
  username: string = '';
  password: string = '';
  rol: string = '';

  logoPath: string = 'assets/logo.png';

  constructor(private http: HttpClient, private router: Router) { }

  camposRellenos(): boolean {
    if (
      this.nombre.trim() === '' ||
      this.apellidos.trim() === '' ||
      this.email.trim() === '' ||
      this.telefono.trim() === '' ||
      this.username.trim() === '' ||
      this.password.trim() === ''
    ) {
      return false;
    }
    return true;
  }

  procesaRegistro() {
    if (!this.camposRellenos()) {
      Swal.fire({
        icon: 'info',
        title: 'Error de registro',
        text: 'Por favor, rellena todos los campos.',
        timer: 3000,
      });
      return false
    }
    this.register();
    return false;
  }

  register() {
    let userRole = this.rol === '2' ? 'PROPIETARIO' : 'USER';
    const body = {
      username: this.username,
      password: this.password,
      nombre: this.nombre,
      apellidos: this.apellidos,
      email: this.email,
      telefono: this.telefono,
      role: userRole
    }


    this.http.post<UserRegister>('http://localhost:8080/user/auth/register', body)
      .subscribe({
        next: (response) => {
          console.log('Registro exitoso', response);

          if (response.token) {
            this.activateAccount(response.token);
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Error de registro',
              text: 'Por favor, inténtalo de nuevo.',
              timer: 3000,
            })
          }
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error de registro',
            text: 'Por favor, inténtalo de nuevo.',
            timer: 3000,
          })
        }
      });

  }

  activateAccount(token: string) {
    const body = { token: token };
    this.http.post('http://localhost:8080/user/activate/account', body)
      .subscribe({
        next: (response) => {
          Swal.fire({
            icon: 'success',
            title: 'Registro exitoso',
            text: 'Por favor, inicia sesión.',
            timer: 3000,
          })
          this.router.navigate(['/main']);
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error de registro',
            text: 'Por favor, verifica tu correo o contacta con soporte.',
            timer: 3000,
          })
        }
      });
  }
}
