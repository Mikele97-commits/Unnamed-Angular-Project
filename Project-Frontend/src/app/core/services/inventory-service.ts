import {inject, Injectable, signal} from '@angular/core';
import {ItemModel} from '../../models/item.model';
import {HttpClient} from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})

export class InventoryService{
  http = inject(HttpClient);



  items=signal<any[]>([])
  loadInventory() {
    this.http.get<Array<ItemModel>>('/api/inventory/get').subscribe(
      {
        next: (data) => {
          this.items.set(data)
        }
      }
    );
  }
}
