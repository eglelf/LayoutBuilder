/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.view;

import builderbuffer.chain.ProcessadorWordKey;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author eglel
 */
public class CellRenderOcurrs extends DefaultTableCellRenderer {

    private ProcessadorWordKey processador;
    private ArrayList<Integer> linhasComWK;
    private Map<Integer, Integer> linhasInicioFimOccurs;
    private Color[] corOccurs;
    private Color corDefalt;

    public CellRenderOcurrs(Color corDefalt, Color[] corOccurs) {
        this.processador = new ProcessadorWordKey();
        this.linhasComWK = new ArrayList<>();
        this.linhasInicioFimOccurs = new HashMap<>();

        this.corOccurs = corOccurs;
        this.corDefalt = corDefalt;
    }

    public Color getColorOccurs(int ocorrenciaAtual) {
        int totalCores = corOccurs.length;

        if (ocorrenciaAtual >= totalCores) {
            return corOccurs[totalCores - 1];
        } else if (ocorrenciaAtual >= 0 && ocorrenciaAtual < totalCores) {
            return corOccurs[ocorrenciaAtual];
        } else {
            return corDefalt;
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //Tratar/Formatar celula com WK
        if (column == 3 && value != null && linhasComWK.contains(row)) {
            setText(processador.tratarWordKey(value.toString()));
        }

        if (l != null) {
            //Destacar linhas que estão contidas dentro das ocorrências
            if (!linhasInicioFimOccurs.isEmpty()) {
                int contOccurs = 0;
                int occursMaisInterno = -1;
                Set<Integer> keys = linhasInicioFimOccurs.keySet();
                for (Integer linhaInicio : keys) {
                    contOccurs++;
                    Integer linhaFim = linhasInicioFimOccurs.get(linhaInicio);
                    if (row >= linhaInicio && row <= linhaFim) {
                        occursMaisInterno = contOccurs;
                    }
                }

                if (occursMaisInterno != -1) {
                    l.setBackground(getColorOccurs(occursMaisInterno - 1));
                } else {
                    l.setBackground(this.corDefalt);
                }
            } else {
                l.setBackground(this.corDefalt);
            }

            //Cor da fonte
            l.setForeground(Color.BLACK);

            //Alinhamento do texto das células
            if (column == 0 || column == 4) {
                l.setHorizontalAlignment(SwingConstants.LEFT);
            } else {
                l.setHorizontalAlignment(SwingConstants.CENTER);
            }

            //Coloca em negrito a linha que contém uma WK
            if (this.linhasComWK.contains(row) && column != 3) {
                String txt = l.getText();
                txt = txt.replace("<", "&lt;");
                txt = txt.replace(">", "&gt;");
                l.setText("<html><b>" + txt + "</b></html>");
            }
        }
        return this;
    }

    public ProcessadorWordKey getProcessador() {
        return processador;
    }

    public void setProcessador(ProcessadorWordKey processador) {
        this.processador = processador;
    }

    public ArrayList<Integer> getLinhasComWK() {
        return linhasComWK;
    }

    public void setLinhasComWK(ArrayList<Integer> linhasComWK) {
        this.linhasComWK = linhasComWK;
    }

    public Map<Integer, Integer> getLinhasInicioFimOccurs() {
        return linhasInicioFimOccurs;
    }

    public void setLinhasInicioFimOccurs(Map<Integer, Integer> linhasInicioFimOccurs) {
        this.linhasInicioFimOccurs = linhasInicioFimOccurs;
    }

    public Color[] getCorOccurs() {
        return corOccurs;
    }

    public void setCorOccurs(Color[] corOccurs) {
        this.corOccurs = corOccurs;
    }

    public Color getCorDefalt() {
        return corDefalt;
    }

    public void setCorDefalt(Color corDefalt) {
        this.corDefalt = corDefalt;
    }
}

/*public Component getTableCellRendererComponent2222(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 3 && value != null) {
            String str = value.toString();

            String novaStr = this.processador.tratarWordKey(str);
            if (!novaStr.equals(str)) {
                setText(novaStr);
            }
        }

        if (l != null) {
            if (getIniOccurs() != -1 && getFimOccurs() != -1) {
                if (row >= getIniOccurs() && row <= getFimOccurs()) {
                    l.setBackground(this.corOccurs);
                } else {
                    l.setBackground(this.corDefalt);
                }
            } else {
                l.setBackground(this.corDefalt);
            }
            
            l.setForeground(Color.BLACK);            
            
            if (column == 0 || column == 4) {
                l.setHorizontalAlignment(SwingConstants.LEFT);
            } else {
                l.setHorizontalAlignment(SwingConstants.CENTER);
            }
            
            if (this.linhasComWK.contains(row) && column != 3) {
                l.setText("<html><b>" + l.getText() + "</b></html>");
            }
        }
        return this;
    }*/
