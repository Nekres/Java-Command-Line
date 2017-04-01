/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.util.Arrays;
import java.util.Objects;
import jlc.exceptions.BadCommandArgumentException;

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
     * Sets a logical separator && or || if
     * @param next 
     */
    public void setNext(String next)throws BadCommandArgumentException {
        if(next.equals("&&")){
            this.next = "&&";
            return;
        }
        if(next.equals("||")){
            this.next = "||";
            return;
        }
        throw new BadCommandArgumentException("Error: must be && or ||, but received: " + next);
    }
    

    @Override
    public String toString() {
        return "Holder{" + "command=" + command + ", arg=" + Arrays.toString(arg) + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.command);
        hash = 29 * hash + Arrays.deepHashCode(this.arg);
        hash = 29 * hash + Objects.hashCode(this.next);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommandWrapper other = (CommandWrapper) obj;
        if (!Objects.equals(this.command, other.command)) {
            return false;
        }
        if (!Objects.equals(this.next, other.next)) {
            return false;
        }
        if (!Arrays.deepEquals(this.arg, other.arg)) {
            return false;
        }
        return true;
    }
    

    
    
}
