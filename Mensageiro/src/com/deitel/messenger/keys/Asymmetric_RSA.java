package com.deitel.messenger.keys;

import com.deitel.messenger.ClientGUI2;
import com.deitel.messenger.MessageManager;
import java.io.*;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;


public class Asymmetric_RSA {

  public static final String ALGORITHM = "RSA";
  public static final String PATH_CHAVE_PRIVADA = "files/private.key";
  public static final String PATH_CHAVE_PUBLICA = "files/public.key";
  private static PublicKey CHAVE_PUBLICA;
  private static PrivateKey CHAVE_PRIVADA;
  private static final HashMap<String, PublicKey> MAPA_USUARIOS_CHAVES = new HashMap<String, PublicKey>();
  
  //Método que verifica se as chaves já existem no SO
  //Se existirem instancia as variáveis
  //Se não existirem gera novas chaves e manda a chave privada para o servidor
  public static void iniciarChaves()
  {
    // Verifica se já existe um par de chaves, caso contrário gera-se as chaves..
    if (!verificaSeExisteChavesNoSO()) 
    {
     // Método responsável por gerar um par de chaves usando o algoritmo RSA e
     // armazena as chaves nos seus respectivos arquivos.
      geraChave();
    }
    
    ObjectInputStream inputStream = null;

    try
    {
        // Criptografa a Mensagem usando a Chave Pública
        inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
        Asymmetric_RSA.CHAVE_PUBLICA = (PublicKey) inputStream.readObject();

        // Decriptografa a Mensagem usando a Chave Pirvada
        inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
        Asymmetric_RSA.CHAVE_PRIVADA = (PrivateKey) inputStream.readObject();
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
  }
  

  /**
   * Gera a chave que contém um par de chave Privada e Pública usando 1025 bytes.
   * Armazena o conjunto de chaves nos arquivos private.key e public.key
   */
  public static void geraChave() {
    try 
    {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(1024);
        KeyPair key = keyGen.generateKeyPair();

        File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA);
        File chavePublicaFile = new File(PATH_CHAVE_PUBLICA);

        // Cria os arquivos para armazenar a chave Privada e a chave Publica
        if (chavePrivadaFile.getParentFile() != null) {
          chavePrivadaFile.getParentFile().mkdirs();
        }

        chavePrivadaFile.createNewFile();

        if (chavePublicaFile.getParentFile() != null) {
          chavePublicaFile.getParentFile().mkdirs();
        }

        chavePublicaFile.createNewFile();

        // Salva a Chave Pública no arquivo
        ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile));
        chavePublicaOS.writeObject(key.getPublic());
        chavePublicaOS.close();

        // Salva a Chave Privada no arquivo
        ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile));
        chavePrivadaOS.writeObject(key.getPrivate());
        chavePrivadaOS.close();
        
        ByteArrayOutputStream chavePublicaToDb;
        chavePublicaToDb = new ByteArrayOutputStream();
        
        chavePublicaOS = new ObjectOutputStream(chavePublicaToDb);
        chavePublicaOS.writeObject(key.getPublic());
        chavePublicaOS.close();
        
        //Depois de gerar as chaves, atualizar a chave pública na web
        MessageManager teste = ClientGUI2.janelaAtual.getMessageManager();
        teste.sendRSAPublicKeyMessage(ClientGUI2.janelaAtual.getUserId(), Arrays.toString(chavePublicaToDb.toByteArray()));
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }
    
  }

  /**
   * Verifica se o par de chaves Pública e Privada já foram geradas.
   */
  public static boolean verificaSeExisteChavesNoSO() {

    File chavePrivada = new File(PATH_CHAVE_PRIVADA);
    File chavePublica = new File(PATH_CHAVE_PUBLICA);

    if (chavePrivada.exists() && chavePublica.exists()) {
      return true;
    }

    return false;
  }

  /**
   * Criptografa o texto puro usando chave pública.
   */
  public static byte[] criptografa(String texto, PublicKey chave) throws Exception {
      
    byte[] cipherText = null;
    final Cipher cipher = Cipher.getInstance(ALGORITHM);
    // Criptografa o texto puro usando a chave Púlica
    cipher.init(Cipher.ENCRYPT_MODE, chave);
    cipherText = cipher.doFinal(texto.getBytes());

    return cipherText;
  }

  /**
   * Decriptografa o texto puro usando chave privada.
   */
  public static String decriptografa(byte[] texto, PrivateKey chave) throws Exception {
    byte[] dectyptedText = null;

    final Cipher cipher = Cipher.getInstance(ALGORITHM);
    // Decriptografa o texto puro usando a chave Privada
    cipher.init(Cipher.DECRYPT_MODE, chave);
    dectyptedText = cipher.doFinal(texto);

    return new String(dectyptedText);
  }

  /**
   * Testa o Algoritmo
   */
  public static void main(String[] args) {

    try {

      // Verifica se já existe um par de chaves, caso contrário gera-se as chaves..
      if (!verificaSeExisteChavesNoSO()) {
       // Método responsável por gerar um par de chaves usando o algoritmo RSA e
       // armazena as chaves nos seus respectivos arquivos.
        geraChave();
      }

      final String msgOriginal = "Exemplo de mensagem";
      ObjectInputStream inputStream = null;

      // Criptografa a Mensagem usando a Chave Pública
      inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
      final PublicKey chavePublica = (PublicKey) inputStream.readObject();
      final byte[] textoCriptografado = criptografa(msgOriginal, chavePublica);

      // Decriptografa a Mensagem usando a Chave Pirvada
      inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
      final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
      final String textoPuro = decriptografa(textoCriptografado, chavePrivada);

      // Imprime o texto original, o texto criptografado e 
      // o texto descriptografado.
      System.out.println("Mensagem Original: " + msgOriginal);
      System.out.println("Mensagem Criptografada: " +textoCriptografado.toString());
      System.out.println("Mensagem Decriptografada: " + textoPuro);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void inserirChaveContato(String contato, byte[] chave)
  {
      PublicKey key = null;
      ByteArrayInputStream bis = new ByteArrayInputStream(chave);
      
      try {
          ObjectInputStream ois = new ObjectInputStream(bis);
          key = (PublicKey) ois.readObject();
          
      } catch (Exception ex) {
          System.out.println(ex);
          ex.printStackTrace();
      }
      
      if(!MAPA_USUARIOS_CHAVES.containsKey(contato))
          MAPA_USUARIOS_CHAVES.put(contato, key);
  }
  
  public static PublicKey obterChaveContato(String contato)
  {
      if(MAPA_USUARIOS_CHAVES.containsKey(contato))
          return MAPA_USUARIOS_CHAVES.get(contato);
      else
          return null;
  }
  
  public static void mensagemRecebida(String NICK_FROM, String MSG_TEXT, String MSG_DTTM)
  {
        String MSG_DECRYPT = null;
        
        //Converter a String recebida para array de bytes
        byte[] received = Utils.converteStringParaArrayDeBytes(MSG_TEXT);
        
        String mensagem = "Tipo de Criptografia: Chave Assimétrica - Protocolo RSA\r\n";
        mensagem = mensagem + NICK_FROM + " escreveu uma mensagem em " + MSG_DTTM + "\r\n";
        mensagem = mensagem + "Texto recebido: " + Utils.converteArrayDeBytesParaString(received) + "\r\n";
        
        //Tentar descriptografar o texto
        try
        {
            mensagem = mensagem + "Texto desencriptado: " + decriptografa(received, CHAVE_PRIVADA) + "\r\n";
            mensagem = mensagem + "Decriptação feita utilizando a chave privada do seu usuário, armazenada nesta máquina\r\n\r\n";
        }
        catch(Exception e)
        {
            mensagem = mensagem + "Não foi possível desencriptar o texto: " + e.toString() + "\r\n\r\n";
        }
        
        //Colocar a mensagem na janela
        ClientGUI2.janelaAtual.appendText(NICK_FROM, mensagem);
  }
  
  //Método responsável por enviar uma mensagem com chave assimétrica
  public static void enviarMensagem(String ID_FROM, String NICK_TO, String MSG_TEXT)
  {
    //Verificar se existe chave pública para o contato em questão
    PublicKey chavePublicaUser = Asymmetric_RSA.obterChaveContato(NICK_TO);
    boolean first = true;
    
    Date dttm = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //Se não houver chave pública, importar
    while(chavePublicaUser == null)
    {
        //Se não houver chave pública, solicitar ao servidor
        ClientGUI2.sendRSAPublicKeyReq(NICK_TO);

        //Exibir na tela a mensagem: Solicitando chave pública ao servidor
        if(first)
        {
            ClientGUI2.janelaAtual.appendText(NICK_TO, "Solicitando chave pública do destinatário ao servidor\r\n");
            first = false;
        }

        //Dormir um segundo para dar tempo de a mensagem chegar
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            System.out.println(e);
        }

        chavePublicaUser = Asymmetric_RSA.obterChaveContato(NICK_TO);

        //Quando obter, escrever a chave na tela
        if(chavePublicaUser != null)
            ClientGUI2.janelaAtual.appendText(NICK_TO, "Chave Pública recebida: \r\n" + chavePublicaUser.toString() + "\r\n\r\n");
    }
    
    //Preparar a mensagem para o envio
    String mensagem = "Tipo de Criptografia: Chave Assimétrica - Protocolo RSA\r\n";
    mensagem = mensagem + "Você escreveu uma mensagem em " + sdf.format(dttm) + "\r\n";
    
    try
    {
        byte[] textoCriptografado = criptografa(MSG_TEXT, chavePublicaUser);
        dttm = new Date();
        
        //Enviar a mensagem para o servidor
        ClientGUI2.sendMessage(ID_FROM, NICK_TO, "3", "1", Arrays.toString(textoCriptografado));
        
        //Montar a mensagem a ser exibida
        mensagem = mensagem + "Texto original: " + MSG_TEXT + "\r\n";
        mensagem = mensagem + "Texto enviado: " + Utils.converteArrayDeBytesParaString(textoCriptografado) + "\r\n\r\n";
    }
    catch(Exception e)
    {
        mensagem = mensagem + "Não foi possível criptografar sua mensagem\r\n";
        mensagem = mensagem + "Nada foi enviado\r\n\r\n";
    }
    
    //Exibir a mensagem
    ClientGUI2.janelaAtual.appendText(NICK_TO, mensagem);
  }
}