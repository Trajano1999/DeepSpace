package deepspace;

import java.util.function.Supplier;

enum CombatResult {ENEMYWINS, NOCOMBAT, STATIONESCAPES, STATIONWINS}
enum GameCharacter {ENEMYSTARSHIP, SPACESTATION}
enum ShotResult {DONOTRESIST, RESIST}

enum WeaponType {
    LASER (2.0f), MISSILE (3.0f), PLASMA (4.0f);
    private float power;
    
    WeaponType (float p) {
        power = p;
    }
    
    float getPower() {
        return power;
    }
}

class Loot {
    private int nSupplies, nWeapons, nShields, nHangars, nMedals;
    
    Loot (int nSu, int nWe, int nSh, int nHa, int nMe) {
        nSupplies = nSu;
        nWeapons = nWe;
        nShields = nSh;
        nHangars = nHa;
        nMedals = nMe;
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

class ShieldBooster{
    private String name;
    private float boost;
    private int uses;
    
    ShieldBooster(String n, float b, int u) {
        name = n;
        boost = b;
        uses = u;
    }
    
    ShieldBooster(final ShieldBooster other) {
        name = other.name;
        boost = other.boost;
        uses = other.uses;
    }
    
    float getBoost(){
        return boost;
    }
    
    int getUses(){
        return uses;
    }
    
    float useIt() {
        if(uses > 0){
            uses -= 1;
            return boost;
        }else
            return 1;
    }
}

class Weapon{
    private String name;
    private WeaponType type;
    private int uses;
    
    Weapon(String n, final WeaponType w, int u) {
        name = n;
        type = w;
        uses = u;
    }
    
    Weapon(final Weapon other) {
        name = other.name;
        type = other.type;
        uses = other.uses;
    }
    
    WeaponType getType() {
        return type;
    }
    
    int getUses() {
        return uses;
    }
    
    float power() {
        return type.getPower();
    }
    
    float useIt() {
        if(uses > 0){
            uses -= 1;
            return power();
        }else
            return 1;
    }
}

class Dice{
    private final float NHANGARSPROB = (float)0.25;
    private final float NSHIELDSPROB = (float)0.25;
    private final float NWEAPONSPROB = (float)0.33;
    private final float FIRSTSHOTPROB = (float)0.5;
    private float generator = (float)Math.random();
    
    Dice() {}
    
    int initWithNHangars() {
        generator = (float)Math.random();
        if(generator < NHANGARSPROB)
            return 0;
        else
            return 1;
    }
    
    int initWithNWeapons() {
        generator = (float)Math.random();
        if(generator < NWEAPONSPROB)
            return 1;
        else{
            if(generator < 2*NWEAPONSPROB)
                return 2;
            else
                return 3;
        }
    }
    
    int initWithNShields() {
        generator = (float)Math.random();
        if(generator < NSHIELDSPROB)
            return 0;
        else
            return 1;
    }
    
    int whoStarts(int nPlayers) {
        return (int)(Math.random()*nPlayers);
    }
    
    GameCharacter firstShot() {
        generator = (float)Math.random();
        if(generator < FIRSTSHOTPROB)
            return GameCharacter.SPACESTATION;
        else
            return GameCharacter.ENEMYSTARSHIP;
    }
    
    boolean spaceStationMoves(float speed) {
        generator = (float)Math.random();
        return generator < speed;
    }
}

public class Deepspace {
    public static void main(String[] args) {
        
        WeaponType wt = WeaponType.MISSILE;
        System.out.println("\nEl power del arma es: " + wt.getPower() + "\n");
        
        System.out.println("----------------------------------------------------\n");
        
        Loot l = new Loot(1,2,2,3,1);
        System.out.println("Los valores actuales del loot son: \n" + l.getNSupplies() + " " + l.getNWeapons() + " " + l.getNShields() + " " +  l.getNHangars() + " " + l.getNMedals() + "\n");
        
        System.out.println("----------------------------------------------------\n");
        
        SuppliesPackage s = new SuppliesPackage(2,5,10);
        System.out.println("Los valores actuales del paquete de suministros son: \n" + s.getAmmoPower()+ " " + s.getFuelUnits() + " " + s.getShieldPower());
        SuppliesPackage s_otro = new SuppliesPackage(s);
        System.out.println("Los valores actuales del otro paquete de suministros son: \n" + s_otro.getAmmoPower()+ " " + s_otro.getFuelUnits() + " " + s_otro.getShieldPower() + "\n");
        
        System.out.println("----------------------------------------------------\n");
        
        ShieldBooster sh = new ShieldBooster("Juan",5,2);
        System.out.println("Los valores actuales del protector de escudo son: \n" + sh.getBoost() + " " + sh.getUses());
        System.out.println("Al usar el protector: " + sh.useIt());
        ShieldBooster sh_otro = new ShieldBooster(sh);
        System.out.println("Los valores actuales del otro protector de escudo son: \n" + sh_otro.getBoost() + " " + sh_otro.getUses() + "\n");
        
        System.out.println("----------------------------------------------------\n");
        
        Weapon w = new Weapon("Alberto", wt, 5);
        System.out.println("El tipo de arma es: " + w.getType());
        System.out.println("Los usos del arma son: " + w.getUses());
        System.out.println("El poder del arma es: " + w.power());
        System.out.println("Al usar el arma: " + w.useIt() + "\n");
        Weapon w2 = new Weapon(w);
        System.out.println("El tipo de arma es: " + w2.getType());
        System.out.println("Los usos del arma son: " + w2.getUses());
        System.out.println("El poder del arma es: " + w2.power());
        System.out.println("Al usar el arma: " + w2.useIt() + "\n");
        
        System.out.println("----------------------------------------------------\n");
        
        Dice d = new Dice();
        System.out.println("Los hangares comienzan con el valor: " + d.initWithNHangars());
        System.out.println("Las armas comienzan con el valor: " + d.initWithNWeapons());
        System.out.println("Los escudos comienzan con el valor: " + d.initWithNShields());
        System.out.println("Comienza el jugador número " + (d.whoStarts(6)+1));
        System.out.println("El primer disparo lo ejecutará " + d.firstShot());
        if(d.spaceStationMoves((float)0.5))
            System.out.println("La estación ha esquivado el disparo\n");
        else
            System.out.println("La estación NO ha esquivado el disparo\n");
        
        System.out.println("----------------------------------------------------");
        System.out.println("-------------- LAS 100 EJECUCIONES -----------------");
        System.out.println("----------------------------------------------------\n");
        
        Dice di = new Dice();
        
        int []IWH = {0,0}; 
        int []IWW = {0,0,0};
        int []IWS = {0,0};
        int []players = {0,0,0,0,0,0};
        int []first = {0,0};
        int []SSM = {0,0};
       
        for(int i=0; i<100; i++){
            if(di.initWithNHangars() == 0)
                IWH[0]++;
            else
                IWH[1]++;
            
            if(di.initWithNWeapons() == 1)
                IWW[0]++;
            else{
                if(di.initWithNWeapons() == 2)
                    IWW[1]++;
                else
                    IWW[2]++;
            }
            
            if(di.initWithNShields() == 0)
                IWS[0]++;
            else
                IWS[1]++;
            
            switch(di.whoStarts(6)){
                case 0: players[0]++; break;
                case 1: players[1]++; break;
                case 2: players[2]++; break;
                case 3: players[3]++; break;
                case 4: players[4]++; break;
                case 5: players[5]++; break;
            }
            
            if(di.firstShot() == GameCharacter.SPACESTATION)
                first[0]++;
            else
                first[1]++;
            
            if(di.spaceStationMoves((float)0.5))
               SSM[0]++;
            else
               SSM[1]++;
        }
        
        System.out.println("Los valores de inicio de los hangares son:");
        for(int i=0; i<2; i++)
            System.out.println(IWH[i]);
        
        System.out.println("\nLos valores de inicio de las armas son:");
        for(int i=0; i<3; i++)
            System.out.println(IWW[i]);
        
        System.out.println("\nLos valores de inicio de los escudos son:");
        for(int i=0; i<2; i++)
            System.out.println(IWS[i]);
        
        System.out.println("");
        for(int i=0; i<6; i++)
            System.out.println("El jugador " + (i+1) + " comienza en " + players[i] + " ocasiones");
        
        System.out.println("\nEl equipo que comienza será: ");
        for(int i=0; i<2; i++)
            System.out.println(first[i]);
        
        System.out.println("\nLa estación se mueve en las siguientes situaciones: ");
        for(int i=0; i<2; i++)
            System.out.println(SSM[i]);
        
        System.out.println("");
    }
}