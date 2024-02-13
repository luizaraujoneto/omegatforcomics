package org.omegat.core.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;


public class ComicBook {

	private List<ComicPage> pages = null;
	private String comicFileName = null;
	private String comicFilePath = null;
	
	public ComicBook(File comicBook){
		
		this.pages = new ArrayList<ComicPage>();
		this.comicFileName = comicBook.getName();
		this.comicFilePath = comicBook.getAbsolutePath();
		
//		try {
//			loadComicPages();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
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
		
//            if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".jpg")) {
//                this.pages.add(ImageIO.read(zipInputStream));
//                this.pageNames.add(entry.getName());
//            }
//            zipInputStream.closeEntry();
        	
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


	public List<ComicPage> getPages() {
		return pages;
	}
	
	public ComicPage getPage(int i) {
		
		if (this.pages == null || i < 0 || i > this.pages.size()) {
			return null;
		};
				
		return this.pages.get(i);
	}
	
	public String getComicFileName() {
		return this.comicFileName;
	}

	public String getComicFilePath() {
		return this.comicFilePath;
	}
	
	
}



	   

