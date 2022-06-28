package com.jbdc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexãoBD {

	public static void main(String[] args) throws SQLException {
		
		String sql = "select * from pessoas_model";
		String url = "jdbc:mysql://localhost:3306/cpqi?user=root&password=Rickhfs0809*";
		
		try (Connection con = DriverManager.getConnection(url);
		PreparedStatement stm =  con.prepareStatement(sql);
		ResultSet rs = stm.executeQuery()) 
		{
			
			while(rs.next()) {
				String s = rs.getString("id") +
						"; " + rs.getString("nome") + 
						"; " + rs.getString("email") +
						"; " + rs.getString("idade") +
						"; " + rs.getString("sexo");
				System.out.println(s);
			}
		}
		
		
	}
}
