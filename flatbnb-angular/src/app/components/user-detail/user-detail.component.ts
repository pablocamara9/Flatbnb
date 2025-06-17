import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-detail',
  standalone: false,
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css'
})
export class UserDetailComponent implements OnInit {
  user: any = null;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    // Obtener el usuario logado del localStorage
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const userObj = JSON.parse(userStr);
      const userId = userObj.id;
      if (userId) {
        this.http.get(`http://localhost:8080/user/${userId}`).subscribe({
          next: (data) => {
            this.user = data;
          },
          error: (err) => {
            this.user = null;
          }
        });
      }
    }
  }

  getRoleText(role: string): string {
    if (role === 'USER') return 'Cliente';
    if (role === 'PROPIETARIO') return 'Propietario';
    if (role === 'ADMIN') return 'Admin';
    return role;
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  }

  eliminarCuenta() {
    if (!this.user || !this.user.id) return;
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará tu cuenta permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.http.delete(`http://localhost:8080/user/${this.user.id}`).subscribe({
          next: () => {
            localStorage.removeItem('user');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            Swal.fire('Eliminado', 'Tu cuenta ha sido eliminada.', 'success').then(() => {
              this.router.navigate(['/main']);
            });
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar la cuenta.', 'error');
          }
        });
      }
    });
  }
}
