/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.factory;

import builderbuffer.collection.Layout;
import javax.swing.JTree;

/**
 *
 * @author eglel
 */
public interface IFabricaTree {
    public JTree criarArvore(Layout layout);
}
