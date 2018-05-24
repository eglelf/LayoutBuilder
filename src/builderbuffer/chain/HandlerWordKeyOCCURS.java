/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.chain;

import builderbuffer.collection.WordKeys;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author eglel
 */
public class HandlerWordKeyOCCURS extends AbstractHandlerWordKey {

    public HandlerWordKeyOCCURS() {
        super(WordKeys.OCCURS);
        this.padroes.add("<<OCCURS \\d+ TIMES>>\n");
        this.padroes.add("<<OCCURS \\d+ TIMES>>");
        this.padroes.add("<<OCCURS \\d+ TIMES>>.*?");
    }
    

    @Override
    public String handler(String str) {
        int times = str.indexOf("TIMES");
        String valor = str.substring(9, times - 1);
        return "<html><b>&lt;&lt;OCCURS <font color='blue'>" + valor + "</font> TIMES&gt;&gt;</b></html>";
    }

}
