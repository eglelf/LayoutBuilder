/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer;

import builderbuffer.presenter.PresenterPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author eglel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                try {
                    UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                new PresenterPrincipal();
//                //Criar Transacao
//                Transacao                   transacao     = new Transacao();                
//                //Criar Presenters
//                PresenterTransacao          pTransacao    = new PresenterTransacao();
//                PresenterTelaEditarCampos   pEditarCampos = new PresenterTelaEditarCampos();
//                
//                //Setar transacao
//                pTransacao.setTransacao(transacao);
//                pEditarCampos.setTransacao(transacao);
//                
//                //Setar Preseter
//                pTransacao.setPresenterTelaEditarCampos(pEditarCampos);
//                
//                //Atualizar telas para ler a transacao
//                pTransacao.update();
//                pEditarCampos.update();
            }
        });
    }

}
