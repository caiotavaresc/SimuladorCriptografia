// MessageManager.java
// MessageManger is an interface for objects capable of managing
// communications with a message server.
package com.deitel.messenger;

public interface MessageManager {   
   
   // connect to message server and route incoming messages
   // to given MessageListener
   public void connect( MessageListener listener );
   
   // disconnect from message server and stop routing
   // incoming messages to given MessageListener
   public void disconnect( MessageListener listener );
   
   // send message to message server
   public void sendMessage( String ID_FROM, String NICK_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT );  
   
   // Mensagem de autenticacao
   public void sendAuthMessage(String nick, String pass);
   
   //Mensagem de Requisição / Atualização de mensagens
   public void sendRequestMessage(String message);
}


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