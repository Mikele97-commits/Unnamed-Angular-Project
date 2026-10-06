import {ItemModel} from './item.model';

export interface WeaponModel extends ItemModel{
  finalMinDmg:number;
  finalMaxDmg:number;
  plus:number;
}
