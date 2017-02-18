/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.exceptions;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author desolation
 */
public class ProcessKilledException extends ExecutionException{

    public ProcessKilledException() {
    }

    public ProcessKilledException(String message) {
        super(message);
    }

    public ProcessKilledException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
