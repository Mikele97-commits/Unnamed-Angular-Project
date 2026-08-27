import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {TrainingStatsDto, TrainingAddDto} from '../../../models/TrainingStats.model';
import {CommonModule} from '@angular/common';
import {TrainingResponseDto} from '../../../models/trainingResponse.model';
import {PlayerStateService} from '../../../core/services/player-state.service';

@Component({
  selector: 'training',
  imports: [FormsModule,CommonModule],
  templateUrl: 'training.component.html',
  styleUrl: 'training.component.css'
})

export class TrainingComponent implements OnInit {
  private http = inject(HttpClient);
  playerState = inject(PlayerStateService);

  trainingDto=signal<TrainingStatsDto | null>(null);
  ngOnInit() {
    this.http.get<TrainingResponseDto>('/api/training').subscribe(
      {
        next: (data) => {
          this.trainingDto.set(data.trainingStatsDto)
        }
      }
    )
  }

  addTraining(stat:string){
      const statistic: TrainingAddDto={
        stat:stat
    }
      this.http.post<TrainingResponseDto>('/api/training/add', statistic).subscribe(
        {
          next: (data) =>{
            if(data.success){
              this.trainingDto.set(data.trainingStatsDto)
              this.playerState.loadTopDiv()

            }else{
              alert(data.message);
            }
          }
        }
      )
  }
}
