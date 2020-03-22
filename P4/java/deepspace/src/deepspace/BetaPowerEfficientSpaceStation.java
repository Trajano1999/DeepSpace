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
public class BetaPowerEfficientSpaceStation extends PowerEfficientSpaceStation {
    private static final float EXTRAEFFICIENCY = (float)1.2;
    
    Dice dice;
    
    BetaPowerEfficientSpaceStation(SpaceStation station) {
        super(station);
        dice = new Dice();
    }
    
    public BetaPowerEfficientSpaceStationToUI getUIversion() {
        return new BetaPowerEfficientSpaceStationToUI(this);
    }
    
    public String toString() {
        return getUIversion().toString();
    }
    
    @Override
    public float fire() {
        return dice.extraEfficiency() ? super.fire() * EXTRAEFFICIENCY: super.fire();
    }
}
