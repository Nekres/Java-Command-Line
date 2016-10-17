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
    private String message = "wrong count of arguments for this command";
    
    public BadCommandArgumentException(String message){
        this.message = message;
    }
    public BadCommandArgumentException(){
    }

    @Override
    public String getMessage() {
        return message; //To change body of generated methods, choose Tools | Templates.
    }
    
}