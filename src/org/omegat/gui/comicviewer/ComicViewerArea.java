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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.undo.UndoManager;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.ComicBook;
import org.omegat.core.data.ComicPage;
import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IApplicationEventListener;
import org.omegat.core.events.IEntryEventListener;
import org.omegat.core.events.IFontChangedEventListener;
import org.omegat.core.events.IProjectEventListener;
import org.omegat.core.events.IProjectEventListener.PROJECT_CHANGE_TYPE;
import org.omegat.gui.dialogs.LastChangesDialog;
import org.omegat.gui.main.DockableScrollPane;
import org.omegat.gui.main.IMainWindow;
import org.omegat.util.OStrings;
import org.omegat.util.Preferences;
import org.omegat.util.gui.IPaneMenu;
import org.omegat.util.gui.StaticUIUtils;

/**
 * This is a pane that displays notes on translation units.
 *
 * @author Luiz Araujo (luizaraujoneto@gmail.com)
 */
@SuppressWarnings("serial")
//public class ComicViewerArea extends EntryInfoPane<String> implements IComicViewer, IPaneMenu {
public class ComicViewerArea extends JPanel implements IComicViewer, IPaneMenu {
    
    UndoManager undoManager;
    private DockableScrollPane scrollPane;
    
    JLabel imagePanel = null;
    
	private int currentPageIndex = -1;
	
	enum NavigateComicPage { FIRST_PAGE, PREVIOUS_PAGE, NEXT_PAGE, LAST_PAGE, CURRENT_PAGE };
	
    /** Creates new Comic Viewer Area Pane */
    public ComicViewerArea(IMainWindow mw) {
        super(true);
        
        String title = OStrings.getString("GUI_COMICVIEWERWINDOW_SUBWINDOWTITLE_ComicViewer");
        scrollPane = new DockableScrollPane("COMICVIEWER", title, this, true);
        mw.addDockable(scrollPane);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        this.setBackground(Color.WHITE);

        scrollPane.setColumnHeaderView(createToolBar()); 
        
        imagePanel = new JLabel();
        
        this.add(imagePanel);
       
        setMinimumSize(new Dimension(100, 50));
        
        CoreEvents.registerEntryEventListener(new IEntryEventListener() {
           
            @Override
            public void onEntryActivated(SourceTextEntry newEntry) {
            	
            	try {
            		
            		String entryID = newEntry.getKey().id;
            		
            		ComicPage page = Core.getProject().getActiveComic().getPageByTokenId(entryID);
            		currentPageIndex = Core.getProject().getActiveComic().getPages().indexOf(page);
          		
            		showComicPage(NavigateComicPage.CURRENT_PAGE);
            		
            	} catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, e.getMessage() );
				}                           	
            }

			@Override
			public void onNewFile(String activeFileName) {
				// TODO Auto-generated method stub
			}

        });
    }

	private JToolBar createToolBar() {
		JToolBar toolbar = new JToolBar();
		      
		JButton firstPage = new JButton(new String("First"));
		JButton previousPage = new JButton(new String("Previous"));
		JButton nextPage = new JButton(new String("Next"));
		JButton lastPage = new JButton(new String("Last"));
		
		firstPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.FIRST_PAGE);
				navigateToFirstSegmentOfPage();
			}
		});

		previousPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.PREVIOUS_PAGE);
				navigateToFirstSegmentOfPage();
			}
		});
		
		nextPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.NEXT_PAGE);
				navigateToFirstSegmentOfPage();
			}
		});
		
		lastPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.LAST_PAGE);
				navigateToFirstSegmentOfPage();
			}
		});
		
        toolbar.add(firstPage);
        toolbar.add(previousPage);
        toolbar.add(nextPage);
        toolbar.add(lastPage); 

        toolbar.addSeparator(new Dimension(5,10));
                
        toolbar.setBorder(new LineBorder(Color.GRAY, 1));
        
        return toolbar;
	}
	
    protected void navigateToFirstSegmentOfPage() {
		
    	String entryID = Core.getProject().getActiveComic().getPage(currentPageIndex).getTextBlocks().get(0).getEntryID();
    	  
    	for ( SourceTextEntry entry: Core.getProject().getAllEntries()) {
    		if (entry.getKey().id.equals(entryID)) {
    			Core.getEditor().gotoEntry(entry.getSrcText(), entry.getKey());
    		}
    	}
		
	}

	protected void showComicPage(NavigateComicPage move) {

    	if (currentPageIndex == -1 ) {
    		return;
    	}    	
    	
    	switch(move) {
    	case FIRST_PAGE:
			currentPageIndex = 0;				
    		break;
    	case PREVIOUS_PAGE:
			currentPageIndex = currentPageIndex > 0 ? currentPageIndex - 1 : 0;				
			break;
    	case NEXT_PAGE:
			int lastPage = Core.getProject().getActiveComic().getPages().size() - 1;
			currentPageIndex = currentPageIndex < lastPage ? currentPageIndex + 1 : lastPage;				
			break;
    	case LAST_PAGE:
			currentPageIndex = Core.getProject().getActiveComic().getPages().size() - 1;				
			break;
    	case CURRENT_PAGE:
    		break;
    	}  	 	   	
    	
    	ComicPage page = Core.getProject().getActiveComic().getPage(currentPageIndex);
    	
    	if (page == null) {
    		return;
    	}

    	imagePanel.setIcon(new ImageIcon(page.getPageImage()));
  	
		this.revalidate();
		this.repaint(); 		
    }     

    protected void onProjectOpen() {
        clear();
    }

    protected void onProjectClose() {
        clear();
    }
    
    /** Clears up the pane. */
    @Override
    public void clear() {
        undoManager.discardAllEdits();
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

	@Override
	public String getNoteText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNoteText(String note) {
		// TODO Auto-generated method stub
		
	}
}
