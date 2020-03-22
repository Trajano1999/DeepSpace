/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

class Hangar {
    private int maxElements;
    private ArrayList<Weapon> weapons;
    private ArrayList<ShieldBooster> shieldBoosters;
    
    Hangar(int capacity){
        maxElements = capacity;
        
        weapons = new ArrayList<Weapon>();
        shieldBoosters = new ArrayList<ShieldBooster>();
    }
    
    Hangar(Hangar h){   // HAY QUE TENER EN CUENTA QUE weapons NO APUNTA AL MISMO SITIO QUE h.weapons
        maxElements = h.maxElements;
        weapons = new ArrayList<Weapon>(h.weapons);
        shieldBoosters = new ArrayList<ShieldBooster>(h.shieldBoosters);
    }
    
    HangarToUI getUIversion(){
        return new HangarToUI(this);
    }
    
    public String toString(){   //NO SE SI ESTA BIEN
        return ("maxElements: " + maxElements + ", weapons: {" + weapons.toString() + "}, shieldBoosters: {" + shieldBoosters.toString() + "}");
    }
    
    private boolean spaceAvailable(){
        return (weapons.size() + shieldBoosters.size() < maxElements);
    }
    
    public boolean addWeapon(Weapon w){
        if(spaceAvailable()){
            weapons.add(w);
            return true;
        }else
            return false;
    }
    
    public boolean addShielBooster(ShieldBooster s){
        if(spaceAvailable()){
            shieldBoosters.add(s);
            return true;
        }else
            return false;
    }
    
    public int getMaxElements(){
        return maxElements;
    }
    
    public ArrayList<ShieldBooster> getShieldBoosters(){
        return shieldBoosters;
    }
    
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }
    
    public ShieldBooster removeShieldBooster(int s){
        return shieldBoosters.remove(s);
    }
    
    public Weapon removeWeapon(int w){
        return weapons.remove(w);
    }
}
