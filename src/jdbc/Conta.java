package jdbc;

public class Conta {

	int numero;
	String cliente;
	double saldo;
	
	public Conta(int numer, String cliente, double saldo) {
		this.numero = numer;
		this.cliente = cliente;
		this.saldo = saldo;
	}
	
	@Override
	public String toString() {
		return numero + "," + cliente + "," + saldo;
	}
}
