/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

class Damage {
    private int nShields;
    private int nWeapons;
    private ArrayList<WeaponType> weapons;
    
    Damage(int w, int s){
        nShields = s;
        nWeapons = w;
        
        weapons = new ArrayList<WeaponType>();
    }
    
    Damage(ArrayList<WeaponType> wl, int s){
        nShields = s;
        weapons = wl;
        
        nWeapons = -1;
    }
    
    Damage(Damage d){
        nShields = d.nShields;
        if(nWeapons == -1)
            weapons = new ArrayList<WeaponType>(d.weapons);
        else
            nWeapons = d.nWeapons;
    }
    
    DamageToUI getUIversion(){
        return new DamageToUI(this);
    }
    
    public String toString(){   //NO SE SI ESTA BIEN
        if(nWeapons == -1)
            return ("nShield: " + nShields + ", weapons: {" + weapons.toString() + "}");
        else
            return ("nShield: " + nShields + ", nWeapons: " + nWeapons);
    }
    
    private int arrayContainsType(ArrayList<Weapon> w, WeaponType t){
        for(int i=0; i<w.size(); ++i)
            if(w.get(i).getType() == t)
                return i;
     
        return -1;
    }
    
    public Damage adjust(ArrayList<Weapon> w, ArrayList<ShieldBooster> s){
        int nS = nShields < s.size() ? nShields : s.size();
        if(nWeapons != -1){
            int nW = nWeapons < w.size() ? nWeapons : w.size();
            return (new Damage(nW, nS));
        }else{
            ArrayList<WeaponType> p = new ArrayList<WeaponType>();
            for(int i=0; i<weapons.size(); ++i)
                for(int j=0; j<w.size(); ++j)
                    if(w.get(j).getType() == weapons.get(i))
                        p.add(weapons.get(i));
            return (new Damage(p, nS));
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
        return (nShields == 0 && (weapons.size() == 0 || nWeapons == 0));
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
