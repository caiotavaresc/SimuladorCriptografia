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
   
   // Socket que escuta as respostas
   private DatagramSocket responseSocket;

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
         responseSocket = 
            new DatagramSocket( CLIENT_LISTENING_PORT );
         
         // set 5 second timeout when waiting for new packets
         responseSocket.setSoTimeout( 2000 );
      }
      
      // handle exception connecting to multicast address
      catch ( IOException ioException ) {
         ioException.printStackTrace();
      }
      
   } // end PacketReceivingThread constructor
   
   // listen for messages from multicast group 
   public void run() 
   {
       //Quando essa Thread startar, um tratador de mensagens de autenticação startará também
       new TratadorMensagensAutenticacao(messageListener).start();
       
      // listen for messages until stopped
      while ( keepListening ) {

         // create buffer for incoming message
         byte[] buffer = new byte[ MESSAGE_SIZE ];
         
         // create DatagramPacket for incoming message
         DatagramPacket packet = new DatagramPacket( buffer, 
            MESSAGE_SIZE );

         // receive new DatagramPacket (blocking call)
         try {            
            responseSocket.receive( packet );
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
             
             System.out.println(message);
             
            // trim extra whitespace from end of message
            message = message.trim();

            // tokenize message to retrieve user name
            // and message body
            StringTokenizer tokenizer = 
               new StringTokenizer( message, MESSAGE_SEPARATOR );

            // ignore messages that do not contain a user
            // name and message body
            if ( tokenizer.countTokens() == 5 ) {

               // send message to MessageListener
               messageListener.messageReceived( 
                  tokenizer.nextToken(),   // ID_FROM
                  tokenizer.nextToken(),   // CRYPTO_TYPE
                  tokenizer.nextToken(),   // MSG_TYPE
                  tokenizer.nextToken(),   // MSG_TEXT
                  tokenizer.nextToken());  // MSG_DATETIME
            }
            else
            {
                String tipoMensagem = tokenizer.nextToken();
                
                //Verificar se o pacote recebido e uma resposta de autenticacao
                if(tipoMensagem.equals("AUTH"))
                {                    
                    // Prosseguir com a resposta -> Id do Usuario + Nome, Mensagem, Endereco
                    messageListener.authMessageReceived(tokenizer.nextToken(), tokenizer.nextToken(), responseSocket.getInetAddress());
                }
                
                //Verificar se o pacote recebido é uma resposta contendo as chaves públicas dos contatos
                if(tipoMensagem.equals("ASYM_PUBLIC_REQ"))
                {
                    //Prosseguir com a resposta
                    messageListener.asymPublicReqReceived(tokenizer.nextToken() + MESSAGE_SEPARATOR + tokenizer.nextToken(), responseSocket.getInetAddress());
                }
            }

         } // end if

      } // end while

      // Close ResponseSocket
         responseSocket.close(); 
      
   } // end method run
   
   // stop listening for new messages
   public void stopListening() 
   {
      // terminate Thread
      keepListening = false;
   }
}

class TratadorMensagensAutenticacao extends Thread implements SocketMessengerConstants
{
   // MessageListener to whom messages should be delivered
   private MessageListener messageListener;
   
   // Socket que escuta as respostas
   private DatagramSocket responseSocket;

   // flag for terminating PacketReceivingThread
   private boolean keepListening = true;
   
   // PacketReceivingThread constructor
   public TratadorMensagensAutenticacao( MessageListener listener ) 
   {
      // invoke superclass constructor to name Thread
      super( "TratadorMensagensAutenticacao" );
      
      // set MessageListener
      messageListener = listener;
      
      // connect MulticastSocket to multicast address and port
      try {
         responseSocket = 
            new DatagramSocket( CLIENT_AUTH_LISTENING_PORT );
         
         // set 2 second timeout when waiting for new packets
         responseSocket.setSoTimeout( 2000 );
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
            responseSocket.receive( packet );
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
             
             System.out.println(message);
             
            // trim extra whitespace from end of message
            message = message.trim();

            // tokenize message to retrieve user name
            // and message body
            StringTokenizer tokenizer = new StringTokenizer( message, MESSAGE_SEPARATOR );

            String tipoMensagem = tokenizer.nextToken();

            //Verificar se o pacote recebido é uma resposta contendo as chaves públicas dos contatos
            if(tipoMensagem.equals("ASYM_PUBLIC_REQ"))
            {
                //Prosseguir com a resposta
                messageListener.asymPublicReqReceived(tokenizer.nextToken() + MESSAGE_SEPARATOR + tokenizer.nextToken(), responseSocket.getInetAddress());
            }

         } // end if

      } // end while

      // Close ResponseSocket
         responseSocket.close(); 
      
   } // end method run
   
   // stop listening for new messages
   public void stopListening() 
   {
      // terminate Thread
      keepListening = false;
   }
}