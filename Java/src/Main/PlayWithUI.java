/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import View.View;
import View.UI.TextMainView;
import Controller.Controller;
import deepspace.GameUniverse;

/**
 *
 * @author Profe
 */
public class PlayWithUI {
    public static void main(String[] args) {
      View ui;
      GameUniverse game = new GameUniverse();
      ui = TextMainView.getInstance();
      Controller controller = Controller.getInstance();
      controller.setModelView(game,ui);
      controller.start();   
    }
  
}
