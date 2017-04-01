/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import jlc.commands.Command;
import static jlc.commands.impl.AbstractCommand.ENCODING;
import jlc.commands.impl.rm.RemoteMode;
import jlc.view.TextStyle;

/**
 *
 * @author desolation
 */
public class ActiveCommandsManager extends AbstractCommand implements Command {
    
    public static final String NAME = "jobs";
    public static final Map<Integer, Task<Boolean>> ACTIVE_TASK_LIST = new HashMap<>();
    
    public static void add(Command c, Integer unique_id, Future<Boolean> f){
        ACTIVE_TASK_LIST.put(unique_id,new Task<Boolean>(c,f));
    }
    public static void remove(final int unique_id){
        ACTIVE_TASK_LIST.remove(unique_id);
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    public static void interruptById(final int id){
        if(ACTIVE_TASK_LIST.get(id).c.getName().equals(RemoteMode.NAME))
            ((RemoteMode)ACTIVE_TASK_LIST.get(id).c).destroy();
        ACTIVE_TASK_LIST.get(id).f.cancel(true);
        ACTIVE_TASK_LIST.remove(id);
    }
    @Override
    public Boolean call() throws Exception {
            try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(currentOutputStream,Charset.forName(ENCODING)))){
            if (!ACTIVE_TASK_LIST.isEmpty()) {
                bw.write("+Name\t\tID+");
                bw.newLine();
                for (Task task : ACTIVE_TASK_LIST.values()) {
                    bw.write(task.c.getName() + "\t\t" + task.c.getID());
                    bw.newLine();
                    bw.flush();
                }
            } else {
                System.out.println(TextStyle.colorText("No processes running in daemon mode.", TextStyle.Color.RED));
            }
        }
        return true;
    }
    public static class Task<T>{
        public final Command c;
        public final Future<T> f;

        public Task(Command c, Future<T> f) {
            this.c = c;
            this.f = f;
        }

        
    }
}
