/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

public class SpaceCity extends SpaceStation {
    SpaceStation base;
    ArrayList<SpaceStation> collaborators;
    
    SpaceCity(SpaceStation b, ArrayList<SpaceStation> rest) {
        super(b.getName(), new SuppliesPackage(b.getAmmoPower(), b.getFuelUnits(), b.getShieldPower()),
            b.getNMedals(), b.getPendingDamage(), b.getWeapons(), b.getShieldBoosters(), b.getHangar());
        base = b;
        collaborators = rest;
    }
    
    public ArrayList<SpaceStation> getCollaborators() {
        return collaborators;
    }
    
    public SpaceCityToUI getUIversion() {
        return new SpaceCityToUI(this);
    }
    
    @Override
    public String toString() {
        return getUIversion().toString();
    }
    
    @Override
    public float fire() {
        int suma = 0;
        for (SpaceStation c: collaborators) {
            suma += c.fire();
        }
        return suma + super.fire();
    }
    
    @Override
    public float protection() {
        int suma = 0;
        for (SpaceStation c: collaborators) {
            suma += c.protection();
        }
        return suma + super.protection();
    }
    
    @Override
    public Transformation setLoot(Loot loot) {
        super.setLoot(loot);
        return Transformation.NOTRANSFORM;
    }
}
