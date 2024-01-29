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
 * @author Maxym Mykhalchuk
 */
public class CbzXMLFilter extends XMLFilter {
	
    private ArrayList<String> props = new ArrayList<String>();
    private StringBuilder text = new StringBuilder();
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
        } 

    }
    
    @Override
    public void tagEnd(String path) {
    	
    	if (path.endsWith("ComicPage/TextBlock")) {
        	textBlock.setText(text.toString());
      
        	addProperty("PageName", page.getPageName());
        	addProperty("PageOrder", page.getPageOrder());
        	addProperty("PageType", page.getPageType());
        	
        	addProperty("ReadingOrder", textBlock.getReadingOrder());
        	addProperty("Position", textBlock.getPosition());        	
        	
        } if (path.endsWith("ComicPage")) {
        	 if (entryParseCallback != null) {
        
                for (int i = 0; i < entryText.size(); i++) {
                    entryParseCallback.addEntryWithProperties(null, entryText.get(i), null, false,
                            finalizeProperties(), null, this, protectedParts.get(i));
                }
            }

        	page = null;
        	props.clear();
            entryText.clear();
            protectedParts.clear();
        } 
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
    
    
    private void addProperty(String key, String value) {
        props.add(key);
        props.add(value);
    }

    private String[] finalizeProperties() {
        if (props.isEmpty()) {
            return null;
        }
        return props.toArray(new String[props.size()]);
    }

    
 
    @SuppressWarnings("unused")
	private class ComicPage{
    	
    	private String pageName = null;
    	private String pageOrder = null;
    	private String pageType = null;
    	
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

    	
    	
    	
    	
    }
 	
    
    @SuppressWarnings("unused")
	private class TextBlock{

		private String readingOrder = null;
    	private String position = null;
    	private String text = null;
        	  	
    	public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = new String( position );
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = new String( text );
		}

    	public String getReadingOrder() {
    		return readingOrder;
    	}
  
		public void setReadingOrder(String readingOrder) {
			this.readingOrder = new String( readingOrder );
		}
    	
    }
    
}
