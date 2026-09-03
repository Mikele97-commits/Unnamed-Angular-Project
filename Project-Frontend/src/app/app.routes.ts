import { Routes } from '@angular/router';
import {LoginComponent} from './pages/home/login/login.component';
import {RegisterComponent} from './pages/home/register/register.component';
import {MenuComponent} from './pages/game/menu/menu.component';
import {authGuard} from './core/auth/guard';
import {TrainingComponent} from './pages/game/training/training.component';
import {GameLayoutComponent} from './pages/game/game-layout.component';
import {AdminComponent} from './pages/game/admin/admin.component';
import {ExpeditionComponent} from './pages/game/expedition/expedition.component';
import {SewersComponent} from './pages/game/expedition/sewers/sewers.component';

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},


  {
    path: '',
    component: GameLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'menu', component: MenuComponent },
      { path: 'training', component: TrainingComponent},
      { path: 'admin', component: AdminComponent},
      {
        path: 'expedition',
        children: [
          { path: '', component: ExpeditionComponent },
          { path: 'sewers', component: SewersComponent }
        ]
      },
      { path: '', redirectTo: 'menu', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'login', pathMatch: 'full' }
];
