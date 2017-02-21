/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.exceptions;


/**
 *  Throws when task was cancelled purposely
 * @author desolation
 */
public class ProcessKilledException extends JCLException{

    public ProcessKilledException() {
    }

    public ProcessKilledException(String message) {
        super(message);
    }
    
    
}
