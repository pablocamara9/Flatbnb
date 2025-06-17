import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserRegister } from '../../models/user.model';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/auth-service.service';

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

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) { }

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
    let endpoint = '';
    if (this.rol === '2') {
      endpoint = 'http://localhost:8080/propietario/';
    } else {
      endpoint = 'http://localhost:8080/user/auth/register';
    }

    this.authService.register(
      this.username,
      this.password,
      this.nombre,
      this.apellidos,
      this.email,
      this.telefono,
      this.rol,
      endpoint
    );

    this.router.navigate(['/main']);
  }
}
