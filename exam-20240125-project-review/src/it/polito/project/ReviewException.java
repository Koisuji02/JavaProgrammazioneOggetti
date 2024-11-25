package it.polito.project;

public class ReviewException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReviewException() {super("Medical center exception");}
	
	public ReviewException(String msg) {super(msg);}

}
