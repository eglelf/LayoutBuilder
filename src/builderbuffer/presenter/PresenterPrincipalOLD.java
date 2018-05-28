/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.presenter;

import builderbuffer.collection.Layout;
import builderbuffer.view.TelaPrincipalOLD;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 *
 * @author eglel
 */
public class PresenterPrincipalOLD {

    private TelaPrincipalOLD tela;
    private Layout transacoes;
    private JTabbedPane tbGuias;

    public PresenterPrincipalOLD() {
        //this.transacoes = new Layout("Nome");

        this.tela = new TelaPrincipalOLD();
        this.tela.setTitle("Layout Builder");

        this.tbGuias = new JTabbedPane();
        //this.tbGuias.
        //this.tbGuias.setUI(new WindowsTabbedPaneUI());
        this.tbGuias.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        JTabbedPane tabMais = new JTabbedPane();
        tabMais.setName("<html><b>+</b></html>");
        this.tbGuias.add(tabMais);
        
        Icon iconPlus = new ImageIcon(getClass().getResource("/builderbuffer/data/plus.png"));
        
        JButton btn = new JButton(iconPlus);
        btn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        btn.setPreferredSize(new Dimension(10, 10));
        btn.setFocusable(false);
        btn.setContentAreaFilled(false);
        
        JPanel panel = new JPanel();
        panel.add(btn);
        panel.setOpaque(false);//Transparent
        
        this.tbGuias.setTabComponentAt(0, panel);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addGuiaTransacao();
                }
            }

        });

        this.tbGuias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int index = tbGuias.getSelectedIndex();
                    if (index >= 0) {
                        Component comp = tbGuias.getComponentAt(index);
                        if (comp instanceof JTabbedPane) {
                            JTabbedPane guia = (JTabbedPane) comp;
                            if (guia.getName().contains("+")) {
                                addGuiaTransacao();
                            }
                        }
                    }
                }
            }

        });

        //tela.getPnlGuias().setLayout(new FlowLayout());        
        tela.getPnlGuias().setLayout(new BorderLayout());//BorderLayout para que as guias sejam redimensionalizáveis.
        //tela.getPnlGuias().setUI(new LayoutBuilderTabbedPaneUI());
        this.tbGuias.setPreferredSize(new Dimension(800, 450));
        //this.tbGuias.setLayout(new BorderLayout());
        //this.tbGuias.setBorder(new javax.swing.border.EtchedBorder());
        tela.getPnlGuias().add(this.tbGuias);
        
        this.tela.setLocationRelativeTo(null);
        this.tela.setVisible(true);
    }

    public void addGuiaTransacao() {
        JTabbedPane novaGuia = new JTabbedPane();
        //novaGuia.setUI(new WindowsTabbedPaneUI());
        novaGuia.setName("Nova transacao");        

        Icon iconClose = new ImageIcon(getClass().getResource("/builderbuffer/data/close1.png"));

        JLabel lbl = new JLabel("Nova transação", SwingConstants.RIGHT);
        lbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton btn = new JButton(iconClose);
        btn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        btn.setPreferredSize(new Dimension(10, 10));
        btn.setFocusable(false);
        btn.setContentAreaFilled(false);

        JPanel panel = new JPanel();
        panel.add(lbl);
        panel.add(btn);
        panel.setOpaque(false);//Transparent

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean achou = false;
                for (int i = 0; i < tbGuias.getTabCount(); i++) {
                    Component c = tbGuias.getTabComponentAt(i);
                    if (c instanceof JPanel) {
                        JPanel pn = (JPanel) c;
                        Component[] cs = pn.getComponents();
                        for (Component cpn : cs) {
                            if (cpn instanceof JButton) {
                                if (cpn.equals(btn)) {
                                    achou = true;
                                    break;
                                }
                            }
                        }

                        if (achou) {
                            tbGuias.setSelectedIndex(i);
                            tbGuias.removeTabAt(i);
                            System.out.println("removeu " + i);
                            break;
                        }
                    }
                }
            }
        });

        int indice = this.tbGuias.getSelectedIndex();
        this.tbGuias.add(novaGuia, indice);
        this.tbGuias.setTabComponentAt(indice, panel);
        this.tbGuias.setSelectedIndex(indice);
        this.tbGuias.updateUI();
    }

}
