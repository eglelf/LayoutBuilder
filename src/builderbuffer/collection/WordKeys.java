/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.collection;

import builderbuffer.chain.ProcessadorWordKey;
import builderbuffer.model.Transacao;
import builderbuffer.observer.IObserverTransacao;
import java.util.ArrayList;

/**
 *
 * @author eglel
 */
public class WordKeys implements IObserverTransacao {

    public static final String OCCURS = "OCCURS";
    public static final String FIM_OCCURS = "FIM OCCURS";
    public static final String GRUPO = "GRUPO";

    private ArrayList<Integer> linhasComWordKey;
    private ProcessadorWordKey processador;
    private Transacao transacao;
    private int inicioOccurs;
    private int fimOccurs;

    public WordKeys(Transacao transacao) {
        this.transacao = transacao;
        this.linhasComWordKey = new ArrayList<>();
        this.processador = new ProcessadorWordKey();
        this.inicioOccurs = -1;
        this.fimOccurs = -1;
    }
    
    private void detectarOccurs(String textoCampos) {

        ArrayList<String> linhas = new ArrayList<>();
        String linha = "";
        for (int i = 0; i < textoCampos.length(); i++) {
            if (textoCampos.charAt(i) != '\n') {
                linha += textoCampos.charAt(i);

                if (i == textoCampos.length() - 1) {
                    linhas.add(linha);
                    break;
                }
            } else {
                linhas.add(linha + "\n");
                linha = "";
            }
        }

        int iniOcc = -1;
        int fimOcc = -1;
        for (int i = 0; i < linhas.size(); i++) {
            String value;
            String[] campos = linhas.get(i).split("\t");
            if (campos != null && campos.length >= 4) {
                if (campos[3] != null) {
                    value = campos[3].replace("\n", "");
                    String wk = processador.getWordKey(value);
                    switch (wk) {
                        case WordKeys.OCCURS:
                            iniOcc = i;
                            break;

                        case WordKeys.FIM_OCCURS:
                            fimOcc = i;
                            break;

                    }
                }
            }
        }

        if (iniOcc != -1 && fimOcc != -1) {
            this.inicioOccurs = iniOcc;
            this.fimOccurs = fimOcc;
        } else {
            this.inicioOccurs = -1;
            this.fimOccurs = -1;
        }
    }

    private void detectarTodasWordKey(String textoCampos) {
        ArrayList<String> linhas = new ArrayList<>();
        String linha = "";
        for (int i = 0; i < textoCampos.length(); i++) {
            if (textoCampos.charAt(i) != '\n') {
                linha += textoCampos.charAt(i);

                if (i == textoCampos.length() - 1) {
                    linhas.add(linha);
                    break;
                }
            } else {
                linhas.add(linha + "\n");
                linha = "";
            }
        }

        this.linhasComWordKey.clear();
        for (int i = 0; i < linhas.size(); i++) {
            String[] campos = linhas.get(i).split("\t");
            
            boolean achou = false;
            for(String c: campos){
                if(processador.isWordKey(c)){
                    achou = true;
                    break;
                }
            }
            if(achou){
                this.linhasComWordKey.add(i);
            }
        }
    }

    public ArrayList<Integer> getLinhasComWordKey() {
        return linhasComWordKey;
    }

    public int getInicioOccurs() {
        return inicioOccurs;
    }

    public int getFimOccurs() {
        return fimOccurs;
    }

    @Override
    public void update() {
        detectarOccurs(this.transacao.getTextoCampos());
        detectarTodasWordKey(this.transacao.getTextoCampos());
    }

}
