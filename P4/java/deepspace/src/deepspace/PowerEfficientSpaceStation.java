/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

/**
 *
 * @author jruib
 */
public class PowerEfficientSpaceStation extends SpaceStation {
    private static final float EFFICIENCYFACTOR = (float)1.1;
    
    PowerEfficientSpaceStation(SpaceStation station) {
        super(station.getName(),
            new SuppliesPackage(station.getAmmoPower(), station.getFuelUnits(), station.getShieldPower()),
            station.getNMedals(),
            station.getPendingDamage(),
            station.getWeapons(),
            station.getShieldBoosters(),
            station.getHangar()
        );
    }
    
    public PowerEfficientSpaceStationToUI getUIversion() {
        return new PowerEfficientSpaceStationToUI(this);
    }
    
    public String toString() {
        return getUIversion().toString();
    }
    
    @Override
    public float fire() {
        return super.fire() * EFFICIENCYFACTOR;
    }
    
    @Override
    public float protection() {
        return super.protection() * EFFICIENCYFACTOR;
    }
    
    @Override
    public Transformation setLoot(Loot loot) {
        super.setLoot(loot);
        return Transformation.NOTRANSFORM;
    }
}
