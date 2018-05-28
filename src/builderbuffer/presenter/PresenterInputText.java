/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.presenter;

import builderbuffer.state.AbstractStateInputText;
import builderbuffer.state.StateInputTextCriarTransacao;
import builderbuffer.state.StateInputTextRenomearLayout;
import builderbuffer.view.TelaInputText;

/**
 *
 * @author eglel
 */
public class PresenterInputText {

    public static final String STATE_NOVA_TRANSACAO = "NOVA TRANSACAO";
    public static final String STATE_RENOMEAR_LAYOUT= "RENOMEAR LAYOUT";
    
    private TelaInputText view;
    private AbstractStateInputText state;    

    public PresenterInputText(String nomeState) {
        this.view = new TelaInputText();  
        if(nomeState.equals(STATE_NOVA_TRANSACAO)){
            this.state = new StateInputTextCriarTransacao(this);
        }else if(nomeState.equals(STATE_RENOMEAR_LAYOUT)){
            this.state = new StateInputTextRenomearLayout(this);
        }        
    }

    public void setVisible(boolean visible) {
        view.getBtnOk().setEnabled(false);
        view.setVisible(visible);
    }

    public TelaInputText getView() {
        return this.view;
    }

}
