import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Piso } from '../../../models/piso.model';
import { AuthService } from '../../../services/auth-service.service';
import { Propietario, Propietarios } from '../../../models/propietario.model';

@Component({
  selector: 'app-propietario-piso-form',
  standalone: false,
  templateUrl: './propietario-piso-form.component.html',
  styleUrl: './propietario-piso-form.component.css'
})
export class PropietarioPisoFormComponent implements OnInit {

  direccion: string = '';
  metrosCuadrados: number | null = null;
  numHabitaciones: number | null = null;
  observaciones: string = '';
  pisoId: string | null = null;
  isEdit: boolean = false;
  propietarioId: string | null = null;
  isAdmin: boolean = false;
  propietarios: Propietario[] = [];

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    if (this.isAdmin) {
      this.cargarPropietarios();
    }
    this.pisoId = this.route.snapshot.paramMap.get('id');
    if (this.pisoId) {
      this.isEdit = true;
      const token = localStorage.getItem('accessToken');
      if (!token) return;
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.get<Piso>(`http://localhost:8080/piso/${this.pisoId}`, { headers }).subscribe({
        next: (data) => {          
          this.direccion = data.direccion;
          this.metrosCuadrados = data.metrosCuadrados;
          this.numHabitaciones = data.numHabitaciones;
          this.observaciones = data.observaciones;
          this.propietarioId = data.propietario.id;
        },
        error: () => {
          Swal.fire('Error', 'No se pudo cargar el piso.', 'error');
        }
      });
    }
  }

  cargarPropietarios() {
    const token = localStorage.getItem('accessToken');
    let headers = undefined;
    if (token) {
      headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    this.http.get<any>('http://localhost:8080/propietario/', { headers }).subscribe({
      next: (data) => {
        if (data && Array.isArray(data.listadoPropietarios)) {
          this.propietarios = data.listadoPropietarios;
        } else {
          this.propietarios = [];
        }
      },
      error: () => { this.propietarios = []; }
    });
  }

  agregarPiso() {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      Swal.fire('Error', 'No se encontró el token de autenticación.', 'error');
      return;
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    // Obtener el id del propietario desde el objeto 'user' en localStorage
    let idPropietario = null;
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr);
        idPropietario = this.propietarioId || userObj.id;
      } catch {}
    }
    const body = {
      direccion: this.direccion,
      metrosCuadrados: this.metrosCuadrados,
      numHabitaciones: this.numHabitaciones,
      observaciones: this.observaciones,
      idPropietario: idPropietario
    };

    if (this.isEdit && this.pisoId) {
      this.http.put(`http://localhost:8080/piso/${this.pisoId}`, body, { headers }).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Piso actualizado correctamente.', 'success').then(() => {
            if(this.authService.isAdmin()) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/propietario-pisos/', idPropietario]);
            }
          });
        },
        error: () => {
          Swal.fire('Error', 'No se pudo actualizar el piso.', 'error').then(() => {
            if(this.authService.isAdmin()) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/propietario-pisos/', idPropietario]);
            }
          });
        }
      });
    } else {
      this.http.post('http://localhost:8080/piso/', body, { headers }).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Piso agregado correctamente.', 'success').then(() => {
            window.location.reload();
          });
          this.router.navigate(['/propietario-pisos/', idPropietario]);
        },
        error: () => {
          Swal.fire('Error', 'No se pudo agregar el piso.', 'error').then(() => {
            window.location.reload();
          });
          this.router.navigate(['/propietario-pisos/', idPropietario]);
        }
      });
    }
  }
}
