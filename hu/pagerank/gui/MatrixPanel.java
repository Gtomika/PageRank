package hu.pagerank.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hu.pagerank.other.Matrix;
import hu.pagerank.other.PageRankMain;

/**
 * MatrixPanel
 * speci�lis JPanel ami egy m�trixot jelen�t meg a k�perny�n
 * 
 * @author G�sp�r Tam�s
 */

public class MatrixPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * MatrixPanel
	 * Konstruktor, ami egy olyan panelt hoz l�tre, ami megjelen�ti a kapott m�trixot
	 * 
	 * @param dimension a n�gyzetes m�trix dimenzi�ja
	 */
	
	public MatrixPanel(Matrix m, boolean isHighlighted) {
		super();
		
		setLayout(new BorderLayout());
		
		JPanel matrixContainer = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		//panel gener�l�sa
		for(int i=0;i<m.M;i++) {
			for(int j=0;j<m.N;j++) {
				
				gbc.gridx=i;
				gbc.gridy=j;
				
				JLabel dataLabel = new JLabel(m.data[i][j]+"");
				dataLabel.setFont(PageRankMain.APPFONT);
				
				if(m.data[i][j]==0) dataLabel.setText(0+"");
				if(isHighlighted && m.data[i][j]!=0) dataLabel.setForeground(Color.RED);
				
				matrixContainer.add(dataLabel,gbc);
				
			}
		}
		
		add(matrixContainer,BorderLayout.CENTER);
		
		JPanel seged1 = new JPanel();
		seged1.setPreferredSize(new Dimension(30,30));
		add(seged1,BorderLayout.LINE_START);
		
		JPanel seged2 = new JPanel();
		seged2.setPreferredSize(new Dimension(30,30));
		add(seged2,BorderLayout.LINE_END);
	}
	
}
