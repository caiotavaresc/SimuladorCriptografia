package com.deitel.messenger.keys;

import com.deitel.messenger.ClientGUI2;
import com.deitel.messenger.MessageManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashMap;
import javax.crypto.Cipher;


public class Asymmetric_RSA {

  public static final String ALGORITHM = "RSA";
  public static final String PATH_CHAVE_PRIVADA = "files/private.key";
  public static final String PATH_CHAVE_PUBLICA = "files/public.key";
  public static PublicKey CHAVE_PUBLICA;
  public static PrivateKey CHAVE_PRIVADA;
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
  public static byte[] criptografa(String texto, PublicKey chave) {
    byte[] cipherText = null;

    try {
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
      // Criptografa o texto puro usando a chave Púlica
      cipher.init(Cipher.ENCRYPT_MODE, chave);
      cipherText = cipher.doFinal(texto.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return cipherText;
  }

  /**
   * Decriptografa o texto puro usando chave privada.
   */
  public static String decriptografa(byte[] texto, PrivateKey chave) {
    byte[] dectyptedText = null;

    try {
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
      // Decriptografa o texto puro usando a chave Privada
      cipher.init(Cipher.DECRYPT_MODE, chave);
      dectyptedText = cipher.doFinal(texto);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

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
}