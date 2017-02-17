/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.parse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBException;
import jlc.CommandWrapper;
import jlc.Settings;
import jlc.commands.Command;
import jlc.commands.CommandFactory;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.NoSuchCommandException;
import jlc.parse.impl.JAXBParser;

/**
 *
 * @author desolation
 */
public class CLParser {
    private static final String OR = "||".intern(); 
    private static final String AND = "&&".intern();
    
    public static List<Command> analyze(List<String> settings, String[] input) throws NoSuchCommandException, BadCommandArgumentException {
        List<CommandWrapper> commands = new ArrayList<>();
        List<Command> result = new ArrayList<>();
        String c = input[0];
        int mark = 0;
        boolean next = false, found = true;
        String splitter = null;
        for (int i = 0; i < input.length; i++) {
            for (String ks : settings) {
                if (input[i] == ks.intern()) {  //if (input[i].equals(ks)) { look for a bug here
                    c = ks;
                    mark = i; //индекс комманды
                    found = true;
                    next = false;
                    break;
                }
            }
            if ((input[i].intern() == OR || input[i].intern() == AND) && i != 0) {
                next = true;
                splitter = input[i];
                load(commands, c, input, mark + 1, i, splitter);
                found = false;
                splitter = null;
                continue;
            }
            if (next) {
                c = input[i];
                mark = i;
                found = true;
            }
            if (c != null && i == input.length - 1 && found) {
                if (!input[input.length - 1].equals("&")) {
                    load(commands, c, input, mark + 1, input.length, splitter);
                } else {
                    load(commands, c, input, mark + 1, input.length - 1, splitter);
                }
                found = false;
                splitter = null;
            }
            next = false;
        }
        for (CommandWrapper h : commands) {
            Command cmd = CommandFactory.createCommand(h.command, h.arg);
            result.add(cmd);
        }
        return result;
    }

    private static final void load(List<CommandWrapper> list, String c, String[] input, int from, int to, String splitter) {
        CommandWrapper h = new CommandWrapper(c, Arrays.copyOfRange(input, from, to));
        if (splitter != null) {
            h.setNext(splitter);
        }
        list.add(h);
        c = null;
    }
    private static final void parseUserInput(String input){
        
    }
}
