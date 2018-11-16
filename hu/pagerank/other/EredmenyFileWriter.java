package hu.pagerank.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * EredmenyFileWriter
 * A program eredményének fájlba írására szolgáló osztály
 * 
 * @author Gáspár Tamás
 */

public class EredmenyFileWriter {
	
	private static String programHelye;
	
	static {
		String ideiglenesHely = EredmenyFileWriter.class.getProtectionDomain().getCodeSource().getLocation().getPath();          //program helye a fájlrendszerben (Eclipseben a bin mappa)
		programHelye = ideiglenesHely.substring(1, ideiglenesHely.lastIndexOf("/"));
	}
	
	public EredmenyFileWriter() {}
	
	/**
	 * fajlbaIr
	 * kiírja az eredményfájlba a google mátrixot és a page rank vektort
	 * 
	 * @param googleMatrix google mátrix
	 * @param pageRank eredményvektor
	 * @throws Exception ha nem lehett írni
	 */
	
	public void fajlbaIr(Matrix googleMatrix,Matrix pageRank, double szamolasiIdo) throws Exception{  		//lista írása fájlba
		PrintWriter iro = null;
		File eredmenyFajl = null;
		
		try {
			 eredmenyFajl = new File(programHelye+"/eredmeny_"+PageRankMain.logID+".txt");
			
			 iro = new PrintWriter(eredmenyFajl);
		} catch(FileNotFoundException e) {
		}
		
		iro.println("A GOOGLE MÁTRIX: ");
		iro.println();
		
		//mátrix írása
		googleMatrix.show(iro);
		
		//számolási idõ kerekítése
		szamolasiIdo = BigDecimal.valueOf(szamolasiIdo).setScale(3, RoundingMode.HALF_UP).doubleValue();
		
		iro.println();
		iro.println("A PAGERANK VEKTOR: (kiszámoláshoz szükséges idõ kb. "+szamolasiIdo+" másodperc volt)");
		iro.println();
		
		//pagerank transzponálása
		pageRank = pageRank.transpose();
		
		//pagerank írása
		pageRank.show(iro);

		iro.close();
	}
	
}
