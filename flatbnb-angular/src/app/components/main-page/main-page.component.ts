import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Anuncio, Anuncios } from '../../models/anuncio.model';

@Component({
  selector: 'app-main-page',
  standalone: false,
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit {
  anuncios: Anuncio[] = [];
  paginaActual: number = 0;
  totalPaginas: number = 1;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.cargarAnuncios(0);
  }

  cargarAnuncios(pagina: number) {
    this.http.get<any>(`http://localhost:8080/anuncio/?page=${pagina}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
            this.paginaActual = data.number;
            this.totalPaginas = data.totalPages;
          } else {
            this.anuncios = [];
            this.paginaActual = 0;
            this.totalPaginas = 1;
          }
        },
        error: (err) => {
          this.anuncios = [];
          this.paginaActual = 0;
          this.totalPaginas = 1;
        }
      });
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 0 && nuevaPagina < this.totalPaginas) {
      this.cargarAnuncios(nuevaPagina);
    }
  }

  verDetalle(id: string) {
    this.router.navigate(['/anuncio', id]);
  }

  ordenaMasCaros() {
    this.http.get<any>(`http://localhost:8080/anuncio/precioDesc?orden=precio&direccion=desc&page=${this.paginaActual}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
          } else {
            this.anuncios = [];
          }
        },
        error: () => {
          this.anuncios = [];
        }
      });
  }

  ordenaMasBaratos() {
    this.http.get<any>(`http://localhost:8080/anuncio/precio?orden=precio&direccion=asc&page=${this.paginaActual}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
          } else {
            this.anuncios = [];
          }
        },
        error: () => {
          this.anuncios = [];
        }
      });
  }

  ordenaMasEspaciosos() {
    this.http.get<any>(`http://localhost:8080/anuncio/espacio?page=${this.paginaActual}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
          } else {
            this.anuncios = [];
          }
        },
        error: () => {
          this.anuncios = [];
        }
      });
  }

  ordenaMenosEspaciosos() {
    this.http.get<any>(`http://localhost:8080/anuncio/espacioMenor?page=${this.paginaActual}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
          } else {
            this.anuncios = [];
          }
        },
        error: () => {
          this.anuncios = [];
        }
      });
  }

  ordenaMasHabitaciones() {
    this.http.get<any>(`http://localhost:8080/anuncio/habitaciones?page=${this.paginaActual}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
          } else {
            this.anuncios = [];
          }
        },
        error: () => {
          this.anuncios = [];
        }
      });
  }

  ordenaMenosHabitaciones() {
        this.http.get<any>(`http://localhost:8080/anuncio/habitacionesMenor?page=${this.paginaActual}`)
      .subscribe({
        next: (data) => {
          if (data && data.content) {
            this.anuncios = data.content;
          } else {
            this.anuncios = [];
          }
        },
        error: () => {
          this.anuncios = [];
        }
      });
  }


}
