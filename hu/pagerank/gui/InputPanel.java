package hu.pagerank.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hu.pagerank.other.InvalidAlphaException;
import hu.pagerank.other.InvalidIterationNumberException;
import hu.pagerank.other.InvalidPossibilityException;
import hu.pagerank.other.InvalidSizeException;
import hu.pagerank.other.PageRankMain;

/**
 * DimensionInputPanel
 * Panel ami az alkalmazás elején megadandó dimenziót kéri be.
 * 
 * @author Gáspár Tamás
 */

public class InputPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public static final int MAXDIMENSION = 1000;
	public static boolean isUpdated;
	private static final int DEFAULTSIZE = 10;
	private static final double DEFAULTALPHA = 0.85;
	private static final int DEFAULTITERATION = 5;
	
	private JTextField sizeField;
	private JTextField possibilityField;
	private JTextField alphaField;
	private JTextField iterationField;
	
	/**
	 * DimensionInputPanel
	 * Konstruktor, létrehozza a panelt és az input field-et
	 */
	
	public InputPanel(boolean hasBeenCalledBefore) {
		super();
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JLabel label0 = new JLabel(" "); //elválasztó
		label0.setFont(PageRankMain.APPFONT);
		add(label0);
		
		JLabel label1 = new JLabel("- Add meg a mátrix méretét! (maximum "+MAXDIMENSION+")"); //méret
		label1.setFont(PageRankMain.APPFONT);
		add(label1);
		
		sizeField = new JTextField();
		if(hasBeenCalledBefore) {
			sizeField.setText(PageRankMain.size+"");
		} else {
			sizeField.setText(DEFAULTSIZE+"");
		}
		sizeField.setAlignmentX(CENTER_ALIGNMENT);
		sizeField.setPreferredSize(new Dimension(100,40));
		add(sizeField);
		
		JLabel label2 = new JLabel("<html><body>- FIGYELEM: Az alkalmazás a nagy mátrixokat, vektorokat <br> nem tudja megjeleníteni, de az eredményfájlba bekerülnek.</body></html>");
		label2.setFont(PageRankMain.APPFONT);
		add(label2);
		
		JLabel label3 = new JLabel(" "); //elválasztó
		label3.setFont(PageRankMain.APPFONT);
		add(label3);
		
		JLabel label4 = new JLabel("Add meg a linkelési valószínűséget! Pl: 0.1"); //linkelési valószínűség
		label4.setFont(PageRankMain.APPFONT);
		add(label4);
		
		possibilityField = new JTextField();
		possibilityField.setAlignmentX(CENTER_ALIGNMENT);
		if(hasBeenCalledBefore) {
			possibilityField.setText(PageRankMain.linkPossibility+"");
		}
		possibilityField.setPreferredSize(new Dimension(100,40));
		add(possibilityField);
		
		JLabel label5 = new JLabel("<html><body>- Linkelési valószínűség = P(i linkel j-re), ahol i,j oldalak és i≠j <br> (Alapértelmezett: 1/méret) </body></html>");
		label5.setFont(PageRankMain.APPFONT);
		add(label5);
		
		JLabel label6 = new JLabel(" "); //elválasztó
		label6.setFont(PageRankMain.APPFONT);
		add(label6);
		
		JLabel label7 = new JLabel("Add meg az α paramétert! Pl: 0.73"); //alfa
		label7.setFont(PageRankMain.APPFONT);
		add(label7);
		
		alphaField = new JTextField();
		alphaField.setAlignmentX(CENTER_ALIGNMENT);
		if(hasBeenCalledBefore) {
			alphaField.setText(PageRankMain.alpha+"");
		} else {
			alphaField.setText(DEFAULTALPHA+"");
		}
		alphaField.setPreferredSize(new Dimension(100,40));
		add(alphaField);
		
		JLabel label8 = new JLabel("<html><body>- α jelentése: A véletlen internetező mekkora valószínűséggel ugrik random oldalra <br> (ahelyett, hogy az adott oldalon lévő linkek szerint haladna tovább) <br> Alapértelmezett: 0.85</body></html>");
		label8.setFont(PageRankMain.APPFONT);
		add(label8);
		
		JLabel label9 = new JLabel(" "); //elválasztó
		label9.setFont(PageRankMain.APPFONT);
		add(label9);
		
		JLabel label10 = new JLabel("Add meg azt, hogy mennyi iterációt végezzen a PageRank számolásakor!"); //iteráció szám
		label10.setFont(PageRankMain.APPFONT);
		add(label10);
		
		iterationField = new JTextField();
		iterationField.setAlignmentX(CENTER_ALIGNMENT);
		if(hasBeenCalledBefore) {
			iterationField.setText(PageRankMain.iterationNumber+"");
		} else {
			iterationField.setText(DEFAULTITERATION+"");
		}
		iterationField.setPreferredSize(new Dimension(100,40));
		add(iterationField);
		
		JLabel label11 = new JLabel("<html><body>- FIGYELEM: alacsony iterációs szám esetén az eredmény nem közelíti a valódi PageRank vektort, <br>nagyon magas esetén sokáig tarthat a számolás! <br>Alapértelmezett érték: 5</body></html>");
		label11.setFont(PageRankMain.APPFONT);
		add(label11);
		
		JLabel label12 = new JLabel(" "); //elválasztó
		label12.setFont(PageRankMain.APPFONT);
		add(label12);
		
		if(hasBeenCalledBefore) {
			JLabel label13 = new JLabel("<html><body>A beállítások frissítésével azonnal generálódik egy új Google mátrix! </body></html>"); //elválasztó
			label13.setFont(PageRankMain.APPFONT);
			label13.setForeground(Color.RED);
			add(label13);
			
			JLabel label14 = new JLabel(" "); //elválasztó
			label14.setFont(PageRankMain.APPFONT);
			add(label14);
		}
	}
	
	public void validateInputs() throws InvalidSizeException, InvalidPossibilityException, InvalidAlphaException, InvalidIterationNumberException {
		
		int size = -1; //méret ellenőrzése
		try {
			size = Integer.parseInt(sizeField.getText());
		} catch(NumberFormatException exc) {
			throw new InvalidSizeException();
		}
		if(size<1 || size>MAXDIMENSION) throw new InvalidSizeException();
		PageRankMain.size = size;
		
		double possibility = -1; //valószínűség ellenőrzése
		try {
			possibility = Double.parseDouble(possibilityField.getText());
		} catch(NumberFormatException exc) {
			throw new InvalidPossibilityException();
		}
		if(possibility<0 || possibility>1) throw new InvalidPossibilityException();
		PageRankMain.linkPossibility = possibility;
		
		double alpha = -1; //alfa ellenőrzése
		try {
			alpha = Double.parseDouble(alphaField.getText());
		} catch(NumberFormatException exc) {
			throw new InvalidAlphaException();
		}
		if(alpha<0 || alpha>1) throw new InvalidAlphaException();
		PageRankMain.alpha = alpha;
		
		int iterationNumber = -1; //iterációs szám ellenőrzése
		try {
			iterationNumber = Integer.parseInt(iterationField.getText());
		} catch(NumberFormatException exc) {
			throw new InvalidIterationNumberException();
		}
		if(iterationNumber<1) throw new InvalidIterationNumberException();
		PageRankMain.iterationNumber = iterationNumber;

	}
	
	public JTextField getSizeField() {
		return sizeField;
	}

	public JTextField getPossibilityField() {
		return possibilityField;
	}

	public JTextField getAlphaField() {
		return alphaField;
	}
	
	public JTextField getIterationField() {
		return iterationField;
	}
	
}
