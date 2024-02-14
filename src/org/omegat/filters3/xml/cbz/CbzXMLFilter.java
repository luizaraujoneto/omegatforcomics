/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
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

package org.omegat.filters3.xml.cbz;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.omegat.core.Core;
import org.omegat.core.data.ComicPage;
import org.omegat.core.data.ComicTextBlock;
import org.omegat.core.data.ProtectedPart;
import org.omegat.filters2.Instance;
import org.omegat.filters3.xml.XMLFilter;
import org.omegat.util.StringUtil;
import org.xml.sax.Attributes;

/**
 * Filter for OpenDocument XML files that are inside there the OpenDocument file
 * (which is actually a ZIP file).
 *
 * @author Luiz Araujo (luizaraujoneto@gmail.com)
 */
public class CbzXMLFilter extends XMLFilter {
	
    private ArrayList<String> entryText = new ArrayList<String>();
    private ArrayList<List<ProtectedPart>> protectedParts = new ArrayList<List<ProtectedPart>>();

    private ComicTextBlock textBlock = null;
	private ComicPage page;
    
    /** Creates a new instance of CbzXMLFilter */
    public CbzXMLFilter() {
        super(new CbzDialect());
    }

    public Instance[] getDefaultInstances() {
        return new Instance[] { new Instance("Transcription.xml", StandardCharsets.UTF_8.name(), StandardCharsets.UTF_8.name()), };
    }

    public String getFileFormatName() {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void tagStart(String path, Attributes atts) {

    	if (atts != null && path.endsWith("ComicPage")) {
    		
    		page = Core.getProject().getActiveComic().getPage(atts.getValue("PageName"));
    		
    		page.setPageOrder(atts.getValue("PageOrder"));
    		page.setPageType(atts.getValue("PageType"));
    	}
    	
    	if (atts != null && path.endsWith("TextBlock")) {
    	
    		textBlock = new ComicTextBlock(page);
    		
    		textBlock.setEntryID(atts.getValue("entryID"));
    		textBlock.setReadingOrder(Integer.valueOf( atts.getValue("ReadingOrder")));
    		
    		String[] position = atts.getValue("Position").split(",");
    		int x = Integer.valueOf(position[0]);
    		int y = Integer.valueOf(position[1]);
    		int width = Integer.valueOf(position[2]);
    		int height = Integer.valueOf(position[3]);
    		
    		textBlock.setX(x);
    		textBlock.setY(y);
    		textBlock.setWidth(width);
    		textBlock.setHeight(height);
    		
    		page.addTextBlock(textBlock);
    		
        } 

    }
    
    @Override
    public void tagEnd(String path) {
    	
    	if (path.endsWith("ComicPage")) {
        	                            	
            if (entryParseCallback != null) {
        
                for (int i = 0; i < entryText.size(); i++) {
                	
                	ComicTextBlock textBlock = page.getTextBlock(i); 
                	
                	String[] props = configureProperties(textBlock);
                	
                    entryParseCallback.addEntryWithProperties(textBlock.getEntryID(), entryText.get(i), null, false,
                            props, null, this, protectedParts.get(i));
                }
            }

            entryText.clear();
            protectedParts.clear();
        } 
    }    

    
    private String[] configureProperties(ComicTextBlock textBlock) {
		
    	ArrayList<String> props = new ArrayList<String>();
    	
    	props.add("PageName" );
    	props.add(textBlock.getComicPage().getPageName());
    	props.add("PageOrder" );
    	props.add(textBlock.getComicPage().getPageOrder());
    	props.add("PageType");
    	props.add(textBlock.getComicPage().getPageType());
    	
    	props.add("entryID");
    	props.add(textBlock.getEntryID());
    	props.add("ReadingOrder");
    	props.add(String.valueOf( textBlock.getReadingOrder()));
    	props.add("Position" );
    	props.add(String.format(" %d, %d, %d, %d", textBlock.getX(), textBlock.getY(), textBlock.getWidth(), textBlock.getHeight() ));	
    	
    	return props.toArray(new String[props.size()]);
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public String translate(String entry, List<ProtectedPart> protectedParts) {
        if (entryParseCallback != null) {
            if (!StringUtil.isEmpty(entry)) {
                entryText.add(entry);
                this.protectedParts.add(protectedParts);
            }
            return entry;
        } else if (entryTranslateCallback != null) {
            String translation = StringUtil.isEmpty(entry) ? entry
                    : entryTranslateCallback.getTranslation(null, entry, null);
            return translation != null ? translation : entry;
        } else {
            return entry;
        }
    }
    
}
