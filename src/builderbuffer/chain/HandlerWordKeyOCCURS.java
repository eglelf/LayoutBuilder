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
public class HandlerWordKeyOCCURS extends AbstractHandlerWordKey {

    public HandlerWordKeyOCCURS() {
        super(WordKeys.OCCURS);
        //this.padroes.add("<<OCCURS \\d+ TIMES>>\n");
        //this.padroes.add("<<OCCURS \\d+ TIMES>>");
        //this.padroes.add("<<OCCURS \\d+ TIMES>>.*?");
        //Deve começar com <<
        //Pode ou não ter espaços depois
        //Deve ocorrer OCCURS
        //Deve conter pelo menos 1 espaço
        //Deve vir um número
        //Deve vir pelo menos 1 espaço
        //Deve ocorrer TIMES
        //Pode ou não ter espaços
        //Deve ocorrer >>
        //Pode ou não ter algo depois
        this.padroes.add("<<\\s*OCCURS\\s+\\d+\\s+TIMES\\s*>>.*?");        
    }
    

    @Override
    public String handler(String str) {
        int times = str.indexOf("TIMES");
        int occurs = str.indexOf("OCCURS");//Tem tamanho 6: 6 letras
        String valor = str.substring(occurs+6, times).trim();
        //System.out.println("Valor="+valor);
        //return "<html><b>&lt;&lt;OCCURS <font color='blue'>" + valor + "</font> TIMES&gt;&gt;</b></html>";
        return "<html><b>&lt;&lt;OCCURS <font color='blue'>" + valor + "</font> TIMES&gt;&gt;</b></html>";
    }

}
