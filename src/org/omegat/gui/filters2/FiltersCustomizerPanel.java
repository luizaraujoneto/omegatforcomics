/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2013 Alex Buloichik, Yu Tang
               2014 Aaron Madlon-Kay
               2015 Yu Tang, Aaron Madlon-Kay
               2016 Aaron Madlon-Kay
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

package org.omegat.gui.filters2;

import javax.swing.JPanel;

import org.omegat.util.OStrings;

/**
 * Main UI for for setting up filters. Filter is a class that allows for
 * reading and writing a single file format. OmegaT has different filters for
 * different supported file formats. E.g. HTML, OpenOffice etc.
 *
 * @author Maxym Mykhalchuk
 * @author Alex Buloichik
 * @author Yu Tang
 * @author Aaron Madlon-Kay
 */
@SuppressWarnings("serial")
public class FiltersCustomizerPanel extends JPanel {

    /** Creates new form FilterCustomizerPanel */
    public FiltersCustomizerPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        description = new javax.swing.JTextArea();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        projectSpecificCB = new javax.swing.JCheckBox();
        cbRemoveTags = new javax.swing.JCheckBox();
        cbRemoveSpacesNonseg = new javax.swing.JCheckBox();
        cbPreserveSpaces = new javax.swing.JCheckBox();
        cbIgnoreFileContext = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        filtersScrollPane = new javax.swing.JScrollPane();
        filtersTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        editButton = new javax.swing.JButton();
        optionsButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        description.setEditable(false);
        description.setFont(jLabel1.getFont());
        description.setLineWrap(true);
        description.setText(OStrings.getString("FILTERSCUSTOMIZER_DESCRIPTION")); // NOI18N
        description.setWrapStyleWord(true);
        description.setAlignmentX(0.0F);
        description.setAlignmentY(0.0F);
        description.setDragEnabled(false);
        description.setFocusable(false);
        description.setOpaque(false);
        add(description);
        add(filler1);

        org.openide.awt.Mnemonics.setLocalizedText(projectSpecificCB, OStrings.getString("FILTERSCUSTOMIZER_CHECKBOX_PROJECTSPECIFIC")); // NOI18N
        projectSpecificCB.setAlignmentY(0.0F);
        projectSpecificCB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        add(projectSpecificCB);

        org.openide.awt.Mnemonics.setLocalizedText(cbRemoveTags, OStrings.getString("FILTERSCUSTOMIZER_OPTION_GLOBAL_REMOVE_TAGS")); // NOI18N
        cbRemoveTags.setAlignmentY(0.0F);
        add(cbRemoveTags);

        org.openide.awt.Mnemonics.setLocalizedText(cbRemoveSpacesNonseg, OStrings.getString("FILTERSCUSTOMIZER_OPTION_GLOBAL_REMOVE_SPACES_NONSEG")); // NOI18N
        cbRemoveSpacesNonseg.setAlignmentY(0.0F);
        add(cbRemoveSpacesNonseg);

        org.openide.awt.Mnemonics.setLocalizedText(cbPreserveSpaces, OStrings.getString("FILTERSCUSTOMIZER_OPTION_GLOBAL_PRESERVE_SPACES")); // NOI18N
        cbPreserveSpaces.setAlignmentY(0.0F);
        add(cbPreserveSpaces);

        org.openide.awt.Mnemonics.setLocalizedText(cbIgnoreFileContext, OStrings.getString("FILTERSCUSTOMIZER_OPTION_GLOBAL_IGNORE_FILE")); // NOI18N
        cbIgnoreFileContext.setAlignmentY(0.0F);
        add(cbIgnoreFileContext);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setLayout(new java.awt.BorderLayout());

        filtersScrollPane.setViewportView(filtersTable);

        jPanel1.add(filtersScrollPane, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(editButton, OStrings.getString("BUTTON_EDIT")); // NOI18N
        editButton.setToolTipText(OStrings.getString("FILTERSCUSTOMIZER_BUTTON_EDIT_HINT")); // NOI18N
        editButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(editButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(optionsButton, OStrings.getString("FILTERSCUSTOMIZER_BUTTON_OPTIONS")); // NOI18N
        optionsButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(optionsButton, gridBagConstraints);

        jPanel3.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel3, java.awt.BorderLayout.EAST);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JCheckBox cbIgnoreFileContext;
    javax.swing.JCheckBox cbPreserveSpaces;
    javax.swing.JCheckBox cbRemoveSpacesNonseg;
    javax.swing.JCheckBox cbRemoveTags;
    javax.swing.JTextArea description;
    javax.swing.JButton editButton;
    private javax.swing.Box.Filler filler1;
    javax.swing.JScrollPane filtersScrollPane;
    javax.swing.JTable filtersTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    javax.swing.JButton optionsButton;
    javax.swing.JCheckBox projectSpecificCB;
    // End of variables declaration//GEN-END:variables
}
