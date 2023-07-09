/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

class SuppliesPackage{
    private float ammoPower, fuelUnits, shieldPower;
    
    SuppliesPackage(float am, float fu, float sh) {
        ammoPower = am;
        fuelUnits = fu;
        shieldPower = sh;
    }
    
    SuppliesPackage(final SuppliesPackage other) {
        ammoPower = other.ammoPower;
        fuelUnits = other.fuelUnits;
        shieldPower = other.shieldPower;
    }
    
    @Override
    public String toString(){
        return ("ammoPower: " + ammoPower + ", fuelUnits: " + fuelUnits + ", shieldPower: " + shieldPower);
    }
    
    float getAmmoPower() {
        return ammoPower;
    }
    
    float getFuelUnits() {
        return fuelUnits;
    }
    
    float getShieldPower() {
        return shieldPower;
    }
}
