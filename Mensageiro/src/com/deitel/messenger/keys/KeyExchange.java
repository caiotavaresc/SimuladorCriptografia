package com.deitel.messenger.keys;

//Classe que encapsula a criptografia com acordo de chaves

import com.deitel.messenger.ClientGUI2;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class KeyExchange {
    
  private static final HashMap<String, List<String>> MAPA_USUARIOS_CHAVES = new HashMap<String, List<String>>();
  private static final List<String> BACKLOG = new ArrayList<String>();
    
  //Método responsável por enviar uma mensagem com acordo de chaves
  public static void enviarMensagem(String ID_FROM, String NICK_TO, String MSG_TEXT)
  {
    //Se o usuário destino já possuir uma chave, basta efetuar a encriptação
      if(!MAPA_USUARIOS_CHAVES.containsKey(NICK_TO))
      {
          //Senão, é necessário gerar
          KeyExchange.gerarChave(NICK_TO);
      }
      
      Date dttm;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      
      dttm = new Date();
      String mensagem = "Tipo de Criptografia: Acordo de Chaves\r\n";
      mensagem = mensagem + "Você escreveu uma mensagem em " + sdf.format(dttm) + "\r\n";
      
      //Obter o vetor de inicialização e a chave
      List<String> dadosCripto = MAPA_USUARIOS_CHAVES.get(NICK_TO);
      String IV = dadosCripto.get(0);
      String chave = dadosCripto.get(1);
      
      //Criptografar a mensagem
      try
      {
          byte[] textoCriptografado = Symmetric_AES.ChaveSimetricaEncrypt(MSG_TEXT, chave, IV);
          
          //Enviar a mensagem criptografada pelo canal de rede
          ClientGUI2.janelaAtual.sendMessage(ID_FROM, NICK_TO, "4", "1", Arrays.toString(textoCriptografado));
          
          //Escrever mensagem na tela
          mensagem = mensagem + "Texto original: " + MSG_TEXT + "\r\n";
          mensagem = mensagem + "Texto enviado: " + Utils.converteArrayDeBytesParaString(textoCriptografado) + "\r\n\r\n";
      }
      catch(Exception ex)
      {
          mensagem = mensagem + "Não foi possível criptografar sua mensagem\r\n";
          mensagem = mensagem + "Nada foi enviado\r\n\r\n";
      }
      
      ClientGUI2.janelaAtual.appendText(NICK_TO, mensagem);
  }
  
  //Método responsável por gerar uma chave AES utilizando troca autenticada RSA
  private static void gerarChave(String NICK_TO)
  {
      //Obter chave pública do usuário destino
      PublicKey chaveContato = Asymmetric_RSA.obterChaveComServidor(NICK_TO);
      
      //Gerar um Vetor de Inicialização aleatório
      String IV = Symmetric_AES.geraValorAleatorioHexa();
      
      //Gerar uma chave AES de 128 bits
      String chave = Symmetric_AES.geraValorAleatorioHexa();
      
      //Criptografar a chave com A CHAVE PÚBLICA DO DESTINATÁRIO - Garantia de Confidencialidade
      String mensagem = "Acordo de Chaves: \r\n";
      try
      {
        byte[] chaveCripto = Asymmetric_RSA.criptografa(chave, chaveContato);
        Date dttm;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      
        //Criar uma entrada para o par IV/Chave no mapa de controle
        List<String> dados = new ArrayList<String>();
        dados.add(IV);
        dados.add(chave);
        MAPA_USUARIOS_CHAVES.put(NICK_TO, dados);

        //Criptografar o IV com a MINHA CHAVE PRIVADA - Garantia de autenticidade
        byte[] ivCripto = Asymmetric_RSA.criptografaComMinhaChavePrivada(IV);
        
        List<byte[]> valores = new ArrayList<byte[]>();
        valores.add(ivCripto);
        valores.add(chaveCripto);
        
        //Enviar a lista com as chaves criptografadas
        String envio = Arrays.toString(Utils.converterObjetoEmArrayDeBytes(valores));
        
        //Compor a mensagem para enviar:
        ClientGUI2.sendMessage(NICK_TO, "4", "2", envio);
        
        //Exibir mensagem na tela
        dttm = new Date();
        mensagem = mensagem + "Você gerou o Vetor de Inicialização: " + IV + "\r\n";
        mensagem = mensagem + "Você gerou a Chave AES: " + chave + "\r\n";
        mensagem = mensagem + "Vetor criptografado com a SUA Chave Privada RSA (Autenticação): " + Utils.converteArrayDeBytesParaString(ivCripto) + "\r\n";
        mensagem = mensagem + "Chave criptografada com a Chave Pública RSA de " + NICK_TO + " (Confiabilidade): " + Utils.converteArrayDeBytesParaString(chaveCripto) + "\r\n";
        mensagem = mensagem + "Vetor de Inicialização criptografado e Chave criptografada foram enviados para " + NICK_TO + " em " + sdf.format(dttm)+ "\r\n\r\n";
      }
      catch(Exception ex)
      {
          //Não foi possível criptografar a chave
          mensagem = mensagem + "Erro no Acordo de Chaves: \r\n";
          mensagem = mensagem + "Não foi possível criptografar as chaves de comunicação: \r\n\r\n";
          mensagem = mensagem + ex.toString();
      }
          
      ClientGUI2.janelaAtual.appendText(NICK_TO, mensagem);
  }
  
  //Método que valida o acordo de chaves e pega a chave e o vetor de inicialização
  public static void acordoChavesRecebido(String NICK_FROM, String MSG_TEXT, String MSG_DTTM)
  {
      //Converter o texto em um array de bytes
      byte[] mensagem = Utils.converteStringParaArrayDeBytes(MSG_TEXT);
      
      //Ler o array de bytes como uma lista
      List<byte[]> listaChaves;
      
      String mensagemC = NICK_FROM + " iniciou um acordo de chaves em "+ MSG_DTTM +"\r\n";
      try
      {
        listaChaves = (List<byte[]>) Utils.converterArrayDeBytesEmObjeto(mensagem);
        
        //Obter o vetor de Inicialização - Autenticação
        //--Utilizando a CHAVE PÚBLICA do REMETENTE (Só o remetente pode ter gerado essa mensagem)
        PublicKey chaveRemetente = Asymmetric_RSA.obterChaveComServidor(NICK_FROM);
        
        //Descriptografar o vetor de inicialização com a chave pública do remetente
        String VetorInicializacao = Asymmetric_RSA.decriptografaComChavePublica(listaChaves.get(0), chaveRemetente);
        
        //Descriptografar a chave AES com a chave privada do destinatário - Garantia de Confidencialidade
        String chaveAES = Asymmetric_RSA.decriptografaComMinhaChavePrivada(listaChaves.get(1));
        
        //Colocar os dois valores (IV e chave) numa lista e pôr no mapa
        List<String> listaChavesUser = new ArrayList<String>();
        listaChavesUser.add(VetorInicializacao);
        listaChavesUser.add(chaveAES);
        
        MAPA_USUARIOS_CHAVES.put(NICK_FROM, listaChavesUser); 
        
        //Montar a mensagem em tela
        mensagemC = mensagemC + "Vetor de Inicialização recebido: " + Utils.converteArrayDeBytesParaString(listaChaves.get(0)) + "\r\n";
        mensagemC = mensagemC + "Chave AES recebida: " + Utils.converteArrayDeBytesParaString(listaChaves.get(1)) + "\r\n";
        mensagemC = mensagemC + "Vetor de Inicialização desencriptado utilizando a Chave Pública de " + NICK_FROM + " (Autenticidade)\r\n";
        mensagemC = mensagemC + "Chave AES desencriptada utilizando a SUA Chave Pública (Confidencialidade)\r\n";
        mensagemC = mensagemC + "Vetor de Inicialização desencriptado: " + VetorInicializacao + "\r\n";
        mensagemC = mensagemC + "Chave AES desencriptada: " + chaveAES + "\r\n\r\n";
        
      }
      catch(Exception ex)
      {
        mensagemC = mensagemC + "Não foi possível desencriptar as chaves de comunicação: \r\n\r\n";
        mensagemC = mensagemC + ex.toString();
      }
      
      ClientGUI2.janelaAtual.appendText(NICK_FROM, mensagemC);
  }
  
  //Método para tratar mensagens recebidas
  public static void mensagemRecebida(String NICK_FROM, String MSG_TEXT, String MSG_DTTM)
  {
      String MSG_DECRYPT = null;
      byte[] received = Utils.converteStringParaArrayDeBytes(MSG_TEXT);
      
      String mensagem = "Tipo de Criptografia: Acordo de Chaves\r\n";
      mensagem = mensagem + NICK_FROM + " escreveu uma mensagem em: " + MSG_DTTM + "\r\n";
      mensagem = mensagem + "Texto recebido: " + Utils.converteArrayDeBytesParaString(received) + "\r\n";
      
      try
      {
        //Obter chave da sessão
        if(!MAPA_USUARIOS_CHAVES.containsKey(NICK_FROM))
        {
            throw new Exception("Você não possui as chaves necessárias para descriptografar essa mensagem");
        }
        
        List<String> listaChaves = MAPA_USUARIOS_CHAVES.get(NICK_FROM);
        MSG_DECRYPT = Symmetric_AES.ChaveSimetricaDecrypt(received, listaChaves.get(1), listaChaves.get(0));
        
        //Escrever mensagem na saída de texto
        mensagem = mensagem + "Vetor de Inicialização usado na decriptação: " + listaChaves.get(0) + "\r\n";
        mensagem = mensagem + "Chave AES usada na decriptação: " + listaChaves.get(1) + "\r\n";
        mensagem = mensagem + "Texto desencriptado: " + MSG_DECRYPT + "\r\n\r\n";
      }
      catch(Exception ex)
      {
          mensagem = mensagem + "Não foi possível desencriptar o texto: \r\n";
          mensagem = mensagem + ex.toString() + "\r\n\r\n";
      }
      
      ClientGUI2.janelaAtual.appendText(NICK_FROM, mensagem);
  }
    
}