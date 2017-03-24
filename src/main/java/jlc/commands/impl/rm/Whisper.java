/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.rm;

import java.io.IOException;
import jlc.commands.Command;
import jlc.commands.impl.AbstractCommand;

/**
 * The class Whisper allows connected clients to send message via printing
 * whisper "message body".
 * @author desolation
 */
public class Whisper extends AbstractCommand implements Command{
    public static final String NAME = "whisper";
    private static final String REGEX = "^\"[a-zA-Z0-9?!-.,*+=()@ ]*\\\"$";
    private final String message;
    private EchoThread from;

    public Whisper(final String message, final EchoThread from) {
        this.message = message;
        this.from = from;
    }
    public Whisper(final String message){
        this.message = message;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    /**
     * @param from represents information about user which whispering, such as name, ip-address or smth another that identifies him
     */
    public final void setFrom(final EchoThread from){
        this.from = from;
    }

    @Override
    public Boolean call() throws IOException{
        if(RemoteMode.CLIENT_LIST.isEmpty()){
            System.out.println("No connected clients.");
            return false;
        }
        if(message.matches(REGEX)){
            if (from != null){
            for (EchoThread et : RemoteMode.CLIENT_LIST)
                    if(et != from)
                    et.write(from.getSocket().getInetAddress().toString().toUpperCase() + " whispers: " + message);
            }else
                for(EchoThread et : RemoteMode.CLIENT_LIST)
                    et.write(message);
        }
        return true;
    }
}
