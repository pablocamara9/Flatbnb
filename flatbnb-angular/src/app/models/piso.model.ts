import { Anuncio } from "./anuncio.model";

export interface Piso {
    id: string;
    direccion: string;
    metrosCuadrados: number;
    numHabitaciones: number;
    observaciones: string;
    anuncio: Anuncio;
}