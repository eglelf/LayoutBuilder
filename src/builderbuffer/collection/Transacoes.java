/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.collection;

import builderbuffer.model.Transacao;
import java.util.ArrayList;

/**
 *
 * @author eglel
 */
public class Transacoes {

    private static Transacoes instance;
    private ArrayList<Transacao> transacoes;

    private Transacoes() {
    }
    
    public static Transacoes getInstance(){
        if(instance == null){
            instance = new Transacoes();
        }
        return instance;
    }
    
    public Transacao getNewTransacao(){
        Transacao transacao = new Transacao();
        this.transacoes.add(transacao);
        return transacao;
    }
    
    public void removeTransacao(Transacao t){
        this.transacoes.remove(t);
    }
    
    public void addTransacao(Transacao t){
        this.transacoes.add(t);
    }
        
}
