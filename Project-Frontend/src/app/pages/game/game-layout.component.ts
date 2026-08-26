import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpClient} from '@angular/common/http';
import {TopDivDto} from '../../models/topDiv.model';
import {TrainingStatsDto} from '../../models/TrainingStats.model';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'layout',
  imports: [FormsModule,CommonModule, RouterOutlet],
  templateUrl: 'game-layout.component.html',
  styleUrl: 'game-layout.component.css'
})

export class GameLayoutComponent implements OnInit {

  private http = inject(HttpClient);

  topDivDto=signal<TopDivDto | null>(null);
  ngOnInit() {
    this.http.get<TopDivDto>('/api/topDiv').subscribe(
      {
        next:(data) =>{
          this.topDivDto.set(data);
        }
      }

    )

  }

}
