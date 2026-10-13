import {Component, inject, OnInit, OnDestroy} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {RouterLink, RouterOutlet, RouterLinkActive, Router, NavigationEnd} from '@angular/router';
import {StatBarComponent} from '../../shared/components/stat-bar/stat-bar.component';
import {PlayerStateService} from '../../core/services/player-state.service';
import {AuthService} from '../../core/auth/auth.service';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'layout',
  imports: [FormsModule, CommonModule, RouterOutlet, StatBarComponent, RouterLink, RouterLinkActive],
  templateUrl: 'game-layout.component.html',
  styleUrl: 'game-layout.component.css'
})

export class GameLayoutComponent implements OnInit {

  router=inject(Router);
  playerState = inject(PlayerStateService);
  authService=inject(AuthService);

  topDivDto=this.playerState.topDivDto;
  shellClass = 'menu-page';
  private sub?: Subscription;

  ngOnInit() {
    this.playerState.loadTopDiv();
    this.updateShellClass(this.router.url);

    this.sub=this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.updateShellClass(event.urlAfterRedirects);
      });
  }

  private updateShellClass(url: string) {
    if (url.includes('/forge')) this.shellClass = 'forge-page';
    else if (url.includes('/training')) this.shellClass = 'training-page';
    else if (url.includes('/sewers')) this.shellClass = 'sewers-page';
    else if (url.includes('/expedition')) this.shellClass = 'expedition-page';
    else if (url.includes('/arena')) this.shellClass = 'arena-page';
    else if (url.includes('/admin')) this.shellClass = 'admin-page';
    else if (url.includes('/report')) this.shellClass = 'report-page';
    else this.shellClass = 'menu-page';
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }



}
