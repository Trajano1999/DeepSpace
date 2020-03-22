/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

/**
 *
 * @author juanma
 */
public class NumericDamage extends Damage {
    private int nWeapons;
    
    NumericDamage(int w, int s) {
        super(s);
        nWeapons = w;
    }
    
    /*
    NumericDamage(NumericDamage d) {
        nWeapons = d.nWeapons;
        nShields = d.nShields;
    }*/
    
    @Override
    public NumericDamage newCopy(){
        //return new NumericDamage(this);
        return new NumericDamage(nWeapons, nShields);
    }
   
    @Override
    NumericDamageToUI getUIversion() {
        return new NumericDamageToUI(this);
    }
    
    public String toString(){
        return getUIversion().toString();
    }
    
    public int getNWeapons() {
        return nWeapons;
    }
    
    public NumericDamage adjust(ArrayList<Weapon> w, ArrayList<ShieldBooster> s){
        int min_w = nWeapons < w.size() ? nWeapons : w.size();
        return new NumericDamage(min_w, adjustNShields(s));
    }
    
    public void discardWeapon(Weapon w){
        nWeapons = nWeapons > 0 ? nWeapons-- : 0;
    }
    
    public boolean hasNoEffect(){
        return super.hasNoEffect() && nWeapons == 0;
    }
}
