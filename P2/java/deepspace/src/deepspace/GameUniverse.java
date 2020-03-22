/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

class GameUniverse {
    private static int WIN = 10;
    private int currentStationIndex;
    private int turns;
    
    private GameStateController gameState;
    private Dice dice;
    private SpaceStation currentStation;
    private ArrayList<SpaceStation> spaceStations;
    private EnemyStarShip currentEnemy;
    
    public GameUniverse(){
        currentStationIndex = 0;
        turns = 0;
        dice = new Dice();
    }
    
    public GameUniverseToUI getUIversion(){
        return new GameUniverseToUI(currentStation, currentEnemy);
    }
    
    public String toString(){   //NO SE SI ESTA BIEN
        return ("WIN: " + WIN + ", currentStationIndex: " + currentStationIndex + ", turns: " + turns + ", gameState: [" + gameState.getState() + "], dice: [" + dice.toString() + "], currentStation: [" + currentStation.toString() + "], spaceStations: {" + spaceStations.toString() + "}, currentEnemy: [" + currentEnemy.toString() + "]");
    }
    
    public GameState getState(){
        return gameState.getState();
    }
    
    public boolean haveAWinner(){
        return (currentStation != null && (currentStation.getNMedals() >= WIN));
    }
    
    public void mountWeapon(int i){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.mountWeapon(i);
    }
    
    public void mountShieldBooster(int i){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.mountShieldBooster(i);
    }
    
    public void discardHangar(){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.discardHangar();
    }
    
    public void discardShieldBooster(int i){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.discardShieldBooster(i);
    }
    
    public void discardShieldBoosterInHangar(int i){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.discardShieldBoosterInHangar(i);
    }
    
    public void discardWeapon(int i){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.discardWeapon(i);
    }
    
    public void discardWeaponInHangar(int i){
        if (currentStation != null && (gameState.getState() == GameState.INIT || gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.discardWeaponInHangar(i);
    }
    
    /*
    +void init(ArrayList<String> names)
    +boolean nextTurn()
    +CombatResult combat()
    ~CombatResult combat(SpaceStation station,EnemyStarShip enemy)
    */
}
