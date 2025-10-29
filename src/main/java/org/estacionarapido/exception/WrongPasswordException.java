package org.estacionarapido.exception;

public class WrongPasswordException extends Exception{
    public WrongPasswordException() {
        super("Wrong Password prompted. Try again.");
    }
    
}
