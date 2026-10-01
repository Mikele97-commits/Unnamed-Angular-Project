import {FightLogEntry} from './fightLogEntry.model';

export interface pvmReport {
 id: number;
 attacker: string;
 defender: string;
 playerWon:boolean;
 expGained: number;
 goldGained: number;
 foughtAt: string;
 loot:string;


  log:Array<FightLogEntry>;
}
