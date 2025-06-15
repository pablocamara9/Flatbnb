import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Anuncio } from '../../../models/anuncio.model';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-propietario',
  standalone: false,
  templateUrl: './propietario-anuncios.component.html',
  styleUrl: './propietario-anuncios.component.css'
})
export class PropietarioComponent implements OnInit {

  anuncios: Anuncio[] = [];
  totalPages: number = 0;
  currentPage: number = 0;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.loadAnuncios(0);
  }

  loadAnuncios(page: number) {
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
      this.http.get<any>(`http://localhost:8080/anuncio/propietario/${propietarioId}?page=${page}`, { headers })
        .subscribe({
          next: (data) => {
            // Si tu backend devuelve { content: [...], totalPages: n, number: n, ... }
            this.anuncios = data.content || [];
            this.totalPages = data.totalPages || 1;
            this.currentPage = data.number || 0;
          },
          error: (err) => {
            this.anuncios = [];
            this.totalPages = 0;
            this.currentPage = 0;
            console.error('Error al cargar anuncios del propietario', err);
          }
        });
    } else {
      this.anuncios = [];
      this.totalPages = 0;
      this.currentPage = 0;
    }
  }

  cambiarPagina(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.loadAnuncios(page);
    }
  }

  eliminarAnuncio(anuncioId: string) {
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
        this.http.delete(`http://localhost:8080/anuncio/${anuncioId}`, { headers })
          .subscribe({
            next: () => {
              // Elimina el anuncio del array local tras borrarlo en el backend
              this.anuncios = this.anuncios.filter(a => a.id !== anuncioId);
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

  nuevoAnuncio() {
    this.router.navigate(['/agregar-anuncio']);
  }

  redirectEditForm(id: string) {
    this.router.navigate(['/editar-anuncio', id]);
  }
}
