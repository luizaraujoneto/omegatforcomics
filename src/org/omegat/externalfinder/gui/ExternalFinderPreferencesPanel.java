/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2016 Aaron Madlon-Kay
               Home page: https://www.omegat.org/
               Support center: https://omegat.org/support

 This file is part of OmegaT.

 OmegaT is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OmegaT is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>.
 **************************************************************************/

package org.omegat.externalfinder.gui;

import org.omegat.util.OStrings;

/**
 * @author Aaron Madlon-Kay
 */
@SuppressWarnings("serial")
public class ExternalFinderPreferencesPanel extends javax.swing.JPanel {

    /**
     * Creates new form ExternalFinderPreferencesPanel
     */
    public ExternalFinderPreferencesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        globalOptionsPanel = new javax.swing.JPanel();
        projectSpecificCommandsCheckBox = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        priorityPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        prioritySpinner = new javax.swing.JSpinner();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        editButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        globalOptionsPanel.setLayout(new javax.swing.BoxLayout(globalOptionsPanel, javax.swing.BoxLayout.PAGE_AXIS));

        org.openide.awt.Mnemonics.setLocalizedText(projectSpecificCommandsCheckBox, OStrings.getString("PREFS_EXTERNALFINDER_PROJECT_COMMANDS_ENABLED")); // NOI18N
        globalOptionsPanel.add(projectSpecificCommandsCheckBox);

        jPanel5.setAlignmentX(0.0F);
        jPanel5.setLayout(new java.awt.BorderLayout());

        priorityPanel.setAlignmentX(0.0F);
        priorityPanel.setLayout(new javax.swing.BoxLayout(priorityPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setLabelFor(prioritySpinner);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, OStrings.getString("PREFS_EXTERNALFINDER_PRIORITY_LABEL")); // NOI18N
        priorityPanel.add(jLabel1);
        priorityPanel.add(filler1);

        prioritySpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 50));
        priorityPanel.add(prioritySpinner);

        jPanel5.add(priorityPanel, java.awt.BorderLayout.WEST);

        globalOptionsPanel.add(jPanel5);

        add(globalOptionsPanel);
        add(filler3);

        jPanel2.setAlignmentX(0.0F);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setAlignmentX(0.0F);

        itemTable.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(itemTable);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(0, 1));

        org.openide.awt.Mnemonics.setLocalizedText(addButton, OStrings.getString("PREFS_EXTERNALFINDER_BUTTON_ADD_ITEM")); // NOI18N
        jPanel4.add(addButton);

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, OStrings.getString("PREFS_EXTERNALFINDER_BUTTON_REMOVE_ITEM")); // NOI18N
        jPanel4.add(removeButton);
        jPanel4.add(filler2);

        org.openide.awt.Mnemonics.setLocalizedText(editButton, OStrings.getString("PREFS_EXTERNALFINDER_BUTTON_EDIT_ITEM")); // NOI18N
        jPanel4.add(editButton);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.add(jPanel3, java.awt.BorderLayout.EAST);

        jLabel2.setLabelFor(itemTable);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, OStrings.getString("PREFS_EXTERNALFINDER_ITEMS_LABEL")); // NOI18N
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton addButton;
    javax.swing.JButton editButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    javax.swing.JPanel globalOptionsPanel;
    javax.swing.JTable itemTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    javax.swing.JPanel priorityPanel;
    javax.swing.JSpinner prioritySpinner;
    javax.swing.JCheckBox projectSpecificCommandsCheckBox;
    javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
}
