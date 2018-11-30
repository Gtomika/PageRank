package hu.pagerank.other;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * PageRank
 * Absztrakt osztály a page rank kiszámolására
 * 
 * @author Gáspár Tamás
 */

public abstract class PageRankUtils {
	
	private static final double ALPHA = 0.85;
	private static Matrix STARTINGMATRIX; //vektor amibõl az iterációt indítjuk: (1/size,1/size,...)

	public static Matrix linkMatrix;
	public static Matrix sMatrix;
	public static Matrix googleMatrix;
	public static int elvegzettIteraciok = 0;
	
	/**
	 * calculatePageRank
	 * Metódus ami a page rankot kiszámolja (power method)
	 * 
	 * @param googleMatrix A linkmátrix
	 * @return Matrix a pagerank vektor
	 */
	
	public static Matrix calculatePageRank() {
		
		double[][] startingData = new double[PageRankMain.size][1]; //kezdeti vektor
		
		for(int i=0;i<PageRankMain.size;i++) {
			startingData[i][0] = 1.0/PageRankMain.size;
		}
		STARTINGMATRIX = new Matrix(startingData);
		
		Matrix pageRank = new Matrix(STARTINGMATRIX);
		if(PageRankMain.usingIterationNumber) { //konkrét iterációs szám alapján
			
			for(int i=0;i< PageRankMain.iterationNumber ;i++) {
				pageRank = new Matrix( googleMatrix.times(pageRank) );
			}
			elvegzettIteraciok = PageRankMain.iterationNumber;
			
		} else { //csökkenés alapján
			elvegzettIteraciok = 0;
			
			boolean csokkenesNagyobb = true;
			while(csokkenesNagyobb) {
				elvegzettIteraciok++;
				
				Matrix pageRankElotte = new Matrix(pageRank); //korábbi pagerank elmentése
				pageRank = new Matrix( googleMatrix.times(pageRank) ); //iteráció elvégzése
				
				csokkenesNagyobb = false;
				for(int i=0;i<PageRankMain.size;i++) {
					if( Math.abs( pageRankElotte.data[i][0] - pageRank.data[i][0] ) > PageRankMain.changeMargin  ) { //ha valahol nagyobb akkor tovább iterálás
						csokkenesNagyobb = true;
						break;
					}
				}
			}
			
		}
		
		//kerekítés 2 számjegyre
		for(int i=0;i<PageRankMain.size;i++) {
			double tempDouble = pageRank.data[i][0];
			pageRank.data[i][0] = BigDecimal.valueOf(tempDouble).setScale(3, RoundingMode.HALF_UP).doubleValue();
		}
		
		return pageRank;
	}
	
	/**
	 * generateRandomGoogleMatrix
	 * Egy véletlen megadott méretû google mátrixot készít
	 * 
	 * @param size a méret
	 * @return Matrix a kész mátrix
	 */
	
	public static void generateRandomGoogleMatrix(int size) {
		
		int[][] linkTabla = generateRandomLinks(size);
		
		int[] numberOfLinksOnPage = new int[size];
		for(int j=0;j<size;j++) {	
			for(int i=0;i<size;i++) {
				if(linkTabla[i][j]==1) {
					numberOfLinksOnPage[j]++;
				}
			}
		}
		
		double[][] linkMatrixData = new double[size][size];
		double[][] sMatrixData = new double[size][size];
		
		//link mátrix feltöltése
		for(int j=0;j<size;j++) {	
			for(int i=0;i<size;i++) {
				
				if(numberOfLinksOnPage[j] == 0) { //dangling node kezelése
					linkMatrixData[i][j] = 1.0/PageRankMain.size;
					linkMatrixData[i][j] = BigDecimal.valueOf(linkMatrixData[i][j]).setScale(3, RoundingMode.HALF_UP).doubleValue();
				} else if(linkTabla[i][j]==1) {
					linkMatrixData[i][j] = 1.0/numberOfLinksOnPage[j];
					linkMatrixData[i][j] = BigDecimal.valueOf(linkMatrixData[i][j]).setScale(3, RoundingMode.HALF_UP).doubleValue();
				} else {
					linkMatrixData[i][j] = 0.0;
				}
				
			}
		}
	
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				//s mátrix feltöltése
				sMatrixData[i][j] = 1.0/size;
			}
		}
		
		linkMatrix = new Matrix(linkMatrixData);
		Matrix localLinkMatrix = new Matrix(linkMatrix); //másolat
		
		sMatrix = new Matrix(sMatrixData);
		Matrix localSMatrix = new Matrix(sMatrix); //másolat
		
		
		//localLinkMatrix = new Matrix(localLinkMatrix.transpose());
		Matrix linkMatrixP = new Matrix( localLinkMatrix.multiplyAllValuesWith(ALPHA) );
		
		Matrix sMatrixP = new Matrix ( localSMatrix.multiplyAllValuesWith(1-ALPHA) );
		
		googleMatrix = new Matrix( linkMatrixP.plus(sMatrixP) );
		
		//kerekítés:
		for(int j=0;j<size;j++) {
			for(int i=0;i<size;i++) {
				double tempDouble = googleMatrix.data[i][j];
				googleMatrix.data[i][j] = BigDecimal.valueOf(tempDouble).setScale(3, RoundingMode.HALF_UP).doubleValue();
			}
		}
	}
	
	/**
	 * generateRandomLinks
	 * Egy táblázatot hoz létre amelyben a (i,j) elem 1 ha j. oldal linkel i. oldalra, egyébként 0
	 * oldal nem linkelhet saját magára
	 * 
	 * @param size oldalak száma
	 * @return int[][] a táblázat
	 */
	
	private static int[][] generateRandomLinks(int size){
		int[][] tabla = new int[size][size];
		
		for(int j=0;j<size;j++) {
			for(int i=0;i<size;i++) {
				
				if(i!=j && ThreadLocalRandom.current().nextDouble(0,1) <= PageRankMain.linkPossibility) {
					tabla[i][j] = 1;
				} else {
					tabla[i][j] = 0;
				}
			}
		}
		
		return tabla;
	}
	
}
