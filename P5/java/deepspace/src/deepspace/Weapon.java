/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

public class Weapon implements CombatElement {
    private String name;
    private WeaponType type;
    private int uses;
    
    Weapon(String n, WeaponType w, int u) {
        name = n;
        type = w;
        uses = u;
    }
    
    Weapon(final Weapon other) {
        name = other.name;
        type = other.type;
        uses = other.uses;
    }
    
    public WeaponToUI getUIversion(){
        return new WeaponToUI(this);
    }
    
    @Override
    public String toString(){
        return getUIversion().toString();
    }
    
    WeaponType getType() {
        return type;
    }
    
    @Override
    public int getUses() {
        return uses;
    }
    
    public float power() {
        return type.getPower();
    }
    
    @Override
    public float useIt() {
        if(uses > 0){
            uses -= 1;
            return power();
        }else
            return 1;
    }
}