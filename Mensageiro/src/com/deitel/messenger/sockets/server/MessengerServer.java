// DeitelMessengerServer.java
// DeitelMessengerServer is a multi-threaded, socket- and 
// packet-based chat server.
package com.deitel.messenger.sockets.server;

// Java core packages
import java.net.*;
import java.io.*;

// Deitel packages
import com.deitel.messenger.*;
import com.deitel.messenger.model.*;
import com.deitel.messenger.sockets.*;

public class MessengerServer implements MessageListener,
   SocketMessengerConstants {
   
   // start chat server
   public void startServer() 
   {      
      // create server and manage new clients
      try {
         
         // create ServerSocket for incoming connections
         ServerSocket serverSocket = 
            new ServerSocket( SERVER_PORT, 100 );
         
         System.out.println( "Server listening on port " + 
            SERVER_PORT + " ..." );
         
         // listen for clients constantly
         while ( true ) {
            
            // accept new client connection
            Socket clientSocket = serverSocket.accept();
            
            // create new ReceivingThread for receiving
            // messages from client
            new ReceivingThread( this, clientSocket ).start();
                        
            // print connection information
            System.out.println( "Connection received from: " +
               clientSocket.getInetAddress() );
            
         } // end while     
         
      } // end try
      
      // handle exception creating server and connecting clients
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }

   } // end method startServer
   
   // when new message is received, broadcast message to clients
   public void messageReceived( String ID_FROM, String ID_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT ) 
   {          
      // create String containing entire message
      //String completeMessage = from + MESSAGE_SEPARATOR + message;
      
      // create and start MulticastSendingThread to broadcast
      // new messages to all clients
      // new MulticastSendingThread( 
      //   completeMessage.getBytes() ).start();
      
      // Ao invés de enviar as mensagens para todos os usuários, nós vamos armazená-las
      Mensagem msg = new Mensagem(Integer.parseInt(ID_FROM), Integer.parseInt(ID_TO), Integer.parseInt(CRYPTO_TYPE), Integer.parseInt(MSG_TYPE), MSG_TEXT);
      MENSAGEMDAO.adiciona(msg);
       
   }

    public void authMessageReceived(String nick, String pass, InetAddress ip_dest)
    {
        //Verificar se o usuário existe e se a senha coincide
        Usuario user = USUARIODAO.usuarioExiste(nick);
        String mensagemResposta;
        
        if(user != null)
            if(user.getPASS().equals(pass))
                mensagemResposta = "AUTH" + MESSAGE_SEPARATOR + user.getUSER_ID() + MESSAGE_SEPARATOR + "Conectado com sucesso!";
            else
                mensagemResposta = "AUTH" + MESSAGE_SEPARATOR + 0 + MESSAGE_SEPARATOR + "Usuario e senha não conferem.";
        else
            mensagemResposta = "AUTH" + MESSAGE_SEPARATOR + 0 + MESSAGE_SEPARATOR + "Esse usuário não existe";
            
        //Enviar a resposta
        new ResponseSendingThread(mensagemResposta.getBytes(), ip_dest).start();
    }
   
   // start the server
   public static void main ( String args[] ) 
   {
      new MessengerServer().startServer();
   }
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