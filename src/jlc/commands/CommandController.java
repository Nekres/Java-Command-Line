/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import jlc.commands.impl.DirectoryTree;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jlc.commands.impl.ChangeDirectory;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandController {
    
    public static String invokeCommand(Command command) throws BadCommandArgumentException{
        return command.invoke();
    }
//    d
    
    public static void tree(String currentDir){
       // DirectoryTree tree = new DirectoryTree(new File(currentDir));
    }
}
