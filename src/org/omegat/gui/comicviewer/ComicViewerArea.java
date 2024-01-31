/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2007 Zoltan Bartko
               2011 John Moran
               2015 Aaron Madlon-Kay
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

package org.omegat.gui.comicviewer;

import java.awt.Dimension;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.undo.UndoManager;

import org.omegat.core.CoreEvents;
import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IEntryEventListener;
import org.omegat.gui.common.EntryInfoPane;
import org.omegat.gui.main.DockableScrollPane;
import org.omegat.gui.main.IMainWindow;
import org.omegat.util.OStrings;
import org.omegat.util.Preferences;
import org.omegat.util.StringUtil;
import org.omegat.util.gui.IPaneMenu;
import org.omegat.util.gui.JTextPaneLinkifier;
import org.omegat.util.gui.StaticUIUtils;
import org.omegat.util.gui.UIThreadsUtil;

/**
 * This is a pane that displays notes on translation units.
 *
 * @author Luiz Araujo (luizaraujoneto@gmail.com)
 */
@SuppressWarnings("serial")
public class ComicViewerArea extends EntryInfoPane<String> implements IComicViewer, IPaneMenu {

    private static final String EXPLANATION = new String( "Comic Viewer Area" );

    UndoManager undoManager;
    private DockableScrollPane scrollPane;

    /** Creates new Comic Viewer Area Pane */
    public ComicViewerArea(IMainWindow mw) {
        super(true);

        String title = OStrings.getString("GUI_COMICVIEWERWINDOW_SUBWINDOWTITLE_ComicViewer");
        scrollPane = new DockableScrollPane("COMICVIEWER", title, this, true);
        mw.addDockable(scrollPane);

        setEditable(false);
        setText(EXPLANATION);
        setMinimumSize(new Dimension(100, 50));

        JTextPaneLinkifier.linkify(this);
        undoManager = new UndoManager();
        getDocument().addUndoableEditListener(undoManager);
        
        CoreEvents.registerEntryEventListener(new IEntryEventListener() {
            @Override
            public void onNewFile(String activeFileName) {
            	System.out.println( "Event ComicViewer::OnNewFile" + activeFileName);
            }

            @Override
            public void onEntryActivated(SourceTextEntry newEntry) {
            	System.out.println( "Event ComicViewer::onEntryActivated " + newEntry.getSrcText());
            	System.out.println( "Event ComicViewer::onEntryActivated " + newEntry.getProtectedParts().toString());
            	System.out.println( "Event ComicViewer::onEntryActivated " + newEntry.getRawProperties().toString());
            }
        });
    }

    @Override
    protected void onProjectOpen() {
        clear();
    }

    @Override
    protected void onProjectClose() {
        clear();
        setText(EXPLANATION);
    }

    /** Clears up the pane. */
    @Override
    public void clear() {
        super.clear();
        setEditable(false);
        undoManager.discardAllEdits();
    }

    public void setNoteText(String text) {
        UIThreadsUtil.mustBeSwingThread();

        if (Preferences.isPreference(Preferences.NOTIFY_NOTES)) {
            if (StringUtil.isEmpty(text)) {
                scrollPane.stopNotifying();
            } else {
                scrollPane.notify(true);
            }
        }
        setText(text);
        setEditable(true);
    }

    public String getNoteText() {
        UIThreadsUtil.mustBeSwingThread();

        String text = getText();
        // Disallow empty note. Use null to indicate lack of note.
        return text.isEmpty() ? null : text;
    }

    @Override
    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    @Override
    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    @Override
    public void populatePaneMenu(JPopupMenu menu) {
        final JMenuItem notify = new JCheckBoxMenuItem(OStrings.getString("GUI_NOTESWINDOW_NOTIFICATIONS"));
        notify.setSelected(Preferences.isPreference(Preferences.NOTIFY_NOTES));
        notify.addActionListener(e -> Preferences.setPreference(Preferences.NOTIFY_NOTES, notify.isSelected()));
        menu.add(notify);
    }
    
    @Override
    public void requestFocus() {
        StaticUIUtils.requestVisible(scrollPane);
        scrollPane.getViewport().getView().requestFocusInWindow();
    }
}
