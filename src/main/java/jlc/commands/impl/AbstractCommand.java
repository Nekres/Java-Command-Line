/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import jlc.commands.Command;

/**
 *
 * @author desolation
 */
public abstract class AbstractCommand implements Command{
    protected static int INSTANCE_ID = 0;
    protected static final PrintStream DEFAULT_OUTPUT = System.out;
    protected PrintStream currentOutput = DEFAULT_OUTPUT;
    protected BufferedWriter bw = new BufferedWriter(new PrintWriter(DEFAULT_OUTPUT));

    @Override
    public void setOutputPath(PrintStream path){
        bw = new BufferedWriter(new PrintWriter(path));
        currentOutput = path;
    }
    
}
