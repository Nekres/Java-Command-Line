/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.Scanner;
import jlc.commands.CommandController;

/**
 *
 * @author desolation
 */
public class JLC {
    private static final Scanner scan = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Java command line.");
        while(running){
            String currentDir = System.getProperty("user.dir");
            System.out.print(currentDir+":");
            String command = scan.nextLine();
            String arr[] = command.split(" ");
            switch(arr[0]){
                case "dir": CommandController.dir(currentDir);
                break;
                case "cd":System.out.println("cd");;
                break;
                case "pwd":System.out.println("pwd");;
                break;
            }
        }
    }
    
}
