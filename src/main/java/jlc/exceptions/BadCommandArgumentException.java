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
public class BadCommandArgumentException extends JCLException{
    private String message = "Error: bad arguments for this command.";
    
    public BadCommandArgumentException(String message){
        this.message = message;
    }
    public BadCommandArgumentException(){
        this.message = "Error: incorrect number of arguments.";
    }

    @Override
    public String getMessage() {
        return message; //To change body of generated methods, choose Tools | Templates.
    }
    
}
