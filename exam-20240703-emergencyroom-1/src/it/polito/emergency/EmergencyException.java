package it.polito.emergency;

public class EmergencyException extends Exception{

    public EmergencyException(String msg) {
        super(msg);
    }

    public EmergencyException() {
        super("Emergency room exception");
    }

	private static final long serialVersionUID = 1L;
}
