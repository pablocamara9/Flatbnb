import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/auth-service.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
  logoPath: string = 'assets/logo.png';
  userRole: string | null = null;
  userId: string | null = null;

  constructor(private router: Router, private authService: AuthService) {}
  ngOnInit(): void {
    this.userRole = this.authService.getRoles();
  }

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

    this.userRole = null;
    this.userId = null;
    this.router.navigate(['/main']);
  }
}
