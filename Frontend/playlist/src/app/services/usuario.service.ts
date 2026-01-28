import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioRequest, UsuarioResponse } from '../models/usuario.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private apiUrl = '/api/v1/usuarios';

  obtenerTodos(): Observable<UsuarioResponse[]> {
    return this.http.get<UsuarioResponse[]>(this.apiUrl, {
      headers: this.authService.getHeaders()
    });
  }

  obtenerPorId(id: number): Observable<UsuarioResponse> {
    return this.http.get<UsuarioResponse>(`${this.apiUrl}/${id}`, {
      headers: this.authService.getHeaders()
    });
  }

  crear(usuario: UsuarioRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(this.apiUrl, usuario, {
      headers: this.authService.getHeaders()
    });
  }

  editar(id: number, usuario: UsuarioRequest): Observable<UsuarioResponse> {
    return this.http.put<UsuarioResponse>(`${this.apiUrl}/${id}`, usuario, {
      headers: this.authService.getHeaders()
    });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.authService.getHeaders()
    });
  }
}
