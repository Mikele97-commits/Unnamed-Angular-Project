import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpClient} from '@angular/common/http';
import {profileDto} from '../../../models/profile.model';
import {PlayerStateService} from '../../../core/services/player-state.service';
import {pvmReport} from '../../../models/pvmReport.model';
import {Router} from '@angular/router';


@Component({
  selector: 'arena',
  imports: [FormsModule,CommonModule],
  templateUrl: 'arena.component.html',
  styleUrl: 'arena.component.css'
})

export class ArenaComponent{
  private router=inject(Router)
  private http = inject(HttpClient);
  playerState = inject(PlayerStateService);

  topDivDto=this.playerState.topDivDto;

  enemies:profileDto[]=[]
  findEnemies(){

    const lvl = this.topDivDto()?.level;
    this.http.get <profileDto[]>(`api/arena/find/${lvl}`).subscribe({
      next: (data) => {
        this.enemies=data;
      }

    })
  }

  attack(username:string){
    this.http.post<pvmReport>('api/arena/attack', username).subscribe({
      next: (data) => {
        this.playerState.loadTopDiv()
        this.router.navigate(['report'], {state:{report:data}});
      }
    })
  }

}
