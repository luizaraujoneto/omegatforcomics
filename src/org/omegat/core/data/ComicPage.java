package org.omegat.core.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ComicPage{
	
	private ComicBook comicBook = null;
	private String pageName = null;
	private String pageOrder = null;
	private String pageType = null;	
	private List<ComicTextBlock> textBlocks = null;
	private Image pageImage;
	
	public ComicPage( ComicBook cb ) {
		comicBook = cb;
		textBlocks = new ArrayList<ComicTextBlock>();
	}
	
	public void clearTextBlocks() {
		this.textBlocks.clear();			
	}
	public ComicTextBlock getTextBlock(int i) {
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
	
	public void addTextBlock(ComicTextBlock textBlock) {
		this.textBlocks.add(textBlock);
	}

	public ComicBook getComicBook() {
		return comicBook;
	}

	public Image getPageImage() {
		return pageImage;
	}
	
	public void setPageImage(Image image) {
		this.pageImage = image;
	}
}