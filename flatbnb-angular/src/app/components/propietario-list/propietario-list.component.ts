import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Propietario } from '../../models/propietario.model';

@Component({
  selector: 'app-propietario-list',
  standalone: false,
  templateUrl: './propietario-list.component.html',
  styleUrls: ['./propietario-list.component.css']
})
export class PropietarioListComponent implements OnInit {

  propietarios: Propietario[] = [];
  propietariosPagina: Propietario[] = [];
  paginaActual: number = 0;
  tamanoPagina: number = 6;
  totalPaginas: number = 1;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any>('http://localhost:8080/propietario/').subscribe({
      next: (data) => {
        console.log('Respuesta propietarios:', data);
        if (data && Array.isArray(data.listadoPropietarios)) {
          this.propietarios = data.listadoPropietarios;
          this.totalPaginas = Math.ceil(this.propietarios.length / this.tamanoPagina);
          this.paginaActual = 0;
          this.actualizarPropietariosPagina();
        } else {
          this.propietarios = [];
          this.propietariosPagina = [];
          this.totalPaginas = 1;
        }
      },
      error: () => {
        this.propietarios = [];
        this.propietariosPagina = [];
        this.totalPaginas = 1;
      }
    });
  }

  actualizarPropietariosPagina() {
    const inicio = this.paginaActual * this.tamanoPagina;
    const fin = inicio + this.tamanoPagina;
    this.propietariosPagina = this.propietarios.slice(inicio, fin);
  }

  cambiarPagina(pagina: number) {
    if (pagina < 0 || pagina >= this.totalPaginas) return;
    this.paginaActual = pagina;
    this.actualizarPropietariosPagina();
  }

  mejorValorados() {
    this.propietarios = [...this.propietarios].sort((a, b) => (b.valoracion ?? 0) - (a.valoracion ?? 0));
    this.paginaActual = 0;
    this.actualizarPropietariosPagina();
  }

  peorValorados() {
    this.propietarios = [...this.propietarios].sort((a, b) => (a.valoracion ?? 0) - (b.valoracion ?? 0));
    this.paginaActual = 0;
    this.actualizarPropietariosPagina();
  }

}
