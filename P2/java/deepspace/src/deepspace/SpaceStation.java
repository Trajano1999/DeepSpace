/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

public class SpaceStation {
    private static int MAXFUEL = 100;
    private static float SHIELDLOSSPERUNITSHOT = 0.1f;
    private float ammoPower;
    private float fuelUnits;
    private String name;
    private int nMedals;
    private float shieldPower;
    
    private Damage pendingDamage;
    private ArrayList<Weapon> weapons;
    private ArrayList<ShieldBooster> shieldBoosters;
    private Hangar hangar;
    
    SpaceStation(String n, SuppliesPackage supplies){
        name = n;
        ammoPower = supplies.getAmmoPower();
        fuelUnits = supplies.getFuelUnits();
        shieldPower = supplies.getShieldPower();
        
        weapons = new ArrayList<Weapon>();
        shieldBoosters = new ArrayList<ShieldBooster>();
    }
    
    public SpaceStationToUI getUIversion(){
        return new SpaceStationToUI(this);
    }
    
    public String toString(){   //NO SE SI ESTA BIEN
        return ("MAXFUEL: " + MAXFUEL + ", SHIELDLOSSPERUNITSHOT: " + SHIELDLOSSPERUNITSHOT + ", ammoPower: " + ammoPower + ", fuelUnits: " + fuelUnits + ", name: " + name.toString() + ", nMedals: " + nMedals + ", shieldPower: " + shieldPower + ", pendingDamage: [" + pendingDamage.toString() + "], weapons: {" + weapons.toString() + "}, shieldBooster: {" + shieldBoosters.toString() + "}, hangar: [" + hangar.toString() + "]");
    }
    
    private void assignFuelValue(float f){  // ES RARO QUE NO HAYAMOS USADO NI assignFuelValue NI cleanPendingDamage en toda la clase, deberiamos usarlas
        fuelUnits = f <= MAXFUEL ? f : MAXFUEL;
    }
    
    private void cleanPendingDamage(){
        if(pendingDamage.hasNoEffect())
            pendingDamage = null;
    }
    
    public float getAmmoPower(){
        return ammoPower;
    }
    
    public float getFuelUnits(){
        return fuelUnits;
    }
    
    public Hangar getHangar(){
        return hangar;
    }
    
    public String getName(){
        return name;
    }
    
    public int getNMedals(){
        return nMedals;
    }
    
    public Damage getPendingDamage(){
        return pendingDamage;
    }
    
    public ArrayList<ShieldBooster> getShieldBoosters(){
        return shieldBoosters;
    }
    
    public float getShieldPower(){
        return shieldPower;
    }
    
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }
    
    public boolean receiveWeapon(Weapon w){
        if(hangar != null){
            return hangar.addWeapon(w);
        }else
            return false;
    }
    
    public boolean receiveShieldBooster(ShieldBooster s){
        if(hangar != null){
            return hangar.addShielBooster(s);
        }else
            return false;
    }
    
    public void receiveHangar(Hangar h){
        if(hangar == null){
            hangar = h;
        }
    }
    
    public void discardHangar(){
        hangar = null;
    }
    
    public void receiveSupplies(SuppliesPackage s){
        ammoPower += s.getAmmoPower();
        shieldPower += s.getShieldPower();
        fuelUnits += s.getFuelUnits();
    }
    
    public void setPendingDamage(Damage d){
        pendingDamage = d.adjust(weapons, shieldBoosters);
    }
    
    public void mountWeapon(int i){
        if(hangar != null){
            Weapon newWeapon = hangar.getWeapons().remove(i);   //No estoy seguro si remove devuelve el elemento eliminado
            if(newWeapon != null)
                weapons.add(newWeapon); 
        }
    }
    
    public void mountShieldBooster(int i){
        if(hangar != null){
            ShieldBooster newShield = hangar.getShieldBoosters().remove(i);   //No estoy seguro si remove devuelve el elemento eliminado
            if(newShield != null)
                shieldBoosters.add(newShield);
        }
    }
    
    public void discardWeaponInHangar(int i){
        if(hangar != null)
            hangar.removeWeapon(i);
    }
    
    public void discardShieldBoosterInHangar(int i){
        if(hangar != null)
            hangar.removeShieldBooster(i);
    }
    
    public float getSpeed(){
        return (float)(fuelUnits / MAXFUEL);
    }
    
    public void move(){
        fuelUnits = fuelUnits >= getSpeed() ? fuelUnits - getSpeed() : 0;
    }
    
    public boolean validState(){
        return (pendingDamage == null || pendingDamage.hasNoEffect());
    }
    
    public void cleanUpMountedItems(){
        if(weapons != null)
            for(int i=0; i<weapons.size(); ++i)
                if(weapons.get(i).getUses() == 0)
                    weapons.remove(i);
    
        if(shieldBoosters != null)
            for(int i=0; i<shieldBoosters.size(); ++i)
                if(shieldBoosters.get(i).getUses() == 0)
                    shieldBoosters.remove(i);
    }         
    
    // +discardWeapon(i : int) : void
    public void discardWeapon(int i){
        
    }
    
    // +discardShieldBooster(i : int) : void
    public void discardShieldBooster(int i){
        
    }
    
    /*
    +fire() : float
    +protection() : float
    +receiveShot(shot : float) : ShotResult
    +setLoot(loot : Loot) : void
    */
}
