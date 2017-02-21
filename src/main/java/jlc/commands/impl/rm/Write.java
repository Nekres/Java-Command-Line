/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.rm;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import jlc.commands.Command;
import jlc.commands.impl.AbstractCommand;

/**
 *
 * @author desolation
 */
public class Write extends AbstractCommand implements Command{
    public static final String NAME = "write";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean call() throws Exception{
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(currentOutput,Charset.forName(ENCODING)))){
            
            
            
            
        }
        return true;
    }
    
}
