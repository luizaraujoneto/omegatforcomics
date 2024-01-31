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

    private TextBlock textBlock = null;
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
    		
    		page = new ComicPage();
    		
    		page.setPageName(atts.getValue("PageName"));
    		page.setPageOrder(atts.getValue("PageOrder"));
    		page.setPageType(atts.getValue("PageType"));
    	}
    	
    	if (atts != null && path.endsWith("TextBlock")) {
    	
    		textBlock = new TextBlock();
    		
    		textBlock.setReadingOrder( atts.getValue("ReadingOrder"));
    		textBlock.setPosition( atts.getValue("Position"));  
    		
    		page.addTextBlock(textBlock);
    		
        } 

    }
    
    @Override
    public void tagEnd(String path) {
    	
    	if (path.endsWith("ComicPage")) {
        	                            	
            if (entryParseCallback != null) {
        
                for (int i = 0; i < entryText.size(); i++) {
                	
                	String[] props = configureProperties(page, i);
                	
                    entryParseCallback.addEntryWithProperties(null, entryText.get(i), null, false,
                            props, null, this, protectedParts.get(i));
                }
            }

        	page.clearTextBlocks();
        	page = null;
            entryText.clear();
            protectedParts.clear();
        } 
    }    

    
    private String[] configureProperties(ComicPage page, int i) {
		
    	ArrayList<String> props = new ArrayList<String>();
    	
    	props.add("PageName" );
    	props.add(page.getPageName());
    	props.add("PageOrder" );
    	props.add(page.getPageOrder());
    	props.add("PageType");
    	props.add(page.getPageType());
    	
    	props.add("ReadingOrder");
    	props.add(page.getTextBlock(i).getReadingOrder());
    	props.add("Position" );
    	props.add(page.getTextBlock(i).getPosition());	
    	
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
        
    private class ComicPage{
    	
    	private String pageName = null;
    	private String pageOrder = null;
    	private String pageType = null;
    	
    	private ArrayList<TextBlock> textBlocks = new ArrayList<TextBlock>();
    	
		public void clearTextBlocks() {
			this.textBlocks.clear();			
		}
		public TextBlock getTextBlock(int i) {
			return textBlocks.get(i);
		}
		public String getPageName() {
			return pageName;
		}		
		public void setPageName(String pageName) {
			this.pageName = new String( pageName );
		}
		public String getPageOrder() {
			return pageOrder;
		}
		public void setPageOrder(String pageOrder) {
			this.pageOrder = new String(  pageOrder );
		}
		public String getPageType() {
			return pageType;
		}
		public void setPageType(String pageType) {
			this.pageType = new String( pageType );
		}
		
		public void addTextBlock(TextBlock textBlock) {
			this.textBlocks.add(textBlock);
		}
    }
 	   
	private class TextBlock{

		private String readingOrder = null;
    	private String position = null;
        	  	
    	public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = new String( position );
		}

    	public String getReadingOrder() {
    		return readingOrder;
    	}
  
		public void setReadingOrder(String readingOrder) {
			this.readingOrder = new String( readingOrder );
		}
    	
    }
    
}
