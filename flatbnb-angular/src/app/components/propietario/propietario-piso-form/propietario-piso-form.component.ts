import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Piso } from '../../../models/piso.model';

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

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.pisoId = this.route.snapshot.paramMap.get('id');
    if (this.pisoId) {
      this.isEdit = true;
      const token = localStorage.getItem('accessToken');
      if (!token) return;
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.get<any>(`http://localhost:8080/piso/${this.pisoId}`, { headers }).subscribe({
        next: (data) => {
          this.direccion = data.direccion;
          this.metrosCuadrados = data.metrosCuadrados;
          this.numHabitaciones = data.numHabitaciones;
          this.observaciones = data.observaciones;
        },
        error: () => {
          Swal.fire('Error', 'No se pudo cargar el piso.', 'error');
        }
      });
    }
  }

  agregarPiso() {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      Swal.fire('Error', 'No se encontró el token de autenticación.', 'error');
      return;
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const body = {
      direccion: this.direccion,
      metrosCuadrados: this.metrosCuadrados,
      numHabitaciones: this.numHabitaciones,
      observaciones: this.observaciones
    };

    if (this.isEdit && this.pisoId) {
      this.http.put(`http://localhost:8080/piso/${this.pisoId}`, body, { headers }).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Piso actualizado correctamente.', 'success').then(() => {
            this.router.navigate(['/mis-pisos']);
          });
        },
        error: () => {
          Swal.fire('Error', 'No se pudo actualizar el piso.', 'error');
        }
      });
    } else {
      this.http.post('http://localhost:8080/piso/', body, { headers }).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Piso agregado correctamente.', 'success').then(() => {
            this.router.navigate(['/mis-pisos']);
          });
        },
        error: () => {
          Swal.fire('Error', 'No se pudo agregar el piso.', 'error');
        }
      });
    }
  }
}
