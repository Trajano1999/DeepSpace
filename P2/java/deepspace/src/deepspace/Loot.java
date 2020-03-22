/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

class Loot {
    private int nSupplies, nWeapons, nShields, nHangars, nMedals;
    
    Loot (int nSu, int nWe, int nSh, int nHa, int nMe) {
        nSupplies = nSu;
        nWeapons = nWe;
        nShields = nSh;
        nHangars = nHa;
        nMedals = nMe;
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
    
    public String toString(){   //NO SE SI ESTA BIEN
        return ("nSupplies: " + nSupplies + ", nWeapons: " + nWeapons + ", nShields: " + nShields + ", nHangars: " + nHangars + ", nMedals: " + nMedals);
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
}
