/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.*;
import jlc.commands.Command;
import jlc.commands.CommandController;
import jlc.commands.CommandFactory;
import jlc.commands.impl.ChangeDirectory;
import jlc.commands.impl.Dir;
import jlc.commands.impl.DirectoryTree;
import jlc.exceptions.BadCommandArgumentException;
import jlc.parse.impl.JAXBParser;

/**
 *
 * @author desolation
 */
public class JLC {

    private static final Scanner SCAN = new Scanner(System.in);
    private static String currentDir = System.getProperty("user.dir");

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Settings set = (Settings) new JAXBParser().getObject(new File("settings.xml"), Settings.class); //settings from "settings.xml" file
        HashMap<String, Class<? extends Command>> map = new HashMap<>();
        map.put(set.getDir(), Dir.class);
        map.put(set.getDirectoryTree(), DirectoryTree.class);
        map.put(set.getChangeDirectory(), ChangeDirectory.class);
        boolean running = true, success = false;
        System.out.println("Java command line.");
        while (running) {
            System.out.print(currentDir + ":");
            String command = SCAN.nextLine();
            String arr[] = command.split(" ");
            try{
            for (String s : map.keySet()) {
                if (s.equals(arr[0])) {
                    if (arr.length == 2) {
                        Command c = CommandFactory.createCommand(map.get(s), currentDir, arr[1]);
                        currentDir = CommandController.invokeCommand(c);
                    } else {
                        Command c = CommandFactory.createCommand(map.get(s), currentDir, null);
                        currentDir = CommandController.invokeCommand(c);
                    }
                    success = true;
                }
            }
            }catch(BadCommandArgumentException e){
                success = false;
            }
            if (!success) {
                System.out.println("Неверная комманда.");
            }
            success = false;
        }
    }

}
