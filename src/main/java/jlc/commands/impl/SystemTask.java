/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.util.ArrayList;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class SystemTask implements Command{
    private String task;
    private String args[];

    public SystemTask(String task, String[] args) {
        this.task = task;
        this.args = args;
    }
    
    public SystemTask(String task) {
        this.task = task;
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException {
        ProcessBuilder pb = new ProcessBuilder(task);
        
    }

    @Override
    public int argsAmount() {
        if(args !=null) 
            return args.length;
        else 
            return 0;
    }

    @Override
    public void run() {
        
    }
    
}
