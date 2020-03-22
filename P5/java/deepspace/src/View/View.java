/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import deepspace.CombatResult;
import java.util.ArrayList;

/**
 *
 * @author jruib
 */
public interface View {
    public void updateView();
    public void showView();
    public ArrayList<String> readNamePlayers();
    
    public void nextTurnNotAllowedMessage();
    public void escapeMessage();
    public void lostCombatMessage();
    public void wonCombatMessage();
    public void wonGameMessage();
    public void conversionMessage();
    public void noCombatMessage();
    public boolean confirmExitMessage();
}
