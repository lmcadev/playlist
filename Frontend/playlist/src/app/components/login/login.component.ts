import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/auth.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  credentials: LoginRequest = {
    correo: '',
    password: ''
  };

  errorMessage = '';
  isLoading = false;

  onSubmit(): void {
    if (!this.credentials.correo || !this.credentials.password) {
      this.errorMessage = 'Por favor, complete todos los campos';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.router.navigate(['/usuarios']);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = 'Credenciales inválidas. Por favor, intente nuevamente.';
        console.error('Error de login:', error);
      }
    });
  }
}
