import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  logoPath: string = 'assets/logo.png';

  constructor(private router: Router) {}

  isLoggedIn(): boolean {
    return !!localStorage.getItem('accessToken');
  }

  logout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');

    Swal.fire({
      icon: 'info',
      title: 'Sesión cerrada',
      text: '¡Hasta pronto!',
      timer: 3000,
    })

    this.router.navigate(['/main']);
  }
}
