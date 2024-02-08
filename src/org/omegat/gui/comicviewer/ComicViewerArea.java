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
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IEditorEventListener;
import org.omegat.core.events.IEntryEventListener;
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

    /** Creates new Comic Viewer Area Pane */
    public ComicViewerArea(IMainWindow mw) {
        super(true);
        
        String title = OStrings.getString("GUI_COMICVIEWERWINDOW_SUBWINDOWTITLE_ComicViewer");
        scrollPane = new DockableScrollPane("COMICVIEWER", title, this, true);
        mw.addDockable(scrollPane);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        this.setBackground(Color.WHITE);

        scrollPane.setColumnHeaderView(createToolBar());        
                
        //showComicPage(null);
        
        setMinimumSize(new Dimension(100, 50));
        
        CoreEvents.registerEntryEventListener(new IEntryEventListener() {
           
            @Override
            public void onEntryActivated(SourceTextEntry newEntry) {
            	SegmentProperties props = getSegmentProperties(newEntry.getRawProperties());
            	
            	try {
            		ImageIcon page = loadComicPage(props); 
            		
            		showComicPage(page);
            		
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
        
        toolbar.add(new JButton(new String("F")));
        toolbar.add(new JButton(new String("P")));
        toolbar.add(new JButton(new String("N")));
        toolbar.add(new JButton(new String("L"))); 

        toolbar.addSeparator(new Dimension(5,10));
        
        toolbar.add(new JButton("A", new ImageIcon("/org/omegat/gui/resources/skip-start-fill.ico")));
        toolbar.add(new JButton("B", new ImageIcon("caret-left.ico")));
        toolbar.add(new JButton("C", new ImageIcon("org.omegat.gui.resources.caret-right.ico")));
        toolbar.add(new JButton("D", new ImageIcon("org/omegat/gui/resources/skip-end-fill.ico"))); 
       
        
        //toolbar.setSize(new Dimension(400, 30));        
        toolbar.setBorder(new LineBorder(Color.GRAY, 1));
        //toolbar.setBackground(Color.LIGHT_GRAY);
        
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

		this.add(new JLabel(page));
   	
		this.revalidate();
		this.repaint(); 

    }     
    
    protected ImageIcon loadComicPage(SegmentProperties props) throws IOException{

        String fileToBeExtracted=props.getPageName();
        String zipPackage=props.getFileName();        
        OutputStream out = new FileOutputStream(fileToBeExtracted);
        FileInputStream fileInputStream = new FileInputStream(zipPackage);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream );
        ZipInputStream zin = new ZipInputStream(bufferedInputStream);
        ZipEntry ze = null;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.getName().equals(fileToBeExtracted)) {
                byte[] buffer = new byte[9000];
                int len;
                while ((len = zin.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.close();
                break;
            }
        }
        zin.close();
		
        BufferedImage imagem = ImageIO.read(new File(fileToBeExtracted));
        ImageIcon page = new ImageIcon(imagem); 
        
        return page;
        
	}
    
    
	protected SegmentProperties getSegmentProperties(String[] rawProperties) {
		
    	SegmentProperties props = new SegmentProperties();
    	
    	props.setFileName(Core.getProject().getProjectProperties().getSourceRoot() + 
    					Core.getProject().getProjectFiles().get(0).filePath);
    	props.setPageName(rawProperties[1]);
    	
    	String position[] = rawProperties[9].split(",");
    	
        try {
            props.setX( Integer.valueOf(position[0]));
            props.setY( Integer.valueOf(position[1]));
            props.setHeight( Integer.valueOf(position[2]));
            props.setWidth( Integer.valueOf(position[3]));
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer input");
        }  	
    	    	
		return props;
	}

//	@Override
    protected void onProjectOpen() {
        clear();
    }

//    @Override
    protected void onProjectClose() {
        clear();
//        setText(EXPLANATION);
    }
    
    /** Clears up the pane. */
    @Override
    public void clear() {
//        super.clear();
//        setEditable(false);
        undoManager.discardAllEdits();
    }

//    public void setNoteText(String text) {
//        UIThreadsUtil.mustBeSwingThread();
//
//        if (Preferences.isPreference(Preferences.NOTIFY_NOTES)) {
//            if (StringUtil.isEmpty(text)) {
//                scrollPane.stopNotifying();
//            } else {
//                scrollPane.notify(true);
//            }
//        }
//        setText(text);
//        setEditable(true);
//    }
//
//    public String getNoteText() {
//        UIThreadsUtil.mustBeSwingThread();
//
//        String text = getText();
//        // Disallow empty note. Use null to indicate lack of note.
//        return text.isEmpty() ? null : text;
//    }

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
    
    
    private class SegmentProperties{
    	
    	private String fileName = null;
    	private String pageName = null;
    	private int pageOrder;
    	private String pageType = null;
    	private int readingOrder;
    	private int x, y, width, height;
    	
    	public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getPageName() {
			return pageName;
		}
		public void setPageName(String pageName) {
			this.pageName = pageName;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
    	
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
