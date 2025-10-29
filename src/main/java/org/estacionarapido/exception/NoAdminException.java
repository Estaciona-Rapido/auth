package org.estacionarapido.exception;

public class NoAdminException extends Exception{
    public NoAdminException() {
        super("There are no administrator's users on the database. Please, check the database integrity.");
    }
    
}
