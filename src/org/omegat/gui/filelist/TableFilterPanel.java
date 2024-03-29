/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2015 Aaron Madlon-Kay
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
package org.omegat.gui.filelist;

import org.omegat.util.OStrings;

/**
 * A text box providing input to a table filter
 * @author Aaron Madlon-Kay
 */
@SuppressWarnings("serial")
public class TableFilterPanel extends javax.swing.JPanel {

    /**
     * Creates new form FileListFilterPanel
     */
    public TableFilterPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        filterTextField = new javax.swing.JTextField();
        filterCloseButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new java.awt.BorderLayout(5, 0));

        jLabel1.setText(OStrings.getString("PF_TABLE_FILTER")); // NOI18N
        add(jLabel1, java.awt.BorderLayout.WEST);
        add(filterTextField, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(filterCloseButton, OStrings.getString("PF_FILTER_BUTTON_CLOSE")); // NOI18N
        add(filterCloseButton, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton filterCloseButton;
    public javax.swing.JTextField filterTextField;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
