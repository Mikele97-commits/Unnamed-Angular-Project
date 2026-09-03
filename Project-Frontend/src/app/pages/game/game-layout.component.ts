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
  ngOnInit() {
      this.playerState.loadTopDiv();
  }

}
