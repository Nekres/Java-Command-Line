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
 * @param <A>
 * @param <B>
 */
public class Holder {
    public final Class command;
    public final String[] arg;

    public Holder(Class command, String[] arg) {
        this.command = command;
        this.arg = arg;
    }

    @Override
    public String toString() {
        return "Holder{" + "command=" + command + ", arg=" + Arrays.toString(arg) + '}';
    }

    
    
}
