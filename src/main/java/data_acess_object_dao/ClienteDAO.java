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
import java.util.StringJoiner;

import historico.cliente.HistoricoCliente;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

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

	public List<Cliente> pesquisaCliente(int id, String nif, String nome, String morada, int ativo) throws Exception {
		List<Cliente> list = new ArrayList<>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM CLIENTE WHERE ";

		try {
			List values = new ArrayList();

			if(id!= 0){
				sj.add("ID=?");
				values.add(id);
			}
			if(nif != null) {
				nif += "%";
				sj.add("NIF LIKE ?");
				values.add(nif);
			}
			if(nome != null) {
				nome += "%";
				sj.add("NOME LIKE ?");
				values.add(nome);
			}
			if(morada != null) {
				morada += "%";
				sj.add("morada LIKE ?");
				values.add(morada);
			}
			if(ativo!=0){
				sj.add("ativo=?");
				values.add(ativo);
			}

			query += sj.toString();

			myStmt = myConn.prepareStatement(query);

			for (int index = 0; index < values.size(); index++){
				myStmt.setObject(index+1 , values.get(index));
			}

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Cliente cliente = converteRowParaCliente(myRs);
				list.add(cliente);
			}

			return list;
		} catch(SQLException exp){
			exp.printStackTrace();

		}
		catch(Exception e){
			e.printStackTrace();

		}
		finally {
			close(myStmt, myRs);

		}
		return list;
	}


	private Cliente pesquisaClienteAuxiliarNIF(String nif) throws Exception {
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

	private Cliente pesquisaClienteAuxiliarID(int id) throws Exception {
		Cliente cliente = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			myStmt = myConn.prepareStatement("select * from cliente where id=?");

			myStmt.setInt(1, id);

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

			Cliente clientCriado = pesquisaClienteAuxiliarNIF(""+cliente.getNif());
			myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");

			myStmt.setInt(1, funcionario.getId());
			myStmt.setInt(2, clientCriado.getId());
			myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			myStmt.setString(4, "Criar Cliente");	

			myStmt.executeUpdate();	

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	@SuppressWarnings("resource")
	public void editarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
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

			myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");

			myStmt.setInt(1, funcionario.getId());
			myStmt.setInt(2, cliente.getId());
			myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			myStmt.setString(4, "Editar Cliente");	

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	@SuppressWarnings("resource")
	public void desativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 0 where id=?");

			myStmt.setLong(1, id);

			myStmt.executeUpdate();

			Cliente cliente = pesquisaClienteAuxiliarID(id);
			myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");

			myStmt.setInt(1, funcionario.getId());
			myStmt.setInt(2, cliente.getId());
			myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			myStmt.setString(4, "Desativar Cliente");	

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
					+ "FROM funcionario_log_cliente HistoricoCliente, funcionario admin WHERE HistoricoCliente.id_funcionario=admin.id AND HistoricoCliente.id_cliente=" + id_cliente;

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoCliente.id_funcionario");
				String descricao = myRs.getString("HistoricoCliente.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoCliente.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");


				HistoricoCliente historico = new HistoricoCliente(id_cliente, id_funcionario, descricao, data_registo, nome);

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
