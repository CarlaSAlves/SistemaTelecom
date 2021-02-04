package data_acess_object_dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import standard_value_object.Cliente;

public class ClienteDAO {

	private Connection myConn;

	public ClienteDAO() throws Exception {

		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");


		myConn = DriverManager.getConnection(dburl, user, password);

	}

	public List<Cliente> getAllClientes() throws Exception {
		List<Cliente> listaClientes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from cliente");
			

			while (myRs.next()) {
				Cliente cliente = converteRowParaCliente(myRs);
				listaClientes.add(cliente);
			}

			return listaClientes;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Cliente> pesquisaCliente(String nif) throws Exception {
		List<Cliente> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from cliente where nif like ?");

			myStmt.setString(1, nif);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Cliente Cliente = converteRowParaCliente(myRs);
				list.add(Cliente);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void criarCliente(Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO cliente(nome, nif, morada, login, password, ativo, id_pacote_cliente) "
					+ "VALUES(?,?,?,?,?,?,?)");
			
			myStmt.setString(1, cliente.getNome());
			myStmt.setLong(2, cliente.getNif());
			myStmt.setString(3, cliente.getMorada());
			myStmt.setString(4, cliente.getLogin());
			myStmt.setString(5, cliente.getPassword());
			myStmt.setBoolean(6, cliente.isAtivo());
			myStmt.setInt(7, cliente.getId_pacote_cliente());

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}
	
	public void editarCliente(Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=?, "
					+ "`login`=?, `password`=?, `ativo`=?, `id_pacote_cliente`=? WHERE  `id`=?");
			
			myStmt.setString(1, cliente.getNome());
			myStmt.setLong(2, cliente.getNif());
			myStmt.setString(3, cliente.getMorada());
			myStmt.setString(4, cliente.getLogin());
			myStmt.setString(5, cliente.getPassword());
			myStmt.setBoolean(6, cliente.isAtivo());
			myStmt.setInt(7, cliente.getId_pacote_cliente());
			myStmt.setInt(8, cliente.getId());
			
			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void desativarCliente(int id ) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 0 where id=?");

			myStmt.setLong(1, id);

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}

	}
	private Cliente converteRowParaCliente(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		long nif = myRs.getInt("nif");
		String morada = myRs.getString("morada");
		String login = myRs.getString("login");
		String password = myRs.getString("password");
		boolean ativo = myRs.getBoolean("ativo");
		int id_pacote_cliente = myRs.getInt("id_pacote_cliente");
		
		Cliente cliente = new Cliente(id, nome, nif, morada, login, password, ativo, id_pacote_cliente);

		return cliente;
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
