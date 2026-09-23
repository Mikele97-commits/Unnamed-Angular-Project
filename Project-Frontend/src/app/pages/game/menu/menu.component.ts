import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../../core/auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {ItemModel} from '../../../models/item.model';
import {TrainingResponseDto} from '../../../models/trainingResponse.model';
import {TrainingStatsDto} from '../../../models/TrainingStats.model';
import {PlayerStateService} from '../../../core/services/player-state.service';


@Component({
  selector: 'menu',
  imports: [FormsModule],
  templateUrl: 'menu.component.html',
  styleUrl: 'menu.component.css'
})

export class MenuComponent implements OnInit {
  authService=inject(AuthService);
  private http = inject(HttpClient);
  playerState = inject(PlayerStateService);


  logout(){
    this.authService.logout();
  }

  equipItem(itemId:number){
    this.http.post('/api/inventory/equipItem',itemId).subscribe(
      {
        next:(data) => {
          console.log(data);
          this.refresh();
        }
      }
    )
  }
  items = signal<any[]>([]);  expandedId: number | null = null;
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
    this.http.get<Array<ItemModel>>('/api/inventory/get').subscribe(
      {
        next: (data) => {
          this.items.set(data)
        }
      }
    );
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


