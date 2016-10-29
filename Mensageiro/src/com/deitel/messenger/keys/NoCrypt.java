package com.deitel.messenger.keys;

//Classe para tratar as transações sem criptografia

import com.deitel.messenger.ClientGUI2;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoCrypt {
 
    //Tratar mensagem recebida
    public static void mensagemRecebida(String NICK_FROM, String MSG_TEXT, String MSG_DTTM)
    {
        String mensagem = "Tipo de criptografia: Nenhuma\r\n";
        mensagem = mensagem + NICK_FROM + " escreveu uma mensagem em " + MSG_DTTM + "\r\n";
        mensagem = mensagem + "Texto recebido: " + MSG_TEXT + "\r\n\r\n";
        
        ClientGUI2.janelaAtual.appendText(NICK_FROM, mensagem);
    }
    
    //Tratar envio de mensagem
    public static void enviarMensagem(String ID_FROM, String NICK_TO, String MSG_TEXT)
    {
        Date dttm = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        //Enviar a mensagem para o servidor
        ClientGUI2.sendMessage(ID_FROM, NICK_TO, "1", "1", MSG_TEXT);
        
        //Escrever o texto
        String mensagem = "Tipo de criptografia: Nenhuma\r\n";
        mensagem = mensagem + "Você escreveu uma mensagem em " + sdf.format(dttm) + "\r\n";
        mensagem = mensagem + "Texto enviado: " + MSG_TEXT + "\r\n\r\n";
        
        ClientGUI2.janelaAtual.appendText(NICK_TO, mensagem);
    }
    
}
