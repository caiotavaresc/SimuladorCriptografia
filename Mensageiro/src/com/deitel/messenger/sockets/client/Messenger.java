// DeitelMessenger.java
// DeitelMessenger is a chat application that uses a ClientGUI
// and SocketMessageManager to communicate with 
// DeitelMessengerServer.
package com.deitel.messenger.sockets.client;

// Deitel packages
import com.deitel.messenger.*;
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
       ClientGUI2.janelaAtual.TratarMensagemResposta(NICK_FROM, CRYPTO_TYPE, MSG_TYPE, MSG_TEXT, MSG_DTTM);

   } // end method messageReceived  
   
   //Resposta da mensagem de autenticacao
   public void authMessageReceived(String id, String response, InetAddress ip_orig)
   {
       JanelaLogin.janelaAtual.tratarResultadoAutenticacao(Integer.parseInt(id), response);
   }
   
   //Mensagem de Autenticação - Não é usado
   public void requestMessageReceived(int userId, InetAddress ip_orig)
   {
       
   }

}  // end MyMessageListener inner class 

/**************************************************************************
 * (C) Copyright 2002 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/