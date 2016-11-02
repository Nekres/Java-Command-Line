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
public class NoSuchCommandException extends JCLException{
    private String message = "Error: no such command.";
    public NoSuchCommandException(){
        
    }

    @Override
    public String getMessage() {
        return message; //To change body of generated methods, choose Tools | Templates.
    }
    
}
