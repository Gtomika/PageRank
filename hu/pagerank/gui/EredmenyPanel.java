package hu.pagerank.gui;

import java.awt.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hu.pagerank.other.Matrix;
import hu.pagerank.other.PageRankMain;

/**
 * EredmenyPanel
 * Panel ami megjeleníti a page rank számolás eredményét is idejét
 * 
 * @author Gáspár Tamás
 */

public class EredmenyPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public EredmenyPanel(Matrix googleMatrix,Matrix pageRank,double szamolasiIdo,int elvegzettIteraciok) {
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//számolási idõ kerekítése
		szamolasiIdo = BigDecimal.valueOf(szamolasiIdo).setScale(3, RoundingMode.HALF_UP).doubleValue();
		
		JLabel label1 = new JLabel("<html><body>- A számolási idõ kb. "+szamolasiIdo+" másodperc volt <br>- Elvégzett iterációk száma: "+elvegzettIteraciok +"<br> A PageRank vektor:</body></html>");
		label1.setFont(PageRankMain.APPFONT);
		label1.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(label1);
		
		JLabel label2 = new JLabel(" ");
		label2.setFont(PageRankMain.APPFONT);
		add(label2);
		
		if(PageRankMain.size<=PageRankMain.MAXNOSCROLLSIZE) {
			PageRankPanel p = new PageRankPanel(pageRank.transpose());
			p.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(p);
			p.getValtoGomb().setAlignmentX(Component.LEFT_ALIGNMENT);
			add(p.getValtoGomb());
			
		} else if(PageRankMain.size<=PageRankMain.MAXDISPLAYSIZE) {
			MatrixScroller ms = new MatrixScroller(pageRank.transpose());
			ms.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(ms);
			ms.getInsidePanel().getValtoGomb().setAlignmentX(Component.LEFT_ALIGNMENT);
			add(ms.getInsidePanel().getValtoGomb());
		} else {
			JLabel infoLabel = new JLabel("<html><body>A mátrix túl nagy ahhoz, hogy meg lehessen jeleníteni!<br>(több mint 150*150)</body></html>");
			infoLabel.setFont(PageRankMain.APPFONT);
			infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(infoLabel);
		}
		
		JLabel label3 = new JLabel(" ");
		label3.setFont(PageRankMain.APPFONT);
		add(label3);
	}
	
}
