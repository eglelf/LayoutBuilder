/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.chain;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author eglel
 */
public abstract class AbstractHandlerWordKey {

    protected String wordKey;
    protected ArrayList<String> padroes;

    public AbstractHandlerWordKey(String wordKey) {
        this.wordKey = wordKey;
        this.padroes = new ArrayList<>();
    }

    public boolean accept(String str) {
        Pattern p = null;
        Matcher matcher = null;
        boolean resultado = false;
        for (String padrao : padroes) {
            p = Pattern.compile(padrao);
            matcher = p.matcher(str);
            resultado = resultado || matcher.matches();
        }

        return resultado;
    }    

    public String getWordKey() {
        return wordKey;
    }
    
    public abstract String handler(String str);
}
