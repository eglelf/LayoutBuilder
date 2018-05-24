/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.model;

import builderbuffer.collection.WordKeys;
import java.util.ArrayList;
import builderbuffer.observer.SubjectTransacao;
import builderbuffer.observer.IObserverTransacao;

/**
 *
 * @author eglel
 */
public class Transacao implements SubjectTransacao {

    private String textoCampos;
    private WordKeys wordKeys;
    private ArrayList<IObserverTransacao> observadores;

    public Transacao() {
        this.observadores = new ArrayList<>();
        this.wordKeys = new WordKeys(this);
        this.textoCampos = "";        
        addObserver(wordKeys);
    }

    public ArrayList getLinhasComWordKey() {
        return this.wordKeys.getLinhasComWordKey();
    }

    public int getInicioOccurs() {
        return this.wordKeys.getInicioOccurs();
    }

    public int getFimOccurs() {
        return this.wordKeys.getFimOccurs();
    }

    public String getTextoCampos() {
        return textoCampos;
    }

    public void setTextoCampos(String textoCampos) {
        this.textoCampos = textoCampos;
        System.out.println("[" + textoCampos + "]");
        notificar();
    }

    @Override
    public void addObserver(IObserverTransacao o) {
        this.observadores.add(o);
    }

    @Override
    public void removeObserver(IObserverTransacao o) {
        this.observadores.remove(o);
    }

    @Override
    public void notificar() {
        for (IObserverTransacao o : this.observadores) {
            o.update();
        }
    }

}
