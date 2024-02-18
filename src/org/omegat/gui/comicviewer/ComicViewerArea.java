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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.undo.UndoManager;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.ComicPage;
import org.omegat.core.data.ComicTextBlock;
import org.omegat.core.data.SourceTextEntry;
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
public class ComicViewerArea extends JPanel implements IComicViewer, IPaneMenu, MouseListener {

	UndoManager undoManager;
	private DockableScrollPane scrollPane;

	JLabel imagePanel = null;

	private int currentPageIndex = -1;

	enum NavigateComicPage {
		FIRST_PAGE, PREVIOUS_PAGE, NEXT_PAGE, LAST_PAGE, CURRENT_SEGMENT
	};

	/** Creates new Comic Viewer Area Pane */
	public ComicViewerArea(IMainWindow mw){
		super(true);

		String title = OStrings.getString("GUI_COMICVIEWERWINDOW_SUBWINDOWTITLE_ComicViewer");
		scrollPane = new DockableScrollPane("COMICVIEWER", title, this, true);
		mw.addDockable(scrollPane);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.WHITE);

		scrollPane.setColumnHeaderView(createToolBar());

		imagePanel = new JLabel();

		this.add(imagePanel);

		setMinimumSize(new Dimension(100, 50));

		addMouseListener(this);
		
		CoreEvents.registerEntryEventListener(new IEntryEventListener() {

			@Override
			public void onEntryActivated(SourceTextEntry newEntry) {

				try {

					showComicPage(NavigateComicPage.CURRENT_SEGMENT);

				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).log(Level.INFO, e.getMessage());
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

		JButton firstPage = new JButton(new String("First"));
		JButton previousPage = new JButton(new String("Previous"));
		JButton nextPage = new JButton(new String("Next"));
		JButton lastPage = new JButton(new String("Last"));

		firstPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.FIRST_PAGE);
				gotoEntry(getFirstEntryIDOfCurrentPage());
			}
		});

		previousPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.PREVIOUS_PAGE);
				gotoEntry(getFirstEntryIDOfCurrentPage());
			}
		});

		nextPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.NEXT_PAGE);
				gotoEntry(getFirstEntryIDOfCurrentPage());
			}
		});

		lastPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showComicPage(NavigateComicPage.LAST_PAGE);
				gotoEntry(getFirstEntryIDOfCurrentPage());
			}
		});

		toolbar.add(firstPage);
		toolbar.add(previousPage);
		toolbar.add(nextPage);
		toolbar.add(lastPage);

		toolbar.addSeparator(new Dimension(5, 10));

		toolbar.setBorder(new LineBorder(Color.GRAY, 1));

		return toolbar;
	}

	protected void gotoEntry(String entryID) {

		for (SourceTextEntry entry : Core.getProject().getAllEntries()) {
			if (entry.getKey().id.equals(entryID)) {
				Core.getEditor().gotoEntry(entry.getSrcText(), entry.getKey());
			}
		}

	}

	private String getFirstEntryIDOfCurrentPage() {
		return Core.getProject().getActiveComic().getPage(currentPageIndex).getTextBlocks().get(0)
				.getEntryID();
	}

	protected void showComicPage(NavigateComicPage move) {

		switch (move) {
		case FIRST_PAGE:
			currentPageIndex = 0;
			break;
		case PREVIOUS_PAGE:
			currentPageIndex = currentPageIndex > 0 ? currentPageIndex - 1 : 0;
			break;
		case NEXT_PAGE:
			int lastPage = Core.getProject().getActiveComic().getPages().size() - 1;
			currentPageIndex = currentPageIndex < lastPage ? currentPageIndex + 1 : lastPage;
			break;
		case LAST_PAGE:
			currentPageIndex = Core.getProject().getActiveComic().getPages().size() - 1;
			break;
		case CURRENT_SEGMENT:
			String entryID = Core.getEditor().getCurrentEntry().getKey().id;
			ComicPage page = Core.getProject().getActiveComic().getPageByTokenId(entryID);
			currentPageIndex = Core.getProject().getActiveComic().getPages().indexOf(page);
			break;
		}

		ComicPage page = Core.getProject().getActiveComic().getPage(currentPageIndex);

		if (page == null) {
			return;
		}

		Image image = page.getPageImage();

		for (ComicTextBlock textBlock : page.getTextBlocks()) {
			image = drawTextBlock(image, textBlock);
		}
		
		imagePanel.setIcon(new ImageIcon(image));

		this.revalidate();
		this.repaint();
	}

	public Image drawTextBlock(Image img, ComicTextBlock textBlock) {

		BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		// Draw original image onto the buffered image
		g2d.drawImage(img, 0, 0, null);
		
		g2d.setXORMode(Color.WHITE);
		
		if (Core.getEditor().getCurrentEntry().getKey().id.equals(textBlock.getEntryID())) {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(8)); // Adjust thickness as needed
		} else {
			g2d.setColor(Color.YELLOW);
			g2d.setStroke(new BasicStroke(2)); // Adjust thickness as needed
		}

		Rectangle rect = textBlock.getRectangle();

		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
		g2d.drawRect(rect.x, rect.y, rect.width, rect.height);

		g2d.dispose(); // Release graphics context resources

		return bufferedImage;
	}
	
	protected void onProjectOpen() {
		clear();
	}

	protected void onProjectClose() {
		clear();
	}

	/** Clears up the pane. */
	@Override
	public void clear() {
		undoManager.discardAllEdits();
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

	@Override
	public String getNoteText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNoteText(String note) {
		// TODO Auto-generated method stub

	}

    private static boolean isInsideRectangle(int x, int y, Rectangle rect) {
//    (int x1, int y1, int x2, int y2, int x, int y) {
        return x >= rect.x && x <= rect.x + rect.width && y >= rect.y && y <= rect.y + rect.height;
    }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		for (ComicTextBlock textBlock : Core.getProject().getActiveComic().getPage(currentPageIndex).getTextBlocks()) {
			if (isInsideRectangle(x, y, textBlock.getRectangle())) {
				gotoEntry(textBlock.getEntryID());
			}		
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
