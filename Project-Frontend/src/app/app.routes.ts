import { Routes } from '@angular/router';
import {LoginComponent} from './pages/home/login/login.component';
import {RegisterComponent} from './pages/home/register/register.component';
import {MenuComponent} from './pages/game/menu/menu.component';
import {authGuard} from './core/auth/guard';
import {TrainingComponent} from './pages/game/training/training.component';

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: 'menu',
    component: MenuComponent,
    canActivate: [authGuard]
  },
  {path: 'training', component: TrainingComponent},

  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
