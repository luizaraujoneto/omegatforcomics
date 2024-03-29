/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2009 Didier Briel
               2014 Didier Briel
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

package org.omegat.filters2.text;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.AbstractAction;

import org.omegat.util.OStrings;
import org.omegat.util.gui.StaticUIUtils;

/**
 * Modal dialog to edit Text filter options.
 *
 * @author Maxym Mykhalchuk
 * @author Didier Briel
 */
@SuppressWarnings("serial")
public class TextOptionsDialog extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    private Map<String, String> options;

    /**
     * Set the value and availability of maxLineLengthSpinner depending on the value of lineLengthSpinner
     */
    private void checkLineLengthValue() {
        try {
            if (Integer.parseInt(lineLengthSpinner.getValue().toString()) <= 0) {
                lineLengthSpinner.setValue(0);
                maxLineLengthSpinner.setValue(0);
                maxLineLengthSpinner.setEnabled(false);
            } else if (Integer.parseInt(lineLengthSpinner.getValue().toString()) > 0) {
                maxLineLengthSpinner.setEnabled(true);
            }
            if (Integer.parseInt(maxLineLengthSpinner.getValue().toString()) < Integer
                    .parseInt(lineLengthSpinner.getValue().toString())) {
                maxLineLengthSpinner.setValue(Integer.parseInt(lineLengthSpinner.getValue().toString()));
            }
        } catch (Exception e) {
            // Eat exception silently
        }

    }

    /**
     * Creates new form TextOptionsDialog
     */
    public TextOptionsDialog(Window parent, Map<String, String> options) {
        super(parent);
        setModal(true);
        this.options = new TreeMap<String, String>(options);
        initComponents();

        String segmentOn = options.get(TextFilter.OPTION_SEGMENT_ON);
        if (TextFilter.SEGMENT_BREAKS.equals(segmentOn)) {
            breaksRB.setSelected(true);
        } else if (TextFilter.SEGMENT_NEVER.equals(segmentOn)) {
            neverRB.setSelected(true);
        } else {
            emptyLinesRB.setSelected(true);
        }

        try {
            lineLengthSpinner.setValue(Integer.parseInt(options.get(TextFilter.OPTION_LINE_LENGTH)));
        } catch (Exception e) {
            lineLengthSpinner.setValue(0);
        }
        try {
            maxLineLengthSpinner.setValue(Integer.parseInt(options.get(TextFilter.OPTION_MAX_LINE_LENGTH)));
        } catch (Exception e) {
            maxLineLengthSpinner.setValue(0);
        }

        checkLineLengthValue();

        StaticUIUtils.setEscapeAction(this, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        setLocationRelativeTo(parent);
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    /** Returns updated options. */
    public Map<String, String> getOptions() {
        return options;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        breaksRB = new javax.swing.JRadioButton();
        emptyLinesRB = new javax.swing.JRadioButton();
        neverRB = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        lineLengthLabel = new javax.swing.JLabel();
        lineLengthSpinner = new javax.swing.JSpinner();
        maxLineLengthLabel = new javax.swing.JLabel();
        maxLineLengthSpinner = new javax.swing.JSpinner();

        setTitle(OStrings.getString("TEXTFILTER_OPTIONS_DIALOG_TITLE")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        org.openide.awt.Mnemonics.setLocalizedText(okButton, OStrings.getString("BUTTON_OK")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(okButton);

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, OStrings.getString("BUTTON_CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, OStrings.getString("TEXTFILTER_OPTIONS_DIALOG_DESCRIPTION")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel1, gridBagConstraints);

        buttonGroup1.add(breaksRB);
        org.openide.awt.Mnemonics.setLocalizedText(breaksRB, OStrings.getString("TEXTFILTER_OPTION_SEGMENT_ON_LINE_BREAKS")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(breaksRB, gridBagConstraints);

        buttonGroup1.add(emptyLinesRB);
        emptyLinesRB.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(emptyLinesRB, OStrings.getString("TEXTFILTER_OPTION_SEGMENT_ON_EMPTY_LINES")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(emptyLinesRB, gridBagConstraints);

        buttonGroup1.add(neverRB);
        org.openide.awt.Mnemonics.setLocalizedText(neverRB, OStrings.getString("TEXTFILTER_OPTION_NEVER_SEGMENT")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(neverRB, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, OStrings.getString("TEXT_FILTER_LINE_LENGTH_DESCRIPTION")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 4);
        jPanel1.add(jLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lineLengthLabel, OStrings.getString("TEXT_FILTER_LINE_LENGTH")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 16, 4, 4);
        jPanel1.add(lineLengthLabel, gridBagConstraints);

        lineLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lineLengthSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel1.add(lineLengthSpinner, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(maxLineLengthLabel, OStrings.getString("TEXT_FILTER_MAX_LINE_LENGTH")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 16, 4, 4);
        jPanel1.add(maxLineLengthLabel, gridBagConstraints);

        maxLineLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                maxLineLengthSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel1.add(maxLineLengthSpinner, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lineLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lineLengthSpinnerStateChanged
        checkLineLengthValue();
    }//GEN-LAST:event_lineLengthSpinnerStateChanged

    private void maxLineLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxLineLengthSpinnerStateChanged
        checkLineLengthValue();
    }//GEN-LAST:event_maxLineLengthSpinnerStateChanged

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
        String segmentOn;
        if (breaksRB.isSelected()) {
            segmentOn = TextFilter.SEGMENT_BREAKS;
        } else if (neverRB.isSelected()) {
            segmentOn = TextFilter.SEGMENT_NEVER;
        } else {
            segmentOn = TextFilter.SEGMENT_EMPTYLINES;
        }

        options.put(TextFilter.OPTION_SEGMENT_ON, segmentOn);
        options.put(TextFilter.OPTION_LINE_LENGTH, lineLengthSpinner.getValue().toString());
        options.put(TextFilter.OPTION_MAX_LINE_LENGTH, maxLineLengthSpinner.getValue().toString());

        doClose(RET_OK);
    }// GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }// GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }// GEN-LAST:event_closeDialog

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton breaksRB;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JRadioButton emptyLinesRB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lineLengthLabel;
    private javax.swing.JSpinner lineLengthSpinner;
    private javax.swing.JLabel maxLineLengthLabel;
    private javax.swing.JSpinner maxLineLengthSpinner;
    private javax.swing.JRadioButton neverRB;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
