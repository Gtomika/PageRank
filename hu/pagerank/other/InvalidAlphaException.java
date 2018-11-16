package hu.pagerank.other;

public class InvalidAlphaException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAlphaException() {}
	
	public InvalidAlphaException(String hiba) {
		super(hiba);
	}
	
}