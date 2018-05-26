/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.presenter;

import builderbuffer.chain.WordKeys;
import builderbuffer.model.Transacao;
import builderbuffer.view.CellRenderOcurrs;
import builderbuffer.view.TelaPrincipalTeste;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import builderbuffer.observer.IObserverTransacao;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author eglel
 */
public class PresenterTransacao implements IObserverTransacao {

    //Tela    
    private TelaPrincipalTeste  tela;
    private DefaultTableModel   tm;
    private CellRenderOcurrs    cellRender;

    //Model
    private PresenterTelaEditarCampos presenterTelaEditarCampos;
    private Transacao                 transacao;
    

    public PresenterTransacao() {
        this.tela = new TelaPrincipalTeste();
        this.tela.setTitle("Layout Builder");

        //Configurar Tabela
        Color[] cinzas = new Color[3];
        cinzas[2] = new Color(192, 192, 192);
        cinzas[1] = new Color(212, 212, 212);
        cinzas[0] = new Color(232, 232, 232);
        
        this.cellRender = new CellRenderOcurrs(Color.white, cinzas);
        Object colunas[] = {"Campo", "Sequência", "Tamanho", "Formato", "Variável"};
        tm = new DefaultTableModel(colunas, 0);
        this.tela.getTblCampos().setDefaultRenderer(Object.class, this.cellRender);
        this.tela.getTblCampos().setModel(tm);
        //this.tela.getTblCampos().getColumnModel().getColumn(0).setMaxWidth(200);
        //this.tela.getTblCampos().getColumnModel().getColumn(1).setMaxWidth(100);
        //this.tela.getTblCampos().getColumnModel().getColumn(2).setMaxWidth(60);
        //this.tela.getTblCampos().getColumnModel().getColumn(3).setMaxWidth(160);
        //this.tela.getTblCampos().getColumnModel().getColumn(4).setMaxWidth(200);

        //Configurar Listners
        this.tela.getTblCampos().getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {                
                if (transacao != null) {
                    String txtCampos = getDadosFromTable();
                    transacao.setTextoCampos(txtCampos);
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                //Do nothing
            }
        });

        //setDadosTabela(this.transacao.getTextoCampos());
        this.tela.getBtnEditarCampos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transacao != null && presenterTelaEditarCampos != null) {
                    String txtCampos = getDadosFromTable();
                    transacao.setTextoCampos(txtCampos);
                    presenterTelaEditarCampos.setVisible(true);
                }
            }
        });

        this.tela.setLocationRelativeTo(null);
        this.tela.setVisible(true);
    }

    @Override
    public void update() {
        if (transacao != null) {
            WordKeys wk = WordKeys.getInstance();
            String textoCampos = transacao.getTextoCampos();
            
            ArrayList<Integer> linhasComWK = wk.getIndiceLinhasComWK(textoCampos);
            Map<Integer, Integer> indicesOccurs = wk.getIndiceOccurs(textoCampos);
            
            cellRender.setLinhasComWK(linhasComWK);
            cellRender.setLinhasInicioFimOccurs(indicesOccurs);
            setDadosTabela(textoCampos);
        }
        tela.getTblCampos().updateUI();
    }
    
    public void setDadosTabela(String txtCampos) {

        String[] linhas = txtCampos.split("\n");

        tm.setNumRows(0);
        for (int i = 0; i < linhas.length; i++) {
            String campo0;
            String campo1;
            String campo2;
            String campo3;
            String campo4;

            String[] campos = linhas[i].split("\t");
            if (campos != null && campos.length == 5) {
                campo0 = campos[0].replace("\n", "");
                campo1 = campos[1].replace("\n", "");
                campo2 = campos[2].replace("\n", "");
                campo3 = campos[3].replace("\n", "");
                campo4 = campos[4].replace("\n", "");
                tm.addRow(new Object[]{campo0, campo1, campo2, campo3, campo4});
            } else if (campos != null && campos.length == 4) {
                campo0 = campos[0].replace("\n", "");
                campo1 = campos[1].replace("\n", "");
                campo2 = campos[2].replace("\n", "");
                campo3 = campos[3].replace("\n", "");
                tm.addRow(new Object[]{campo0, campo1, campo2, campo3});
            } else if (campos != null && campos.length == 3) {
                campo0 = campos[0].replace("\n", "");
                campo1 = campos[1].replace("\n", "");
                campo2 = campos[2].replace("\n", "");
                tm.addRow(new Object[]{campo0, campo1, campo2});
            } else if (campos != null && campos.length == 2) {
                campo0 = campos[0].replace("\n", "");
                campo1 = campos[1].replace("\n", "");
                tm.addRow(new Object[]{campo0, campo1, ""});
            } else if (campos != null && campos.length >= 1) {
                campo0 = campos[0].replace("\n", "");
                tm.addRow(new Object[]{campo0, "", ""});
            } else if (campos != null && campos.length == 0) {
                tm.addRow(new Object[]{"", "", ""});
            }
        }
        //this.transacao.detectarOccurs();
    }

    public String getDadosFromTable() {
        int qtdRows = tm.getRowCount();
        StringBuilder linhas = new StringBuilder();
        for (int i = 0; i < qtdRows; i++) {
            String campo     = (tm.getValueAt(i, 0) == null) ? "" : ((String) tm.getValueAt(i, 0));
            String sequencia = (tm.getValueAt(i, 1) == null) ? "" : ((String) tm.getValueAt(i, 1));
            String tamanho   = (tm.getValueAt(i, 2) == null) ? "" : ((String) tm.getValueAt(i, 2));
            String formato   = (tm.getValueAt(i, 3) == null) ? "" : ((String) tm.getValueAt(i, 3));
            String variavel  = (tm.getValueAt(i, 4) == null) ? "" : ((String) tm.getValueAt(i, 4));

            if (campo.isEmpty() && sequencia.isEmpty() && tamanho.isEmpty() && formato.isEmpty() && variavel.isEmpty()) {
                if (i == 0) {
                    linhas.append("");
                } else {
                    linhas.append("\n");
                }
            } else if (sequencia.isEmpty() && tamanho.isEmpty() && formato.isEmpty() && variavel.isEmpty()) {
                linhas.append(campo);
                linhas.append("\n");
            } else if (tamanho.isEmpty() && formato.isEmpty() && variavel.isEmpty()) {
                linhas.append(campo);
                linhas.append("\t");
                linhas.append(sequencia);
                linhas.append("\n");
            } else if (formato.isEmpty() && variavel.isEmpty()) {
                linhas.append(campo);
                linhas.append("\t");
                linhas.append(sequencia);
                linhas.append("\t");
                linhas.append(tamanho);
                linhas.append("\n");
            } else if (variavel.isEmpty()) {
                linhas.append(campo);
                linhas.append("\t");
                linhas.append(sequencia);
                linhas.append("\t");
                linhas.append(tamanho);
                linhas.append("\t");
                linhas.append(formato);
                linhas.append("\n");
            } else {
                linhas.append(campo);
                linhas.append("\t");
                linhas.append(sequencia);
                linhas.append("\t");
                linhas.append(tamanho);
                linhas.append("\t");
                linhas.append(formato);
                linhas.append("\t");
                linhas.append(variavel);
                linhas.append("\n");
            }
        }
        return linhas.toString();
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
        if(this.transacao != null){
            this.transacao.addObserver(this);
        }
    }

    public void setPresenterTelaEditarCampos(PresenterTelaEditarCampos presenterEditarCampos) {
        this.presenterTelaEditarCampos = presenterEditarCampos;
    }
}
