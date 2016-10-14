// SocketMessageManager.java
// SocketMessageManager is a MessageManager implementation for
// communicating with a DeitelMessengerServer using Sockets
// and MulticastSockets.
package com.deitel.messenger.sockets.client;

// Java core packages
import java.util.*;
import java.net.*;
import java.io.*;

// Deitel packages
import com.deitel.messenger.*;
import com.deitel.messenger.sockets.*;

public class SocketMessageManager implements MessageManager,
   SocketMessengerConstants {
   
   // Socket for outgoing messages
   private Socket clientSocket; 
   
   // DeitelMessengerServer address
   private String serverAddress; 
   
   // Thread for receiving multicast messages
   private PacketReceivingThread receivingThread;
   
   // flag indicating connection status
   private boolean connected = false;
   
   // SocketMessageManager constructor
   public SocketMessageManager( String address )
   {
      serverAddress = address;
   }
   
   // connect to server and send messages to given MessageListener
   public void connect( MessageListener listener ) 
   {
      // if already connected, return immediately
      if ( connected )
         return;

      // open Socket connection to DeitelMessengerServer
      try {
         clientSocket = new Socket( 
            InetAddress.getByName( serverAddress ), SERVER_PORT );

         // create Thread for receiving incoming messages
         receivingThread = new PacketReceivingThread( listener );
         receivingThread.start();
         
         // update connected flag
         connected = true;
         
      } // end try

      // handle exception connecting to server
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }
   
   } // end method connect
   
   // disconnect from server and unregister given MessageListener
   public void disconnect( MessageListener listener ) 
   {
      // if not connected, return immediately
      if ( !connected )
         return;
      
      // stop listening thread and disconnect from server
      try {     
         
         // notify server that client is disconnecting
         Thread disconnectThread = new SendingThread( 
            clientSocket, "", DISCONNECT_STRING );         
         disconnectThread.start();         
         
         // wait 10 seconds for disconnect message to be sent
         disconnectThread.join( 10000 );
         
         // stop receivingThread and remove given MessageListener
         receivingThread.stopListening();
         
         // close outgoing Socket
         clientSocket.close(); 
         
      } // end try

      // handle exception disconnecting from server
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }
      
      // handle exception joining disconnectThread
      catch ( InterruptedException interruptedException ) {
         interruptedException.printStackTrace();
      }
     
      // update connected flag
      connected = false;
      
   } // end method disconnect
   
   // send message to server
   public void sendMessage( String from, String message ) 
   {
      // if not connected, return immediately
      if ( !connected )
         return;
      
      // create and start new SendingThread to deliver message
      new SendingThread( clientSocket, from, message).start();
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