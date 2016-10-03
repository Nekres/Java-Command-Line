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
    private static HashMap<String, Class<? extends Command>> settings = new HashMap<>();
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Settings set = (Settings) new JAXBParser().getObject(new File("settings.xml"), Settings.class); //settings from "settings.xml" file
        HashMap<String, Class<? extends Command>> settings = new HashMap<>();
        settings.put(set.getDir(), Dir.class);
        settings.put(set.getDirectoryTree(), DirectoryTree.class);
        settings.put(set.getChangeDirectory(), ChangeDirectory.class);
        boolean running = true, success = false;
        System.out.println("Java command line.");
        while (running) {
            System.out.print(currentDir + ":");
            String command = SCAN.nextLine();
            String arr[] = command.split(" ");
            analyze(settings,arr);
            try{
            for (String s : settings.keySet()) {
                if (s.equals(arr[0])) {
                    if (arr.length == 2) {
                        Command c = CommandFactory.createCommand(settings.get(s), currentDir, arr[1]);
                        currentDir = CommandController.invokeCommand(c);
                    } else {
                        Command c = CommandFactory.createCommand(settings.get(s), currentDir, null);
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
    public static HashMap<Command, String[]> analyze(HashMap<String, Class<? extends Command>> settings,String[] input) throws BadCommandArgumentException{
        String or = "||";
        String and = "&&";
        if (!settings.containsKey(input[0])) {
            throw new BadCommandArgumentException("wrong command"); // если первый аргумент не комманда - нет смысла проверять всю строку.
        }
        HashMap<Class<? extends Command>, String[]> commands = new HashMap<>();
        Class<? extends Command> c = null;
        int temp = 0, mark = 0;
        for (String ks : settings.keySet()) {
            for (int i = temp; i < input.length; i++) {
                if (input[i].equals(ks)) {
                    c = settings.get(ks);
                    mark = i;
                    System.out.println("s");
                    continue;
                } else if (input[i] != or && input[i] != and) {
                    temp++;
                } else {
                    c = null;
                    System.out.println("s");
                    break;
                }
            }
            if (c != null) {
                commands.put(c, Arrays.copyOfRange(input, mark, temp));
            }
        }
        for (Class cc : commands.keySet()) {
            System.out.println(cc.getName());
            System.out.println(settings.get(cc));
        }
        return null;
    }

}
