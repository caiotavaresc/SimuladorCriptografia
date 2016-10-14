// PacketReceivingThread.java
// PacketReceivingThread listens for DatagramPackets containing
// messages from a DeitelMessengerServer.
package com.deitel.messenger.sockets.client;

// Java core packages
import java.io.*;
import java.net.*;
import java.util.*;

// Deitel packages
import com.deitel.messenger.*;
import com.deitel.messenger.sockets.*;

public class PacketReceivingThread extends Thread 
   implements SocketMessengerConstants {
   
   // MessageListener to whom messages should be delivered
   private MessageListener messageListener;
   
   // MulticastSocket for receiving broadcast messages
   private MulticastSocket multicastSocket;
   
   // InetAddress of group for messages
   private InetAddress multicastGroup;

   // flag for terminating PacketReceivingThread
   private boolean keepListening = true;
   
   // PacketReceivingThread constructor
   public PacketReceivingThread( MessageListener listener ) 
   {
      // invoke superclass constructor to name Thread
      super( "PacketReceivingThread" );
      
      // set MessageListener
      messageListener = listener;
      
      // connect MulticastSocket to multicast address and port
      try {
         multicastSocket = 
            new MulticastSocket( MULTICAST_LISTENING_PORT );
         
         multicastGroup = 
            InetAddress.getByName( MULTICAST_ADDRESS );
         
         // join multicast group to receive messages
         multicastSocket.joinGroup( multicastGroup ); 
         
         // set 5 second timeout when waiting for new packets
         multicastSocket.setSoTimeout( 5000 );
      }
      
      // handle exception connecting to multicast address
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }
      
   } // end PacketReceivingThread constructor
   
   // listen for messages from multicast group 
   public void run() 
   {          
      // listen for messages until stopped
      while ( keepListening ) {

         // create buffer for incoming message
         byte[] buffer = new byte[ MESSAGE_SIZE ];

         // create DatagramPacket for incoming message
         DatagramPacket packet = new DatagramPacket( buffer, 
            MESSAGE_SIZE );

         // receive new DatagramPacket (blocking call)
         try {            
            multicastSocket.receive( packet );
         }

         // handle exception when receive times out
         catch ( InterruptedIOException interruptedIOException ) {
            
            // continue to next iteration to keep listening
            continue;
         }
         
         // handle exception reading packet from multicast group
         catch ( IOException ioException ) {
            ioException.printStackTrace();
            break;
         }

         // put message data in a String
         String message = new String( packet.getData() );

         // ensure non-null message
         if ( message != null ) {

            // trim extra whitespace from end of message
            message = message.trim();

            // tokenize message to retrieve user name
            // and message body
            StringTokenizer tokenizer = 
               new StringTokenizer( message, MESSAGE_SEPARATOR );

            // ignore messages that do not contain a user
            // name and message body
            if ( tokenizer.countTokens() == 2 ) {

               // send message to MessageListener
               messageListener.messageReceived( 
                  tokenizer.nextToken(),   // user name
                  tokenizer.nextToken() ); // message body
            }

         } // end if

      } // end while

      // leave multicast group and close MulticastSocket
      try {
         multicastSocket.leaveGroup( multicastGroup );
         multicastSocket.close(); 
      }
      
      // handle exception reading packet from multicast group
      catch ( IOException ioException ) { 
         ioException.printStackTrace();
      }
      
   } // end method run
   
   // stop listening for new messages
   public void stopListening() 
   {
      // terminate Thread
      keepListening = false;
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