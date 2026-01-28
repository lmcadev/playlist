import { Component, OnInit, inject, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { AuthService } from '../../services/auth.service';
import { UsuarioRequest, UsuarioResponse } from '../../models/usuario.model';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit, OnDestroy {
  private usuarioService = inject(UsuarioService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);
  private routerSubscription?: Subscription;

  usuarios: UsuarioResponse[] = [];
  showModal = false;
  isEditing = false;
  errorMessage = '';
  successMessage = '';
  
  currentUsuario: UsuarioRequest = {
    correo: '',
    nombre: '',
    password: ''
  };

  editingId: number | null = null;

  ngOnInit(): void {
    this.cargarUsuarios();
    
    // Suscribirse a eventos de navegación para recargar cuando se hace clic en el navbar
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        if (event.url === '/usuarios') {
          console.log('Navegación a /usuarios detectada - Recargando usuarios');
          this.cargarUsuarios();
        }
      });
  }

  ngOnDestroy(): void {
    // Limpiar suscripción para evitar memory leaks
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerTodos().subscribe({
      next: (data) => {
        console.log('Usuarios cargados:', data);
        this.usuarios = data;
        this.cdr.detectChanges(); // Forzar detección de cambios
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar usuarios';
        console.error('Error completo:', error);
      }
    });
  }

  abrirModal(usuario?: UsuarioResponse): void {
    this.showModal = true;
    this.errorMessage = '';
    
    if (usuario) {
      this.isEditing = true;
      this.editingId = usuario.id;
      this.currentUsuario = {
        correo: usuario.correo,
        nombre: usuario.nombre,
        password: ''
      };
    } else {
      this.isEditing = false;
      this.editingId = null;
      this.currentUsuario = {
        correo: '',
        nombre: '',
        password: ''
      };
    }
  }

  cerrarModal(): void {
    this.showModal = false;
    this.errorMessage = '';
    this.currentUsuario = {
      correo: '',
      nombre: '',
      password: ''
    };
  }

  guardarUsuario(): void {
    if (this.isEditing && this.editingId) {
      this.usuarioService.editar(this.editingId, this.currentUsuario).subscribe({
        next: () => {
          this.successMessage = 'Usuario actualizado correctamente';
          this.cargarUsuarios();
          this.cerrarModal();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (error) => {
          this.errorMessage = 'Error al actualizar usuario';
          console.error(error);
        }
      });
    } else {
      this.usuarioService.crear(this.currentUsuario).subscribe({
        next: () => {
          this.successMessage = 'Usuario creado correctamente';
          this.cargarUsuarios();
          this.cerrarModal();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (error) => {
          this.errorMessage = 'Error al crear usuario';
          console.error(error);
        }
      });
    }
  }

  eliminarUsuario(id: number): void {
    if (confirm('¿Está seguro de eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => {
          this.successMessage = 'Usuario eliminado correctamente';
          this.cargarUsuarios();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (error) => {
          this.errorMessage = 'Error al eliminar usuario';
          console.error(error);
        }
      });
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getUserName(): string | null {
    return this.authService.getUserName();
  }
}
