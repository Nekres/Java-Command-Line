/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import jlc.ProcessOutputReader;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class SystemTask extends AbstractCommand implements Command{
    private boolean successFinished = true;
    private ArrayList<String> task = new ArrayList<>();
    public SystemTask(String task, String[] args) {
        this.task.add(task);
        for(String s :args)
            this.task.add(s);
    }
    
    public SystemTask(String task) {
        this.task.add(task);
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException,IOException {
        Thread t = new Thread(this);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        try(BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(currentOutput,Charset.forName(ENCODING))))){
        ProcessBuilder pb = new ProcessBuilder(task);
        pb.redirectErrorStream(true);
        pb.directory(new File(System.getProperty("user.dir")));
        try {
            try {
                Process p = pb.start();
                ProcessOutputReader por = new ProcessOutputReader(p.getInputStream(), currentOutput);
                por.run();
                p.waitFor();
            } catch (InterruptedException ex) {
                successFinished = false;
                Thread.currentThread().interrupt();
                bw.write("The command is aborted.");
                bw.flush();
            } catch (IOException ex) {
                successFinished = false;
                bw.write("Error: no such command.\n");
                bw.flush();
            }
        } catch (IOException ex1) {
            throw new RuntimeException(ex1);
        }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "_"+ task.get(0) + "#ID{" + id + "}";
    }

    @Override
    public String getName() {
        return task.get(0);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Boolean call() throws Exception {
            run();
        return successFinished;
    }
    
    
}
