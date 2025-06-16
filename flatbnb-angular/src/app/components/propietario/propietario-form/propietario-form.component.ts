import { Component } from '@angular/core';

@Component({
  selector: 'app-propietario-form',
  standalone: false,
  templateUrl: './propietario-form.component.html',
  styleUrl: './propietario-form.component.css'
})
export class PropietarioFormComponent {
  anuncio = {
    descripcion: '',
    precio: null,
    imagenUrl: ''
  };

  onSubmit() {
    // Aquí puedes manejar el envío del formulario, por ejemplo, enviarlo a un servicio
    console.log('Anuncio enviado:', this.anuncio);
    // Opcional: resetear el formulario
    this.anuncio = {
      descripcion: '',
      precio: null,
      imagenUrl: ''
    };
  }
}
