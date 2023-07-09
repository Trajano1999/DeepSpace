/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.ArrayList;

public class GameUniverse {
    private static int WIN = 10;
    
    private int currentStationIndex;
    private int turns;
    
    private GameStateController gameState;
    private Dice dice;
    private SpaceStation currentStation;
    private ArrayList<SpaceStation> spaceStations;
    private EnemyStarShip currentEnemy;
    
    private boolean haveSpaceCity;
    
    public GameUniverse(){
        currentStationIndex = 0;
        turns = 0;
        
        gameState = new GameStateController();
        dice = new Dice();
        currentStation = null;
        spaceStations = new ArrayList<>();
        currentEnemy = null;
        
        haveSpaceCity = false;
    }
    
    public GameUniverseToUI getUIversion(){
        return new GameUniverseToUI(currentStation, currentEnemy);
    }
    
    @Override
    public String toString(){
        return getUIversion().toString();
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
    
    public void init(ArrayList<String> names) {
        if (getState() == GameState.CANNOTPLAY) {
            CardDealer dealer = CardDealer.getInstance();
            for (String name : names) {
                SuppliesPackage supplies = dealer.nextSuppliesPackage();
                SpaceStation station = new SpaceStation(name, supplies);
                spaceStations.add(station);
                int nh = dice.initWithNHangars();
                int nw = dice.initWithNWeapons();
                int ns = dice.initWithNShields();
                Loot lo = new Loot(0, nw, ns, nh, 0);
                station.setLoot(lo);
            }
            currentStationIndex = dice.whoStarts(names.size());
            currentStation = spaceStations.get(currentStationIndex);
            currentEnemy = dealer.nextEnemy();
            gameState.next(turns, spaceStations.size());
        }
    }
    
    public boolean nextTurn() {
        if (getState() == GameState.AFTERCOMBAT) {
            if (currentStation.validState()) {
                currentStationIndex = (currentStationIndex+1) % spaceStations.size();
                turns += 1;

                currentStation = spaceStations.get(currentStationIndex);
                currentStation.cleanUpMountedItems();

                CardDealer dealer = CardDealer.getInstance();
                currentEnemy = dealer.nextEnemy();
                gameState.next(turns, spaceStations.size());
                return true;
            }
        }
        return false;
    }

    public CombatResult combat() {
        GameState state = getState();
        if (state == GameState.BEFORECOMBAT || state == GameState.INIT)
            return combat(currentStation, currentEnemy);
        else
            return CombatResult.NOCOMBAT;
    }
    
    CombatResult combat(SpaceStation station, EnemyStarShip enemy) {
        CombatResult combatResult;
        float fire;
        ShotResult result;
        boolean enemyWins;
        GameCharacter ch = dice.firstShot();
        
        if (ch == GameCharacter.ENEMYSTARSHIP) {
            fire = enemy.fire();
            result = station.receiveShot(fire);
            
            if (result == ShotResult.RESIST) {
                fire = station.fire();
                result = enemy.receiveShot(fire);
                enemyWins = result == ShotResult.RESIST;
            }
            else
                enemyWins = true;
        }
        else {
            fire = station.fire();
            result = enemy.receiveShot(fire);
            enemyWins = result == ShotResult.RESIST;
        }
        
        if (enemyWins) {
            float s = station.getSpeed();
            boolean moves = dice.spaceStationMoves(s);
            
            if (!moves) {
                Damage damage = enemy.getDamage();
                station.setPendingDamage(damage);
                combatResult = CombatResult.ENEMYWINS;
            }
            else {
                station.move();
                combatResult = CombatResult.STATIONESCAPES;
            }
        }
        else {
            Loot aloot = enemy.getLoot();
            Transformation trans = station.setLoot(aloot);
            
            if (trans == Transformation.GETEFFICIENT) {
                makeStationEfficient();
                combatResult = CombatResult.STATIONWINSANDCONVERTS;
            }
            else if (trans == Transformation.SPACECITY) {
                createSpaceCity();
                combatResult = CombatResult.STATIONWINSANDCONVERTS;
            }
            else // if (trans == Transformation.NOTRANSFORM)
                combatResult = CombatResult.STATIONWINS;
        }
        gameState.next(turns, spaceStations.size());
        return combatResult;
    }
    
    private void makeStationEfficient() {
        if (dice.extraEfficiency())
            currentStation = new BetaPowerEfficientSpaceStation(currentStation);
        else
            currentStation = new PowerEfficientSpaceStation(currentStation);
        spaceStations.set(currentStationIndex, currentStation);
    }
    
    private void createSpaceCity() {
        if (haveSpaceCity == false) {
            ArrayList<SpaceStation> collaborators = new ArrayList<>(spaceStations);
            collaborators.remove(currentStation);
            currentStation = new SpaceCity(currentStation, collaborators);
            haveSpaceCity = true;
            spaceStations.set(currentStationIndex, currentStation);
        }
    }
}
