/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import deepspace.GameUniverse;
import View.View;
import deepspace.CombatResult;
import deepspace.GameUniverseToUI;

import deepspace.GameState; // ?
import java.util.Collections; // ?

public class Controller {
    private GameUniverse model;
    private View view;
    
    public static final int WEAPON = 0x1; // ?
    public static final int SHIELD = 0x2;
    public static final int HANGAR = 0x4;
    
    private static Controller instance = new Controller();
    public static Controller getInstance () {
        return instance;
    }
    
    private Controller() {}
    
    public void setModelView(GameUniverse g, View v) {
        model = g;
        view = v;
    }
    
    public void start() {
        model.init(view.readNamePlayers());
        updateView();
        view.showView();
    }
   
    public void finish (int i) {
        if (view.confirmExitMessage()) {
            System.exit(i);
        }
    }
    
    public GameUniverseToUI getUIversion(){
        return model.getUIversion();
    }
    
    public boolean nextTurn () {
        boolean nextTurnAllowed = model.nextTurn();
        if (!nextTurnAllowed) {
            view.nextTurnNotAllowedMessage();
        }
        updateView();
        return nextTurnAllowed;
    }
    
    public void combat () {
        CombatResult result = model.combat();
        switch (result) {
            case ENEMYWINS :
                view.lostCombatMessage();
                break;
            case STATIONESCAPES :
                view.escapeMessage();
                break;
            case STATIONWINS :
                view.conversionMessage();
                if (model.haveAWinner()) {
                    view.wonGameMessage();
                    System.exit (0);
                }
                break;
            case STATIONWINSANDCONVERTS :
                view.conversionMessage();
                if (model.haveAWinner()) {
                    view.wonGameMessage();
                    System.exit(0);
                }
                break;
            case NOCOMBAT :
                view.noCombatMessage();
                break;
        }
        updateView();
    }
    
    public GameState getState(){
        return model.getState();
    }
    
    public void updateView(){
        view.updateView();
    }
    
    public boolean haveAWinner(){
        return model.haveAWinner();
    }
    
    public void discardHangar(){
        model.discardHangar();
        updateView();
    }
    
    private void invertArray (ArrayList<Integer> array) {
        int i, n;

        n = array.size();
        for (i = 0; i < n/2; i++)
            Collections.swap(array, i, n-i-1);
    }
    
    public void mount (ArrayList<Integer> weapons, ArrayList<Integer> shields) {
      invertArray (weapons);
      invertArray (shields);
      
      for (int i : weapons) {
        model.mountWeapon(i);
      }
      for (int i : shields) {
        model.mountShieldBooster(i);
      }
      updateView();
    }
    
    public void discard (int places, ArrayList<Integer> weapons, ArrayList<Integer> shields) {
      invertArray(weapons);
      invertArray(shields);
      
      if ((places & WEAPON) == WEAPON) {
        for (int i : weapons) {
          model.discardWeapon(i);
        }
      } else if ((places & SHIELD) == SHIELD) {
        for (int i : shields) {
          model.discardShieldBooster(i);
        }
      } else if((places & HANGAR) == HANGAR) {
        for (int i : weapons) {
          model.discardWeaponInHangar(i);
        }
        for (int i : shields) {
          model.discardShieldBoosterInHangar(i);
        }
      }
    }
}
