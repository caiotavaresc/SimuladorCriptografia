//Classe que realiza a conexao com o banco de dados

package com.deitel.messenger.model;

import java.sql.*;

public class ConnectionFactory {
	
	public static Connection getConnection() throws SQLException
	{
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SIMULADOR_CRIPTO", "SIMULADOR_CRIPTO");
	}
	
}
