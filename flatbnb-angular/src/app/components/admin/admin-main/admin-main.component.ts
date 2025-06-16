import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ListadoUsuario, Root } from '../../../models/user.model';
import { Pisos } from '../../../models/piso.model';

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

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarAnuncios();
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

  cargarAnuncios() {
    this.http.get<any>('http://localhost:8080/anuncio/')
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
}
