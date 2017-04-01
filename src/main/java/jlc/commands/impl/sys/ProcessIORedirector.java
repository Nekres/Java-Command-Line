/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.sys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import jlc.exceptions.ProcessKilledException;

/**
 *This class allows to redirect user input to process output, and redirect process input to user output
 * @author desolation
 */
public class ProcessIORedirector{
    private InputStream processInput;
    private OutputStream targetOuput;
    
    public void run() throws ProcessKilledException {
                try {
            InputStreamReader isr = new InputStreamReader(processInput);
            BufferedWriter bw = new BufferedWriter(new PrintWriter(targetOuput)); //ресурсы закрой
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line+"\n");
                bw.flush();
            }
        } catch (IOException e) {
                    System.out.println(e.getMessage());
        }
            
    }
    
    public static Builder newBuilder(){
        return new ProcessIORedirector().new Builder();
    }
    public class Builder{
        private Builder(){}
        
        public Builder setProcessInput(InputStream is){
            ProcessIORedirector.this.processInput = is;
            return this;
        }
        public Builder setTargetOutput(OutputStream targetOs){
            ProcessIORedirector.this.targetOuput = targetOs;
            return this;
        }
        public ProcessIORedirector build(){
            return ProcessIORedirector.this;
        }
    }
}
