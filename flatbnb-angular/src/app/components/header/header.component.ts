import { Component, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/auth-service.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements DoCheck {
  logoPath: string = 'assets/logo.png';
  userRole: string | null = null;
  userId: string | null = null;

  constructor(private router: Router, private authService: AuthService) {}

  ngDoCheck(): void {
    // Se ejecuta en cada cambio de detección, útil para reflejar cambios de sesión dinámicamente
    this.setUserRole();
  }

  setUserRole() {
    this.userRole = this.authService.getRoles();
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr);
        this.userId = userObj.id || null;
      } catch {
        this.userId = null;
      }
    } else {
      this.userId = null;
    }
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('accessToken');
  }

  logout() {
    this.authService.logout();

    Swal.fire({
      icon: 'info',
      title: 'Sesión cerrada',
      text: '¡Hasta pronto!',
      timer: 3000,
    })

    this.userRole = null;
    this.userId = null;
    this.router.navigate(['/main']);
  }
}
