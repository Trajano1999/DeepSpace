/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

public enum WeaponType {
    LASER (2.0f), MISSILE (3.0f), PLASMA (4.0f);
    private float power;
    
    WeaponType (float p) {
        power = p;
    }
    
    public String toString(){
        if(power == 2.0)
            return "LASER";
        else if(power == 3.0)
            return  "MISSILE";
        else if (power == 4.0)
            return "PLASMA";
        else 
            return "";
    }
    
    float getPower() {
        return power;
    }
}
