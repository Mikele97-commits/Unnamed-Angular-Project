import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {TrainingStatsDto, TrainingAddDto} from '../../../models/TrainingStats.model';
import {CommonModule} from '@angular/common';


@Component({
  selector: 'training',
  imports: [FormsModule,CommonModule],
  templateUrl: 'training.component.html',
  styleUrl: 'training.component.css'
})

export class TrainingComponent implements OnInit {
  private http = inject(HttpClient);

  trainingDto=signal<TrainingStatsDto | null>(null);
    ngOnInit() {
    this.http.get<TrainingStatsDto>('/api/training').subscribe(
      {
        next: (data) => {
          this.trainingDto.set(data)
        }
      }
    )
  }

  addTraining(stat:string){
      const statistic: TrainingAddDto={
        stat:stat
    }
      this.http.post<TrainingStatsDto>('/api/training/add', statistic).subscribe(
        {
          next: (data) =>{
            this.trainingDto.set(data)
          }
        }
      )
  }
}
