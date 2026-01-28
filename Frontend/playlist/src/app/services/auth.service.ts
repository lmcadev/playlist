import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = '/api/v1/auth';
  private tokenKey = 'auth_token';

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem(this.tokenKey, response.token);
          localStorage.setItem('user_email', response.correo);
          localStorage.setItem('user_name', response.nombre);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem('user_email');
    localStorage.removeItem('user_name');
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  getUserName(): string | null {
    return localStorage.getItem('user_name');
  }

  getHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }
}
