// SendingThread.java
// SendingThread sends a message to the chat server in a 
// separate Thread.
package com.deitel.messenger.sockets.client;

// Java core packages
import java.io.*;
import java.net.*;

// Deitel packages
import com.deitel.messenger.sockets.*;

public class SendingThread extends Thread
   implements SocketMessengerConstants {

   // Socket over which to send message
   private Socket clientSocket;
   private String messageToSend;

   // SendingThread constructor
   public SendingThread( Socket socket, String userName, 
      String message ) 
   {
      // invoke superclass constructor to name Thread
      super( "SendingThread: " + socket );

      clientSocket = socket;
      
      // build the message to be sent
      messageToSend = userName + MESSAGE_SEPARATOR + message;
   }
   
   // send message and exit Thread
   public void run() 
   {
      // send message and flush PrintWriter
      try {     
         PrintWriter writer = 
            new PrintWriter( clientSocket.getOutputStream() );
         writer.println( messageToSend );
         writer.flush();        
      } 

      // handle exception sending message
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }

   } // end method run
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