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
import java.util.List;

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
   public void messageReceived( String ID_FROM, String NICK_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT ) 
   {          
      // A partir do nick do usuário, descobrir o ID
       Usuario destinatario = USUARIODAO.usuarioExiste(NICK_TO);
      
      // Ao invés de enviar as mensagens para todos os usuários, nós vamos armazená-las
      Mensagem msg = new Mensagem(Integer.parseInt(ID_FROM), destinatario.getUSER_ID(), Integer.parseInt(CRYPTO_TYPE), Integer.parseInt(MSG_TYPE), MSG_TEXT);
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
    
    //Mensagem de requisicao de mensagens de texto
    public void requestMessageReceived(int userId, InetAddress ip_dest)
    {
        
        //Pegar todas as mensagens pendentes para o usuario
        List<Mensagem> mensagensUsuario = MENSAGEMDAO.obterMensagensUsuario(userId);
        
        //Montar as mensagens da requisicao
        String mensagem;
        
        for(int i = 0; i < mensagensUsuario.size(); i++)
        {
            Mensagem msg = mensagensUsuario.get(i);
            mensagem = msg.getNICK_FROM() + MESSAGE_SEPARATOR + msg.getCRYPTO_TYPE() + MESSAGE_SEPARATOR + msg.getMSG_TYPE() + MESSAGE_SEPARATOR + msg.getMSG_TEXT() + MESSAGE_SEPARATOR + msg.getDataHoraFormatado();
            
            //Enviar a resposta
            new ResponseSendingThread(mensagem.getBytes(), ip_dest).start();
            
            MENSAGEMDAO.destroi(msg);
        }
    }
    
    //Mensagem contendo a chave pública
    public void asymPublicMessageReceived(int userId, String arrayBytes)
    {
        if(!USUARIODAO.atualizarChavePublica(userId, arrayBytes))
        {
            System.out.println("Não foi possível atualizar a chave");
        }
    }
    
    //Mensagem contendo uma lista de contatos para retornar as chaves públicas
    public void asymPublicReqReceived(String contato, InetAddress ip_dest)
    {
        String nick, mensagemResposta;
        Usuario user;
        
        //Identificador da mensagem de resposta
        mensagemResposta = "ASYM_PUBLIC_REQ";

        //Pegar os dados do contato (inclusive a chave pública RSA)
        user = USUARIODAO.usuarioExiste(contato);
        
        System.out.println("Enviou de " + contato);
            
        mensagemResposta = mensagemResposta + MESSAGE_SEPARATOR + contato + MESSAGE_SEPARATOR + user.getPUBLIC_KEY1();
        
        //Enviar a resposta
        new ResponseSendingThread(mensagemResposta.getBytes(), ip_dest, CLIENT_AUTH_LISTENING_PORT).start();
    }
   
   // start the server
   public static void main ( String args[] ) 
   {
      new MessengerServer().startServer();
   }
}