/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.*;
import jlc.commands.*;
import jlc.commands.impl.*;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.NoSuchCommandException;
import jlc.parse.impl.JAXBParser;

/**
 *
 * @author desolation
 */
public class JLC {

    private static final Scanner SCAN = new Scanner(System.in);
    private static String currentDir = System.getProperty("user.dir");
    private static List<String> settings = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        File logs = new File("logs");
        if(!logs.exists()){
            logs.mkdir();
        }
        File file = new File("settings.xml");
        if (!file.exists()) {
            Settings.setDefault(file);
            System.out.println("Run with default settings...");
        }
        Settings options = (Settings) new JAXBParser().getObject(file, Settings.class); //settings from "settings.xml" file
        settings.add(options.getDir());
        Dir.NAME = options.getDir();
        settings.add(options.getDirectoryTree());
        DirectoryTree.NAME = options.getDirectoryTree();
        settings.add(options.getChangeDirectory());
        ChangeDirectory.NAME = options.getChangeDirectory();
        boolean running = true, daemon = false;
        System.out.println("Java command line.");
        while (running) {
            System.out.print(System.getProperty("user.dir") + ":");
            String command = SCAN.nextLine().trim();
            String arr[] = command.split(" ");
            daemon = arr[arr.length - 1].equals("&");
            if(daemon)
                System.out.println("Daemon processing..");
            try {
                List<Command> commandList = analyze(settings, arr);
                Command.execute(commandList, daemon);
            } catch (BadCommandArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static List<Command> analyze(List<String> settings, String[] input) throws NoSuchCommandException, BadCommandArgumentException {
        String or = "||";
        String and = "&&";
        List<Holder> list = new ArrayList<>();
        List<Command> result = new ArrayList<>();
//        if (!settings.containsKey(input[0])) {
//            throw new NoSuchCommandException(); // если первый аргумент не комманда - нет смысла проверять всю строку.
//        }
        String c = null;
        int temp = 0, mark = 0;
        boolean next = true, found = false;
        String splitter = null;
        for (int i = 0; i < input.length; i++) {
            for (String ks : settings) {
                if (input[i].equals(ks)) {
                    c = ks;
                    mark = i; //индекс комманды
                    found = true;
                    next = false;
                    break;
                }
            }
            if (next) {
                c = input[i];
                mark = i;
                found = true;
                next = false;
            }
            if (input[i].equals(or) || input[i].equals(and)) {
                next = true;
                splitter = input[i];
            }
            if (c != null && i == input.length - 1 && found) {
                if (!input[input.length - 1].equals("&")) {
                    load(list, c, input, mark + 1, input.length, splitter);
                } else {
                    load(list, c, input, mark + 1, input.length - 1, splitter);
                }
                found = false;
                splitter = null;
            } else if ((c != null && next)) {
                load(list, c, input, mark + 1, temp, splitter);
                found = false;
                splitter = null;
            }
            next = false;
            temp++;
        }
        for (Holder h : list){
            System.out.println(h);
        }
        for(Holder h: list){
            Command cmd = CommandFactory.createCommand(h.command, h.arg);
            result.add(cmd);
        }
        return result;
    }

    private static final void load(List<Holder> list, String c, String[] input, int from, int to, String splitter) {
        Holder h = new Holder(c, Arrays.copyOfRange(input, from, to));
        if (splitter != null) {
            h.setNext(splitter);
        }
        list.add(h);
        c = null;
    }

}
