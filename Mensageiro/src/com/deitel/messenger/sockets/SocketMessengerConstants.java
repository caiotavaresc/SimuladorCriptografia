// SocketMessengerConstants.java
// SocketMessengerConstants defines constants for the port numbers
// and multicast address in DeitelMessenger
package com.deitel.messenger.sockets;

import com.deitel.messenger.model.*;

public interface SocketMessengerConstants {
   
   // porta em que o cliente escuta as respostas do servidor
   public static final int CLIENT_LISTENING_PORT = 5555;
   
   // port for Socket connections to DeitelMessengerServer
   public static final int SERVER_PORT = 5000;   
   
   // String that indicates disconnect
   public static final String DISCONNECT_STRING = "DISCONNECT";

   // String that separates the user name from the message body
   public static final String MESSAGE_SEPARATOR = ">>>";

   // message size (in bytes)
   public static final int MESSAGE_SIZE = 2048;
   
   // DAO de Mensagem
   public static final MensagemDAO MENSAGEMDAO = new MensagemDAO();
   
   // DAO de Usuario
   public static final UsuarioDAO USUARIODAO = new UsuarioDAO();
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