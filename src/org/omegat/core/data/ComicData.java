package org.omegat.core.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ComicData {

	private List<Image> pages = null;
	private List<String> pageNames = null;
	private String comicFileName = null; 
	
	public ComicData(String comicFileName) {
		
		this.pages = new ArrayList<Image>();
		this.pageNames = new ArrayList<String>();
		this.comicFileName = comicFileName;
		
		try {
			loadComicPages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    protected void loadComicPages() throws IOException{

//        String zipPackage=comicFileName;        
//        OutputStream out = new FileOutputStream(this.comicFileName);
       
    	FileInputStream fileInputStream = new FileInputStream(this.comicFileName);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream );
        ZipInputStream zipInputStream = new ZipInputStream(bufferedInputStream);
        
        ZipEntry entry = null;    
        while ((entry = zipInputStream.getNextEntry()) != null) {
        	
//        	String fileName = entry.getName();
		
            if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".jpg")) {
                this.pages.add(ImageIO.read(zipInputStream));
                this.pageNames.add(entry.getName());
            }
            zipInputStream.closeEntry();
        	
//			if (ze.getName().endsWith(".jpg")) {
//                byte[] buffer = new byte[9000];
//                int len;
//                while ((len = zin.read(buffer)) != -1) {
//                    out.write(buffer, 0, len);
//                }
//                out.close();
//                break;
//            }
        }
        zipInputStream.close();
		
//        BufferedImage imagem = ImageIO.read(new File(fileToBeExtracted));
//        ImageIcon page = new ImageIcon(imagem); 
              
	}


	public List<Image> getPages() {
		return pages;
	}
	
	public Image getPage(int i) {
		
		if (this.pages == null || i < 0 || i > this.pages.size()) {
			return null;
		};
				
		return this.pages.get(i);
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
