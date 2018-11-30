package hu.pagerank.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import hu.pagerank.other.InvalidAlphaException;
import hu.pagerank.other.InvalidChangeException;
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
	public static boolean isUpdated;
	private static final String ITERACIOSZAM = "iterszam";
	private static final String HIBAHATAR = "hibahatar";
	
	private JTextField sizeField;
	private JTextField possibilityField;
	private JTextField alphaField;
	private JTextField iterationField;
	private JTextField changeMarginField;
	
	/**
	 * DimensionInputPanel
	 * Konstruktor, létrehozza a panelt és az input field-et
	 */
	
	public InputPanel(boolean hasBeenCalledBefore) {
		super();
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JLabel label0 = new JLabel(" "); //elválasztó
		label0.setFont(PageRankMain.APPFONT);
		label0.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label0);
		
		JLabel label1 = new JLabel("Add meg a mátrix méretét!"); //méret
		label1.setFont(PageRankMain.APPFONT);
		label1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label1);
		
		sizeField = new JTextField();
		sizeField.setText(PageRankMain.size+"");
		sizeField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		sizeField.setPreferredSize(new Dimension(100,40));
		add(sizeField);
		
		JLabel label2 = new JLabel("<html><body>FIGYELEM: Az alkalmazás a nagy mátrixokat, vektorokat <br> nem tudja megjeleníteni, de az eredményfájlba bekerülnek.</body></html>");
		label2.setFont(PageRankMain.APPFONT);
		label2.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label2);
		
		JLabel label3 = new JLabel(" "); //elválasztó
		label3.setFont(PageRankMain.APPFONT);
		label3.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label3);
		
		JLabel label4 = new JLabel("Add meg a linkelési valószínűséget! Pl: 0.1"); //linkelési valószínűség
		label4.setFont(PageRankMain.APPFONT);
		label4.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label4);
		
		possibilityField = new JTextField();
		possibilityField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		possibilityField.setText(PageRankMain.linkPossibility+"");
		possibilityField.setPreferredSize(new Dimension(100,40));
		add(possibilityField);
		
		JLabel label5 = new JLabel("<html><body>Linkelési valószínűség = P(i linkel j-re), ahol i,j oldalak és i≠j <br> (Alapértelmezett: 1/méret) </body></html>");
		label5.setFont(PageRankMain.APPFONT);
		label5.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label5);
		
		JLabel label6 = new JLabel(" "); //elválasztó
		label6.setFont(PageRankMain.APPFONT);
		label6.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label6);
		
		JLabel label7 = new JLabel("Add meg az α paramétert! Pl: 0.73"); //alfa
		label7.setFont(PageRankMain.APPFONT);
		label7.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label7);
		
		alphaField = new JTextField();
		alphaField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		alphaField.setText(PageRankMain.alpha+"");
		alphaField.setPreferredSize(new Dimension(100,40));
		add(alphaField);
		
		JLabel label8 = new JLabel("<html><body>α jelentése: A véletlen internetező mekkora valószínűséggel ugrik random oldalra <br> (ahelyett, hogy az adott oldalon lévő linkek szerint haladna tovább) <br> Alapértelmezett: 0.85</body></html>");
		label8.setFont(PageRankMain.APPFONT);
		label8.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label8);
		
		JLabel label9 = new JLabel(" "); //elválasztó
		label9.setFont(PageRankMain.APPFONT);
		label9.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(label9);
		
		CardLayout iteracioCards = new CardLayout();
		JPanel iteracioPanel = new JPanel(iteracioCards);
		
		JPanel valasztoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
		valasztoPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		valasztoPanel.setPreferredSize(new Dimension(400,50));
		JLabel valasztoLabel = new JLabel("Iteráció megállításának módja: ");
		valasztoLabel.setFont(new Font("Arial",Font.BOLD,15));
		valasztoPanel.add(valasztoLabel);
		JToggleButton valasztoGomb = new JToggleButton( PageRankMain.usingIterationNumber ? "Iterációk száma alapján" : "Eredmyényvektor változása alapján" );
		if(PageRankMain.usingIterationNumber) valasztoGomb.setSelected(true);
		valasztoGomb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(valasztoGomb.isSelected()) {
					valasztoGomb.setText("Iterációk száma alapján");
					iteracioCards.show(iteracioPanel,InputPanel.ITERACIOSZAM);
					PageRankMain.usingIterationNumber = true;
				} else {
					valasztoGomb.setText("Eredményvektor változása alapján");
					iteracioCards.show(iteracioPanel,InputPanel.HIBAHATAR);
					PageRankMain.usingIterationNumber = false;
				}
				
			}
		});
		valasztoPanel.add(valasztoGomb);
		add(valasztoPanel);
		
		iteracioPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		iteracioPanel.add( iteractioSzamPanelKeszitese() , InputPanel.ITERACIOSZAM );
		iteracioPanel.add( hibahatarPanelKeszitese(), InputPanel.HIBAHATAR );
		iteracioCards.show(iteracioPanel, PageRankMain.usingIterationNumber ? InputPanel.ITERACIOSZAM : InputPanel.HIBAHATAR);
		add(iteracioPanel);
		
		if(hasBeenCalledBefore) {
			JLabel label13 = new JLabel("<html><body>A beállítások frissítésével azonnal generálódik egy új Google mátrix! </body></html>"); //elválasztó
			label13.setFont(PageRankMain.APPFONT);
			label13.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			label13.setForeground(Color.RED);
			add(label13);
			
			JLabel label14 = new JLabel(" "); //elválasztó
			label14.setFont(PageRankMain.APPFONT);
			label14.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			add(label14);
		}
	}
	
	private JPanel iteractioSzamPanelKeszitese() {
		JPanel iPanel = new JPanel();
		iPanel.setLayout(new BoxLayout(iPanel,BoxLayout.Y_AXIS));
		
		JLabel label10 = new JLabel("Add meg azt, hogy mennyi iterációt végezzen a PageRank számolásakor!"); //iteráció szám
		label10.setFont(PageRankMain.APPFONT);
		label10.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		iPanel.add(label10);
		
		iterationField = new JTextField();
		iterationField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		iterationField.setText(PageRankMain.iterationNumber+"");
		iterationField.setPreferredSize(new Dimension(100,40));
		iPanel.add(iterationField);
		
		JLabel label11 = new JLabel("<html><body>- FIGYELEM: alacsony iterációs szám esetén az eredmény nem közelíti a valódi PageRank vektort, <br>nagyon magas esetén sokáig tarthat a számolás! <br>Alapértelmezett érték: 10</body></html>");
		label11.setFont(PageRankMain.APPFONT);
		label11.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		iPanel.add(label11);
		
		JLabel label12 = new JLabel(" "); //elválasztó
		label12.setFont(PageRankMain.APPFONT);
		label12.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		iPanel.add(label12);
		
		return iPanel;
	}
	
	private JPanel hibahatarPanelKeszitese() {
		JPanel hPanel = new JPanel();
		hPanel.setLayout(new BoxLayout(hPanel,BoxLayout.Y_AXIS));
		
		JLabel label10 = new JLabel("Add meg azt, hogy mekkora változás alatt álljon le az iteráció! (Az eredményvektor minden komponensére teljesülnie kell)"); //hibahatár
		label10.setFont(PageRankMain.APPFONT);
		label10.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		hPanel.add(label10);
		
		changeMarginField = new JTextField();
		changeMarginField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		changeMarginField.setText(PageRankMain.changeMargin+"");
		changeMarginField.setPreferredSize(new Dimension(100,40));
		hPanel.add(changeMarginField);
		
		JLabel label11 = new JLabel("<html><body>- FIGYELEM: Nagy változás esetén az eredmény nem közelíti a valódi PageRank vektort, <br>nagyon kicsi esetén sokáig tarthat a számolás! <br>Alapértelmezett érték: 0.01</body></html>");
		label11.setFont(PageRankMain.APPFONT);
		label11.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		hPanel.add(label11);
		
		JLabel label12 = new JLabel(" "); //elválasztó
		label12.setFont(PageRankMain.APPFONT);
		label12.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		hPanel.add(label12);
		
		return hPanel;
	}
	
	public void validateInputs() throws InvalidSizeException, InvalidPossibilityException, InvalidAlphaException, InvalidIterationNumberException, InvalidChangeException {
		
		int size = -1; //méret ellenőrzése
		try {
			size = Integer.parseInt(sizeField.getText());
		} catch(NumberFormatException exc) {
			throw new InvalidSizeException();
		}
		if(size<2) throw new InvalidSizeException();
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
		
		double changeMargin = -1.0;
		try {
			changeMargin = Double.parseDouble(changeMarginField.getText());
		} catch(NumberFormatException exc) {
			throw new InvalidChangeException();
		}
		if(changeMargin <= 0) throw new InvalidChangeException();
		PageRankMain.changeMargin = changeMargin;
		
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
