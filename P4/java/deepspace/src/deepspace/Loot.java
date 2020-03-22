/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

class Loot {
    private int nSupplies, nWeapons, nShields, nHangars, nMedals;
    private boolean efficient, spaceCity;
    
    Loot (int nSu, int nWe, int nSh, int nHa, int nMe) {
        nSupplies = nSu;
        nWeapons = nWe;
        nShields = nSh;
        nHangars = nHa;
        nMedals = nMe;
        
        efficient = false;
        spaceCity = false;
    }
    
    Loot (int nSu, int nWe, int nSh, int nHa, int nMe, boolean ef, boolean city) {
        nSupplies = nSu;
        nWeapons = nWe;
        nShields = nSh;
        nHangars = nHa;
        nMedals = nMe;
        
        efficient = ef;
        spaceCity = city;
    }
    
    Loot(Loot l){ //Hago este constructor de copia para que en la clase EnemyStarShip no se creen dos objetos apuntando a lo mismo en el constructor por copia
        nSupplies = l.nSupplies;
        nWeapons = l.nWeapons;
        nShields = l.nShields;
        nHangars = l.nHangars;
        nMedals = l.nMedals;
    }
    
    LootToUI getUIversion(){
        return (new LootToUI(this));
    }
    
    public String toString(){
        return getUIversion().toString();    
    }
    
    int getNSupplies() {
        return nSupplies;
    }
    
    int getNWeapons() {
        return nWeapons;
    }
    
    int getNShields() {
        return nShields;
    }
    
    int getNHangars() {
        return nHangars;
    }
    
    int getNMedals() {
        return nMedals;
    }
    
    boolean getEfficient() {
        return efficient;
    }
    
    boolean spaceCity() {
        return spaceCity;
    }
}
