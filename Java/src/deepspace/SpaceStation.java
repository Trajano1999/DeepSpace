/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;
import java.lang.Math;

public class SpaceStation implements SpaceFighter {
    private static int MAXFUEL = 100;
    private static float SHIELDLOSSPERUNITSHOT = 0.1f;
    
    private String name;
    
    private float ammoPower;
    private float fuelUnits;
    private float shieldPower;
    
    private int nMedals;
   
    private Damage pendingDamage;
    private ArrayList<Weapon> weapons;
    private ArrayList<ShieldBooster> shieldBoosters;
    private Hangar hangar;
    
    SpaceStation(String n, SuppliesPackage supplies){
        name = n;
        
        ammoPower = supplies.getAmmoPower();
        fuelUnits = supplies.getFuelUnits();
        shieldPower = supplies.getShieldPower();
        
        nMedals = 0;
        
        pendingDamage = null;
        weapons = new ArrayList<>();
        shieldBoosters = new ArrayList<>();
        hangar = null;
    }
    
    SpaceStation(String n, SuppliesPackage supplies, int nM, Damage pD, ArrayList<Weapon> w, ArrayList<ShieldBooster> sh, Hangar h) {
        name = n;
        
        ammoPower = supplies.getAmmoPower();
        fuelUnits = supplies.getFuelUnits();
        shieldPower = supplies.getShieldPower();
        
        nMedals = nM;
        
        pendingDamage = pD;
        weapons = w;
        shieldBoosters = sh;
        hangar = h;
    }
    
    public SpaceStationToUI getUIversion(){
        return new SpaceStationToUI(this);
    }
    
    @Override
    public String toString(){
        return getUIversion().toString();
    }
    
    private void assignFuelValue(float f){
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
        assignFuelValue(fuelUnits + s.getFuelUnits());
    }
    
    public void setPendingDamage(Damage d){
        pendingDamage = d.adjust(weapons, shieldBoosters);
    }
    
    public void mountWeapon(int i){
        if (0 <= i && i < hangar.getWeapons().size()) {
            if(hangar != null){
                Weapon newWeapon = hangar.getWeapons().remove(i);
                if(newWeapon != null)
                    weapons.add(newWeapon); 
            }
        }
    }
    
    public void mountShieldBooster(int i){
        if (0 <= i && i < hangar.getShieldBoosters().size()) {
            if(hangar != null){
                ShieldBooster newShield = hangar.getShieldBoosters().remove(i);
                if(newShield != null)
                    shieldBoosters.add(newShield);
            }
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
    
    public void discardWeapon(int i){
        int size = weapons.size();
        if(i >= 0 && i < size){
            Weapon w = weapons.remove(i);
            if(pendingDamage != null){
                pendingDamage.discardWeapon(w);
                cleanPendingDamage();
            }
        }
    }
    
    public void discardShieldBooster(int i){
        int size = shieldBoosters.size();
        if(i >= 0 && i < size){
            ShieldBooster s = shieldBoosters.remove(i);
            if(pendingDamage != null){
                pendingDamage.discardShieldBooster();
                cleanPendingDamage();
            }
        }
    }
    
    @Override
    public float fire() {
        float factor = 1;
        if (weapons != null) {
            for (Weapon w: weapons) {
                factor *= w.useIt();
            }
        }
        return ammoPower*factor;
    }
    
    @Override
    public float protection() {
        float factor = 1;
        if (shieldBoosters != null) {
            for (ShieldBooster s: shieldBoosters) {
                factor *= s.useIt();
            }
        }
        return shieldPower*factor;
    }
    
    @Override
    public ShotResult receiveShot(float shot) {
        float myProtection = protection();
        
        if (myProtection >= shot) {
            shieldPower = Math.max(0, shieldPower - SHIELDLOSSPERUNITSHOT*shot);
            return ShotResult.RESIST;
        }
        else {
            shieldPower = 0;
            return ShotResult.DONOTRESIST;
        }
    }
    
    public Transformation setLoot(Loot loot) {
        CardDealer dealer = CardDealer.getInstance();
        int h = loot.getNHangars();
        
        if (h > 0) {
            Hangar hangar = dealer.nextHangar();
            receiveHangar(hangar);
        }
        
        int elements = loot.getNSupplies();
        SuppliesPackage sup;
        for (int i=0; i<elements; i++) {
            sup = dealer.nextSuppliesPackage();
            receiveSupplies(sup);
        }
        
        elements = loot.getNWeapons();
        Weapon weap;
        for (int i=0; i<elements; i++) {
            weap = dealer.nextWeapon();
            receiveWeapon(weap);
        }
        
        elements = loot.getNShields();
        ShieldBooster sh;
        for (int i=0; i<elements; i++) {
            sh = dealer.nextShieldBooster();
            receiveShieldBooster(sh);
        }
        
        int medals = loot.getNMedals();
        nMedals += medals;
        
        Transformation trans;
        if (loot.spaceCity())
            trans = Transformation.SPACECITY;
        else if (loot.getEfficient())
            trans = Transformation.GETEFFICIENT;
        else
            trans = Transformation.NOTRANSFORM;
        
        return trans;
    }
}
