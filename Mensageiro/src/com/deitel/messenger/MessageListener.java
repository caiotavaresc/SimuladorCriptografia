// MessageListener.java
// MessageListener is an interface for classes that wish to
// receive new chat messages.
package com.deitel.messenger;

import java.net.InetAddress;
import java.util.List;

public interface MessageListener {

   // receive new chat message
   //public void messageReceived( String from, String message );
   
   // Novo formato de mensagem
   public void messageReceived( String ID_FROM, String ID_TO, String CRYPTO_TYPE, String MSG_TYPE, String MSG_TEXT );
   
   // Mensagem de autenticacao
   public void authMessageReceived ( String nick, String pass, InetAddress ip_dest);
   
   // Mensagem de requisicao
   public void requestMessageReceived ( int userId, InetAddress ip_from);
   
   // Chave assimétrica recebida
   public void asymPublicMessageReceived( int userId, String arrayBytes);
   
   // Chaves públicas dos contatos
   public void asymPublicReqReceived(String contato, InetAddress ip_dest);
}