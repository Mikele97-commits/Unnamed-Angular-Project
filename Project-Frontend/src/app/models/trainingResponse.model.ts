import {TrainingStatsDto} from './TrainingStats.model';

export interface TrainingResponseDto {
  trainingStatsDto: TrainingStatsDto;
  success: boolean;
  message: string;
}
