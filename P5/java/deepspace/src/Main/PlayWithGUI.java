/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import deepspace.GameUniverse;
import View.View;
import View.GUI.MainWindow;
import Controller.Controller;

/**
 *
 * @author jruib
 */
public class PlayWithGUI {
    public static void main(String args[]) {
        GameUniverse gameUniverse = new GameUniverse();
        View view = MainWindow.getInstance();
        Controller controller = Controller.getInstance();
        controller.setModelView(gameUniverse, view);
        controller.start();
    }
}
