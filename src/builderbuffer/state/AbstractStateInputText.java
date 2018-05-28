/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.state;

import builderbuffer.presenter.PresenterInputText;
import builderbuffer.view.TelaInputText;

/**
 *
 * @author eglel
 */
public abstract class AbstractStateInputText{ 
    protected PresenterInputText presenter;
    protected TelaInputText view;

    public AbstractStateInputText(PresenterInputText presenter) {
        this.presenter = presenter;        
        this.view = presenter.getView();
        configurarTela();
    }
    
    public abstract void configurarTela();
    
}
