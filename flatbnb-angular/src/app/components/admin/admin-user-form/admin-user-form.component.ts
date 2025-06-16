import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-user-form',
  standalone: false,
  templateUrl: './admin-user-form.component.html',
  styleUrl: './admin-user-form.component.css'
})
export class AdminUserFormComponent implements OnInit {
  nombre: string = '';
  apellidos: string = '';
  email: string = '';
  telefono: string = '';
  role: string = '';
  userId: string | null = null;

constructor(
  private route: ActivatedRoute,
  private http: HttpClient,
  private router: Router
) { }

ngOnInit(): void {
  this.userId = this.route.snapshot.paramMap.get('id');
  if(this.userId) {
  const token = localStorage.getItem('accessToken');
  let headers = undefined;
  if (token) {
    headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }
  this.http.get<any>(`http://localhost:8080/user/${this.userId}`, { headers }).subscribe({
    next: (data) => {
      if (data) {
        this.nombre = data.nombre || '';
        this.apellidos = data.apellidos || '';
        this.email = data.email || '';
        this.telefono = data.telefono || '';
        this.role = data.role || '';
      } else {
        Swal.fire('Error', 'Usuario no encontrado.', 'error');
        this.router.navigate(['/admin']);
      }
    },
    error: () => {
      Swal.fire('Error', 'No se pudo cargar el usuario.', 'error');
    }
  });
}
  }

guardarUsuario() {
  if (!this.userId) return;
  const token = localStorage.getItem('accessToken');
  let headers = undefined;
  // Quitar corchetes si existen en this.role
  const cleanRole = this.role.replace(/^\[|\]$/g, '');
  const body = {
    nombre: this.nombre,
    apellidos: this.apellidos,
    email: this.email,
    telefono: this.telefono,
    role: cleanRole
  }
  if (token) {
    headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }
  this.http.put(`http://localhost:8080/user/${this.userId}`, body, { headers }).subscribe({
    next: () => {
      Swal.fire('Éxito', 'Usuario actualizado correctamente.', 'success').then(() => {
        this.router.navigate(['/admin']);
      });
    },
    error: () => {
      Swal.fire('Error', 'No se pudo actualizar el usuario.', 'error');
    }
  });
}
}
