/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.chain;

/**
 *
 * @author eglel
 */
public class HandlerWordKeyGRUPO extends AbstractHandlerWordKey {

    public HandlerWordKeyGRUPO() {
        super(WordKeys.GRUPO);
        //padroes.add("<<GRUPO>>.*?");
        //padroes.add("<<grupo>>.*?");
        //padroes.add("<<Grupo>>.*?");
        //padroes.add("<<GRUPO>>\n");
        padroes.add("<<\\s*(?i)grupo\\s*>>.*?");
    }

    @Override
    public String handler(String str) {
        return "<html><b>&lt;&lt;GRUPO&gt;&gt;</b></html>";
    }

}
