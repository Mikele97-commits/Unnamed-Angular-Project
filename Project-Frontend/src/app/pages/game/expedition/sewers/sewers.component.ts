import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {PlayerStateService} from '../../../../core/services/player-state.service';
import {Router} from '@angular/router';
import {pvmReport} from '../../../../models/pvmReport.model';

@Component({
  selector: 'sewers',
  imports: [FormsModule],
  templateUrl: 'sewers.component.html',
  styleUrl: 'sewers.component.css'
})

export class SewersComponent {
  playerStatService = inject(PlayerStateService);

  private http= inject(HttpClient)
  private router=inject(Router)
  playerState=inject(PlayerStateService)


  fightRat() : void{
    this.http.get<pvmReport>('api/expedition/Rat').subscribe({
      next: (dto:pvmReport)=>{
        this.playerState.loadTopDiv()
        this.router.navigate(['report'], {state:{report:dto}});
      },
      error: (err) => {
        console.error('POST failed', err);
      }
    })
  }



}
