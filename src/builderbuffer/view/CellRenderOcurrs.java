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
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author eglel
 */
public class CellRenderOcurrs extends DefaultTableCellRenderer{

    //private Transacao transacao;
    private ProcessadorWordKey processador;
    private Color corOccurs;
    private Color corDefalt;    
    private int iniOccurs;
    private int fimOccurs;
    private ArrayList<Integer> linhasEmNegrito;

    public CellRenderOcurrs(Color corDefalt, Color corOccurs) {
        this.iniOccurs = -1;
        this.fimOccurs = -1;
        this.linhasEmNegrito = new ArrayList<>();
        this.corOccurs = corOccurs;
        this.corDefalt = corDefalt;
        this.processador = new ProcessadorWordKey();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

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
            
            if (this.linhasEmNegrito.contains(row) && column != 3) {
                l.setText("<html><b>" + l.getText() + "</b></html>");
            }
        }
        return this;
    }
    
    

//    public void atualizaLinesBold(int row, boolean adicionar) {
//        Integer line = null;
//        for (Integer i : linesBold) {
//            if (i.equals(row)) {
//                line = row;
//                break;
//            }
//        }
//
//        if (adicionar) {
//            if (line == null) {
//                this.linesBold.add(row);
//            }
//        } else {
//            if (line != null) {
//                this.linesBold.remove(line);
//            }
//        }
//
//    }

//    public void detectarOccurs(DefaultTableModel tm) {
//        int qtdRows = tm.getRowCount();
//        for (int i = 0; i < qtdRows; i++) {
//            String campo = (String) tm.getValueAt(i, 3);
//            if (campo != null) {
//                if (processador.isWordKey(campo)) {
//                    switch (processador.getUltimoTratador().getNomePadrao()) {
//                        case ProcessadorWordKey.OCCURS:
//                            iniOcc = i;
//                            break;
//
//                        case ProcessadorWordKey.FIM_OCCURS:
//                            fimOcc = i;
//                            break;
//                    }
//                }
//            }
//        }
//
//        if (iniOcc != -1 && fimOcc != -1) {
//            setInicioOccurs(iniOcc);
//            setFimOccurs(fimOcc);
//        } else {
//            setInicioOccurs(-1);
//            setFimOccurs(-1);
//        }
//    }

    public int getIniOccurs() {
        return iniOccurs;
    }

    public void setIniOccurs(int iniOccurs) {
        this.iniOccurs = iniOccurs;
    }

    public int getFimOccurs() {
        return fimOccurs;
    }

    public void setFimOccurs(int fimOccurs) {
        this.fimOccurs = fimOccurs;
    }

    public ArrayList<Integer> getLinhasEmNegrito() {
        return linhasEmNegrito;
    }

    public void setLinhasEmNegrito(ArrayList<Integer> linhasEmNegrito) {
        this.linhasEmNegrito = linhasEmNegrito;
    }       

}
