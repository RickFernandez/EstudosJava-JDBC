package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaCrud {

	public void criar(Connection con, Conta conta) throws SQLException {
		String sql = "insert into cpqi.conta values (?, ?, ?)";
		
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, conta.numero);
			stm.setString(2, conta.cliente);
			stm.setDouble(3, conta.saldo);
			stm.executeUpdate();
		}
	}
	
	public List<Conta> ler(Connection con) throws SQLException {
		List<Conta> lista = new ArrayList<>();
		
		String sql = "select numero, cliente, saldo from cpqi.conta";
		
		try (PreparedStatement stm = con.prepareStatement(sql);
				ResultSet rs = stm.executeQuery()) {
			while(rs.next()) {
				lista.add(new Conta(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
			}
		}
		return lista;
	}
	
	public void alterar(Connection con, Conta conta) throws SQLException {
		String sql = "update cpqi.conta set cliente=?, saldo=? where numero=?";
		
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setString(1, conta.cliente);
			stm.setDouble(2, conta.saldo);
			stm.setInt(3, conta.numero);
			stm.executeUpdate();
		}
	}
	
	public void excluir(Connection con, Conta conta) throws SQLException {
		String sql = "delete from cpqi.conta where numero=?";
		
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setDouble(1, conta.numero);
			stm.executeUpdate();
		}
	}
	
	public void transferir(Connection con, Conta origem, Conta destino, double valor) throws SQLException {
		if(origem.saldo >= valor) {
			
			try {
				con.setAutoCommit(false); //Faz com que o programa trate as ações a seguir em conjunto
				// SAQUE
				origem.saldo -= valor;
				alterar(con, origem);
				
//				int x = 1/0;
				
				// DEPÓSITO
				destino.saldo += valor;
				alterar(con, destino);
				con.commit(); //Confirma a execução da transação caso tenha dado certo
			 
			} catch (Exception e) {
				 con.rollback(); //Desfaz a tramsação caso de algum erro
			 }
		}
	}
	
	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/cpqi?user=root&password=Rickhfs0809*";
		
		try (Connection con = DriverManager.getConnection(url)) {
			ContaCrud crud = new ContaCrud();
			
			Conta conta1 = new Conta(1, "Rick", 1000.10);
			conta1.saldo = 9000.99;
			crud.alterar(con, conta1);
			
			Conta conta3 = new Conta(3, "Biel", 3000.30);
			conta3.saldo = 0000.00;
			crud.alterar(con, conta3);
			crud.excluir(con, conta3);
			
			/*
			Conta conta2 = new Conta(2, "Cindy", 2000.20);
			
			crud.criar(con, conta1);
			crud.criar(con, conta2);
			crud.criar(con, conta3);
			*/
			
			List<Conta> contas = crud.ler(con);
			for (Conta conta : contas) {
				System.out.println(conta);
			}
			
			Conta origem = contas.get(0);
			Conta destino = contas.get(1);
			crud.transferir(con, origem, destino, 900);
		
			contas = crud.ler(con);
			for (Conta conta : contas) {
				System.out.println(conta);
			}
		
		}
	}
}
