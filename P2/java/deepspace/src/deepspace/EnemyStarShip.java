/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

class EnemyStarShip {
    private float ammoPower;
    private String name;
    private float shieldPower;
    private Damage damage;
    private Loot loot;

    EnemyStarShip(String n, float a, float s, Loot l, Damage d){
        name = n;
        ammoPower = a;
        shieldPower = s;
        loot = l;
        damage = d;
    }

    EnemyStarShip(EnemyStarShip e){
        name = e.name;
        ammoPower = e.ammoPower;
        shieldPower = e.shieldPower;
        loot = new Loot(e.loot);
        damage = new Damage(e.damage);
    }
    
    EnemyToUI getUIversion(){
        return (new EnemyToUI(this));
    }
    
    public String toString(){   //NO SE SI ESTA BIEN
        return ("name: " + name.toString() + ", ammoPower: " + ammoPower + ", shieldPower: " + shieldPower + ", loot: [" + loot.toString() + "], damage: [" + damage.toString() + "]");
    }
    
    public float getAmmoPower(){
        return ammoPower;
    }
    
    public Damage getDamage(){
        return damage;
    }
    
    public Loot getLoot(){
        return loot;
    }
    
    public String getName(){
        return name;
    }
    
    public float getShieldPower(){
        return shieldPower;
    }
    
    public float fire(){
        return ammoPower;
    }
    
    public float protection(){
        return shieldPower;
    }
    
    public ShotResult receiveShot(float shot){
        if(shieldPower < shot)
            return (ShotResult.DONOTRESIST);
        else 
            return (ShotResult.RESIST);
    }
}
