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
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketSendingRequestThread extends Thread 
   implements SocketMessengerConstants {

   // flag for terminating PacketReceivingThread
   private boolean keepUpdating = true;
   
   // Id do usuário que faz as requisições
   private int userId;
   
   // MessageManager que controla as mensagens
   private MessageManager messageManager;
   
   // PacketReceivingThread constructor
   public PacketSendingRequestThread( MessageManager manager ) 
   {
      // invoke superclass constructor to name Thread
      super( "PacketSendingRequestThread" );
      
      // set MessageListener
      this.messageManager = manager;
      
   } // end PacketReceivingThread constructor
   
   // listen for messages from multicast group 
   public void run() 
   {          
      // listen for messages until stopped
      while ( keepUpdating ) {

         System.out.println("Tá enviando");

          try {
              //Pausar a execucao por dois segundos
              Thread.sleep(2000);
          } catch (InterruptedException ex) {
              System.out.println(ex);
          }

         // put message data in a String
         String message = "REQUEST" + MESSAGE_SEPARATOR + this.userId;

         messageManager.sendRequestMessage(message);

      } // end while
      
   } // end method run
   
   // stop listening for new messages
   public void stopListening() 
   {
      // terminate Thread
      keepUpdating = false;
   }

    public void setUserId(int userId) {
        this.userId = userId;
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