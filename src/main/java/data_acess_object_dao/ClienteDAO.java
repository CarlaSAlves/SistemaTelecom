package data_acess_object_dao;

import java.io.FileInputStream;
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
import historico.cliente.HistoricoCliente;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

public class ClienteDAO {

	private Connection myConn;

	public ClienteDAO() throws Exception {

		int test2;
		int test3;
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
				Cliente cliente = converteRowParaCliente(myRs);
				list.add(cliente);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}

	private Cliente pesquisaClienteAuxiliar(String nif) throws Exception {
		Cliente cliente = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from cliente where nif like ?");

			myStmt.setString(1, nif);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				cliente = converteRowParaCliente(myRs);
			}
			return cliente;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	@SuppressWarnings("resource")
	public void criarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
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

			Cliente clientCriado = pesquisaClienteAuxiliar(""+cliente.getNif());
			myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");
			
			myStmt.setInt(1, funcionario.getId());
			myStmt.setInt(2, clientCriado.getId());
			myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			myStmt.setString(4, "Criou Cliente");	
			
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


	public List<HistoricoCliente> getHistoricoCliente(int id_cliente) throws Exception {
		List<HistoricoCliente> list = new ArrayList<HistoricoCliente>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoCliente.id_funcionario, HistoricoCliente.id_cliente, HistoricoCliente.descricao, "
					+ "HistoricoCliente.data_registo, admin.nome "
					+ "FROM funcionario_log_cliente HistoricoCliente, funcionario admin WHERE HistoricoCliente.id_funcionario=admin.id AND HistoricoCliente.id_cliente=2" + id_cliente;

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoCliente.id_funcionario");
				String descricao = myRs.getString("HistoricoCliente.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoCliente.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");


				HistoricoCliente historico = new HistoricoCliente(id_funcionario, id_cliente, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
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
