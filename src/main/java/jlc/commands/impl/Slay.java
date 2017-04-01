/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.util.Map;
import jlc.commands.Command;
import jlc.commands.impl.ActiveCommandsManager.Task;
import jlc.exceptions.ProcessKilledException;

/**
 *
 * @author desolation
 */
public class Slay extends AbstractCommand implements Command{
    public static final String NAME = "slay";
    private final int id;
    public Slay(final int id){
        this.id = id;
    }
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean call() throws ProcessKilledException {
        Map<Integer, Task<Boolean>> map = ActiveCommandsManager.ACTIVE_TASK_LIST;
        if(map.containsKey(id))
            ActiveCommandsManager.interruptById(id);
        else
            throw new ProcessKilledException("Process with id " + id + " not found.");
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Slay other = (Slay) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
