/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.util.concurrent.Callable;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public interface Command extends Callable<String>{
    public String invoke() throws BadCommandArgumentException;//return currentDir if dir not changed
}
