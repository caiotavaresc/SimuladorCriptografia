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
import java.util.ArrayList;
import java.util.List;

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
         clientSocket.setSoTimeout( 2000 );
         
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
         try 
         {
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
            {

               // if disconnect message received, stop listening
               if ( message.equalsIgnoreCase( MESSAGE_SEPARATOR +
                  DISCONNECT_STRING ) ) {

                  stopListening();
               }
            
                String tipoMSG = tokenizer.nextToken();
                
               // Se a mensagem recebida foi de AUTENTICACAO
               if(tipoMSG.equals("AUTH"))
               {                   
                   messageListener.authMessageReceived(tokenizer.nextToken()
                           , tokenizer.nextToken()
                           , clientSocket.getInetAddress());
               }
               
               //Se a mensagem recebida for de REQUISICAO
               if(tipoMSG.equals("REQUEST"))
               {
                   messageListener.requestMessageReceived(Integer.parseInt(tokenizer.nextToken()), clientSocket.getInetAddress());
               }
               
               //Se a mensagem recebida for de CHAVE PUBLICA RSA
               if(tipoMSG.equals("ASYM_PUBLIC"))
               {                   
                   String idUser = tokenizer.nextToken();
                   String chave = tokenizer.nextToken();
                   
                   //Essa representação do array de bytes será guardada no BD
                   messageListener.asymPublicMessageReceived(Integer.parseInt(idUser), chave);
               }
               
               //Se a mensagem for uma requisição das chaves públicas dos contatos
               if(tipoMSG.equals("ASYM_PUBLIC_REQ"))
               {                   
                   //Enviar o contato para o listener, para ele enviar a resposta (chave pública do contato)
                   messageListener.asymPublicReqReceived(tokenizer.nextToken(), clientSocket.getInetAddress());
               }
               
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