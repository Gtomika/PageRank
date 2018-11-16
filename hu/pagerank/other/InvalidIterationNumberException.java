package hu.pagerank.other;

public class InvalidIterationNumberException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidIterationNumberException() {}
	
	public InvalidIterationNumberException(String hiba) {
		super(hiba);
	}
	
}