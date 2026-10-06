import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../../core/auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {TrainingResponseDto} from '../../../models/trainingResponse.model';
import {TrainingStatsDto} from '../../../models/TrainingStats.model';
import {PlayerStateService} from '../../../core/services/player-state.service';
import {InventoryService} from '../../../core/services/inventory-service';


@Component({
  selector: 'menu',
  imports: [FormsModule],
  templateUrl: 'menu.component.html',
  styleUrl: 'menu.component.css'
})

export class MenuComponent implements OnInit {
  authService=inject(AuthService);
  http = inject(HttpClient);
  playerState = inject(PlayerStateService);
  inventoryService= inject(InventoryService);


  logout(){
    this.authService.logout();
  }

  equipItem(itemId:number){
    this.http.post('/api/inventory/equipItem',itemId).subscribe(
      {
        next:() => {
          this.refresh();
        }
      }
    )
  }

  unequipItem(itemId:number){
    this.http.post('/api/inventory/unequipItem',itemId).subscribe(
      {
        next:(data) => {
          console.log(data);
          this.refresh();
        }
      }
    )
  }

  expandedId: number | null = null;
  toggleItem(id: number) {
    this.expandedId = this.expandedId === id ? null : id;
  }

  trainingDto=signal<TrainingStatsDto | null>(null);
  ngOnInit() {
    this.refresh();
  }

  refresh(){
    this.playerState.loadTopDiv();
    this.loadStats()
    this.loadInventory()
  }
  loadInventory() {
    this.inventoryService.loadInventory()
  }

  loadStats(){
    this.http.get<TrainingResponseDto>('/api/training').subscribe(
      {
        next: (data) => {
          this.trainingDto.set(data.trainingStatsDto)
        }
      }
    )
  }


}


