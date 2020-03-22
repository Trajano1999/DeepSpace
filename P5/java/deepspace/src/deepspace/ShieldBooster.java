/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deepspace;

class ShieldBooster implements CombatElement {
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
    
    @Override
    public String toString(){
        return getUIversion().toString();
    }
    
    float getBoost(){
        return boost;
    }
    
    @Override
    public int getUses(){
        return uses;
    }
    
    @Override
    public float useIt() {
        if(uses > 0){
            uses -= 1;
            return boost;
        }else
            return 1;
    }
}