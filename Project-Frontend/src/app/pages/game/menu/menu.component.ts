import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../../core/auth/auth.service';

@Component({
  selector: 'menu',
  imports: [FormsModule],
  templateUrl: 'menu.component.html',
  styleUrl: 'menu.component.css'
})

export class MenuComponent {

  authService=inject(AuthService);
  logout(){
    this.authService.logout();
  }
}
