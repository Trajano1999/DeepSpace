/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

public abstract class Damage {
    protected int nShields;
    
    Damage(int s) {
        nShields = s;
    }
    
    abstract Damage newCopy();
    
    public int getNShields() {
        return nShields;
    }
    
    abstract DamageToUI getUIversion();
    
    protected int adjustNShields(ArrayList<ShieldBooster> s) {
        return nShields < s.size() ? nShields : s.size();
    }
    
    abstract Damage adjust(ArrayList<Weapon> w, ArrayList<ShieldBooster> s);
    
    public void discardShieldBooster() {
        if (nShields > 0)
            nShields -= 1;
    }
        
    abstract void discardWeapon(Weapon w);
    
    public boolean hasNoEffect() {
        return nShields == 0;
    }
}
