import { Anuncio } from "./anuncio.model";
import { Piso } from "./piso.model";

export interface Propietario {
    nombre: string;
    apellidos: string;
    email: string;
    telefono: string;
    pisos: Piso[];
    anuncios: Anuncio[];
}