package data_acess_object_dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import standard_value_object.PacoteCliente;

import standard_value_object.Promocao;

public class PacoteClienteDAO {

	private Connection myConn;

	public PacoteClienteDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	public List<PacoteCliente> pesquisarTodosPacotesCliente() throws Exception {
		List<PacoteCliente> listaClientes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_cliente");

			while (myRs.next()) {
				PacoteCliente dacoteCliente = convertRowToPacoteCliente(myRs);
				listaClientes.add(dacoteCliente);
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return listaClientes;	
	}
	
	public PacoteCliente pesquisarPacoteClienteId(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		PacoteCliente pacoteCliente = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from pacote_cliente where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				pacoteCliente = new PacoteCliente();
				pacoteCliente.setId(myRs.getInt(1));
				pacoteCliente.setId_pacote_comercial(myRs.getInt(2));
				
				java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp(3).getTime());
				pacoteCliente.setData_inicio(data_inicio);
				
				pacoteCliente.setId_criado_por(myRs.getInt(4));
			}
			return pacoteCliente;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return pacoteCliente;
	}
	
	
	public PacoteCliente criarPacoteCliente(PacoteCliente pacoteCliente) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("insert into pacote_cliente(id_pacote_comercial, data_inicio, id_criado_por) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			myStmt.setInt(1, pacoteCliente.getId_pacote_comercial());
			myStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			myStmt.setInt(3, pacoteCliente.getId_criado_por());
			myStmt.executeUpdate();
			
		
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                pacoteCliente.setId((int)generatedKeys.getLong(1));
	            }
	            else {
	                throw new SQLException("Criação de pacote falhou. Nenhum ID foi devolvido.");
	            }
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return pacoteCliente;
	}
	
	public int adicionarPromocao(PacoteCliente pacoteCliente, Promocao promocao) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO pacote_cliente_promoçao(id_pacote_cliente, id_promocao) VALUES(?, ?);");
			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, promocao.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return 1;
	}
	
	public void removerPromocao(PacoteCliente pacoteCliente, Promocao promocao) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("DELETE FROM `sistema_tele`.`pacote_cliente_promoçao` WHERE (`id_pacote_cliente` = ?) and (`id_promocao` = ?);");
			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, promocao.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	//por enquanto apenas muda o id_pacote_comercial do pacote_cliente. Ser� que deve poder mudar mais algo ?????
	public void editarPacoteCliente(PacoteCliente pacoteCliente) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE pacote_cliente "
					+ "SET `id_pacote_comercial`=? WHERE `id`=?");
			myStmt.setInt(1, pacoteCliente.getId_pacote_comercial());
			myStmt.setInt(2, pacoteCliente.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	//para apagar um pacote_cliente, vai ser necessario remover todas as promo�oes associadas a esse pacote.
	// Vai ser tambem necessario remover o pacote do cliente que o det�m. S� depois � possivel apagar o pacote cliente.
	@SuppressWarnings("resource")
	public void eliminarPacoteById(int id) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			String query1 = "Update `cliente` Set `id_pacote_cliente` = NULL Where (`id_pacote_cliente` =" + id + ");";
			String query2 = "DELETE from `pacote_cliente` where (`id`=" + id + ");";
					
			myStmt = myConn.prepareStatement(query1);
			myStmt.executeUpdate();
			
			myStmt = myConn.prepareStatement(query2);
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	private PacoteCliente convertRowToPacoteCliente(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		int id_pacote_comercial = myRs.getInt("id_pacote_comercial");
		java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		int id_criado_por = myRs.getInt("id_criado_por");

		PacoteCliente pacoteCliente = new PacoteCliente(id, id_pacote_comercial, data_inicio, id_criado_por);

		return pacoteCliente;
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
