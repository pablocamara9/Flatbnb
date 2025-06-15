import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Anuncio } from '../../models/anuncio.model';

@Component({
  selector: 'app-propietario',
  standalone: false,
  templateUrl: './propietario.component.html',
  styleUrl: './propietario.component.css'
})
export class PropietarioComponent implements OnInit {
  anuncios: Anuncio[] = [];
  totalPages: number = 0;
  currentPage: number = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadAnuncios(0);
  }

  loadAnuncios(page: number) {
    const userStr = localStorage.getItem('user');
    let propietarioId = null;
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr);
        propietarioId = userObj.id;
      } catch {}
    }
    if (propietarioId) {
      this.http.get<any>(`http://localhost:8080/anuncio/propietario/${propietarioId}?page=${page}`)
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
}
