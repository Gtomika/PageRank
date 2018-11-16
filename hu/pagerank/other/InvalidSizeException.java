package hu.pagerank.other;

public class InvalidSizeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidSizeException() {}
	
	public InvalidSizeException(String hiba) {
		super(hiba);
	}
	
}
