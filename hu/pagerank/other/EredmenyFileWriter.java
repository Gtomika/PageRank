package hu.pagerank.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * EredmenyFileWriter
 * A program eredm�ny�nek f�jlba �r�s�ra szolg�l� oszt�ly
 * 
 * @author G�sp�r Tam�s
 */

public class EredmenyFileWriter {
	
	private static String programHelye;
	
	static {
		String ideiglenesHely = EredmenyFileWriter.class.getProtectionDomain().getCodeSource().getLocation().getPath();          //program helye a f�jlrendszerben (Eclipseben a bin mappa)
		programHelye = ideiglenesHely.substring(1, ideiglenesHely.lastIndexOf("/"));
	}
	
	public EredmenyFileWriter() {}
	
	/**
	 * fajlbaIr
	 * ki�rja az eredm�nyf�jlba a google m�trixot �s a page rank vektort
	 * 
	 * @param googleMatrix google m�trix
	 * @param pageRank eredm�nyvektor
	 * @throws Exception ha nem lehett �rni
	 */
	
	public void fajlbaIr(Matrix googleMatrix,Matrix pageRank, double szamolasiIdo) throws Exception{  		//lista �r�sa f�jlba
		PrintWriter iro = null;
		File eredmenyFajl = null;
		
		try {
			 eredmenyFajl = new File(programHelye+"/eredmeny_"+PageRankMain.logID+".txt");
			
			 iro = new PrintWriter(eredmenyFajl);
		} catch(FileNotFoundException e) {
		}
		
		iro.println("A GOOGLE M�TRIX: ");
		iro.println();
		
		//m�trix �r�sa
		googleMatrix.show(iro);
		
		//sz�mol�si id� kerek�t�se
		szamolasiIdo = BigDecimal.valueOf(szamolasiIdo).setScale(3, RoundingMode.HALF_UP).doubleValue();
		
		iro.println();
		iro.println("A PAGERANK VEKTOR: (kisz�mol�shoz sz�ks�ges id� kb. "+szamolasiIdo+" m�sodperc volt)");
		iro.println();
		
		//pagerank transzpon�l�sa
		pageRank = pageRank.transpose();
		
		//pagerank �r�sa
		pageRank.show(iro);

		iro.close();
	}
	
}
