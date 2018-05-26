/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eglel
 */
public class WordKeys{

    public static final String OCCURS       = "OCCURS";
    public static final String FIM_OCCURS   = "FIM OCCURS";
    public static final String GRUPO        = "GRUPO";

    private ProcessadorWordKey processador;
    
    private static WordKeys instance;
    
    public static WordKeys getInstance(){
        if(instance == null){
            instance = new WordKeys();
        }
        return instance;
    }

    private WordKeys() {
        this.processador = new ProcessadorWordKey();
    }

    public Map<Integer,Integer> getIndiceOccurs(String textoCampos) {
        
        Map<Integer,Integer> occurs = new HashMap<>();
        ArrayList<Integer> inicio   = new ArrayList<>();        
        
        //A ideia é toda vez que encontrar um FIM, desempilhar um INICIO.
        //Se não houver um INICIO para desempinhas, ignora o FIM.
        String linhas[] = textoCampos.split("\n");
        for (int i = 0; i < linhas.length; i++) {
            String value;
            String[] campos = linhas[i].split("\t");
            
            if (campos != null && campos.length >= 4) {
                if (campos[3] != null) {
                    value = campos[3].replace("\n", "");
                    String wk = processador.getWordKey(value);
                    if(wk.equals(WordKeys.OCCURS)){
                        inicio.add(i);
                    }else if(wk.equals(WordKeys.FIM_OCCURS)){
                        if(inicio.size()>0){
                            int ini = inicio.get(inicio.size()-1);
                            occurs.put(ini, i);
                            inicio.remove(inicio.size()-1);
                        }
                    }                                        
                }
            }
        }

        return occurs;
    }

    //As WordKeys são reconhecidas apenas estando na 4 coluna(Formato)
    public ArrayList<Integer> getIndiceLinhasComWK(String textoCampos) {
        ArrayList<Integer> linhasComWK = new ArrayList<>();

        String linhas[] = textoCampos.split("\n");
        for (int i = 0; i < linhas.length; i++) {
            String[] campos = linhas[i].split("\t");
            if(campos.length>=4){
                if(campos[3]!=null){
                    String value = campos[3].replace("\n", "");
                    if(processador.isWordKey(value)){
                        linhasComWK.add(i);
                    }
                }
            }            
        }
        return linhasComWK;        
    }
}
