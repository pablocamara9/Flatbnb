import { Anuncio } from "./anuncio.model";
import { Propietario } from "./propietario.model";

export interface Pisos {
    pisos: Piso[];
}

export interface Piso {
    id: string;
    direccion: string;
    metrosCuadrados: number;
    numHabitaciones: number;
    observaciones: string;
    anuncio: Anuncio;
    propietario: Propietario;
}

export interface Root {
  listadoPisos: ListadoPiso[]
}

export interface ListadoPiso {
  id: string
  direccion: string
  metrosCuadrados: number
  numHabitaciones: number
  observaciones: string
  propietario: Propietario
}