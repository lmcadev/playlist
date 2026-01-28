export interface Cancion {
  titulo: string;
  artista: string;
  album: string;
  anno: string;
  genero: string;
}

export interface ListaReproduccion {
  nombre: string;
  descripcion: string;
  canciones: Cancion[];
}

export interface ListaReproduccionRequest {
  nombre: string;
  descripcion: string;
  canciones: Cancion[];
}

export interface ListaReproduccionResponse {
  nombre: string;
  descripcion: string;
  canciones: Cancion[];
}
