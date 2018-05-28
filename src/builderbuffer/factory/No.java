/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.factory;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author eglel
 */
public class No extends DefaultMutableTreeNode {

    public static String LAYOUT     = "LAYOUT";
    public static String TRANSACAO  = "TRANSACAO";    

    private String tipo;

    public No(String tipo, Object userObject) {
        super(userObject);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
