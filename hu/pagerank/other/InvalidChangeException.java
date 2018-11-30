package hu.pagerank.other;

public class InvalidChangeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidChangeException() {}
	
	public InvalidChangeException(String hiba) {
		super(hiba);
	}
}
