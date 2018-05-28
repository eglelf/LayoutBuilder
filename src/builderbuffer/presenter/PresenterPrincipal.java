/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.presenter;

import builderbuffer.collection.Layout;
import builderbuffer.factory.FabricaTree;
import builderbuffer.factory.IFabricaTree;
import builderbuffer.factory.No;
import builderbuffer.model.Transacao;
import builderbuffer.observer.IObserverLayout;
import builderbuffer.view.TelaPrincipal;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author eglel
 */
public class PresenterPrincipal implements IObserverLayout {

    private TelaPrincipal view;
    private PresenterInputText presenterInputText;

    private JTree arvore;
    private JPopupMenu popMenuLayout;
    private JPopupMenu popMenuTransacao;
    private No noClicado;

    public PresenterPrincipal() {
        Layout.getInstance().setNome("NovoLayout");
        Layout.getInstance().addObserver(this);
        this.view = new TelaPrincipal();                
        criarPopupsMenus();

        view.getMenuItemSair().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        criarArvore();
        this.view.setVisible(true);
    }

    public void criarPopupsMenus() {
        //Menu Layout
        this.popMenuLayout = new JPopupMenu();
        JMenuItem opcaoNovaTransacao = new JMenuItem("Nova Transacao");
        opcaoNovaTransacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterInputText presenterNT = new PresenterInputText(PresenterInputText.STATE_NOVA_TRANSACAO);
            }
        });
        JMenuItem opcaoRenomearLayout = new JMenuItem("Renomear");
        opcaoRenomearLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterInputText presenterNT = new PresenterInputText(PresenterInputText.STATE_RENOMEAR_LAYOUT);
            }
        });
        popMenuLayout.add(opcaoNovaTransacao);
        popMenuLayout.add(opcaoRenomearLayout);

        //Menu Transacao
        this.popMenuTransacao = new JPopupMenu();
        JMenuItem opcaoRenomearTransacao = new JMenuItem("Renomear");
        JMenuItem opcaoExcluirTransacao = new JMenuItem("Excluir");
        opcaoExcluirTransacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (noClicado != null) {
                    int response = JOptionPane.showConfirmDialog(null, "Deseja Excluir a Transação?");
                    if (response == JOptionPane.YES_OPTION) {
                        Layout layout = Layout.getInstance();
                        String nomeTransacao = (String) noClicado.getUserObject();
                        Transacao t = layout.getTransacaoByName(nomeTransacao);
                        if (t != null) {
                            layout.removeTransacao(t);
                            update();
                        }
                    }
                }
            }
        });
        popMenuTransacao.add(opcaoExcluirTransacao);
        popMenuTransacao.add(opcaoRenomearTransacao);
    }

    public void criarArvore() {
        Layout l = Layout.getInstance();
        UIManager.put("Tree.expandedIcon", new WindowsTreeUI.ExpandedIcon());
        UIManager.put("Tree.collapsedIcon", new WindowsTreeUI.CollapsedIcon());

        IFabricaTree fabricaArvore = new FabricaTree();
        this.arvore = fabricaArvore.criarArvore(l);

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) arvore.getCellRenderer();
        arvore.setRootVisible(true);

        //Coloca árvore na tela
        view.getjScrollPaneTree().remove(view.getTreeNavigator());
        view.getjScrollPaneTree().setViewportView(arvore);

        arvore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent eventoDoMouse) {
                if (!arvore.isSelectionEmpty()) {
                    No no = (No) arvore.getLastSelectedPathComponent();
                    if (eventoDoMouse.getClickCount() >= 2 && eventoDoMouse.getButton() == MouseEvent.BUTTON1) { //Exibir tela
                        exibirTela(no);
                    } else if (eventoDoMouse.getClickCount() == 1 && eventoDoMouse.getButton() == MouseEvent.BUTTON3) { //Botão esquerdo, exibir Menu
                        exibirMenu(no, eventoDoMouse);
                    }
                }
            }
        });
    }

    public void exibirTela(No no) {
        System.out.println("Exibir tela");
    }

    public void exibirMenu(No no, MouseEvent e) {
        this.noClicado = no;
        if (no.getTipo().equals(No.LAYOUT)) {
            popMenuLayout.show((Component) e.getSource(), e.getX(), e.getY());
        } else if (no.getTipo().equals(No.TRANSACAO)) {
            popMenuTransacao.show((Component) e.getSource(), e.getX(), e.getY());
        }
    }

    @Override
    public void update() {
        criarArvore();
    }

}
