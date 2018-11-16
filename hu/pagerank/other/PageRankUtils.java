package hu.pagerank.other;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * PageRank
 * Absztrakt oszt�ly a page rank kisz�mol�s�ra
 * 
 * @author G�sp�r Tam�s
 */

public abstract class PageRankUtils {
	
	private static final double ALPHA = 0.85;
	private static Matrix STARTINGMATRIX; //vektor amib�l az iter�ci�t ind�tjuk: (1/size,1/size,...)

	public static Matrix linkMatrix;
	public static Matrix sMatrix;
	public static Matrix googleMatrix;
	
	/**
	 * calculatePageRank
	 * Met�dus ami a page rankot kisz�molja (power method)
	 * 
	 * @param googleMatrix A linkm�trix
	 * @return Matrix a pagerank vektor
	 */
	
	public static Matrix calculatePageRank() {
		
		double[][] startingData = new double[PageRankMain.size][1]; //kezdeti vektor
		
		for(int i=0;i<PageRankMain.size;i++) {
			startingData[i][0] = 1.0/PageRankMain.size;
		}
		STARTINGMATRIX = new Matrix(startingData);
		
		Matrix pageRank = new Matrix(STARTINGMATRIX);
		for(int i=0;i< PageRankMain.iterationNumber ;i++) {
			pageRank = new Matrix( googleMatrix.times(pageRank) );
		}
		
		//kerek�t�s 2 sz�mjegyre
		for(int i=0;i<PageRankMain.size;i++) {
			double tempDouble = pageRank.data[i][0];
			pageRank.data[i][0] = BigDecimal.valueOf(tempDouble).setScale(3, RoundingMode.HALF_UP).doubleValue();
		}
		
		return pageRank;
	}
	
	/**
	 * generateRandomGoogleMatrix
	 * Egy v�letlen megadott m�ret� google m�trixot k�sz�t
	 * 
	 * @param size a m�ret
	 * @return Matrix a k�sz m�trix
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
		
		//link m�trix felt�lt�se
		for(int j=0;j<size;j++) {	
			for(int i=0;i<size;i++) {
			
				if(linkTabla[i][j]==1) {
					linkMatrixData[i][j] = 1.0/numberOfLinksOnPage[j];
					linkMatrixData[i][j] = BigDecimal.valueOf(linkMatrixData[i][j]).setScale(3, RoundingMode.HALF_UP).doubleValue();
				} else {
					linkMatrixData[i][j] = 0.0;
				}
				
			}
		}
	
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				//s m�trix felt�lt�se
				sMatrixData[i][j] = 1.0/size;
			}
		}
		
		linkMatrix = new Matrix(linkMatrixData);
		Matrix localLinkMatrix = new Matrix(linkMatrix); //m�solat
		
		sMatrix = new Matrix(sMatrixData);
		Matrix localSMatrix = new Matrix(sMatrix); //m�solat
		
		
		//localLinkMatrix = new Matrix(localLinkMatrix.transpose());
		Matrix linkMatrixP = new Matrix( localLinkMatrix.multiplyAllValuesWith(1-ALPHA) );
		
		Matrix sMatrixP = new Matrix ( localSMatrix.multiplyAllValuesWith(ALPHA) );
		
		googleMatrix = new Matrix( linkMatrixP.plus(sMatrixP) );
		
		//kerek�t�s:
		for(int j=0;j<size;j++) {
			for(int i=0;i<size;i++) {
				double tempDouble = googleMatrix.data[i][j];
				googleMatrix.data[i][j] = BigDecimal.valueOf(tempDouble).setScale(3, RoundingMode.HALF_UP).doubleValue();
			}
		}
	}
	
	/**
	 * generateRandomLinks
	 * Egy t�bl�zatot hoz l�tre amelyben a (i,j) elem 1 ha j. oldal linkel i. oldalra, egy�bk�nt 0
	 * oldal nem linkelhet saj�t mag�ra
	 * 
	 * @param size oldalak sz�ma
	 * @return int[][] a t�bl�zat
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
