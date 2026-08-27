import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {TopDivDto} from '../../models/topDiv.model';

@Injectable({
  providedIn: 'root'
})

export class PlayerStateService {
  private http = inject(HttpClient);

  topDivDto=signal<TopDivDto | null>(null);

  loadTopDiv(){
    this.http.get<TopDivDto>('/api/topDiv').subscribe(data =>{
          this.topDivDto.set(data);
      }
    )
  }
}
