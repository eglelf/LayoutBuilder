/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.state;

import builderbuffer.collection.Layout;
import builderbuffer.presenter.PresenterInputText;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author eglel
 */
public class StateInputTextRenomearLayout extends AbstractStateInputText {

    public StateInputTextRenomearLayout(PresenterInputText presenter) {
        super(presenter);
    }

    @Override
    public void configurarTela() {
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                view.dispose();
            }
        });

        view.getTxtNome().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                verificaTamanhoDoCampoTxt();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                verificaTamanhoDoCampoTxt();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }
        });

        this.view.getBtnOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String nome = view.getTxtNome().getText();
                Layout.getInstance().setNome(nome);
                view.setVisible(false);
                view.dispose();
            }
        });

        view.setLocationRelativeTo(null);
        view.getBtnOk().setEnabled(false);
        view.setVisible(true);
    }

    private void verificaTamanhoDoCampoTxt() {
        if (view.getTxtNome().getText().length() > 0) {
            view.getBtnOk().setEnabled(true);
        } else {
            view.getBtnOk().setEnabled(false);
        }
    }

}
