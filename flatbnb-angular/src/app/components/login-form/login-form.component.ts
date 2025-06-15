import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../models/user.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/auth-service.service';

@Component({
  selector: 'app-login-form',
  standalone: false,
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  username: string = '';
  password: string = '';

  logoPath: string = 'assets/logo.png';

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) { }

  // Devuelve true si ambos campos están rellenos
  camposRellenos(): boolean {
    return this.username.trim() !== '' && this.password.trim() !== '';
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
    this.authService.login(this.username, this.password);
    this.router.navigate(['/main']);
  }

}
