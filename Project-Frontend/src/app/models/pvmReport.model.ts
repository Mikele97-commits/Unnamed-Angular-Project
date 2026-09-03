import {FightLogEntry} from './fightLogEntry.model';

export interface pvmReport {
 id: number;
 username: string;
 monsterName: string;
 playerWon:boolean;
 expGained: number;
 goldGained: number;
 localDateTime: string;

 log:Array<FightLogEntry>;
}
