// MulticastSendingThread.java
// MulticastSendingThread is a Thread that broadcasts a chat
// message using a multicast datagram.
package com.deitel.messenger.sockets.server;

// Java core packages
import java.io.*;
import java.net.*;

// Deitel packages
import com.deitel.messenger.sockets.*;

public class ResponseSendingThread extends Thread 
   implements SocketMessengerConstants {
   
   // message data
   private byte[] messageBytes;
   private InetAddress end_dest;
   private int port_dest;
   
   // MulticastSendingThread constructor
   public ResponseSendingThread( byte[] bytes, InetAddress ip_dest ) 
   { 
      // invoke superclass constructor to name Thread
      super( "ResponseSendingThread" );
      
      messageBytes = bytes; 
      end_dest = ip_dest;
      port_dest = CLIENT_LISTENING_PORT;
   }
   
   //Construtuor passando a porta
   public ResponseSendingThread(byte[] bytes, InetAddress ip_dest, int port)
   {
       // invoke superclass constructor to name Thread
      super( "ResponseSendingThread" );
      
      messageBytes = bytes; 
      end_dest = ip_dest;
      port_dest = port;
   }

   // deliver message to MULTICAST_ADDRESS over DatagramSocket
   public void run() 
   {
      // deliver message
      try {         
         System.out.println(new String(messageBytes));
         
         // create DatagramSocket for sending message
         DatagramSocket socket = new DatagramSocket();
         
         // create DatagramPacket containing message
         DatagramPacket packet = new DatagramPacket( 
            messageBytes, messageBytes.length, end_dest, port_dest );
         
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