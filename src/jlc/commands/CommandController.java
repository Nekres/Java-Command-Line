/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.File;

/**
 *
 * @author desolation
 */
public class CommandController {
    
    public static void dir(String arg){
        File file = new File(arg);
        if (file.isDirectory())
            for(File f : file.listFiles())
                System.out.println(f.getName());
    }
    public static void cd(String arg){
        
    }
    public static void pwd(){
        
    }
}
