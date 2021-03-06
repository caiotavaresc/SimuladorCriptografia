/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deitel.messenger;

import com.deitel.messenger.keys.*;
import com.deitel.messenger.sockets.client.PacketSendingRequestThread;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class ClientGUI2 extends javax.swing.JFrame {

    public ClientGUI2() {
        initComponents();
        this.mapaPaineis = new HashMap<String, List<Component>>();
        
        //Carregar os contatos do Arquivo
        this.loadContacts();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        PainelAbas = new javax.swing.JTabbedPane();
        PainelInterior = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        CaixaTexto = new javax.swing.JTextPane();
        BotaoEnviar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Conversa = new javax.swing.JTextPane();
        Cripto1 = new javax.swing.JRadioButton();
        Cripto2 = new javax.swing.JRadioButton();
        Cripto3 = new javax.swing.JRadioButton();
        Cripto4 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        CaixaTexto.setAutoscrolls(false);
        CaixaTexto.setEnabled(false);
        jScrollPane2.setViewportView(CaixaTexto);

        BotaoEnviar.setText("Enviar");
        BotaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoEnviarActionPerformed(evt);
            }
        });

        Conversa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Conversa.setFocusable(false);
        jScrollPane3.setViewportView(Conversa);

        buttonGroup1.add(Cripto1);
        Cripto1.setSelected(true);
        Cripto1.setText("Sem Criptografia");

        buttonGroup1.add(Cripto2);
        Cripto2.setText("Chave Simétrica");

        buttonGroup1.add(Cripto3);
        Cripto3.setText("Chave Assimétrica");

        buttonGroup1.add(Cripto4);
        Cripto4.setText("Acordo de Chaves");

        javax.swing.GroupLayout PainelInteriorLayout = new javax.swing.GroupLayout(PainelInterior);
        PainelInterior.setLayout(PainelInteriorLayout);
        PainelInteriorLayout.setHorizontalGroup(
            PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelInteriorLayout.createSequentialGroup()
                .addGroup(PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(PainelInteriorLayout.createSequentialGroup()
                        .addComponent(Cripto1)
                        .addGap(18, 18, 18)
                        .addComponent(Cripto2)
                        .addGap(18, 18, 18)
                        .addComponent(Cripto3)
                        .addGap(18, 18, 18)
                        .addComponent(Cripto4)
                        .addGap(0, 75, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3)
        );
        PainelInteriorLayout.setVerticalGroup(
            PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelInteriorLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelInteriorLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cripto1)
                            .addComponent(Cripto2)
                            .addComponent(Cripto3)
                            .addComponent(Cripto4)))))
        );

        PainelAbas.addTab("Inicie uma conversa", PainelInterior);

        jButton1.setText("Adicionar Contato");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "item1", "item2", "item3" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelAbas, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PainelAbas))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        
        if(evt.getClickCount() == 2)
        {            
            //Duplo ou Triplo clique detectado
            int index = jList1.locationToIndex(evt.getPoint());
            String textoAba = this.jList1.getModel().getElementAt(index);
            
            //Se a aba já existir, apenas deixá-la visível
            for(int i = 0; i < PainelAbas.getTabCount(); i++)
            {
                if(PainelAbas.getTitleAt(i).equals(textoAba))
                {
                    PainelAbas.setSelectedIndex(i);
                    return;
                }
                
            }
            
            this.abreNovaAba(textoAba);
            
            //Tentar remover a primeira aba
            PainelAbas.remove(PainelInterior);
            PainelAbas.setSelectedIndex(PainelAbas.getComponentCount()-1);
        }
        
    }//GEN-LAST:event_jList1MouseClicked

    private void BotaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoEnviarActionPerformed
        // Enviar o texto da caixa de texto apropriada para o servidor
        List<Component> dadosAbaAtiva;
        int cryptoType = 1;
        
        //Pegar a aba que está ativa
        String AbaAtiva = PainelAbas.getTitleAt(PainelAbas.getSelectedIndex());
        
        //Pegar a instância da caixa de texto da aba ativa na lista e daí pegar a mensagem
        dadosAbaAtiva = this.mapaPaineis.get(AbaAtiva);
        
        JTextPane caixaTexto = (JTextPane) dadosAbaAtiva.get(2);
        String textoMensagem = caixaTexto.getText();
        
        //Tratar criptografia
        if(((JRadioButton) dadosAbaAtiva.get(3)).isSelected())
        {
            //Sem criptografia
            //O texto da mensagem não sofre alteração, nenhuma mensagem é mandada antes - As ações ocorrem na classe
            NoCrypt.enviarMensagem(Integer.toString(this.userId), AbaAtiva, textoMensagem);
        }
        
        if(((JRadioButton) dadosAbaAtiva.get(4)).isSelected())
        {
            //Criptografia de Chave Simétrica
            Symmetric_AES.enviarMensagem(Integer.toString(this.userId), AbaAtiva, textoMensagem);
        }
        
        //Criptografia de chave assimétrica
        if(((JRadioButton) dadosAbaAtiva.get(5)).isSelected())
        {
            //Criptografia de chave assimétrica
            Asymmetric_RSA.enviarMensagem(Integer.toString(this.userId), AbaAtiva, textoMensagem);
        }
        
        //Criptografia com Acordo de Chaves
        if(((JRadioButton) dadosAbaAtiva.get(6)).isSelected())
        {
            //Criptografia com Acordo de Chaves
            KeyExchange.enviarMensagem(Integer.toString(this.userId), AbaAtiva, textoMensagem);
        }

        //Limpar a aba de conversa
        caixaTexto.setText("");
    }//GEN-LAST:event_BotaoEnviarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static ClientGUI2 InterfaceUsuario (MessageManager messageManager) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        ClientGUI2 retorno = new ClientGUI2();
        retorno.setMessageManager(messageManager);
        retorno.setVisible(true);
        
        ClientGUI2.janelaAtual = retorno;

        return retorno;

    }
    
    public void loadContacts()
    {
        File arqContatos = new File("files/contatos.rp2");
        
        if(arqContatos.exists())
        {
            String contato;
            
            try
            {
                FileReader reader = new FileReader(arqContatos);
                BufferedReader reader2 = new BufferedReader(reader);
                DefaultListModel model = new DefaultListModel<String>();
                
                
                while((contato = reader2.readLine()) != null)
                    model.addElement(contato);
                
                jList1.setModel(model);
                
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoEnviar;
    private javax.swing.JTextPane CaixaTexto;
    private javax.swing.JTextPane Conversa;
    private javax.swing.JRadioButton Cripto1;
    private javax.swing.JRadioButton Cripto2;
    private javax.swing.JRadioButton Cripto3;
    private javax.swing.JRadioButton Cripto4;
    private javax.swing.JTabbedPane PainelAbas;
    private javax.swing.JPanel PainelInterior;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
    
    //Variaveis de controle de pagina
    public static ClientGUI2 janelaAtual;
    private HashMap<String,List<Component>> mapaPaineis;
    private String userName;
    private int userId;
    private MessageListener messageListener;
    private MessageManager messageManager;
    private PacketSendingRequestThread atualizacao;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        
        this.atualizacao.setUserId(userId);
        
        //Startar a thread de atualizacao
        this.atualizacao.start();
                
        //Carregar as chaves Assimétricas
        Asymmetric_RSA.iniciarChaves();
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
        
        //Cria a thread de atualizacao
        this.atualizacao = new PacketSendingRequestThread(this.messageManager);
    }
    
    //Método que escreve o texto na aba apropriada
    public void appendText(String NICK_FROM, String MENSAGEM)
    {
        //Painel de texto onde a mensagem será escrita
        JTextPane conversa;
        List listaDadosPaineis;
        
        //Se o NICK_FROM sou eu, o texto é escrito na aba atual
        if(NICK_FROM.equals(this.userName))
        {
            conversa = (JTextPane) mapaPaineis.get(PainelAbas.getTitleAt(PainelAbas.getSelectedIndex())).get(1);
        }
        else
        {
            //Caso contrário, encontrar/criar a aba apropriada
            
            listaDadosPaineis = mapaPaineis.get(NICK_FROM);
            if(listaDadosPaineis == null)
            {
                //Abrir uma nova aba para o id em questão
                this.abreNovaAba(NICK_FROM);

                //Pegar de novo o painel
                listaDadosPaineis = mapaPaineis.get(NICK_FROM);
            }
            
            //Colocar a aba em questão em foco
            PainelAbas.setSelectedComponent((Component) listaDadosPaineis.get(0));
            
            conversa = (JTextPane) listaDadosPaineis.get(1);
        }
        
        conversa.setText(conversa.getText() + MENSAGEM);
    }
    
    //Método redirecionador para envio de mensagens
    public static void sendMessage(String NICK_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT)
    {
        ClientGUI2.sendMessage(Integer.toString(ClientGUI2.janelaAtual.getUserId()), NICK_TO, CRYPTO_TYPE, MSG_TYPE, MSG_TEXT);
    }
    
    //Método redirecionador para envio de mensagens
    public static void sendMessage(String ID_FROM, String NICK_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT)
    {
        janelaAtual.messageManager.sendMessage(ID_FROM, NICK_TO, CRYPTO_TYPE, MSG_TYPE, MSG_TEXT);
    }
    
    //Método redirecionador para envio de mensagens
    public static void sendRSAPublicKeyReq(String NICK_TO)
    {
        janelaAtual.messageManager.sendRSAPublicKeyReq(NICK_TO);
    }
    
    private void abreNovaAba(String textoAba)
    {
        //Abrir uma nova aba para conversar com o novo usuario
        JPanel adicao = new JPanel();
        JButton ad_Enviar = new JButton();
        JTextPane ad_CaixaTexto = new JTextPane();
        JTextPane ad_Conversa = new JTextPane();
        JScrollPane ad_ScrollConversa = new JScrollPane();
        JScrollPane ad_ScrollCaixaTexto = new JScrollPane();
        JRadioButton ad_Cripto1 = new JRadioButton();
        JRadioButton ad_Cripto2 = new JRadioButton();
        JRadioButton ad_Cripto3 = new JRadioButton();
        JRadioButton ad_Cripto4 = new JRadioButton();
        ButtonGroup ad_ButtonGroup = new ButtonGroup();

        ad_CaixaTexto.setAutoscrolls(false);
        ad_ScrollCaixaTexto.setViewportView(ad_CaixaTexto);

        ad_Conversa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ad_Conversa.setFocusable(false);
        ad_ScrollConversa.setViewportView(ad_Conversa);

        ad_Enviar.setText("Enviar");
        ad_Enviar.addActionListener(new java.awt.event.ActionListener() 
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoEnviarActionPerformed(evt);
            }
        });

        ad_ButtonGroup.add(ad_Cripto1);
        ad_Cripto1.setSelected(true);
        ad_Cripto1.setText("Sem Criptografia");

        ad_ButtonGroup.add(ad_Cripto2);
        ad_Cripto2.setText("Chave Simétrica");

        ad_ButtonGroup.add(ad_Cripto3);
        ad_Cripto3.setText("Chave Assimétrica");
        
        ad_ButtonGroup.add(ad_Cripto4);
        ad_Cripto4.setText("Acordo de Chaves");

        javax.swing.GroupLayout adicaoLayout = new javax.swing.GroupLayout(adicao);
        adicao.setLayout(adicaoLayout);
        
        /*adicaoLayout.setHorizontalGroup(
            adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adicaoLayout.createSequentialGroup()
                .addGroup(adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(PainelInteriorLayout.createSequentialGroup()
                        .addComponent(Cripto1)
                        .addGap(18, 18, 18)
                        .addComponent(Cripto2)
                        .addGap(18, 18, 18)
                        .addComponent(Cripto3)
                        .addGap(18, 18, 18)
                        .addComponent(Cripto4)
                        .addGap(0, 75, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3)
        );
        PainelInteriorLayout.setVerticalGroup(
            PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelInteriorLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelInteriorLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PainelInteriorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cripto1)
                            .addComponent(Cripto2)
                            .addComponent(Cripto3)
                            .addComponent(Cripto4)))))
        );*/

        adicaoLayout.setHorizontalGroup(
        adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adicaoLayout.createSequentialGroup()
                .addGroup(adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ad_ScrollCaixaTexto)
                    .addGroup(adicaoLayout.createSequentialGroup()
                        .addComponent(ad_Cripto1)
                        .addGap(18, 18, 18)
                        .addComponent(ad_Cripto2)
                        .addGap(18, 18, 18)
                        .addComponent(ad_Cripto3)
                        .addGap(18, 18, 18)
                        .addComponent(ad_Cripto4)
                        .addGap(0, 75, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ad_Enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(ad_ScrollConversa)
        );

        adicaoLayout.setVerticalGroup(
            adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adicaoLayout.createSequentialGroup()
                .addComponent(ad_ScrollConversa, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ad_Enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(adicaoLayout.createSequentialGroup()
                        .addComponent(ad_ScrollCaixaTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(adicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ad_Cripto1)
                            .addComponent(ad_Cripto2)
                            .addComponent(ad_Cripto3)
                            .addComponent(ad_Cripto4)))))
        );

        List<Component> listaDados = new ArrayList<Component>();
        listaDados.add(adicao);
        listaDados.add(ad_Conversa);
        listaDados.add(ad_CaixaTexto);
        listaDados.add(ad_Cripto1);
        listaDados.add(ad_Cripto2);
        listaDados.add(ad_Cripto3);
        listaDados.add(ad_Cripto4);
        
        PainelAbas.add(textoAba, adicao);
        mapaPaineis.put(textoAba, listaDados);
        
        //Tentar remover a primeira aba (vazia)
        PainelAbas.remove(PainelInterior);
    }        
}