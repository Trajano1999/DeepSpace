/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;
import java.util.stream.Collectors;

class Damage {
    private int nShields;
    private int nWeapons;
    private ArrayList<WeaponType> weapons;
    
    Damage(int w, int s){
        nShields = s;
        nWeapons = w;
        
        weapons = null;
    }
    
    Damage(ArrayList<WeaponType> wl, int s){
        nShields = s;
        weapons = wl;
        
        nWeapons = -1;
    }
    
    Damage(Damage d){
        nShields = d.nShields;
        if(d.nWeapons == -1 && d.weapons != null){
            weapons = new ArrayList<WeaponType>(d.weapons);
            nWeapons = d.nWeapons;
        }else
            if(d.nWeapons != -1 && d.weapons == null){
                nWeapons = d.nWeapons;
                weapons = null;
            }
    }
    
    DamageToUI getUIversion(){
        return new DamageToUI(this);
    }
    
    public String toString(){
        return getUIversion().toString();
    }
    
    private int arrayContainsType(ArrayList<Weapon> w, WeaponType t){
        for(int i=0; i<w.size(); ++i)
            if(w.get(i).getType() == t)
                return i;
     
        return -1;
    }
    
    public Damage adjust(ArrayList<Weapon> weap, ArrayList<ShieldBooster> s){
        int nSh = nShields < s.size() ? nShields : s.size();      
        if (nWeapons == -1 && weapons != null) {  // SpecificWeapons   
            ArrayList<WeaponType> wCopy;
            wCopy = weap.stream().map(Weapon::getType).collect(Collectors.toCollection(ArrayList::new));
            wCopy.retainAll(weapons);
            return new Damage(wCopy, nSh);    
        } else {    // NumericWeapons
            int nWp = (nWeapons < weap.size()) ? nWeapons : weap.size();
            return new Damage(nWp, nSh);
        }
    }
    
    public void discardWeapon(Weapon w){
        if(nWeapons == -1){
            for(int i=0; i<weapons.size(); ++i)
                if(weapons.get(i) == w.getType())
                    weapons.remove(i);
        }else{
            if(nWeapons > 0)
                nWeapons--;
        }
    }
    
    public void discardShieldBooster(){
        if(nShields > 0)
            nShields--;
    }
    
    public boolean hasNoEffect(){
        boolean numericNoEffect = nWeapons == 0 && weapons == null;
        boolean specificNoEffect = nWeapons == -1 && weapons.size() == 0;
        return nShields == 0 && (specificNoEffect || numericNoEffect);
    }
    
    public int getNShields(){
        return nShields;
    }
    
    public int getNWeapons(){
        return nWeapons;
    }
    
    public ArrayList<WeaponType> getWeapons(){
        return weapons;
    }
}
