import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {RouterLink, RouterOutlet, RouterLinkActive} from '@angular/router';
import {StatBarComponent} from '../../shared/components/stat-bar/stat-bar.component';
import {PlayerStateService} from '../../core/services/player-state.service';
import {AuthService} from '../../core/auth/auth.service';

@Component({
  selector: 'layout',
  imports: [FormsModule, CommonModule, RouterOutlet, StatBarComponent, RouterLink, RouterLinkActive],
  templateUrl: 'game-layout.component.html',
  styleUrl: 'game-layout.component.css'
})

export class GameLayoutComponent implements OnInit {

  playerState = inject(PlayerStateService);
  authService=inject(AuthService);

  isAdmin=this.authService.isAdmin;
  topDivDto=this.playerState.topDivDto;
  minutes = 0;
  seconds = 0;
  timerId: any = null;
  ngOnInit() {
      this.playerState.loadTopDiv();

      const times = this.topDivDto()?.regenTime;
      if(times) this.calculateTimeToRegen(times[0], times[1]);

  }

  calculateTimeToRegen(minutes: number, seconds:number){
    console.log("Got calculation request for:"+minutes+' '+seconds);
    this.minutes= minutes;
    this.seconds=seconds;

    if (this.timerId) {
      clearInterval(this.timerId);
    }

    this.timerId = setInterval(() => {
      if (this.seconds > 0) {
        this.seconds--;
      } else if (this.minutes > 0) {
        this.minutes--;
        this.seconds = 59;
      }
    }, 1000);
  }

}
