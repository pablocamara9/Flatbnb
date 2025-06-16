import { Anuncio } from "./anuncio.model";
import { Piso } from "./piso.model";

export interface Propietarios {
    propietarios: Propietario[];
}

export interface Propietario {
    id: string;
    nombre: string;
    apellidos: string;
    email: string;
    telefono: string;
    pisos: Piso[];
    anuncios: Anuncio[];
}