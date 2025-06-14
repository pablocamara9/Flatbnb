import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../models/user.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login-form',
  standalone: false,
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  email: string = '';
  password: string = '';

  logoPath: string = 'assets/logo.png';

  constructor(private http: HttpClient, private router: Router) {}

  // Devuelve true si ambos campos están rellenos
  camposRellenos(): boolean {
    return this.email.trim() !== '' && this.password.trim() !== '';
  }

  procesaFormulario() {
    if (!this.camposRellenos()) {
          Swal.fire({
            icon: 'info',
            title: 'Error de inicio de sesión',
            text: 'Por favor, rellena todos los campos.',
            timer: 3000,
          })
      return false; // Evita el envío del formulario
    }
    this.login();
    return false;
  }

  login() {
    const body = {
      username: this.email,
      password: this.password
    };
    this.http.post<User>('http://localhost:8080/user/auth/login', body)    
      .subscribe({
        next: (response) => {
          console.log('Login exitoso', response);
          localStorage.setItem('user', JSON.stringify(response));
          localStorage.setItem('accessToken', response.token);
          localStorage.setItem('refreshToken', response.refreshToken);
          
          Swal.fire({
            icon: 'success',
            title: 'Inicio de sesión exitoso',
            text: `Bienvenido de nuevo, ${response.username}!`,
            timer: 3000,
          })
          this.router.navigate(['/main']);
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error de inicio de sesión',
            text: 'Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.',
            timer: 3000,
          })
        }
      });
  }

}
