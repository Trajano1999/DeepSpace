/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

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
    
    ShieldToUI getUIversion(){
        return new ShieldToUI(this);
    }
    
    public String toString(){   //NO SE SI ESTA BIEN
        return ("name: " + name.toString() + ", boost: " + boost + ", uses: " + uses);
    }
    
    float getBoost(){
        return boost;
    }
    
    int getUses(){
        return uses;
    }
    
    public float useIt() {
        if(uses > 0){
            uses -= 1;
            return boost;
        }else
            return 1;
    }
}