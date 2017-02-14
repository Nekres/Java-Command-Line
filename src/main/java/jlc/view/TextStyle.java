/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.view;

/**
 *
 * @author desolation
 */
public class TextStyle {

    public enum Color {
        RED, GREEN, YELLOW, BLUE, MAGENTA,
        CYAN, WHITE, BLACK, BRIGHT;
    }

    public static String colorText(String text, Color color) {
        if (!System.getProperty("os.name").contains("Windows")) {
            int x = color.equals(Color.RED) ? 31
                    : color.equals(Color.GREEN) ? 32
                    : color.equals(Color.YELLOW) ? 33
                    : color.equals(Color.BLUE) ? 34
                    : color.equals(Color.MAGENTA) ? 35
                    : color.equals(Color.CYAN) ? 36
                    : color.equals(Color.WHITE) ? 37
                    : color.equals(Color.BLACK) ? 30
                    : color.equals(Color.BRIGHT) ? 1 : 0;
            return (char) 27 + "[" + x + "m" + text + (char) 27 + "[0m";
        }else return text;

    }
}
