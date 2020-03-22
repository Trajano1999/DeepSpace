/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author juanma
 */
public class SpecificDamage extends Damage{
    private ArrayList<WeaponType> weapons;
    
    SpecificDamage(ArrayList<WeaponType> wl, int s){
        super(s);
        weapons = wl;
    }
    
    /*
    SpecificDamage(SpecificDamage sp){
        nShields = sp.nShields;
        weapons = new ArrayList<>(sp.weapons);
    }*/
    
    @Override
    public SpecificDamage newCopy(){
        //return new SpecificDamage(this);
        return new SpecificDamage(weapons, nShields);
    }
    
    @Override
    SpecificDamageToUI getUIversion(){
        return new SpecificDamageToUI(this);
    }
    
    public String toString(){
        return getUIversion().toString();
    }
    
    public ArrayList<WeaponType> getWeapons(){
        return weapons;
    }
    
    private int arrayContainsType(ArrayList<Weapon> w, WeaponType t){
        for(int i=0; i<w.size(); ++i)
            if(w.get(i).getType() == t)
                return i;
        return -1;
    }
    
    public SpecificDamage adjust(ArrayList<Weapon> weap, ArrayList<ShieldBooster> s){
        ArrayList<WeaponType> wCopy;
        wCopy = weap.stream().map(Weapon::getType).collect(Collectors.toCollection(ArrayList::new));
        wCopy.retainAll(weapons);
        return new SpecificDamage(wCopy, adjustNShields(s));
    }
    
    public void discardWeapon(Weapon w){
        weapons.remove(w);
    }
    
    public boolean hasNoEffect(){
        return super.hasNoEffect() && (weapons == null || weapons.size() == 0);
    }
}
