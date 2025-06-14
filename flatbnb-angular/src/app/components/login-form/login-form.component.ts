import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../models/user.model';
import { Router } from '@angular/router';

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
      alert('Por favor, rellena todos los campos.');
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
          // Maneja el login exitoso aquí (guardar token, redirigir, etc)
          console.log('Login exitoso', response);
          localStorage.setItem('user', JSON.stringify(response));
          localStorage.setItem('accessToken', response.token);
          localStorage.setItem('refreshToken', response.refreshToken);
          
          this.router.navigate(['/main']);
        },
        error: (error) => {
          // Maneja el error de login aquí
          alert('Usuario o contraseña incorrectos');
        }
      });
  }

}
