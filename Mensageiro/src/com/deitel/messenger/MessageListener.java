// MessageListener.java
// MessageListener is an interface for classes that wish to
// receive new chat messages.
package com.deitel.messenger;

import java.net.InetAddress;

public interface MessageListener {

   // receive new chat message
   //public void messageReceived( String from, String message );
   
   // Novo formato de mensagem
   public void messageReceived( String ID_FROM, String ID_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT );
   
   // Mensagem de autenticacao
   public void authMessageReceived ( String nick, String pass, InetAddress ip_dest);
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