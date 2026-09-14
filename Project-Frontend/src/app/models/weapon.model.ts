import {ItemModel} from './item.model';

export interface WeaponModel extends ItemModel{
  minDmg:number;
  maxDmg:number;
  plus:number;
}
