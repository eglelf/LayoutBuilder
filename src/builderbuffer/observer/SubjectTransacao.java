/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.observer;

/**
 *
 * @author eglel
 */
public interface SubjectTransacao {
    public void addObserver(IObserverTransacao o);
    public void removeObserver(IObserverTransacao o);
    public void notificar();
}
