import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { jwtDecode } from 'jwt-decode';
import { User, UserRegister } from '../models/user.model';
import { jwtDecode } from 'jwt-decode';
import Swal from 'sweetalert2';

@Injectable({ providedIn: 'root' })
export class AuthService {
  
  constructor(private http: HttpClient) { }

  logout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
  }

  // Obetener el rol del usuario logado desde el token almacenado en localStorage
  getRoles(): string {
    const userStr = localStorage.getItem('user') || sessionStorage.getItem('user');
    if (userStr) {
      try {
        const user = JSON.parse(userStr);
        if (user.token) {
          const decoded: any = jwtDecode(user.token);
          if (decoded.roles && Array.isArray(decoded.roles) && decoded.roles.length > 0) {
            return decoded.roles[0];
          }
          if (typeof decoded.roles === 'string') {
            return decoded.roles;
          }
        }
      } catch (e) {
        console.error('Error decodificando el token:', e);
      }
    }
    return '';
  }

  // Login del usuario
  login(username: string, password: string) {
    const body = {
      username: username,
      password: password
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

  register(
    username: string,
    password: string,
    nombre: string,
    apellidos: string,
    email: string,
    telefono: string,
    role: string
  ) {
    let userRole = role === '2' ? 'PROPIETARIO' : 'USER';
    const body = {
      username: username,
      password: password,
      nombre: nombre,
      apellidos: apellidos,
      email: email,
      telefono: telefono,
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
          //this.router.navigate(['/main']);
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

  isAdmin(): boolean {
    const userStr = localStorage.getItem('user') || sessionStorage.getItem('user');
    if (userStr) {
      try {
        const user = JSON.parse(userStr);
        if (user.token) {
          const decoded: any = jwtDecode(user.token);
          if (decoded.roles && Array.isArray(decoded.roles)) {
            return decoded.roles.includes('ADMIN');
          }
        }
      } catch (e) {
        console.error('Error decodificando el token:', e);
      }
    }
    return false;
  }

}