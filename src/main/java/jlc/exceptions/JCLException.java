/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.exceptions;

/**
 *
 * @author desolation
 */
public class JCLException extends Exception{
    private String message;

    public JCLException(String message) {
        this.message = message;
    }

    public JCLException() {
    }
    
    @Override
    public String getMessage() {
        return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
    }

    public JCLException(String message, Throwable cause) {
        super(message, cause);
    }

    public JCLException(Throwable cause) {
        super(cause);
    }
    
}
