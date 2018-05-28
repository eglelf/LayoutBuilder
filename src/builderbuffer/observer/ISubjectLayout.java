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
public interface ISubjectLayout {
    public void addObserver(IObserverLayout o);
    public void removeObserver(IObserverLayout o);
    public void notificar();
}
