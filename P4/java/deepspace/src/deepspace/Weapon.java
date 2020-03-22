/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

class Weapon implements CombatElement {
    private String name;
    private WeaponType type;
    private int uses;
    
    Weapon(String n, final WeaponType w, int u) { //NO SE SI SE PONE EL FINAL
        name = n;
        type = w;
        uses = u;
    }
    
    Weapon(final Weapon other) {
        name = other.name;
        type = other.type;
        uses = other.uses;
    }
    
    WeaponToUI getUIversion(){
        return new WeaponToUI(this);
    }
    
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