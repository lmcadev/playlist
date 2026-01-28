import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ListaReproduccionRequest, ListaReproduccionResponse } from '../models/lista-reproduccion.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ListaReproduccionService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private apiUrl = '/api/v1/lists';

  obtenerTodas(): Observable<ListaReproduccionResponse[]> {
    return this.http.get<ListaReproduccionResponse[]>(this.apiUrl, {
      headers: this.authService.getHeaders()
    });
  }

  obtenerPorNombre(nombre: string): Observable<ListaReproduccionResponse> {
    return this.http.get<ListaReproduccionResponse>(`${this.apiUrl}/${nombre}`, {
      headers: this.authService.getHeaders()
    });
  }

  crear(lista: ListaReproduccionRequest): Observable<ListaReproduccionResponse> {
    return this.http.post<ListaReproduccionResponse>(this.apiUrl, lista, {
      headers: this.authService.getHeaders()
    });
  }

  eliminar(nombre: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${nombre}`, {
      headers: this.authService.getHeaders()
    });
  }
}
