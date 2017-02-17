/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.rm;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import jlc.commands.Command;
import jlc.commands.impl.AbstractCommand;
import jlc.commands.impl.ActiveCommandsManager;
import jlc.view.TextStyle;

/**
 *
 * @author desolation
 */
public class Who extends AbstractCommand implements Command{
    public static final String NAME = "who";

    @Override
    public Boolean call() throws Exception {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(currentOutput,Charset.forName(ENCODING)))){
            if(RemoteMode.CLIENT_LIST.isEmpty())
                bw.write(TextStyle.colorText("No clients connected.\n", TextStyle.Color.RED));
            else
            for(EchoThread s : RemoteMode.CLIENT_LIST){
                bw.write(s.toString()+"\n");
                bw.flush();
            }
            ActiveCommandsManager.remove(this.getID());
        }
        return true;
    }


    @Override
    public String getName() {
        return NAME;
    }
    
}
