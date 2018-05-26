/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builderbuffer.presenter;

import builderbuffer.model.Transacao;
import builderbuffer.observer.IObserverTransacao;
import builderbuffer.view.TelaEditarCampos;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author eglel
 */
public class PresenterTelaEditarCampos implements IObserverTransacao {

    //Model
    private Transacao transacao;

    private TelaEditarCampos tela;
    private DefaultStyledDocument document;
    private StyleContext context;
    private Style[] estilos;

    public PresenterTelaEditarCampos() {
        this.tela = new TelaEditarCampos();

        this.tela.getBtnOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarDados();
                tela.dispose();
            }
        });

        this.tela.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tela.dispose();
            }
        });

        //Função para colorir as palavras
        this.tela.getTxtAreaCampos().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int p = tela.getTxtAreaCampos().getCaretPosition();
                    //System.out.println("pos[" + p + "]");
                    if (!(e.getKeyCode() == KeyEvent.VK_CONTROL)
                            && !(e.getKeyCode() == KeyEvent.VK_SHIFT)
                            && !(e.getKeyCode() == KeyEvent.VK_LEFT)
                            && !(e.getKeyCode() == KeyEvent.VK_RIGHT)
                            && !(e.getKeyCode() == KeyEvent.VK_UP)
                            && !(e.getKeyCode() == KeyEvent.VK_DOWN)
                            && !(e.getKeyCode() == KeyEvent.VK_END)
                            && !(e.getKeyCode() == KeyEvent.VK_HOME)
                            && !(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
                            && !(e.getKeyCode() == KeyEvent.VK_PAGE_UP)) {

                        int pos = tela.getTxtAreaCampos().getCaretPosition();
                        TextLine linha = getTextLineWhereCursorIs(pos, document);
                        renderLine(linha, document);
                        tela.getTxtAreaCampos().setCaretPosition(pos);

                    }

                    if (e.getKeyCode() == KeyEvent.VK_V) {
                        if (e.isControlDown()) {
                            renderAllText(document.getText(0, document.getLength()));
                        }
                    }
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                }
            }

        });

        InputMap map = tela.getTxtAreaCampos().getInputMap();
        map.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "paste");

        tela.getTxtAreaCampos().getActionMap().put("paste", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String texto = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    int pos = tela.getTxtAreaCampos().getCaretPosition();
                    document.insertString(pos, texto, estilos[0]);
                    renderAllText(document.getText(0, document.getLength()));
                } catch (Exception ex) {
                    System.out.println("Erro no crtl+V:" + ex.getMessage());
                }
            }
        });

        document = new DefaultStyledDocument();
        tela.getTxtAreaCampos().setDocument(document);
        context = new StyleContext();

        Style styleBlack = context.addStyle("black", null);
        StyleConstants.setForeground(styleBlack, Color.BLACK);

        Style styleOrange = context.addStyle("orange", null);
        StyleConstants.setForeground(styleOrange, Color.ORANGE);

        Style styleRed = context.addStyle("red", null);
        StyleConstants.setForeground(styleRed, Color.RED);

        Style styleBlue = context.addStyle("blue", null);
        StyleConstants.setForeground(styleBlue, Color.BLUE);

        Style styleGreen = context.addStyle("green", null);
        StyleConstants.setForeground(styleGreen, Color.GREEN);

        estilos = new Style[5];
        estilos[0] = styleBlack;
        estilos[1] = styleOrange;
        estilos[2] = styleRed;
        estilos[3] = styleBlue;
        estilos[4] = styleGreen;

        //renderAllText(this.transacao.getTextoCampos());
        this.tela.setLocationRelativeTo(null);
        //this.tela.setVisible(true);
    }

    public void setVisible(boolean visible) {
        this.tela.setVisible(visible);
    }

    //Retorna a linha em que o cursor está, a posição inicial e final da linha no document.
    public TextLine getTextLineWhereCursorIs(int posCursor, AbstractDocument document) throws Exception {
        String texto = document.getText(0, document.getLength());
        String[] linhas = texto.split("\n");

        int[][] posIniFimLinhas = new int[linhas.length][2];
        int soma = 0;
        for (int i = 0; i < linhas.length; i++) {
            posIniFimLinhas[i][0] = soma;
            posIniFimLinhas[i][1] = soma + linhas[i].length();
            soma += linhas[i].length() + 1;
        }

        int linha = -1;
        for (int i = 0; i < linhas.length; i++) {
            if ((posIniFimLinhas[i][0] <= posCursor) && (posCursor <= posIniFimLinhas[i][1])) {
                linha = i;
                break;
            }
        }

        if (linha == -1) {
            //System.out.println("linhaCurso[]");
            return new TextLine("", posCursor, posCursor);
        }
        //System.out.println("linhaCurso[" + linhas[linha] + "]");
        return new TextLine(linhas[linha], posIniFimLinhas[linha][0], posIniFimLinhas[linha][1]);
    }

    public void renderLine(TextLine textLine, AbstractDocument document) throws Exception {
        String line = textLine.getText();
        int posIni = textLine.getPosIni();
        int posLine = 0;
        ArrayList<Integer> indexTAB = new ArrayList<Integer>();

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\t') {
                indexTAB.add(i);
            }
        }

        if (indexTAB.isEmpty()) {
            document.replace(posIni, line.length(), line, estilos[0]);
        } else {
            int i = 0;
            boolean tab = false;
            boolean inseriuEspaco = false;
            //A Quantidade de campos é 1 a mais que a quantidade de TABs            
            while (i < indexTAB.size() + 1) {
                if (i < indexTAB.size()) {
                    tab = true;
                }

                String campo = "";
                if (tab) {
                    campo = line.substring(posLine, indexTAB.get(i));
                    posLine += campo.length();
                    tab = false;
                } else {
                    campo = line.substring(posLine, line.length());
                    posLine += campo.length();
                }

                if (i == 0) {
                    document.replace(posIni, campo.length(), campo, estilos[0]);
                    posIni += campo.length();
                } else if (i == 1) {
                    document.replace(posIni, campo.length(), campo, estilos[1]);
                    posIni += campo.length();
                } else if (i == 2) {
                    document.replace(posIni, campo.length(), campo, estilos[2]);
                    posIni += campo.length();
                } else if (i == 3) {
                    document.replace(posIni, campo.length(), campo, estilos[3]);
                    posIni += campo.length();
                } else if (i == 4) {
                    document.replace(posIni, campo.length(), campo, estilos[4]);
                    posIni += campo.length();
                } else {
                    //System.out.println("TAB ignorado");
                    break;
                }
                i++;
            }

            if (inseriuEspaco) {
                document.replace(textLine.getPosIni(), 1, "", estilos[0]);
            }
        }

    }

    public void renderAllText(String txtCampos) {
        try {
            document.remove(0, document.getLength());
            document.insertString(0, txtCampos, estilos[0]);

            String[] linhas = txtCampos.split("\n");

            int[][] posIniFimLinhas = new int[linhas.length][2];
            int soma = 0;
            for (int i = 0; i < linhas.length; i++) {
                posIniFimLinhas[i][0] = soma;
                posIniFimLinhas[i][1] = soma + linhas[i].length();
                soma += linhas[i].length() + 1;
            }

            for (int i = 0; i < linhas.length; i++) {
                renderLine(new TextLine(linhas[i], posIniFimLinhas[i][0], posIniFimLinhas[i][0]), document);
            }
        } catch (Exception ex) {
            System.out.println("renderAllText:" + ex.getMessage());
        }
    }

    public void gravarDados() {
        try {
            //System.out.println("gravar[" + document.getText(0, document.getLength()) + "]");
            if (transacao != null) {
                this.transacao.setTextoCampos(document.getText(0, document.getLength()));
            }
        } catch (Exception ex) {
            System.out.println("Erro ao ler dados do document:" + ex.getMessage());
        }
    }

    public void setTransacao(Transacao t) {
        this.transacao = t;
        if (this.transacao != null) {
            this.transacao.addObserver(this);
        }
    }

    @Override
    public void update() {
        if (transacao != null) {
            String txtCampos = this.transacao.getTextoCampos();
            renderAllText(txtCampos);
        }
    }

}

class TextLine {

    private String text;
    private int posIni;
    private int posFim;

    public TextLine(String text, int posIni, int posFim) {
        this.text = text;
        this.posIni = posIni;
        this.posFim = posFim;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosIni() {
        return posIni;
    }

    public void setPosIni(int posIni) {
        this.posIni = posIni;
    }

    public int getPosFim() {
        return posFim;
    }

    public void setPosFim(int posFim) {
        this.posFim = posFim;
    }

}
//========================================================================
//    public void renderLineWithENTER(TextLine textLine, DefaultStyledDocument document) throws Exception {
//        String line = textLine.getText();
//        int posIni = textLine.getPosIni();
//        document.replace(posIni, 1, line, estilos[0]);
//
//    }
//    public void renderLineWithTAB(TextLine textLine, AbstractDocument document) throws Exception {
//        String line = textLine.getText();
//        String[] campos = line.split("\t");
//        int posIni = textLine.getPosIni();
//
//        if (campos.length == 0 && line.contains("\t")) {
//            int cont = 0;
//            for (int i = 0; i < line.length(); i++) {
//                if (line.charAt(i) == '\t') {
//                    cont++;
//                }
//            }
//            for (int i = 0; i < cont; i++) {
//                if (i == 0) {
//                    document.replace(posIni++, 1, "\t", estilos[1]);
//                } else {
//                    document.replace(posIni++, 1, "\t", estilos[2]);
//                }
//            }
//        } else {
//            //int posFim = textLine.getPosFim();
//
//            for (int i = 0; i < campos.length; i++) {
//                if (i < 2) {
//                    document.replace(posIni, campos[i].length(), campos[i], estilos[i]);
//                    posIni += campos[i].length();
//                    document.replace(posIni, 1, "\t", estilos[i + 1]);
//                    posIni++;
//                } else {
//                    document.replace(posIni, campos[i].length(), campos[i], estilos[2]);
//                    posIni += campos[i].length();
//                    document.replace(posIni, 1, "\t", estilos[2]);
//                    posIni++;
//                }
//            }
//        }
//    }
//    public String getTextFromCursorToEndLine() throws Exception {
//        int pos = tela.getTxtAreaCampos().getCaretPosition();
//        int tam = document.getLength();
//        int dif = tam - pos;
//        //System.out.println("[P,T]=["+pos+","+tam+"]");
//        if (dif > 0) {
//            String textoRestante = document.getText(pos, tam - pos);
//            int indice = textoRestante.indexOf("\n");
//            if (indice == -1) {
//                return document.getText(pos, tam - pos);
//            } else {
//                return document.getText(pos, indice);
//            }
//        }
//        return "";
//    }
//        public void destacarDados(String txtCampos) {
//        // System.out.println("linhasEditar\n[" + txtCampos + "]");
//        try {
//            document.remove(0, document.getLength());
//        } catch (Exception ex) {
//            System.out.println("destacarDados- Erro ao apagar document[" + ex.getMessage() + "]");
//        }
//
//        String[] linhas = txtCampos.split("\n");
//
//        try {
//
//            for (int i = linhas.length - 1; i >= 0; i--) {
//                String[] campos = linhas[i].split("\t");
//
//                if (campos != null && campos.length == 3) {
//                    if (i == (linhas.length - 1)) {
//                        document.insertString(0, campos[2], styleBlue);
//                    } else {
//                        document.insertString(0, campos[2] + "\n", styleBlue);
//                    }
//
//                    document.insertString(0, campos[1] + "\t", styleRed);
//                    document.insertString(0, campos[0] + "\t", styleBlack);
//                } else if (campos != null && campos.length == 2) {
//                    if (i == (linhas.length - 1)) {
//                        document.insertString(0, campos[1], styleRed);
//                    } else {
//                        document.insertString(0, campos[1] + "\n", styleRed);
//                    }
//                    document.insertString(0, campos[0] + "\t", styleBlack);
//                } else if (campos != null && campos.length >= 1) {
//                    if (i == (linhas.length - 1)) {
//                        document.insertString(0, campos[0], styleBlack);
//                    } else {
//                        document.insertString(0, campos[0] + "\n", styleBlack);
//                    }
//                } else if (campos != null && campos.length == 0) {
//                    if (i == (linhas.length - 1)) {
//                        document.insertString(0, "", styleBlack);
//                    } else {
//                        document.insertString(0, "\n", styleBlack);
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            System.out.println("destacarDados:" + ex.getMessage());
//        }
//    }
//============================================ KEY EVENT OLD ===============================
//                    if (renderTAB) {
//                        int pos = tela.getTxtAreaCampos().getCaretPosition();
//                        TextLine linha = getTextLineWhereCursorIs(pos, document);
//                        renderLine(linha, document);
//                        renderTAB = false;
//                    }
//                    if (e.getKeyCode() == KeyEvent.VK_TAB) {
//                        renderTAB = true;
//                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                        int pos = tela.getTxtAreaCampos().getCaretPosition();
//                        TextLine linha = getTextLineWhereCursorIs(pos, document);
//                        if (linha.getText().isEmpty()) {
//                            tela.getTxtAreaCampos().setCharacterAttributes(estilos[0], true);
//                        } else {
//                            renderLine(linha, document);
//                        }
//                    } else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_V)) {                        
//                        renderAllText(document.getText(0, document.getLength()));
//                    }else{
//                        int pos = tela.getTxtAreaCampos().getCaretPosition();
//                        TextLine linha = getTextLineWhereCursorIs(pos, document);
//                        renderLine(linha, document);
//                        tela.getTxtAreaCampos().setCaretPosition(pos);
//                    }

