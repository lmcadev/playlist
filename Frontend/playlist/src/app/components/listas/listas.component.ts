import { Component, OnInit, inject, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { ListaReproduccionService } from '../../services/lista-reproduccion.service';
import { AuthService } from '../../services/auth.service';
import { ListaReproduccionRequest, ListaReproduccionResponse, Cancion } from '../../models/lista-reproduccion.model';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-listas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './listas.component.html',
  styleUrls: ['./listas.component.css']
})
export class ListasComponent implements OnInit, OnDestroy {
  private listaService = inject(ListaReproduccionService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);
  private routerSubscription?: Subscription;

  listas: ListaReproduccionResponse[] = [];
  showModal = false;
  errorMessage = '';
  successMessage = '';
  listaSeleccionada: ListaReproduccionResponse | null = null;

  constructor() {
    console.log('ListasComponent constructor - listas:', this.listas);
  }
  
  nuevaLista: ListaReproduccionRequest = {
    nombre: '',
    descripcion: '',
    canciones: []
  };

  nuevaCancion: Cancion = {
    titulo: '',
    artista: '',
    album: '',
    anno: '',
    genero: ''
  };

  ngOnInit(): void {
    console.log('ngOnInit - Iniciando carga de listas');
    this.cargarListas();
    
    // Suscribirse a eventos de navegación para recargar cuando se hace clic en el navbar
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        if (event.url === '/listas') {
          console.log('Navegación a /listas detectada - Recargando listas');
          this.cargarListas();
        }
      });
  }

  ngOnDestroy(): void {
    // Limpiar suscripción para evitar memory leaks
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  cargarListas(): void {
    console.log('cargarListas - Haciendo petición al backend');
    this.listaService.obtenerTodas().subscribe({
      next: (data) => {
        console.log('Listas cargadas exitosamente:', data);
        console.log('Cantidad de listas:', data.length);
        this.listas = data;
        console.log('this.listas después de asignar:', this.listas);
        this.cdr.detectChanges(); // Forzar detección de cambios
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar listas';
        console.error('Error completo al cargar listas:', error);
        console.error('Status:', error.status);
        console.error('Message:', error.message);
      }
    });
  }

  abrirModal(): void {
    this.showModal = true;
    this.errorMessage = '';
    this.nuevaLista = {
      nombre: '',
      descripcion: '',
      canciones: []
    };
  }

  cerrarModal(): void {
    this.showModal = false;
    this.errorMessage = '';
  }

  agregarCancion(): void {
    if (this.nuevaCancion.titulo && this.nuevaCancion.artista) {
      this.nuevaLista.canciones.push({ ...this.nuevaCancion });
      this.nuevaCancion = {
        titulo: '',
        artista: '',
        album: '',
        anno: '',
        genero: ''
      };
    }
  }

  eliminarCancion(index: number): void {
    this.nuevaLista.canciones.splice(index, 1);
  }

  guardarLista(): void {
    if (this.nuevaLista.canciones.length === 0) {
      this.errorMessage = 'Debe agregar al menos una canción';
      return;
    }

    this.listaService.crear(this.nuevaLista).subscribe({
      next: () => {
        this.successMessage = 'Lista creada correctamente';
        this.cargarListas();
        this.cerrarModal();
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (error) => {
        this.errorMessage = 'Error al crear lista';
        console.error(error);
      }
    });
  }

  verDetalle(lista: ListaReproduccionResponse): void {
    this.listaSeleccionada = lista;
  }

  cerrarDetalle(): void {
    this.listaSeleccionada = null;
  }

  eliminarLista(nombre: string): void {
    if (confirm('¿Está seguro de eliminar esta lista?')) {
      this.listaService.eliminar(nombre).subscribe({
        next: () => {
          this.successMessage = 'Lista eliminada correctamente';
          this.cargarListas();
          this.cerrarDetalle();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (error) => {
          this.errorMessage = 'Error al eliminar lista';
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
