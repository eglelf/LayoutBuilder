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
public class HandlerWordKeyFIMOCCURS extends AbstractHandlerWordKey{

    public HandlerWordKeyFIMOCCURS() {
        super(WordKeys.FIM_OCCURS);
        //padroes.add("<<FIM OCCURS>>");
        //padroes.add("<<FIM OCCURS>>\n");
        //padroes.add("<<FIM OCCURS>>.*?");
        this.padroes.add("<<\\s*FIM OCCURS\\s*>>.*?");
    }

    @Override
    public String handler(String str) {
        return "<html><b>&lt;&lt;FIM OCCURS&gt;&gt;</b></html>";
    }
    
}
