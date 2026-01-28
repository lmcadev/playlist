export interface Usuario {
  id?: number;
  correo: string;
  nombre: string;
  password?: string;
}

export interface UsuarioRequest {
  correo: string;
  nombre: string;
  password: string;
}

export interface UsuarioResponse {
  id: number;
  correo: string;
  nombre: string;
}
