/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.chain;

import java.util.ArrayList;

/**
 *
 * @author eglel
 */
public class ProcessadorWordKey {
    
    private ArrayList<AbstractHandlerWordKey> tratadores;

    public ProcessadorWordKey() {
        this.tratadores = new ArrayList<>();
        this.tratadores.add(new HandlerWordKeyOCCURS());
        this.tratadores.add(new HandlerWordKeyFIMOCCURS());
        this.tratadores.add(new HandlerWordKeyGRUPO());
    }
    
    public String tratarWordKey(String str){
        String novaStr = str;
        for(AbstractHandlerWordKey tratador: this.tratadores){
            if(tratador.accept(str)){
                novaStr = tratador.handler(str);
            }
        }       
        
        return novaStr;
    }
    
    public boolean isWordKey(String str){
        for(AbstractHandlerWordKey tratador: this.tratadores){
            if(tratador.accept(str)){
                return true;
            }
        }
        return false;
    }
    
    public String getWordKey(String str){
        for(AbstractHandlerWordKey tratador: this.tratadores){
            if(tratador.accept(str)){
                return tratador.getWordKey();
            }
        }
        return "";
    }
    
}