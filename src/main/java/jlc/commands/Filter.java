/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 *
 * @author desolation
 */
public class Filter implements FilenameFilter{
    private Pattern pattern;

    public Filter(final String regex) {
        this.pattern = Pattern.compile(regex);
    }
    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
    
}
