import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpClient} from '@angular/common/http';
import {AdminDto} from '../../../models/admin.model';
import {PlayerStateService} from '../../../core/services/player-state.service';

@Component({
  selector: 'admin',
  imports: [FormsModule,CommonModule],
  templateUrl: 'admin.component.html',
  styleUrl: 'admin.component.css'
})

export class AdminComponent{
  playerStateService=inject(PlayerStateService);
  private http = inject(HttpClient);
  energyUsername ='';
  energyAmount: number | null = null;
  hpUsername ='';
  hpAmount: number | null = null;

  setEnergy(form:any): void {
    const adminDto : AdminDto =
    {
       username:form.value.energyUsername,
       amount:form.value.energyAmount
    }
    this.http.post('/api/admin/set-energy', adminDto).subscribe({
      next: ()=>{
        console.log("Energy set successfully");
        this.playerStateService.loadTopDiv()

      }
    }
    )
  }

  setHp(form:any):void {
    const adminDto : AdminDto =
      {
        username:form.value.hpUsername,
        amount:form.value.hpAmount
      }
      this.http.post('/api/admin/set-hp', adminDto).subscribe({
        next: ()=>{
          console.log("Hp set successfully");
          this.playerStateService.loadTopDiv()
        }
      })
  }
}
