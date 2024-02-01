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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.undo.UndoManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IApplicationEventListener;
import org.omegat.core.events.IEditorEventListener;
import org.omegat.core.events.IEntryEventListener;
import org.omegat.filters2.FilterContext;
import org.omegat.filters2.TranslationException;
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
	
	private static final Pattern DIGITS = Pattern.compile("(\\d+)\\.jpg");
    private String documents;

    private static final String EXPLANATION = new String( "Comic Viewer Area" );
    
    private static String comicFileName = null;

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
            	comicFileName = new String(activeFileName);
            	System.out.println( "Event ComicViewer::OnNewFile" + activeFileName);
            }

            @Override
            public void onEntryActivated(SourceTextEntry newEntry) {
            	SegmentProperties props = getSegmentProperties(newEntry.getRawProperties());
            	
            	updateComicPage(props);
                        	
            	System.out.println( "Event ComicViewer::onEntryActivated " + newEntry.getRawProperties().toString());
            	System.out.println( "Event ComicViewer::onEntryActivated " + props);
            	
            }

        });
    }

    protected void updateComicPage(SegmentProperties props)  throws IOException{

    	File inFile = new File(props.getFileName());
    	
    	try (ZipFile zipfile = new ZipFile(inFile)) {
    	    Enumeration<? extends ZipEntry> unsortedZipcontents = zipfile.entries();
    	    List<? extends ZipEntry> filelist = Collections.list(unsortedZipcontents);
    	    Collections.sort(filelist, this::compareZipEntries);

    	    for (ZipEntry zipentry : filelist) {
    	        String shortname = removePath(zipentry.getName());
    	    
    	    
    	}
		
	}

    /**
     * @param fileName A filename with a path
     * @return A string without the path
     */
    private static String removePath(String fileName) {
        if (fileName.lastIndexOf('/') >= 0) {
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        }  else if (fileName.lastIndexOf('\\') >= 0) { // Some weird files may use a backslash
            fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
        }
        return fileName;
    }

    /**
     * @param fileName A filename
     * @return The filename without an .xml extension if found in it
     */
    private static String removeXML(String fileName) {
        if (fileName.endsWith(".xml")) {
            fileName = fileName.substring(0, fileName.lastIndexOf(".xml"));
        }
        return fileName;
    }
    
    public int compareZipEntries(ZipEntry z1, ZipEntry z2) {
    
    	
        String s1 = z1.getName();
        String s2 = z2.getName();
        
//        return s1.compareTo(s2);
//    }
    
        String[] words1 = s1.split("\\d+\\.");
        String[] words2 = s2.split("\\d+\\.");
        // Digits at the end and same text
        if ((words1.length > 1 && words2.length > 1) && // Digits
                (words1[0].equals(words2[0]))) { // Same text
            int number1 = 0;
            int number2 = 0;
            Matcher getDigits = DIGITS.matcher(s1);
            if (getDigits.find()) {
                number1 = Integer.parseInt(getDigits.group(1));
            }
            getDigits = DIGITS.matcher(s2);
            if (getDigits.find()) {
                number2 = Integer.parseInt(getDigits.group(1));
            }
            if (number1 > number2) {
                return 1;
            } else if (number1 < number2) {
                return -1;
            } else {
                return 0;
            }
        } else {
            String shortname1 = removePath(words1[0]);
            shortname1 = removeXML(shortname1);
            String shortname2 = removePath(words2[0]);
            shortname2 = removeXML(shortname2);

            // Specific case for Excel
            // because "comments" is present twice in DOCUMENTS
            if (shortname1.indexOf("sharedStrings") >= 0 || shortname2.indexOf("sharedStrings") >= 0) {
                if (shortname2.indexOf("sharedStrings") >= 0) {
                    return 1; // sharedStrings must be first
                } else {
                    return -1;
                }
            }

            int index1 = documents.indexOf(shortname1);
            int index2 = documents.indexOf(shortname2);

            if (index1 > index2) {
                return 1;
            } else if (index1 < index2) {
                return -1;
            } else { // Documents were not in DOCUMENTS, we keep the normal order
                return s1.compareTo(s2);
            }
        }
    }  

    
    
    public void processFile(File inFile, File outFile, FilterContext fc) throws IOException,
    TranslationException {

ZipOutputStream zipout = null;
try (ZipFile zipfile = new ZipFile(inFile)) {
    if (outFile != null) {
        zipout = new ZipOutputStream(new FileOutputStream(outFile));
    }
    Enumeration<? extends ZipEntry> unsortedZipcontents = zipfile.entries();
    List<? extends ZipEntry> filelist = Collections.list(unsortedZipcontents);
    // Sort filenames, because zipfile.entries give a random order
    // We use a simplified natural sort, to have slide1, slide2 ...
    // slide10
    // instead of slide1, slide10, slide 2
    // We also order files arbitrarily, to have, for instance
    // documents.xml before comments.xml
    Collections.sort(filelist, this::compareZipEntries);

    for (ZipEntry zipentry : filelist) {
        String shortname = removePath(zipentry.getName());
        if (translatable.matcher(shortname).matches()) {
            File tmpin = tmp();
            FileUtils.copyInputStreamToFile(zipfile.getInputStream(zipentry), tmpin);
            File tmpout = null;
            if (zipout != null) {
                tmpout = tmp();
            }
            try {
                createXMLFilter().processFile(tmpin, tmpout, fc);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
                throw new TranslationException(e.getLocalizedMessage() + "\n"
                        + OStrings.getString("OpenXML_ERROR_IN_FILE") + inFile, e);
            }

            if (zipout != null) {
                ZipEntry outEntry = new ZipEntry(zipentry.getName());
                zipout.putNextEntry(outEntry);
                FileUtils.copyFile(tmpout, zipout);
                zipout.closeEntry();
            }
            if (!tmpin.delete()) {
                tmpin.deleteOnExit();
            }
            if (tmpout != null && !tmpout.delete()) {
                tmpout.deleteOnExit();
            }
        } else {
            if (zipout != null) {
                ZipEntry outEntry = new ZipEntry(zipentry.getName());
                zipout.putNextEntry(outEntry);
                try (InputStream is = zipfile.getInputStream(zipentry)) {
                    IOUtils.copy(is, zipout);
                }
                zipout.closeEntry();
            }
        }
    }
} finally {
    if (zipout != null) {
        zipout.close();
    }
}
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
}
