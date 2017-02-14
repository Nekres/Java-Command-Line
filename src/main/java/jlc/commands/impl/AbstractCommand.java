/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.OutputStream;
import java.io.PrintStream;
import jlc.commands.Command;
import org.apache.commons.io.output.CloseShieldOutputStream;

/**
 *
 * @author desolation
 */
public abstract class AbstractCommand implements Command{
    protected static final String ENCODING = System.getProperty("os.name").contains("Windows") ? "cp866" : "UTF-8";
    protected final CloseShieldOutputStream DEFAULT_OUTPUT = new CloseShieldOutputStream(System.out);
    protected String currentDir = System.getProperty("user.dir");
    protected static int INSTANCE_ID = 0;
    protected final int id = INSTANCE_ID++;
    protected OutputStream currentOutput = DEFAULT_OUTPUT;
    protected String delim;
    
    @Override
    public void setOutputPath(OutputStream path){
        currentOutput = path;
    }
    /**
     * @return - returns a logical separator "and" or "or"
     */
    @Override
    public String getSeparator(){
        return delim;
    }
    
    @Override
    public void setSeparator(String delim){
        this.delim = delim;
    }

    
    
}
