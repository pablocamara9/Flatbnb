import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ListadoUsuario, Root } from '../../../models/user.model';
import { AdminService } from '../../../services/admin.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-main',
  standalone: false,
  templateUrl: './admin-main.component.html',
  styleUrl: './admin-main.component.css'
})
export class AdminMainComponent implements OnInit {

  usuarios: ListadoUsuario[] = [];

  anuncios: any[] = [];

  pisos: any[] = [];

  constructor(private http: HttpClient, private service: AdminService, private router: Router) { }

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarAnuncios(0, 10000000);
    this.cargarPisos();
  }

  cargarUsuarios() {
    const token = localStorage.getItem('accessToken');
    let headers = undefined;
    if (token) {
      headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    this.http.get<Root>('http://localhost:8080/user/', { headers })
      .subscribe({
        next: (data) => {
          // Asegura que el array usuarios contiene objetos UserAdminView
          if (Array.isArray(data)) {
            this.usuarios = data as ListadoUsuario[];
          } else if (data && Array.isArray(data.listadoUsuarios)) {
            this.usuarios = data.listadoUsuarios as ListadoUsuario[];
          } else if (data && Array.isArray(data.listadoUsuarios)) {
            this.usuarios = data.listadoUsuarios as ListadoUsuario[];
          } else {
            this.usuarios = [];
          }
        },
        error: () => { this.usuarios = []; }
      });
  }

  cargarAnuncios(pagina: number, size: number) {
    this.http.get<any>(`http://localhost:8080/anuncio/?page=${pagina}&size=${size}`)
      .subscribe({
        next: (data) => {
          if (Array.isArray(data)) {
            this.anuncios = data;
          } else if (data && Array.isArray(data.content)) {
            this.anuncios = data.content;
          } else if (data && Array.isArray(data.anuncios)) {
            this.anuncios = data.anuncios;
          } else {
            this.anuncios = [];
          }
        },
        error: () => { this.anuncios = []; }
      });
  }

  cargarPisos() {
    this.http.get<any>('http://localhost:8080/piso/')
      .subscribe({
        next: (data) => {
          if (Array.isArray(data)) {
            this.pisos = data;
          } else if (data && Array.isArray(data.content)) {
            this.pisos = data.content;
          } else if (data && Array.isArray(data.pisos)) {
            this.pisos = data.pisos;
          } else if (data && Array.isArray(data.listadoPisos)) {
            this.pisos = data.listadoPisos;
          } else {
            this.pisos = [];
          }
        },
        error: () => { this.pisos = []; }
      });
  }

  editarUsuario(id: string) {
    this.router.navigate(['/admin/editar-usuario', id]);
  }

  eliminarUsuario(id: string) {
    Swal.fire({
          title: "¿Estás seguro?",
          text: "No podrás deshacer esta acción!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Sí, bórralo!"
        }).then((result) => {
          if (result.isConfirmed) {
            this.service.eliminarUsuario(id).subscribe({
              next: () => {
                Swal.fire('Eliminado!', 'El usuario ha sido eliminado.', 'success').then(() => {
                  window.location.reload();
                  //this.cargarUsuarios();
                  
                });
              },
              error: () => {
                Swal.fire('Error', 'No se pudo eliminar el usuario.', 'error');
              }
            });
          }
        });
    }

    eliminarAnuncio(id: string) {
    Swal.fire({
          title: "¿Estás seguro?",
          text: "No podrás deshacer esta acción!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Sí, bórralo!"
        }).then((result) => {
          if (result.isConfirmed) {
            this.service.eliminarAnuncio(id).subscribe({
              next: () => {
                Swal.fire('Eliminado!', 'El anuncio ha sido eliminado.', 'success').then(() => {
                  window.location.reload();
                });
              },
              error: () => {
                Swal.fire('Error', 'No se pudo eliminar el anuncio.', 'error');
              }
            });
          }
        });
    }

    editarAnuncio(id: string) {
      this.router.navigate(['editar-anuncio/', id]);
    }
}
