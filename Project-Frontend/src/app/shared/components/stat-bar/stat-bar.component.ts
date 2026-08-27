import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-stat-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './stat-bar.component.html',
  styleUrls: ['./stat-bar.component.css']
})
export class StatBarComponent {
  @Input({ required: true }) current: number = 0;
  @Input({ required: true }) max: number = 100;
  @Input() type: 'hp' | 'energy' | 'exp' = 'hp';

  get percentage(): number {
    if (this.max <= 0) return 0;
    const calc = (this.current / this.max) * 100;
    return Math.min(100, Math.max(0, calc)); // Clamps value between 0% and 100%
  }
}
