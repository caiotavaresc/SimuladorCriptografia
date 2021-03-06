package com.deitel.messenger.model;

import java.sql.*;
import com.deitel.messenger.model.Usuario;

public class UsuarioDAO {
    
    private Connection conn;
    
    public UsuarioDAO()
    {
        try
        {
            this.conn = ConnectionFactory.getConnection();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    /*Verificar se o usuario existe na base*/
    public Usuario usuarioExiste(String nick)
    {
        String consulta = "SELECT U.* FROM USUARIO U WHERE U.NICK = ?";
        
        try
        {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(consulta);
            stmt.setString(1, nick);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next())
            {
                Usuario retorno = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getString(6));
                return retorno;
            }
            
            stmt.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
        
        return null;
    }
    
    /*Verificar se o usuario e a senha conferem*/
    public boolean usuarioConfereSenha(int id, String pass)
    {
        String consulta = "SELECT U.* FROM USUARIO U WHERE U.USER_ID = ?";
        
        try
        {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(consulta);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next())
                if(rs.getString(3).equals(pass))
                    return true;
            
            stmt.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
        
        return false;
    }
    
    //Atualizar a chave pública assimétrica do usuário
    public boolean atualizarChavePublica(int userId, String chavePublicaAssimetrica)
    {
        String consulta = "UPDATE USUARIO SET PUBLIC_KEY1 = ? WHERE USER_ID = ?";
        
        try
        {
            PreparedStatement stmt = conn.prepareStatement(consulta);
            
            stmt.setString(1, chavePublicaAssimetrica);
            stmt.setInt(2, userId);
            
            stmt.execute();
            stmt.close();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            return false;
        }
    }
    
}