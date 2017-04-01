/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import jlc.commands.Command;
import org.apache.commons.io.input.CloseShieldInputStream;
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
    protected OutputStream currentOutputStream = DEFAULT_OUTPUT;
    protected PrintStream currentErrStream = System.err;
    protected InputStream currentInputStream = new CloseShieldInputStream(System.in);
    protected String delim;
    
    
    @Override
    public void setOutputStream(OutputStream path){
        currentOutputStream = path;
    }
    
    @Override
    public void setErrStream(PrintStream err) {
        this.currentErrStream = err;
    }

    @Override
    public void setInputStream(InputStream in) {
        this.currentInputStream = in;
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
    @Override
    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + this.id;
    }
    

    
    
}
