package it.polito.ski;

@SuppressWarnings("serial")
public class InvalidLiftException extends Exception {

	public InvalidLiftException() {
		super("Invalid lift type");
	}
	public InvalidLiftException(String msg) {
		super(msg);
	}

}
