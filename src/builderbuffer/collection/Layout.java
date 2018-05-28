/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.collection;

import builderbuffer.model.Transacao;
import builderbuffer.observer.IObserverLayout;
import builderbuffer.observer.ISubjectLayout;
import java.util.ArrayList;

/**
 *
 * @author eglel
 */
public class Layout implements ISubjectLayout {

    private static Layout instance;
    private String nome;
    private ArrayList<Transacao> transacoes;
    private ArrayList<IObserverLayout> observers;

    public static Layout getInstance() {
        if (instance == null) {
            instance = new Layout();
        }
        return instance;
    }

    private Layout() {
        this.nome = "";
        this.transacoes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void removeTransacao(Transacao t) {
        this.transacoes.remove(t);
        notificar();
    }

    public void addTransacao(Transacao t) {
        this.transacoes.add(t);
        notificar();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        notificar();
    }

    public ArrayList<Transacao> getTransacoes() {
        return this.transacoes;
    }

    public Transacao getTransacaoByName(String nome) {
        for (Transacao t : this.transacoes) {
            if (nome.equals(t.getNome())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void addObserver(IObserverLayout o) {
        if (!this.observers.contains(o)) {
            this.observers.add(o);
        }
    }

    @Override
    public void removeObserver(IObserverLayout o) {
        this.observers.remove(o);
    }

    @Override
    public void notificar() {
        for(IObserverLayout o: this.observers){
            o.update();
        }
    }
}
