/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import deepspace.*;

public class Main {
    public static void main(String[] args) {
        
        WeaponType wt = WeaponType.MISSILE;
        
        /*
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
        */
    }
}
