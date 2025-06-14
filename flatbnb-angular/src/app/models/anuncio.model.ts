export interface Anuncios {
    anuncios: Anuncio[];
}

export interface Anuncio {
    id: string;
    descripcion: string;
    precio: number;
    urlImagen: string;
}