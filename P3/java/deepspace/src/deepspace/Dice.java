/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

import java.util.Random;

class Dice{
    private final float NHANGARSPROB; 
    private final float NSHIELDSPROB;
    private final float NWEAPONSPROB;
    private final float FIRSTSHOTPROB;
    private Random generator;
    
    Dice() {
        NHANGARSPROB = (float)0.25;
        NSHIELDSPROB = (float)0.25;
        NWEAPONSPROB = (float)0.33;
        FIRSTSHOTPROB = (float)0.5;
        generator = new Random();
    }
    
    public String toString(){
        return ("NHANGARSPROB: " + NHANGARSPROB + ", NSHIELDSPROB: " + NSHIELDSPROB + ", NWEAPONSPROB: " + NWEAPONSPROB + ", FIRSTSHOTPROB: " + FIRSTSHOTPROB);
    }
    
    int initWithNHangars() {
        if(generator.nextFloat() < NHANGARSPROB)
            return 0;
        else
            return 1;
    }
    
    int initWithNWeapons() {
        float aleatorio = generator.nextFloat();
        if(aleatorio < NWEAPONSPROB)
            return 1;
        else{
            if(aleatorio < 2*NWEAPONSPROB)
                return 2;
            else
                return 3;
        }
    }
    
    int initWithNShields() {
        float aleatorio = generator.nextFloat();
        if(aleatorio < NSHIELDSPROB)
            return 0;
        else
            return 1;
    }
    
    int whoStarts(int nPlayers) {
        return generator.nextInt(nPlayers);
    }
    
    GameCharacter firstShot() {
        float aleatorio = generator.nextFloat();
        if(aleatorio < FIRSTSHOTPROB)
            return GameCharacter.SPACESTATION;
        else
            return GameCharacter.ENEMYSTARSHIP;
    }
    
    boolean spaceStationMoves(float speed) {
        return generator.nextFloat() < speed;
    }
}