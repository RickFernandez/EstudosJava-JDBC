package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class IncluirDados {

	public static void main(String[] args) throws SQLException {
		
		String sql = "insert into pessoas_model values (?, ?, ?, ?, ?)";
		String url = "jdbc:mysql://localhost:3306/cpqi?user=root&password=Rickhfs0809*";
		String[] pessoas = {"Diego", "Leandro", "Kalu", "Carlos", "Eduardo"};
		
		try (Connection con = DriverManager.getConnection(url)) {
			try (PreparedStatement stm = con.prepareStatement(sql)) {
				for (int i = 0; i < pessoas.length; i++) {
					int random = 1 + (int)  (Math.random() * 100);
					
					stm.setInt(1, i+4);
					stm.setString(2, pessoas[1]);
					stm.setString(3, pessoas[i].toLowerCase() + "@emai.com");
					stm.setInt(4, random);
					stm.setString(5, "M");
					stm.addBatch();
					
				}
				stm.executeBatch(); //Método que inclui, altera e exclui dados no BD
			} catch (SQLException e) {}
			
			sql = "select nome, email from pessoas_model";
		
			try (PreparedStatement stm2 = con.prepareStatement(sql);
			ResultSet rs = stm2.executeQuery()) {
				while(rs.next()) {
					System.out.println(rs.getString(1) + ":" + rs.getString(2));
				}
			}
		}
	
	}
}
