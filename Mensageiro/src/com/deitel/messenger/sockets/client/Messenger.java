// DeitelMessenger.java
// DeitelMessenger is a chat application that uses a ClientGUI
// and SocketMessageManager to communicate with 
// DeitelMessengerServer.
package com.deitel.messenger.sockets.client;

// Deitel packages
import com.deitel.messenger.*;
import com.deitel.messenger.keys.*;
import java.net.InetAddress;

public class Messenger {
   
   // execute application
   public static void main( String args[] ) 
   {
      MessageManager messageManager;
      MessageListener messageListener;
      
      // create new DeitelMessenger
      if ( args.length == 0 )
         messageManager = new SocketMessageManager("localhost");
      else
         messageManager = new SocketMessageManager( args[ 0 ] );  
      
      //Criar o listener que envia e recebe mensagens
      messageListener = new MyMessageListener();
      
      JanelaLogin.abrirAutenticacao(messageManager, messageListener);
      
   }
}

// MyMessageListener listens for new messages from the
// MessageManager and displays the messages in messageArea
// using a MessageDisplayer.
class MyMessageListener implements MessageListener {

   // when received, display new messages in messageArea
   public void messageReceived( String NICK_FROM, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT, String MSG_DTTM ) 
   {
       int iCRYPTO_TYPE = Integer.valueOf(CRYPTO_TYPE);
       
       //Se a mensagem for um acordo de chaves, chamar a classe Symmetric_AES pra tratar
       if(MSG_TYPE.equals("2"))
       {
           switch(iCRYPTO_TYPE)
           {
               case 2:
                   Symmetric_AES.chaveSimetricaRecebida(NICK_FROM, MSG_TEXT, MSG_DTTM);
                   break;
                    
               case 4:
                   KeyExchange.acordoChavesRecebido(NICK_FROM, MSG_TEXT, MSG_DTTM);
                   break;
            
           }
           
           return;
       }
               
       //Senão, vai depender do tipo de mensagem
       switch(iCRYPTO_TYPE)
       {
           case 1:
               NoCrypt.mensagemRecebida(NICK_FROM, MSG_TEXT, MSG_DTTM);
               break;
               
           case 2:
               Symmetric_AES.mensagemRecebida(NICK_FROM, MSG_TEXT, MSG_DTTM);
               break;
               
           case 3: 
               Asymmetric_RSA.mensagemRecebida(NICK_FROM, MSG_TEXT, MSG_DTTM);
               break;
               
           case 4:
               KeyExchange.mensagemRecebida(NICK_FROM, MSG_TEXT, MSG_DTTM);
               break;
       }

   } // end method messageReceived  
   
   //Resposta da mensagem de autenticacao
   public void authMessageReceived(String id, String response, InetAddress ip_orig)
   {
       //A janela de login trata o resultado da autenticação
       JanelaLogin.janelaAtual.tratarResultadoAutenticacao(Integer.parseInt(id), response);
   }
   
   //Mensagem de Autenticação - Não é usado
   public void requestMessageReceived(int userId, InetAddress ip_orig)
   {
       
   }
   
   //Mensagem de Chave Pública Assimétrica - Não é usado
   public void asymPublicMessageReceived(int userId, String arrayBytes)
   {
       
   }
   
   //Mensagem contendo todas as chaves públicas de todos os contatos
   public void asymPublicReqReceived(String parContatoChave, InetAddress ip_orig)
   {
        String[] par;
        byte[] key;
       
        par = parContatoChave.split(">>>");

        //pegar a chave e transformá-la em array de bytes
        key = Utils.converteStringParaArrayDeBytes(par[1]);

        Asymmetric_RSA.inserirChaveContato(par[0], key);
        System.out.println("Inseriu "+par[0]);
   }

}