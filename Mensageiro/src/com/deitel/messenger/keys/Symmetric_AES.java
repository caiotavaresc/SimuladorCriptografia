package com.deitel.messenger.keys;

import com.deitel.messenger.ClientGUI2;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Symmetric_AES {
    
    public static final String ALGORITHM = "AES";
    private final static String IV = "AAAAAAAAAAAAAAAA";
    private final static char[] hexa = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private static final HashMap<String, String> MAPA_USUARIOS_CHAVES = new HashMap<String, String>();
    
    //Método que gera uma chave aleatória e coloca no array de chaves
    public static String geraChave(String userName)
    {
        String chaveFinal = Utils.sequenciaAleatoria(16, hexa);
                
        MAPA_USUARIOS_CHAVES.put(userName, chaveFinal);
        return chaveFinal;
    }
    
    public static String geraValorAleatorioHexa()
    {
        String VetorInicializacao = Utils.sequenciaAleatoria(16, hexa);
        return VetorInicializacao;
    }

    //Métodos de encriptação e decriptação com AES - Chave Simétrica
    public static byte[] ChaveSimetricaEncrypt(String textopuro, String chaveencriptacao) throws Exception 
    {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), ALGORITHM);
        encripta.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8"));
    }
    
    //Método que faz exatamente o que o de cima faz, com a diferença de passar o vetor de inicialização
    public static byte[] ChaveSimetricaEncrypt(String textopuro, String chaveencriptacao, String Vetor) throws Exception
    {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), ALGORITHM);
        encripta.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(Vetor.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8"));
    }
   
    public static String ChaveSimetricaDecrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception
    {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), ALGORITHM);
        decripta.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado),"UTF-8");
    }
    
    public static String ChaveSimetricaDecrypt(byte[] textoencriptado, String chaveencriptacao, String vetor) throws Exception
    {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), ALGORITHM);
        decripta.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(vetor.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado),"UTF-8");
    }
    
    //Métodos que manipulam o mapa de chaves e contatos
    public static String obterChaveContato(String userName)
    {
        if(MAPA_USUARIOS_CHAVES.containsKey(userName))
            return MAPA_USUARIOS_CHAVES.get(userName);
        else
            return null;
    }
    
    public static void inserirChaveContato(String userName, String chave)
    {
        if(MAPA_USUARIOS_CHAVES.containsKey(userName))
            MAPA_USUARIOS_CHAVES.replace(userName, chave);
        else
            MAPA_USUARIOS_CHAVES.put(userName, chave);
    }
    
    //Métodos que manipulam as mensagens
    public static void chaveSimetricaRecebida(String NICK_FROM, String MSG_TEXT, String MSG_DTTM)
    {
        //Incluir a chave no mapa de chaves
        inserirChaveContato(NICK_FROM, MSG_TEXT);
        
        //Montar a mensagem
        String mensagem = NICK_FROM + " gerou a chave " + MSG_TEXT + " em " + MSG_DTTM + "\r\n";
        mensagem = mensagem + "Chave Simétrica - Protocolo AES \r\n";
        mensagem = mensagem + "Essa chave foi recebida pelo canal de rede\r\n\r\n";
        
        ClientGUI2.janelaAtual.appendText(NICK_FROM, mensagem);
    }
    
    public static void mensagemRecebida(String NICK_FROM, String MSG_TEXT, String MSG_DTTM)
    {
        String MSG_DECRYPT = null;
        
        //Converter a String recebida para array de bytes
        byte[] received = Utils.converteStringParaArrayDeBytes(MSG_TEXT);
        
        String mensagem = "Tipo de Criptografia: Chave Simétrica - Protocolo AES\r\n";
        mensagem = mensagem + NICK_FROM + " escreveu uma mensagem em: " + MSG_DTTM + "\r\n";
        mensagem = mensagem + "Texto recebido: " + Utils.converteArrayDeBytesParaString(received) + "\r\n";
        
        if(MAPA_USUARIOS_CHAVES.containsKey(NICK_FROM))
        {
            String chave = MAPA_USUARIOS_CHAVES.get(NICK_FROM);
            mensagem = mensagem + "Chave utilizada na decriptação: " + chave + "\r\n";
            
            try
            {
                MSG_DECRYPT = ChaveSimetricaDecrypt(received, chave);
                mensagem = mensagem + "Texto desencriptado: " + MSG_DECRYPT + "\r\n\r\n";
            }
            catch(Exception e)
            {
                mensagem = mensagem + "Não foi possível desencriptar o texto: " + e.toString() + "\r\n\r\n";
            }
        }
        else
        {
            mensagem = mensagem + "Você não possui a chave necessária para desencriptar o texto\r\n\r\n";
        }
        
        //Colocar a mensagem na janela
        ClientGUI2.janelaAtual.appendText(NICK_FROM, mensagem);
    }
    
    //Método responsável por enviar a mensagem
    public static void enviarMensagem(String ID_FROM, String NICK_TO, String MSG_TEXT)
    {
        String chave;
        Date dttm = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        //Verificar se existe chave para o usuário em questão
        if(!MAPA_USUARIOS_CHAVES.containsKey(NICK_TO))
        {
            //Se a chave não existir, gerar e enviar a mensagem de acordo de chaves
            chave = geraChave(NICK_TO);
            
            //Enviar a chave pelo canal de rede
            ClientGUI2.sendMessage(ID_FROM, NICK_TO, "2", "2", chave);
            
            //Exibir para o usuário que a chave foi gerada
            dttm = new Date();
            String mensagemC = "Você gerou a chave " + chave + " em " + sdf.format(dttm) + "\r\n";
            mensagemC = mensagemC + "Chave Simétrica - Protocolo AES \r\n";
            mensagemC = mensagemC + "Essa chave foi enviada para "+ NICK_TO +" pelo canal de rede\r\n\r\n";
            
            ClientGUI2.janelaAtual.appendText(NICK_TO, mensagemC);
        }
        else
        {
            chave = obterChaveContato(NICK_TO);
        }
        
        dttm = new Date();
        String mensagem = "Tipo de Criptografia: Chave Simétrica - Protocolo AES\r\n";
        mensagem = mensagem + "Você escreveu uma mensagem em " + sdf.format(dttm) + "\r\n";
        
        try
        {
            //Criptografar a mensagem a ser enviada
            byte[] textoCriptografado;
            textoCriptografado = ChaveSimetricaEncrypt(MSG_TEXT, chave);
            
            //Enviar a mensagem criptografada pelo canal de rede
            ClientGUI2.janelaAtual.sendMessage(ID_FROM, NICK_TO, "2", "1", Arrays.toString(textoCriptografado));
            
            //Escrever mensagem na tela
            mensagem = mensagem + "Texto original: " + MSG_TEXT + "\r\n";
            mensagem = mensagem + "Texto enviado: " + Utils.converteArrayDeBytesParaString(textoCriptografado) + "\r\n\r\n";
        }
        catch(Exception e)
        {
            mensagem = mensagem + "Não foi possível criptografar sua mensagem\r\n";
            mensagem = mensagem + "Nada foi enviado\r\n\r\n";
        }
        
        ClientGUI2.janelaAtual.appendText(NICK_TO, mensagem);
    }
}
