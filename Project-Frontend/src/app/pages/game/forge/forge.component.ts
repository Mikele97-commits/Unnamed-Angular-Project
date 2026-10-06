import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {InventoryService} from '../../../core/services/inventory-service';
import {ForgingInfo} from '../../../models/forgingInfo.model';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'forge',
  imports: [FormsModule],
  templateUrl: 'forge.component.html',
  styleUrl: 'forge.component.css'
})

export class ForgeComponent implements OnInit {
  inventoryService=inject(InventoryService)
  http = inject(HttpClient);

  forgingInfo=signal<ForgingInfo| null>(null);

  items=this.inventoryService.items;
  upgradeId=signal<number|null>(null);
  ngOnInit() {
    this.inventoryService.loadInventory();
  }

  getForgingInfo(itemId:number){

    this.http.get<ForgingInfo>(`api/forge/getForgingInfo/${itemId}`).subscribe({
        next: (data) => {
          this.forgingInfo.set(data);
          this.upgradeId.set(itemId)
        }
      })
  }

  upgrade(itemId:number){
    this.http.post<any>(`/api/forge/upgrade/${itemId}`, null, {responseType:'text'}).subscribe({
      next: (data) => {
        console.log(data);
      }
    })
  }

  expandedId: number | null = null;
  toggleItem(id: number) {
    this.expandedId = this.expandedId === id ? null : id;
  }



}
