import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Piso } from '../../../models/piso.model';

@Component({
  selector: 'app-propietario-pisos',
  standalone: false,
  templateUrl: './propietario-pisos.component.html',
  styleUrl: './propietario-pisos.component.css'
})
export class PropietarioPisosComponent implements OnInit {

  pisos: Piso[] = [];
  totalPages: number = 0;
  currentPage: number = 0;
  urlImagen = 'https://www.ecartelera.com/images/noticias/fotos/61700/61737/1.jpg';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.loadPisos(0);
  }

  loadPisos(page: number) {
    const userStr = localStorage.getItem('user');
    const token = localStorage.getItem('accessToken');
    let propietarioId = null;
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr);
        propietarioId = userObj.id;
      } catch { }
    }
    if (propietarioId && token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.get<any>(`http://localhost:8080/piso/propietario/${propietarioId}?page=${page}`, { headers })
        .subscribe({
          next: (data) => {
            this.pisos = data.content || [];
            this.totalPages = data.totalPages || 1;
            this.currentPage = data.number || 0;
          },
          error: (err) => {
            this.pisos = [];
            this.totalPages = 0;
            this.currentPage = 0;
            console.error('Error al cargar anuncios del propietario', err);
          }
        });
    } else {
      this.pisos = [];
      this.totalPages = 0;
      this.currentPage = 0;
    }
  }

  cambiarPagina(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.loadPisos(page);
    }
  }

  eliminarPiso(anuncioId: string) {
    const token = localStorage.getItem('accessToken');
    const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : undefined;
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
        this.http.delete(`http://localhost:8080/piso/${anuncioId}`, { headers })
          .subscribe({
            next: () => {
              // Elimina el anuncio del array local tras borrarlo en el backend
              this.pisos = this.pisos.filter(a => a.id !== anuncioId);
              Swal.fire({
                title: "Eliminado",
                text: "El anuncio se eliminó exitosamente.",
                icon: "success"
              });
            },
            error: () => {
              Swal.fire({
                icon: "error",
                title: "Error",
                text: "No se pudo eliminar el anuncio. Inténtalo de nuevo.",
                timer: 3000,
              });
            }
          });
      }
    });
  }

  nuevoPiso() {
    this.router.navigate(['/agregar-piso']);
  }

  redirectEditForm(id: string) {
    this.router.navigate(['/editar-piso', id]);
  }
}
