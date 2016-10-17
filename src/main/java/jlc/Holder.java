/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.util.Arrays;

/**
 *
 * @author desolation
 */
public class Holder {
    public final Class command;
    public final String[] arg;
    private String next;

    public Holder(Class command, String[] arg) {
        this.command = command;
        this.arg = arg;
    }

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
