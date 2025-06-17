import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Anuncio } from '../../../models/anuncio.model';
import { Piso } from '../../../models/piso.model';
import { AuthService } from '../../../services/auth-service.service';
import { Propietario } from '../../../models/propietario.model';

@Component({
  selector: 'app-propietario-form',
  standalone: false,
  templateUrl: './propietario-form.component.html',
  styleUrl: './propietario-form.component.css'
})
export class PropietarioFormComponent implements OnInit {

  isEdit: boolean = false;
  descripcion: string = '';
  precio: number | null = null;
  urlImagen: string = '';
  anuncioId: string | null = null;
  propietarioId: string | null = null;
  propietarios: Propietario[] = [];
  admin: boolean = false;

  pisoId: string = '';
  pisos: Piso[] = [];

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.admin = this.authService.isAdmin();
    this.obtenerPropietarios();
    this.anuncioId = this.route.snapshot.paramMap.get('id');
    if (this.anuncioId) {
      this.isEdit = true;
      const token = localStorage.getItem('accessToken');
      if (!token) return;
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.get<Anuncio>(`http://localhost:8080/anuncio/${this.anuncioId}`, { headers }).subscribe({
        next: (data) => {
          this.descripcion = data.descripcion;
          this.precio = data.precio;
          this.urlImagen = data.urlImagen;
          this.pisoId = data.piso?.id ?? null;
          this.propietarioId = data.piso.propietario.id
          this.cargarPisos(); // <-- Mueve la llamada aquí
        },
        error: () => {
          Swal.fire('Error', 'No se pudo cargar el anuncio.', 'error');
        }
      });
      // this.cargarPisos();
    } else {
      this.cargarPisos();
    }
  }

  obtenerPropietarios() {
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

  agregarAnuncio() {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      Swal.fire('Error', 'No se encontró el token de autenticación.', 'error');
      return;
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    let idPropietario = null;
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr);
        idPropietario = this.propietarioId || userObj.id;
      } catch { }
    }

    let idPiso = this.pisoId
    const body = {
      descripcion: this.descripcion,
      precio: this.precio,
      urlImagen: this.urlImagen,
      idPiso: idPiso,
      idPropietario: idPropietario
    };

    if (this.isEdit && this.anuncioId) {
      this.http.put(`http://localhost:8080/anuncio/${this.anuncioId}`, body, { headers }).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Anuncio actualizado correctamente.', 'success').then(() => {
            if(this.authService.isAdmin()) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/propietario/', idPropietario]);
            }
          });
        },
        error: () => {
          Swal.fire('Error', 'No se pudo actualizar el anuncio.', 'error').then(() => {
            if(this.authService.isAdmin()) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/propietario/', idPropietario]);
            }
          });
        }
      });
    } else {
      this.http.post('http://localhost:8080/anuncio/', body, { headers }).subscribe({
        next: () => {
          Swal.fire('Éxito', 'Anuncio agregado correctamente.', 'success').then(() => {
            if(this.authService.isAdmin()) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/propietario/', idPropietario]);
            }
          });
        },
        error: () => {
          Swal.fire('Error', 'No se pudo agregar el anuncio.', 'error').then(() => {
            if(this.authService.isAdmin()) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/propietario/', idPropietario]);
            }
          });
        }
      });
    }
  }

  /*cargarPisos() {
    const token = localStorage.getItem('accessToken');
    if (!token) return;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    // Obtener el id del propietario desde el objeto 'user' en localStorage
    let idPropietario = null;
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr);
        idPropietario = userObj.id;
      } catch { }
    }
    this.http.get<Piso[]>(`http://localhost:8080/piso/propietario/${idPropietario}`, { headers }).subscribe({
      next: (data) => {
        this.pisos = data;
      },
      error: () => {
        Swal.fire('Error', 'No se pudieron cargar los pisos.', 'error');
      }
    });
  }*/

  cargarPisos() {
    const token = localStorage.getItem('accessToken');
    let propietarioId = this.propietarioId;
    // Si no hay propietario seleccionado y no es admin, usar el del usuario logado
    if (!propietarioId && !this.admin) {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const userObj = JSON.parse(userStr);
          propietarioId = userObj.id;
        } catch { }
      }
    }
    if (propietarioId && token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.get<any>(`http://localhost:8080/piso/propietario/${propietarioId}?page=0`, { headers })
        .subscribe({
          next: (data) => {
            this.pisos = data.content || [];
          },
          error: (err) => {
            this.pisos = [];
            console.error('Error al cargar pisos del propietario', err);
          }
        });
    } else {
      this.pisos = [];
    }
  }
}
