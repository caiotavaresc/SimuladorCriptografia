// MulticastSendingThread.java
// MulticastSendingThread is a Thread that broadcasts a chat
// message using a multicast datagram.
package com.deitel.messenger.sockets.server;

// Java core packages
import java.io.*;
import java.net.*;

// Deitel packages
import com.deitel.messenger.sockets.*;

public class MulticastSendingThread extends Thread 
   implements SocketMessengerConstants {
   
   // message data
   private byte[] messageBytes;
   
   // MulticastSendingThread constructor
   public MulticastSendingThread( byte[] bytes ) 
   { 
      // invoke superclass constructor to name Thread
      super( "MulticastSendingThread" );
      
      messageBytes = bytes; 
   }

   // deliver message to MULTICAST_ADDRESS over DatagramSocket
   public void run() 
   {
      // deliver message
      try {         
         
         // create DatagramSocket for sending message
         DatagramSocket socket = 
            new DatagramSocket( MULTICAST_SENDING_PORT );
         
         // use InetAddress reserved for multicast group
         InetAddress group = InetAddress.getByName(
            MULTICAST_ADDRESS );
         
         // create DatagramPacket containing message
         DatagramPacket packet = new DatagramPacket( 
            messageBytes, messageBytes.length, group, 
            MULTICAST_LISTENING_PORT );
         
         // send packet to multicast group and close socket
         socket.send( packet );
         socket.close();
      } 
      
      // handle exception delivering message
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