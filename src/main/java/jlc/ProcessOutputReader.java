/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author desolation
 */
public class ProcessOutputReader{
    private InputStream in;
    private OutputStream out;
    
    public ProcessOutputReader(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(in);
            BufferedWriter bw = new BufferedWriter(new PrintWriter(out));
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line+"\n");
                bw.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
