package com.deitel.messenger.keys;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

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
        /*ByteArrayInputStream bis = new ByteArrayInputStream(array);
        
        try
        {
            ObjectInputStream ois = new ObjectInputStream(bis);
            String retorno = (String) ois.readObject();
            
            return retorno;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }*/
        return new String(array);
    }
    
}
