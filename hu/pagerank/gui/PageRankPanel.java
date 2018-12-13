package hu.pagerank.gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hu.pagerank.other.Matrix;
import hu.pagerank.other.PageRankMain;

/**
 * <h1>PageRankPanel</h1>
 * Van benne egy card layout és egy gomb amivel a page rank vektort lehet rendezni
 *
 * @author Gáspár Tamás
 */

public class PageRankPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String RENDEZETT = "r";
	private static final String NEMRENDEZETT = "nr";
	
	private boolean rendezett;
	private JPanel nrPageRankPanel;
	private JPanel rPageRankPanel;
	
	public PageRankPanel(Matrix pageRank) {
		
		if(pageRank.M != 1) throw new IllegalArgumentException();
		
		rendezett = false;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JPanel pageRankTarto = new JPanel();
		pageRankTarto.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		CardLayout pageRankCards = new CardLayout();
		pageRankTarto.setLayout(pageRankCards);
		
		nrPageRankPanel = prpKeszito(pageRank,false);
		pageRankTarto.add(nrPageRankPanel,NEMRENDEZETT);
		rPageRankPanel = prpKeszito(pageRank,true);
		pageRankTarto.add(rPageRankPanel,RENDEZETT);
		add(pageRankTarto);
		
		JButton valtoGomb = new JButton("Rendezés fontosság szerint");
		valtoGomb.setAlignmentX(JButton.CENTER_ALIGNMENT);
		valtoGomb.setFont(PageRankMain.APPFONT);
		valtoGomb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!rendezett) {
					pageRankCards.show(pageRankTarto, RENDEZETT);
					valtoGomb.setText("Rendezés sorszám szerint");
				} else {
					pageRankCards.show(pageRankTarto, NEMRENDEZETT);
					valtoGomb.setText("Rendezés fontosság szerint");
				}
				rendezett = !rendezett;
				
			}
			
		});
		add(valtoGomb);
		
	}
	
	private JPanel prpKeszito(Matrix pageRank, boolean rendezett) {
		JPanel prpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		
		SorszamAdat[] saTomb = new SorszamAdat[pageRank.N];
		for(int i=0; i<saTomb.length; i++) {
			saTomb[i] = new SorszamAdat(i+1, pageRank.data[0][i]);
		}
		if(rendezett) Arrays.sort(saTomb);
		
		for(SorszamAdat sa: saTomb) {
			prpPanel.add(new SorszamAdatPanel(sa));
		}
		
		return prpPanel;
	}
	
	class SorszamAdatPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private SorszamAdatPanel(SorszamAdat sa) {
			
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			
			JLabel sorszamMutato = new JLabel(sa.sorszam + ".");
			sorszamMutato.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			add(sorszamMutato);
			
			add(new JLabel(" "));
			
			JLabel adatMutato = new JLabel(sa.adat + "");
			adatMutato.setFont(PageRankMain.APPFONT);
			adatMutato.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			add(adatMutato);
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SorszamAdat implements Comparable {
		
		private int sorszam;
		private double adat;
		
		private SorszamAdat(int sorszam,double adat) {
			this.sorszam = sorszam;
			this.adat = adat;
		}

		@Override
		public int compareTo(Object o) { //CSÖKKENÕ SORRENDBE RENDEZ
			
			if( !(o instanceof SorszamAdat) ) throw new IllegalArgumentException();
			
			SorszamAdat other = (SorszamAdat)o;
			if(this.adat > other.adat) return -1;
			if(this.adat < other.adat) return 1;
			
			return 0;
		}
	}
}
