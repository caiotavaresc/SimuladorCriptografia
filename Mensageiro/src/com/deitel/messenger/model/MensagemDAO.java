package com.deitel.messenger.model;

import java.sql.*;
import java.util.*;

public class MensagemDAO {
    
    private Connection conn;
    
    public MensagemDAO()
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
    
    //Método que adiciona a mensagem
    public boolean adiciona(Mensagem mensagem)
    {
        String consulta = "INSERT INTO MENSAGENS(ID_MSG, ID_FROM, ID_TO, CRYPTO_TYPE, MSG_TYPE, MSG_TEXT, MSG_DATETIME) VALUES (MENSAGEM.NEXTVAL,?,?,?,?,?,SYSDATE)";
                
	//Passa os valores
	try
	{
            PreparedStatement stmt = conn.prepareStatement(consulta);
            stmt.setInt(1, mensagem.getID_FROM());
            stmt.setInt(2, mensagem.getID_TO());
            stmt.setInt(3, mensagem.getCRYPTO_TYPE());
            stmt.setInt(4, mensagem.getMSG_TYPE());
            stmt.setString(5, mensagem.getMSG_TEXT());
			
            //Executa a consulta
            stmt.execute();
            stmt.close();
                        
            return true;
	}
	catch(Exception e)
	{
            System.out.println(e.toString());
                        
            return false;
	}
    }
    
    public List<Mensagem> obterMensagensUsuario(int userId)
    {
        List<Mensagem> lista = new ArrayList<Mensagem>();
        
        String consulta = "SELECT M.*, U.NICK, TO_CHAR(M.MSG_DATETIME, 'DD/MM/YYYY HH24:MI:SS') FROM MENSAGENS M, USUARIO U WHERE M.ID_TO = ? AND U.USER_ID = M.ID_FROM ORDER BY M.MSG_DATETIME ASC";
        
        try
        {
            PreparedStatement stmt = conn.prepareStatement(consulta);
            stmt.setInt(1, userId);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next())
            {
                Mensagem msg = new Mensagem(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getDate(7));
                msg.setNICK_FROM(rs.getString(8));
                msg.setDataHoraFormatado(rs.getString(9));
                lista.add(msg);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return lista;
    }
    
    public void destroi(Mensagem m)
    {
        String consulta = "DELETE FROM MENSAGENS M WHERE M.ID_MSG = ?";
        
        try
        {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(consulta);
            stmt.setInt(1, m.getID_MSG());
            
            stmt.execute();
        }
        catch(Exception e)
        {
            System.out.println("Erro: " + e);
        }
    }
    
}
