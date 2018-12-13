package hu.pagerank.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hu.pagerank.other.EredmenyFileWriter;
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
		
		if(PageRankMain.size<=PageRankMain.MAXDISPLAYSIZE) {
			PageRankPanel p = new PageRankPanel(pageRank.transpose());
			p.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(p);
		} else {
			JLabel infoLabel = new JLabel("<html><body>Az eredményvektor túl nagy ahhoz hogy itt megjelenjen!<br> (de az output fájlba bekerül)</body></html>");
			infoLabel.setFont(PageRankMain.APPFONT);
			infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(infoLabel);
		}
		
		JLabel label3 = new JLabel(" ");
		label3.setFont(PageRankMain.APPFONT);
		add(label3);
		
		if(PageRankMain.size<PageRankMain.MAXDISPLAYSIZE) {
			JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
			
			JLabel fileLabel = new JLabel("Kis méretû eredmény, nem került automatikusan fájlba.");
			fileLabel.setFont(PageRankMain.APPFONT);
			filePanel.add(fileLabel);
			
			JButton fileButton = new JButton("Írd fájlba!");
			final double szIdo = szamolasiIdo;
			fileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						new EredmenyFileWriter().fajlbaIr(googleMatrix, pageRank, szIdo);
						JOptionPane.showMessageDialog(PageRankMain.frame, "Az output fájl neve: eredmeny_"+PageRankMain.logID+".txt", "Fájlba írva", JOptionPane.PLAIN_MESSAGE);
						PageRankMain.logID = ThreadLocalRandom.current().nextInt(10000);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(PageRankMain.frame, "Sikertelen fájlba írás!", "Hiba", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			});
			filePanel.add(fileButton);
			
			filePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(filePanel);
		} else {
			if(!InputPanel.isUpdated) {
				try {
					new EredmenyFileWriter().fajlbaIr(googleMatrix, pageRank, szamolasiIdo);
					JLabel label4 = new JLabel("A generált output fájl neve: eredmeny_"+PageRankMain.logID+".txt");
					label4.setFont(PageRankMain.APPFONT);
					label4.setAlignmentX(Component.LEFT_ALIGNMENT);
					add(label4);
					PageRankMain.logID = ThreadLocalRandom.current().nextInt(10000);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(PageRankMain.frame, "Sikertelen fájlba írás!", "Hiba", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JLabel label5 = new JLabel("A generált output fájl neve: eredmeny_"+PageRankMain.logID+".txt");
				label5.setFont(PageRankMain.APPFONT);
				label5.setAlignmentX(Component.LEFT_ALIGNMENT);
				add(label5);
			}
		}
	}
	
}
