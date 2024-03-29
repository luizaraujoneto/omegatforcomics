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
public class ExternalFinderItemEditorPanel extends javax.swing.JPanel {

    /**
     * Creates new form ExternalFinderItemEditorPanel
     */
    public ExternalFinderItemEditorPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        nameField = new javax.swing.JTextField();
        popupCheckBox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        keystrokeLabel = new javax.swing.JLabel();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        setKeystrokeButton = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        urlsTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        addUrlButton = new javax.swing.JButton();
        removeUrlButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        editUrlButton = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        commandsTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        addCommandButton = new javax.swing.JButton();
        removeCommandButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        editCommandButton = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel3.setAlignmentX(0.0F);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setLabelFor(nameField);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, OStrings.getString("EXTERNALFINDER_EDITOR_NAME_LABEL")); // NOI18N
        jPanel3.add(jLabel1);
        jPanel3.add(filler1);

        nameField.setToolTipText(OStrings.getString("EXTERNALFINDER_EDITOR_NAME_MESSAGE")); // NOI18N
        jPanel3.add(nameField);

        add(jPanel3);

        org.openide.awt.Mnemonics.setLocalizedText(popupCheckBox, OStrings.getString("EXTERNALFINDER_EDITOR_POPUP_CHECKBOX")); // NOI18N
        add(popupCheckBox);

        jPanel4.setAlignmentX(0.0F);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setLabelFor(keystrokeLabel);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, OStrings.getString("EXTERNALFINDER_EDITOR_KEYSTROKE_LABEL")); // NOI18N
        jPanel4.add(jLabel2);
        jPanel4.add(filler2);

        keystrokeLabel.setFont(keystrokeLabel.getFont().deriveFont(keystrokeLabel.getFont().getStyle() | java.awt.Font.BOLD));
        org.openide.awt.Mnemonics.setLocalizedText(keystrokeLabel, OStrings.getString("KEYSTROKE_EDITOR_NOT_SET")); // NOI18N
        jPanel4.add(keystrokeLabel);
        jPanel4.add(filler8);

        org.openide.awt.Mnemonics.setLocalizedText(setKeystrokeButton, OStrings.getString("EXTERNALFINDER_EDITOR_KEYSTROKE_SET_BUTTON")); // NOI18N
        jPanel4.add(setKeystrokeButton);

        add(jPanel4);
        add(filler7);

        jPanel5.setAlignmentX(0.0F);
        jPanel5.setLayout(new java.awt.BorderLayout());

        urlsTable.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(urlsTable);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel3.setLabelFor(urlsTable);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, OStrings.getString("EXTERNALFINDER_EDITOR_URLS_LABEL")); // NOI18N
        jPanel5.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.GridLayout(0, 1));

        org.openide.awt.Mnemonics.setLocalizedText(addUrlButton, OStrings.getString("EXTERNALFINDER_EDITOR_BUTTON_ADD_URL")); // NOI18N
        jPanel7.add(addUrlButton);

        org.openide.awt.Mnemonics.setLocalizedText(removeUrlButton, OStrings.getString("EXTERNALFINDER_EDITOR_BUTTON_REMOVE_URL")); // NOI18N
        jPanel7.add(removeUrlButton);
        jPanel7.add(filler3);

        org.openide.awt.Mnemonics.setLocalizedText(editUrlButton, OStrings.getString("EXTERNALFINDER_EDITOR_BUTTON_EDIT_URL")); // NOI18N
        jPanel7.add(editUrlButton);

        jPanel6.add(jPanel7, java.awt.BorderLayout.NORTH);

        jPanel5.add(jPanel6, java.awt.BorderLayout.EAST);

        add(jPanel5);
        add(filler6);

        jPanel8.setAlignmentX(0.0F);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel4.setLabelFor(commandsTable);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, OStrings.getString("EXTERNALFINDER_EDITOR_COMMANDS_LABEL")); // NOI18N
        jPanel8.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        commandsTable.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(commandsTable);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setLayout(new java.awt.GridLayout(0, 1));

        org.openide.awt.Mnemonics.setLocalizedText(addCommandButton, OStrings.getString("EXTERNALFINDER_EDITOR_BUTTON_ADD_COMMAND")); // NOI18N
        jPanel10.add(addCommandButton);

        org.openide.awt.Mnemonics.setLocalizedText(removeCommandButton, OStrings.getString("EXTERNALFINDER_EDITOR_BUTTON_REMOVE_COMMAND")); // NOI18N
        jPanel10.add(removeCommandButton);
        jPanel10.add(filler4);

        org.openide.awt.Mnemonics.setLocalizedText(editCommandButton, OStrings.getString("EXTERNALFINDER_EDITOR_BUTTON_EDIT_COMMAND")); // NOI18N
        jPanel10.add(editCommandButton);

        jPanel9.add(jPanel10, java.awt.BorderLayout.NORTH);

        jPanel8.add(jPanel9, java.awt.BorderLayout.EAST);

        add(jPanel8);
        add(filler5);

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        org.openide.awt.Mnemonics.setLocalizedText(okButton, OStrings.getString("BUTTON_OK")); // NOI18N
        jPanel2.add(okButton);

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, OStrings.getString("BUTTON_CANCEL")); // NOI18N
        jPanel2.add(cancelButton);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton addCommandButton;
    javax.swing.JButton addUrlButton;
    javax.swing.JButton cancelButton;
    javax.swing.JTable commandsTable;
    javax.swing.JButton editCommandButton;
    javax.swing.JButton editUrlButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    javax.swing.JLabel keystrokeLabel;
    javax.swing.JTextField nameField;
    javax.swing.JButton okButton;
    javax.swing.JCheckBox popupCheckBox;
    javax.swing.JButton removeCommandButton;
    javax.swing.JButton removeUrlButton;
    javax.swing.JButton setKeystrokeButton;
    javax.swing.JTable urlsTable;
    // End of variables declaration//GEN-END:variables
}
