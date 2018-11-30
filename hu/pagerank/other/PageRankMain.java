package hu.pagerank.other;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hu.pagerank.gui.InputPanel;
import hu.pagerank.gui.EredmenyPanel;
import hu.pagerank.gui.MatrixPanel;

public class PageRankMain {
	
	public static int size = 10;
	public static double linkPossibility = 1.0/size;
	public static double alpha = 0.85;
	public static int iterationNumber = 10;
	public static double changeMargin = 0.01;
	public static boolean usingIterationNumber = true;
	
	public static final Font APPFONT = new Font("Arial",Font.PLAIN,20);
	public static int logID;
	public static final int MAXDISPLAYSIZE = 20;
	
	public static JFrame frame;
	private static Matrix pageRank;
	private static long szamolasiIdo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PageRankMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * PageRankMain
	 * Konstruktor, ami inicializálja az alkalmazást
	 */
	
	public PageRankMain() {
		
		collectInputs(false);
		
		Random r = new Random();
		logID = r.nextInt(10000);
		
		InputPanel.isUpdated = true;
		
		initialize();
	}
	
	/**
	 * initialize
	 * Az ablakot elkészítő metódus
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PageRank számoló");
		
		JPanel mainPanel = createMainPanel();
		frame.getContentPane().add(mainPanel);
		
		frame.pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
	
	public static JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JLabel generalvaLabel = new JLabel("Google mátrix létrehozva: α*linkmátrix + (1-α)*S (α="+alpha+")");
		generalvaLabel.setFont(APPFONT);
		
		JPanel generalvaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,30));
		generalvaPanel.add(generalvaLabel);
		mainPanel.add(generalvaPanel,BorderLayout.PAGE_START);
		
		//mátrix generálása
		PageRankUtils.generateRandomGoogleMatrix(size); //PageRankUtils.googleMatrix-ba
		
		if(size<=MAXDISPLAYSIZE) {
			MatrixPanel matrixPanel = new MatrixPanel(PageRankUtils.googleMatrix,false);
			mainPanel.add(matrixPanel,BorderLayout.CENTER);
		} else {
			JLabel infoLabel = new JLabel("<html><body>A mátrix túl nagy ahhoz hogy itt megjelenjen!<br> (de az output fájlba bekerül)</body></html>");
			infoLabel.setFont(APPFONT);
			infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mainPanel.add(infoLabel,BorderLayout.CENTER);
		}
		
		JPanel gombokPanel = new JPanel(); //segéd panelek
		gombokPanel.setLayout(new BoxLayout(gombokPanel,BoxLayout.Y_AXIS));
		
		JPanel gombokSeged1 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		
		JButton linkMatrixGomb = new JButton("Link mátrix mutatása");
		linkMatrixGomb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(size<MAXDISPLAYSIZE) {
					JOptionPane.showMessageDialog(frame, new MatrixPanel(PageRankUtils.linkMatrix,true), "A linkmátrix", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Túl nagy ahhoz, hogy itt megjelenjen!", "A linkmátrix", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		gombokSeged1.add(linkMatrixGomb);
		
		JButton sMatrixGomb = new JButton("S mátrix mutatása");
		sMatrixGomb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(size<MAXDISPLAYSIZE) {
					JOptionPane.showMessageDialog(frame, new MatrixPanel(PageRankUtils.sMatrix,false), "S mátrix", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Túl nagy ahhoz, hogy itt megjelenjen!", "S mátrix", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		gombokSeged1.add(sMatrixGomb);
		
		gombokPanel.add(gombokSeged1);
		
		JPanel gombokSeged2 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		
		JButton beallitasokGomb = new JButton("Beállítások");
		beallitasokGomb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				collectInputs(true);
				frame.setContentPane(createMainPanel());
				frame.revalidate();
				frame.repaint();
				frame.pack();
			}
		});
		gombokSeged2.add(beallitasokGomb);
		
		JButton ujMatrixGomb = new JButton("Új mátrix generálása");
		ujMatrixGomb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(createMainPanel());
				frame.revalidate();
				frame.repaint();
				frame.pack();
			}
		});
		gombokSeged2.add(ujMatrixGomb);
		
		JButton szamolasGomb = new JButton("PageRank kiszámolása!");
		szamolasGomb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(InputPanel.isUpdated) {
					long startTime = System.currentTimeMillis(); 
					pageRank = PageRankUtils.calculatePageRank();
					long endTime = System.currentTimeMillis();
					szamolasiIdo = endTime-startTime;
					
					InputPanel.isUpdated = false;
				}
				JOptionPane.showMessageDialog(frame, new EredmenyPanel(PageRankUtils.googleMatrix,pageRank,(int)szamolasiIdo/1000.0,PageRankUtils.elvegzettIteraciok), "A PageRank vektor", JOptionPane.PLAIN_MESSAGE);
			}
		});
		gombokSeged2.add(szamolasGomb);
		
		gombokPanel.add(gombokSeged2);
		
		JPanel memoriaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,30)); //memória foglalást mutató panel
		
		double foglaltMemoria = memoriaIgenySzamolasa();
		JLabel memoriaLabel = new JLabel("Egy "+ size +"*"+ size +" mérető mátrix "+ foglaltMemoria + " mB helyet foglal.");
		memoriaLabel.setFont(new Font("Arial",Font.BOLD,15));
		
		memoriaPanel.add(memoriaLabel);
		
		gombokPanel.add(memoriaPanel);
		
		mainPanel.add(gombokPanel,BorderLayout.PAGE_END);
		
		return mainPanel;
	}
	
	public static void collectInputs(boolean hasBeenCalledBefore) {
		boolean helytelenInput = false;
		do {
			InputPanel ip = new InputPanel(hasBeenCalledBefore); //a méret bekérése
			
			String[] gombok;
			if(hasBeenCalledBefore) {
				gombok = new String[2];
				gombok[0] = "OK!";
				gombok[1] = "Mégse";
			} else {
				gombok = new String[1];
				gombok[0] = "OK!";
			}
			String title = hasBeenCalledBefore ? "Beállítások" : "Indítási beállítások" ;
			
			int dontes = JOptionPane.showOptionDialog(frame, ip, title, JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE, null, gombok, 0);
			
			if(dontes==-1 && !hasBeenCalledBefore) {
				System.exit(0);
			} else if(dontes==-1 && hasBeenCalledBefore) {
				break;
			}
			
			try { //helyesség ellenőrzése
				ip.validateInputs();
				helytelenInput=false;
				
			} catch(InvalidSizeException exc) {
				JOptionPane.showMessageDialog(frame, "Nem megfelelő méret!", "Rossz input!", JOptionPane.ERROR_MESSAGE);
				helytelenInput=true;
			} catch(InvalidPossibilityException exc) {
				if(!hasBeenCalledBefore && ip.getPossibilityField().getText().equals("")) {
					linkPossibility = 1.0/size;
				} else {
					JOptionPane.showMessageDialog(frame, "Nem megfelelő linkelési valószínűség!", "Rossz input!", JOptionPane.ERROR_MESSAGE);
					helytelenInput=true;
				}
			} catch(InvalidAlphaException exc) {
				JOptionPane.showMessageDialog(frame, "Nem megfelelő alfa paraméter!", "Rossz input!", JOptionPane.ERROR_MESSAGE);
				helytelenInput=true;
			} catch(InvalidIterationNumberException exc) {
				if(usingIterationNumber) {
					JOptionPane.showMessageDialog(frame, "Nem megfelelő iterációs szám!", "Rossz input!", JOptionPane.ERROR_MESSAGE);
					helytelenInput=true;
				} else {
					iterationNumber = 10; //ha nem ez volt választva akkor hiba esetén csak alapértelmezettre áll
				}
			} catch(InvalidChangeException exc) {
				if(!usingIterationNumber) {
					JOptionPane.showMessageDialog(frame, "Nem megfelelő minimális változás!", "Rossz input!", JOptionPane.ERROR_MESSAGE);
					helytelenInput=true;
				} else {
					changeMargin = 0.01;
				}
			}
			
			InputPanel.isUpdated = true;
		} while(helytelenInput==true);
	}
	
	private static double memoriaIgenySzamolasa() { //mátrix memória igényét számolja
		
		int elemekSzama = size*size;
		
		double foglalt =  (elemekSzama * Double.BYTES) / new Double(1024 * 1024);
		
		return BigDecimal.valueOf(foglalt).setScale(3, RoundingMode.HALF_UP).doubleValue();
		
	}
	
}
