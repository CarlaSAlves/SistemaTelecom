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
import java.util.StringJoiner;
import historicos.HistoricoCliente;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;


public class ClienteDAO {

	private Connection myConn;

	public ClienteDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

		return listaClientes;
	}

	public List<Cliente> pesquisaCliente(int id, String nif, String nome, String morada, int ativo) throws Exception {
		List<Cliente> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM CLIENTE WHERE ";

		//TODO: maneira mais simples de escrever o código abaixo
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id != 0){
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
			if(ativo != 0){
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

	//nao vai ser necess�rio visto eu ter alterado o metodo criarCliente para usar o id da entidade recem-criada
	@SuppressWarnings("unused")
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

		return cliente;
	}

	public Cliente pesquisaClienteAuxiliarID(int id) throws Exception {
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

		return cliente;
	}

	public Cliente pesquisaClienteLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Cliente cliente = null;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM cliente WHERE login=? AND password=?;");
			myStmt.setString(1, login);

			//vamos encriptar a palavra pass antes de a enviar
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));

			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				cliente = new Cliente();
				cliente.setId(myRs.getInt(1));
				cliente.setNome(myRs.getString(2));
				cliente.setNif(myRs.getInt(3));
				cliente.setMorada(myRs.getString(4));
				cliente.setLogin(myRs.getString(5));
				cliente.setPassword(myRs.getString(6));
				cliente.setAtivo(myRs.getInt(7) == 1 ? true : false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

		return cliente;
	}

	@SuppressWarnings("resource")
	public Cliente criarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		try {
			//Statement.RETURN_GENERATED_KEYS permite ao driver jdbc devolver o id da entidade criada, caso a criaçao seja bem sucedida
			myStmt = myConn.prepareStatement("INSERT INTO cliente(nome, nif, morada, login, password, ativo, id_pacote_cliente) "
					+ "VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			//encriptar palavra pass do cliente antes da cria��o
			cliente.setPassword(PasswordEncryption.get_SHA_512_SecurePassword(cliente.getPassword()));

			myStmt.setString(1, cliente.getNome());
			myStmt.setLong(2, cliente.getNif());
			myStmt.setString(3, cliente.getMorada());
			myStmt.setString(4, cliente.getLogin());
			myStmt.setString(5, cliente.getPassword());
			myStmt.setBoolean(6, cliente.isAtivo());
			myStmt.setNull(7, java.sql.Types.INTEGER);
			myStmt.executeUpdate();

			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//recuperamos o id do cliente recém-criado e vamos atribui-lo ao objeto cliente enviado como parametro nesta funçao, só para o reaproveitar
					cliente.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criação de cliente falhou, nenhum ID foi devolvido.");
				}
			}

			//o nosso objeto cliente já contém o id, por isso podemos usa-lo diretamente na funçao seguinte
			myStmt = logUpdate(funcionario, cliente, "Criar Cliente");	

			myStmt.executeUpdate();	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return cliente;
	}

	@SuppressWarnings("resource")
	public Cliente editarCliente(Cliente cliente, Funcionario funcionario, String password) throws Exception {
		PreparedStatement myStmt = null;

		try {

			if(password == null || password.isBlank()) {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=?, "
						+ "`login`=? WHERE  `id`=?");

				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setString(4, cliente.getLogin());
				myStmt.setInt(5, cliente.getId());

				myStmt.executeUpdate();

			}else if (password != null && !password.isBlank()) {

				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=?, "
						+ "`login`=?, `password`=? WHERE  `id`=?");

				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setString(4, cliente.getLogin());
				myStmt.setString(5, PasswordEncryption.get_SHA_512_SecurePassword(password));
				myStmt.setInt(6, cliente.getId());

				myStmt.executeUpdate();


			}

			myStmt = logUpdate(funcionario, cliente, "Editar Cliente");	
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return cliente;
	}

	@SuppressWarnings("resource")
	public void desativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			Cliente cliente = pesquisaClienteAuxiliarID(id);

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 0 where id=?");
			myStmt.setLong(1, id);

			myStmt.executeUpdate();


			myStmt = logUpdate(funcionario, cliente, "Desativar Cliente");	

			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	@SuppressWarnings("resource")
	public void ativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			Cliente cliente = pesquisaClienteAuxiliarID(id);

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 1 where id=?");
			myStmt.setLong(1, id);

			myStmt.executeUpdate();

			myStmt = logUpdate(funcionario, cliente, "Ativar Cliente");	

			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	public void atribuirPacoteCliente(PacoteCliente pacoteCliente, Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("UPDATE `cliente` SET `id_pacote_cliente`=? WHERE `id`=?");
			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, cliente.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
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

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		return list;
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

	private PreparedStatement logUpdate(Funcionario funcionario, Cliente cliente, String descricao) throws SQLException {
		PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");

		myStmt.setInt(1, funcionario.getId());
		myStmt.setInt(2, cliente.getId());
		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		myStmt.setString(4, descricao);
		return myStmt;
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
