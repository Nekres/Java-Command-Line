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
    private static HashMap<String, Class<? extends Command>> settings = new HashMap<>();

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("user.dir", currentDir);
        File file = new File("settings.xml");
        if (!file.exists()) {
            Settings.setDefault(file);
            System.out.println("Run with default settings...");
        }
        Settings set = (Settings) new JAXBParser().getObject(new File("settings.xml"), Settings.class); //settings from "settings.xml" file
        HashMap<String, Class<? extends Command>> settings = new HashMap<>();
        settings.put(set.getDir(), Dir.class);
        settings.put(set.getDirectoryTree(), DirectoryTree.class);
        settings.put(set.getChangeDirectory(), ChangeDirectory.class);
        boolean running = true, daemon = false;
        System.out.println("Java command line.");
        while (running) {
            System.setProperty("user.dir", currentDir);
            System.out.print(currentDir + ":");
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
            } catch(NoSuchCommandException e){
                ProcessBuilder pb = new ProcessBuilder("gedit", "pom.xml");
                Process p = pb.start();
            }
            
        }
    }

    public static List<Command> analyze(HashMap<String, Class<? extends Command>> settings, String[] input) throws NoSuchCommandException, BadCommandArgumentException {
        String or = "||";
        String and = "&&";
        List<Holder> list = new ArrayList<>();
        List<Command> result = new ArrayList<>();
        if (!settings.containsKey(input[0])) {
            throw new NoSuchCommandException(); // если первый аргумент не комманда - нет смысла проверять всю строку.
        }
        Class<? extends Command> c = null;
        int temp = 0, mark = 0;
        boolean next = false, found = false;
        String splitter = null;
        for (int i = 0; i < input.length; i++) {
            for (String ks : settings.keySet()) {
                if (input[i].equals(ks)) {
                    c = settings.get(ks);
                    mark = i; //индекс комманды
                    found = true;
                    break;
                }
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
        for(Holder h: list){
            Command cmd = CommandFactory.createCommand(h.command, currentDir, h.arg);
            result.add(cmd);
        }
        return result;
    }

    private static final void load(List<Holder> list, Class<? extends Command> c, String[] input, int from, int to, String splitter) {
        Holder h = new Holder(c, Arrays.copyOfRange(input, from, to));
        if (splitter != null) {
            h.setNext(splitter);
        }
        list.add(h);
        c = null;
    }

}
