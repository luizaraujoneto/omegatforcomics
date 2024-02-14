package org.omegat.core.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ComicBook {

	private List<ComicPage> pages = null;
	private String comicFileName = null;
	private String comicFilePath = null;
	
	public ComicBook(File comicBook){
		
		this.pages = new ArrayList<ComicPage>();
		this.comicFileName = comicBook.getName();
		this.comicFilePath = comicBook.getAbsolutePath();
				
	}

	public List<ComicPage> getPages() {
		return pages;
	}
	
	public ComicPage getPage(int i) {
		
		if (this.pages == null || i < 0 || i > this.pages.size()) {
			return null;
		};
				
		return this.pages.get(i);
	}

	public ComicPage getPage(String value) {
		for (ComicPage page : getPages()) {
			if (page.getPageName().equals(value )) {
				return page;
			}			
		}		
		return null;
	}

	public ComicPage getPageByTokenId(String entryID) {
		for (ComicPage page : getPages()) {
			for (ComicTextBlock textBlock : page.getTextBlocks()) {
				if (textBlock.getEntryID().equals(entryID)) {
					return page;
				}			
			}
		}		
		return null;
	}
	
	public String getComicFileName() {
		return this.comicFileName;
	}

	public String getComicFilePath() {
		return this.comicFilePath;
	}
	
}



	   

