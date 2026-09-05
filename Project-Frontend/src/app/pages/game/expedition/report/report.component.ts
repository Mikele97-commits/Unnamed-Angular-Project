import {Component, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {pvmReport} from '../../../../models/pvmReport.model';
import {DatePipe, DecimalPipe} from '@angular/common';
import {PlayerStateService} from '../../../../core/services/player-state.service';

@Component({
  selector: 'report',
  templateUrl: 'report.component.html',
  imports: [
    DatePipe,
    DecimalPipe
  ],
  styleUrl: 'report.component.css'
})

export class ReportComponent implements OnInit {

  playerState = inject(PlayerStateService);

  report:pvmReport | null = null;
  constructor(private router: Router) {
    // Best place to read navigation state (works reliably)
    const navigation = this.router.currentNavigation();
    this.report = navigation?.extras?.state?.['report'] as pvmReport;
  }

  ngOnInit() {
    if (!this.report) {
      this.report = history.state?.['report'] as pvmReport ?? null;
    }


    if (!this.report) {
      console.error('Dto not found', history.state?.['report'] as pvmReport);
    }


  }
}
