/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.util.Arrays;

/**
 * CommandWrapper represents simple tuple of command and its arguments
 * @author desolation
 */
public class CommandWrapper {
    public final String command;
    public final String[] arg;
    public String next;
    /**
     * Creates a new tuple
     * @param command - command
     * @param arg - args
     */
    public CommandWrapper(String command, String[] arg) {
        this.command = command;
        this.arg = arg;
    }
    /**
     * @return 
     */
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
    

    @Override
    public String toString() {
        return "Holder{" + "command=" + command + ", arg=" + Arrays.toString(arg) + '}';
    }

    
    
}
