package com.deitel.messenger.keys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

/* Biblioteca de métodos estáticos úteis */
public class Utils {
    
    /* Método que converte uma string formatada pelo Arrays.toString para um array de bytes */
    public static byte[] converteStringParaArrayDeBytes(String texto)
    {
        String[] quebra = texto.split(",");
        
        //O primeiro e o último elementos têm um colchete - remover
        quebra[0] = quebra[0].substring(1);
        quebra[quebra.length - 1] = quebra[quebra.length - 1].substring(1, quebra[quebra.length - 1].length() - 1);
        
        byte[] retorno = new byte[quebra.length];
        
        int i;
        for(i = 0; i < retorno.length; i++)
            retorno[i] = Byte.valueOf(quebra[i].trim());
        
        return retorno;
    }
    
    /* Método que converte array de bytes para string */
    public static String converteArrayDeBytesParaString(byte[] array)
    {
        return new String(array);
    }
    
    /* Método que gera uma sequência aleatória de caracteres de um array de CHARs */
    public static String sequenciaAleatoria(int numElementos, char[] arrayCaracteres)
    {
        Random r = new Random();
        int i;
        char[] saida = new char[numElementos];
        
        for(i=0; i < numElementos; i++)
            saida[i] = arrayCaracteres[r.nextInt(arrayCaracteres.length)];
        
        return String.valueOf(saida);
    }
    
    //Converter [qualquer] objeto em array de bytes
    public static byte[] converterObjetoEmArrayDeBytes(Object objeto) throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(objeto);
        oos.close();
        
        return bos.toByteArray();
    }
    
    //Converter [qualquer] array de bytes em um objeto
    public static Object converterArrayDeBytesEmObjeto(byte[] array) throws Exception
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        ObjectInputStream ois = new ObjectInputStream(bis);
        
        Object retorno = ois.readObject();
        ois.close();
        
        return retorno;
    }
    
}
