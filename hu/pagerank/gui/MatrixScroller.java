package hu.pagerank.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import hu.pagerank.other.Matrix;

/**
 * <h1>MatrixScroller</h1>
 * Nagy m�trixokat jelen�t meg kis helyen.
 *
 * @author G�sp�r Tam�s
 */
public class MatrixScroller extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private PageRankPanel insidePanel;
	
	public MatrixScroller(Matrix matrix){
		
		if(matrix.M == 1) { //ekkor pageRank
			
			PageRankPanel prPanel = new PageRankPanel(matrix);
			JScrollPane scroller = new JScrollPane(prPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroller.setPreferredSize(new Dimension(700,100));
			add(scroller);
			insidePanel = prPanel;
			
		} else { //ekkor NxN m�trix
		
			MatrixPanel mPanel = new MatrixPanel(matrix);
			JScrollPane scroller = new JScrollPane(mPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroller.setPreferredSize(new Dimension(700,500));
			add(scroller);
			insidePanel = null;
			
		}
	}

	public PageRankPanel getInsidePanel() {
		return insidePanel;
	}
	
}
