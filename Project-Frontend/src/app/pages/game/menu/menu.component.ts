import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../../core/auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {ItemModel} from '../../../models/item.model';


@Component({
  selector: 'menu',
  imports: [FormsModule],
  templateUrl: 'menu.component.html',
  styleUrl: 'menu.component.css'
})

export class MenuComponent {
  authService=inject(AuthService);
  private http = inject(HttpClient);

  logout(){
    this.authService.logout();
  }

  getInventory(){
    this.http.get<Array<ItemModel>>('/api/inventory/get').subscribe(
      {
        next: (data) =>{
          console.log(data);
        }
      }
    )
  }


}
