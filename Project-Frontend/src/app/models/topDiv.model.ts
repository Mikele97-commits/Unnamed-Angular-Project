export interface TopDivDto{
  name: string;
  level: number;

  minDmg: number;
  maxDmg: number;

  armor: number;

  currEnergy: number;
  maxEnergy: number;

  currentHp: number;
  maxHp: number;

  currentExp: number;
  nxtLvlExp: number;

  questPoints: number;
  gold: number;

  regenTime: Array<number>;
}
