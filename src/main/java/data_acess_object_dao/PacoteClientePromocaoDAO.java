package data_acess_object_dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import standard_value_object.PacoteCliente;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

/*
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "pacote_cliente_promocao"
 */
public class PacoteClientePromocaoDAO {

	private Connection myConn;

	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public PacoteClientePromocaoDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	public List<Promocao> getPacoteClientePromocaoInfo(int id) throws Exception {
		List<Promocao> listaPromocao =new ArrayList<>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		

		try {
			myStmt = myConn.prepareStatement("SELECT promocaoCliente.nome, promocaoCliente.descricao, promocaoCliente.ativa "
					+ "FROM pacote_cliente pacoteCliente INNER JOIN pacote_cliente_promocao pacoteClientePromocao on pacoteCliente.id = pacoteClientePromocao.id_pacote_cliente "
					+ "INNER JOIN promocao promocaoCliente ON pacoteClientePromocao.id_promocao = promocaoCliente.id "
					+ "WHERE pacoteCliente.id = ?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				listaPromocao.add(promocao);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}

		return listaPromocao;
	}

	
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {
		
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativa");
		
		Promocao promocao = new Promocao(nome, descricao, ativo);

		return promocao;
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}

}
