import { Component } from '@angular/core';

@Component({
  selector: 'app-propietario-form',
  standalone: false,
  templateUrl: './propietario-form.component.html',
  styleUrl: './propietario-form.component.css'
})
export class PropietarioFormComponent {
  
  isEdit: boolean = false;
  descripcion: string = '';
  precio: number | null = null;
  imagenUrl: string = '';


  agregarAnuncio() {

  }
}
