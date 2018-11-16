package hu.pagerank.other;

public class InvalidPossibilityException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPossibilityException() {}
	
	public InvalidPossibilityException(String hiba) {
		super(hiba);
	}
	
}
