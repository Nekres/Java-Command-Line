/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import jlc.commands.Command;

/**
 *
 * @author desolation
 */
public abstract class AbstractCommand implements Command{
    protected final String enc = System.getProperty("os.name").contains("Windows") ? "cp866" : "UTF-8";
    protected String currentDir = System.getProperty("user.dir");
    protected static int INSTANCE_ID = 0;
    protected final int id = INSTANCE_ID++;
    protected static final PrintStream DEFAULT_OUTPUT = System.out;
    protected PrintStream currentOutput = DEFAULT_OUTPUT;
    protected BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(DEFAULT_OUTPUT,Charset.forName(enc))));

    @Override
    public void setOutputPath(PrintStream path){
        bw = new BufferedWriter(new PrintWriter(path));
        currentOutput = path;
    }
    
}
