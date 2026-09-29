import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {TopDivDto} from '../../models/topDiv.model';

@Injectable({
  providedIn: 'root'
})

export class PlayerStateService {
  private http = inject(HttpClient);

  topDivDto=signal<TopDivDto | null>(null);
  minutes=signal(0)
  seconds=signal(0)
  loadTopDiv(){
    this.http.get<TopDivDto>('/api/topDiv').subscribe(data =>{
          this.topDivDto.set(data);
          this.minutes.set(data.regenTime[0]);
          this.seconds.set(data.regenTime[1]);
          this.startCountdown();
      }
    )
  }

  private timerId: any = null;
  startCountdown(){
    if (this.timerId) {
      clearInterval(this.timerId);
    }
    this.timerId=setInterval(()=>{

      const m = this.minutes();
      const s = this.seconds();

      if (s > 0) {
        this.seconds.set(s - 1);
      } else if (m > 0) {
        this.minutes.set(m - 1);
        this.seconds.set(59);
      } else {
        clearInterval(this.timerId);
        this.timerId = null;
        setTimeout(() => {
          this.loadTopDiv();
        }, 1500)
      }
    },1000)
  }
}
