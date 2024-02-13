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
    
	private ComicBook activeComic = null;

    /** Creates new Comic Viewer Area Pane */
    public ComicViewerArea(IMainWindow mw) {
        super(true);
        
        String title = OStrings.getString("GUI_COMICVIEWERWINDOW_SUBWINDOWTITLE_ComicViewer");
        scrollPane = new DockableScrollPane("COMICVIEWER", title, this, true);
        mw.addDockable(scrollPane);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        this.setBackground(Color.WHITE);

        scrollPane.setColumnHeaderView(createToolBar());   
       
        setMinimumSize(new Dimension(100, 50));
        
        CoreEvents.registerApplicationEventListener(new IApplicationEventListener() {
			
			@Override
			public void onApplicationStartup() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onApplicationShutdown() {
				// TODO Auto-generated method stub
			}
		});
        
        CoreEvents.registerProjectChangeListener(new IProjectEventListener() {
			
			@Override
			public void onProjectChanged(PROJECT_CHANGE_TYPE eventType) {
							
				if ( eventType.equals(PROJECT_CHANGE_TYPE.LOAD)) {
					
					for( int i=0; i < Core.getProject().getProjectFiles().size(); i++) {
						
						String comicFileName = Core.getProject().getProjectProperties().getSourceRoot() + 
								Core.getProject().getProjectFiles().get(i).filePath;
						
						if (comicFileName.toLowerCase().endsWith(".cbz")) {						
//							activeComic = new ComicBook(comicFileName);
						}
					}
				}
				
			}
		});
        
        CoreEvents.registerEntryEventListener(new IEntryEventListener() {
           
            @Override
            public void onEntryActivated(SourceTextEntry newEntry) {
//            	SegmentProperties props = getSegmentProperties(newEntry.getRawProperties());
            	
            	try {
            		//ImageIcon page = loadComicPage(props); 
            		
//            		ImageIcon page = new ImageIcon( activeComic.getPage(0 ));
//            		
//            		showComicPage(page);
            		
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
		
		//toolbar.setSize(new Dimension( 300, 100));
        
		JButton firstPage = new JButton(new String("First"));
		JButton previousPage = new JButton(new String("Previous"));
		JButton nextPage = new JButton(new String("Next"));
		JButton lastPage = new JButton(new String("Last"));
		
		firstPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JOptionPane.showMessageDialog(null, "First");
			}
		});

		previousPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JOptionPane.showMessageDialog(null, "Previous");
			}
		});
		
		nextPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JOptionPane.showMessageDialog(null, "Next");
			}
		});
		
		lastPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JOptionPane.showMessageDialog(null, "Last");
			}
		});
		
        toolbar.add(firstPage);
        toolbar.add(previousPage);
        toolbar.add(nextPage);
        toolbar.add(lastPage); 

        toolbar.addSeparator(new Dimension(5,10));
        
//        JButton first = new JButton(new String( "A"));
//        	        
//        first.setIcon(UIManager.getIcon("OmegaT.ComicViewArea.first.icon"));
//        
////        first.setSize(new Dimension( 150,150));
//        first.setBorderPainted(true);
//        
//        toolbar.add(first);
//        toolbar.add(new JButton(new String( "B"), new ImageIcon("comicviewarea.previous.ico")));
//        toolbar.add(new JButton(new String( "C" ), new ImageIcon("org.omegat.gui.resources.comicviewarea.next.ico")));
//        toolbar.add(new JButton(new String( "D") )); // , new ImageIcon("/org/omegat/gui/resources/comicviewarea.last.ico"))); 
        
        toolbar.setBorder(new LineBorder(Color.GRAY, 1));
        
        return toolbar;
	}
	
    protected void showComicPage(ImageIcon page) {
    	
    	if (page == null) {
    		return;
    	}
    	
		int cc = this.getComponentCount();
		
		for( int i=0; i<cc ;i++){
			this.remove(0);			
		}

//        this.add(new JLabel(UIManager.getIcon("OmegaT.ComicViewArea.first.icon")));
		this.add(new JLabel(page));
   	
		this.revalidate();
		this.repaint(); 

    }     
    
//    protected ImageIcon loadComicPage(SegmentProperties props) throws IOException{
//
//        String fileToBeExtracted=props.getPageName();
//        String zipPackage=props.getFileName();        
//        OutputStream out = new FileOutputStream(fileToBeExtracted);
//        FileInputStream fileInputStream = new FileInputStream(zipPackage);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream );
//        ZipInputStream zin = new ZipInputStream(bufferedInputStream);
//        ZipEntry ze = null;
//        while ((ze = zin.getNextEntry()) != null) {
//            if (ze.getName().equals(fileToBeExtracted)) {
//                byte[] buffer = new byte[9000];
//                int len;
//                while ((len = zin.read(buffer)) != -1) {
//                    out.write(buffer, 0, len);
//                }
//                out.close();
//                break;
//            }
//        }
//        zin.close();
//		
//        BufferedImage imagem = ImageIO.read(new File(fileToBeExtracted));
//        ImageIcon page = new ImageIcon(imagem); 
//        
//        return page;
//        
//	}
    
    
//	protected SegmentProperties getSegmentProperties(String[] rawProperties) {
//		
//    	SegmentProperties props = new SegmentProperties();
//    	
//    	props.setFileName(Core.getProject().getProjectProperties().getSourceRoot() + 
//    					Core.getProject().getProjectFiles().get(0).filePath);
//    	props.setPageName(rawProperties[1]);
//    	
//    	String position[] = rawProperties[9].split(",");
//    	
//        try {
//            props.setX( Integer.valueOf(position[0]));
//            props.setY( Integer.valueOf(position[1]));
//            props.setHeight( Integer.valueOf(position[2]));
//            props.setWidth( Integer.valueOf(position[3]));
//            
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid integer input");
//        }  	
//    	    	
//		return props;
//	}

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
