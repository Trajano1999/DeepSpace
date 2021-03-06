/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.GUI;

import java.util.ArrayList;
import java.awt.Component;
import deepspace.HangarToUI;
import deepspace.WeaponToUI;
import deepspace.ShieldToUI;

public class HangarView extends javax.swing.JPanel {

    /**
     * Creates new form HangarView
     */
    public HangarView() {
        initComponents();
    }

    public void setHangarView(HangarToUI h){
        ArrayList<WeaponToUI> w = h.getWeapons();
        ArrayList<ShieldToUI> sh = h.getShieldBoosters();
        WeaponView wv;
        ShieldBoosterView sv;
        
        jPanelWeapons.removeAll();
        for(WeaponToUI x : w){
            wv = new WeaponView();
            wv.setWeaponView(x);
            jPanelWeapons.add(wv);
        }
        
        jPanelShieldBoosters.removeAll();
        for(ShieldToUI x : sh){
            sv = new ShieldBoosterView();
            sv.setShieldBoosterView(x);
            jPanelShieldBoosters.add(sv);
        }
        
        repaint();
        revalidate();
    }
    
    ArrayList<Integer> getSelectedWeapons() {
        ArrayList<Integer> selectedWeapons = new ArrayList<>();
        int i = 0;
        
        for (Component c : jPanelWeapons.getComponents()) {
            if (((WeaponView) c).isSelected())
                selectedWeapons.add(i);
            i++;
        }
        
        return selectedWeapons;
    }
    
    ArrayList<Integer> getSelectedShieldBoosters() {
        ArrayList<Integer> selectedShieldBoosters = new ArrayList<>();
        int i = 0;
        
        for (Component c : jPanelShieldBoosters.getComponents()) {
            if (((ShieldBoosterView) c).isSelected())
                selectedShieldBoosters.add(i);
            i++;
        }
        
        return selectedShieldBoosters;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelWeapons = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelShieldBoosters = new javax.swing.JPanel();

        jPanelWeapons.setBorder(javax.swing.BorderFactory.createTitledBorder("Potenciadores de Fuego"));
        jScrollPane1.setViewportView(jPanelWeapons);

        jPanelShieldBoosters.setBorder(javax.swing.BorderFactory.createTitledBorder("Potenciadores de Escudo"));
        jScrollPane2.setViewportView(jPanelShieldBoosters);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelShieldBoosters;
    private javax.swing.JPanel jPanelWeapons;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
