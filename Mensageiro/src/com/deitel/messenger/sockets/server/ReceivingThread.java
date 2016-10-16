// ReceivingThread.java
// ReceivingThread is a Thread that listens for messages
// from a particular client and delivers messages to a
// MessageListener.
package com.deitel.messenger.sockets.server;

// Java core packages
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

// Deitel packages
import com.deitel.messenger.*;
import com.deitel.messenger.sockets.*;

public class ReceivingThread extends Thread implements
   SocketMessengerConstants {

   private BufferedReader input;
   private MessageListener messageListener;
   private Socket clientSocket;
   private boolean keepListening = true;
   
   // ReceivingThread constructor
   public ReceivingThread( MessageListener listener, 
      Socket clientSocket ) 
   {
      // invoke superclass constructor to name Thread
      super( "ReceivingThread: " + clientSocket );
      
      // set listener to which new messages should be sent
      messageListener = listener;
      this.clientSocket = clientSocket;
      
      // set timeout for reading from clientSocket and create
      // BufferedReader for reading incoming messages
      try {         
         clientSocket.setSoTimeout( 5000 );
         
         input = new BufferedReader( new InputStreamReader( 
            clientSocket.getInputStream() ) );
      }
      
      // handle exception creating BufferedReader
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }
   
   } // end ReceivingThread constructor
   
   // listen for new messages and deliver them to MessageListener
   public void run() 
   {    
      String message;
      
      // listen for messages until stoppped
      while ( keepListening ) {   

         // read message from BufferedReader
         try {            
            message = input.readLine();
         }
         
         // handle exception if read times out
         catch ( InterruptedIOException interruptedIOException ) {

            // continue to next iteration to keep listening
            continue;
         }
         
         // handle exception reading message
         catch ( IOException ioException ) {
            ioException.printStackTrace();            
            break;
         }

         // ensure non-null message
         if ( message != null ) {

            // tokenize message to retrieve user name
            // and message body
            StringTokenizer tokenizer = 
               new StringTokenizer( message, MESSAGE_SEPARATOR );

            // ignore messages that do not contain a user
            // name and message body               
            if ( tokenizer.countTokens() == 5 ) {

               // 1 - ID FROM
               // 2 - ID_TO
               // 3 - CRYPTO_TYPE
               // 4 - MSG_TYPE
               // 5 - MSG_TEXT
               messageListener.messageReceived( 
                  tokenizer.nextToken(),   // ID_FROM
                  tokenizer.nextToken(),   // ID_TO
                  tokenizer.nextToken(),   // CRYPTO_TYPE
                  tokenizer.nextToken(),   // MSG_TYPE
                  tokenizer.nextToken());  // MSG_TEXT
            }

            else

               // if disconnect message received, stop listening
               if ( message.equalsIgnoreCase( MESSAGE_SEPARATOR +
                  DISCONNECT_STRING ) ) {

                  stopListening();
               }
            
               // Se a mensagem recebida foi de AUTENTICACAO
               if(tokenizer.nextToken().equals("AUTH"))
               {                   
                   messageListener.authMessageReceived(tokenizer.nextToken()
                           , tokenizer.nextToken()
                           , clientSocket.getInetAddress());
               }

         }  // end if

      } // end while  
      
      // close BufferedReader (also closes Socket)
      try {         
         input.close();      
      }
      
      // handle exception closing BufferedReader
      catch ( IOException ioException ) {
         ioException.printStackTrace();     
      }       
 
   } // end method run
   
   // stop listening for incoming messages
   public void stopListening() 
   {
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