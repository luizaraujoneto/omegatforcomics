package org.omegat.core.data;

public class ComicTextBlock{

	private String entryID = null;
	private ComicPage comicPage = null;
	private int readingOrder;
	private int x, y, width, height;
	
	public ComicTextBlock(ComicPage page) {
		this.comicPage = page;
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
	public String getEntryID() {
		return entryID;
	}
	public void setEntryID(String entryID) {
		this.entryID = entryID;
	}
	
	public ComicPage getComicPage() {
		return comicPage;
	}
	public int getReadingOrder() {
		return readingOrder;
	}
	public void setReadingOrder(int readingOrder) {
		this.readingOrder = readingOrder;
	}
	
}
