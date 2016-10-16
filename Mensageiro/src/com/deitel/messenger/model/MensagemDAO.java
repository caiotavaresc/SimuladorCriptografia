package com.deitel.messenger.model;

import java.sql.*;
import java.util.*;
import java.sql.Date;

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
    
}
