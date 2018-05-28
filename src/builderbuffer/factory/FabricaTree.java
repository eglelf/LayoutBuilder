/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.factory;

import builderbuffer.collection.Layout;
import builderbuffer.model.Transacao;
import java.util.ArrayList;
import javax.swing.JTree;

/**
 * @author eglel
 */
public class FabricaTree implements IFabricaTree {

    @Override
    public JTree criarArvore(Layout layout) {
        No noLayout = new No(No.LAYOUT, layout.getNome());
        for (Transacao t:  layout.getTransacoes()) {
            No transacao = new No(No.TRANSACAO, t.getNome());
            noLayout.add(transacao);
        }
        return new JTree(noLayout);
    }    
}