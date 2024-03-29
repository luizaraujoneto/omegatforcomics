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

package org.omegat.gui.issues;

import org.omegat.util.OStrings;

/**
 * A panel for graphically displaying and organizing issues.
 *
 * @author Aaron Madlon-Kay <aaron@madlon-kay.com>
 */
@SuppressWarnings("serial")
public class IssuesPanel extends javax.swing.JPanel {

    /**
     * Creates new form IssuesPanel
     */
    public IssuesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        instructionsPanel = new javax.swing.JPanel();
        instructionsTextArea = new javax.swing.JTextArea();
        outerSplitPane = new javax.swing.JSplitPane();
        innerSplitPane = new javax.swing.JSplitPane();
        typeListScrollPanel = new javax.swing.JScrollPane();
        typeList = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        messagePanel = new javax.swing.JPanel();
        messageLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jumpButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        showAllButtonPanel = new javax.swing.JPanel();
        showAllButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        reloadButton = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        closeButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();

        setLayout(new java.awt.BorderLayout());

        instructionsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionsPanel.setLayout(new java.awt.BorderLayout());

        instructionsTextArea.setFont(messageLabel.getFont());
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);
        instructionsTextArea.setOpaque(false);
        instructionsPanel.add(instructionsTextArea, java.awt.BorderLayout.CENTER);

        add(instructionsPanel, java.awt.BorderLayout.NORTH);

        outerSplitPane.setBorder(null);
        outerSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        outerSplitPane.setResizeWeight(0.5);

        innerSplitPane.setBorder(null);

        typeListScrollPanel.setViewportView(typeList);

        innerSplitPane.setLeftComponent(typeListScrollPanel);

        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(table);

        innerSplitPane.setRightComponent(jScrollPane1);

        outerSplitPane.setTopComponent(innerSplitPane);

        messagePanel.setLayout(new java.awt.BorderLayout());

        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messagePanel.add(messageLabel, java.awt.BorderLayout.CENTER);

        outerSplitPane.setRightComponent(messagePanel);

        add(outerSplitPane, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler1);

        org.openide.awt.Mnemonics.setLocalizedText(jumpButton, OStrings.getString("ISSUES_BUTTON_JUMP_TO_SEGMENT")); // NOI18N
        jumpButton.setEnabled(false);
        jPanel1.add(jumpButton);
        jPanel1.add(filler3);

        showAllButtonPanel.setLayout(new javax.swing.BoxLayout(showAllButtonPanel, javax.swing.BoxLayout.LINE_AXIS));

        org.openide.awt.Mnemonics.setLocalizedText(showAllButton, OStrings.getString("ISSUES_BUTTON_SHOW_ALL")); // NOI18N
        showAllButton.setEnabled(false);
        showAllButtonPanel.add(showAllButton);
        showAllButtonPanel.add(filler4);

        jPanel1.add(showAllButtonPanel);

        org.openide.awt.Mnemonics.setLocalizedText(reloadButton, OStrings.getString("ISSUES_BUTTON_REFRESH")); // NOI18N
        reloadButton.setEnabled(false);
        jPanel1.add(reloadButton);
        jPanel1.add(filler5);

        org.openide.awt.Mnemonics.setLocalizedText(closeButton, OStrings.getString("BUTTON_CLOSE")); // NOI18N
        jPanel1.add(closeButton);
        jPanel1.add(filler2);

        jPanel3.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(progressBar, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel2, java.awt.BorderLayout.SOUTH);

        add(jPanel3, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton closeButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    javax.swing.JSplitPane innerSplitPane;
    javax.swing.JPanel instructionsPanel;
    javax.swing.JTextArea instructionsTextArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    javax.swing.JButton jumpButton;
    javax.swing.JLabel messageLabel;
    javax.swing.JPanel messagePanel;
    javax.swing.JSplitPane outerSplitPane;
    javax.swing.JProgressBar progressBar;
    javax.swing.JButton reloadButton;
    javax.swing.JButton showAllButton;
    javax.swing.JPanel showAllButtonPanel;
    javax.swing.JTable table;
    javax.swing.JList<String> typeList;
    javax.swing.JScrollPane typeListScrollPanel;
    // End of variables declaration//GEN-END:variables
}
