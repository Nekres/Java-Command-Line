/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.parse;

import java.io.File;
import javax.xml.bind.JAXBException;

/**
 *
 * @author desolation
 */
public interface Parser {
    
    public Object getObject(File file, Class c) throws JAXBException;
}
