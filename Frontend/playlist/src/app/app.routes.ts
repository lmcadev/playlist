import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { UsuariosComponent } from './components/usuarios/usuarios.component';
import { ListasComponent } from './components/listas/listas.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { 
    path: 'usuarios', 
    component: UsuariosComponent,
    canActivate: [authGuard]
  },
  { 
    path: 'listas', 
    component: ListasComponent,
    canActivate: [authGuard]
  },
  { path: '**', redirectTo: '/login' }
];
