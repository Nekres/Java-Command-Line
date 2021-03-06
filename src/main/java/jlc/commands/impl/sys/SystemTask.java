/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.sys;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import jlc.commands.Command;
import jlc.commands.impl.AbstractCommand;
import jlc.commands.impl.ActiveCommandsManager;
import jlc.commands.impl.rm.EchoThread;
import jlc.exceptions.ProcessKilledException;

/**
 *
 * @author desolation
 */
public class SystemTask extends AbstractCommand implements Command{
    private boolean successFinished = true;
    private Process p;
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
    public String getName() {
        return task.get(0);
    }

    @Override
    public Boolean call() throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try(BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(currentOutputStream,Charset.forName(ENCODING))))){
        ProcessBuilder pb = new ProcessBuilder(task);
        pb.redirectErrorStream(true);
        pb.directory(new File(System.getProperty("user.dir")));
        try {
            try {
                p = pb.start();
                ProcessIORedirector por = ProcessIORedirector.newBuilder().setProcessInput(p.getInputStream()).setTargetOutput(currentOutputStream).build();
                por.run();
                if(p.waitFor() != 0)
                    successFinished = false;
            } catch (InterruptedException ex) {
                ActiveCommandsManager.remove(SystemTask.this.getID());
                successFinished = false;
                Thread.currentThread().interrupt();
                bw.write("The command is aborted.");
                bw.flush();
            } catch (IOException ex) {
                ActiveCommandsManager.remove(SystemTask.this.getID());
                successFinished = false;
                bw.write("Error: no such command.\n");
                bw.flush();
            }
            catch(ProcessKilledException e){
                bw.write(task.get(0) + "closing info:\n"+e.getMessage());
                bw.flush();
            }
        } finally{
            ActiveCommandsManager.remove(SystemTask.this.getID());
        }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }finally{
            if (p != null)
            p.destroy();
        }
        return successFinished;
    }
    
    
}
