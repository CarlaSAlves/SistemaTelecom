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


/*
 * Class used to establish connection with the database and interact with the "cliente" table.
 */
public class ClienteDAO {

	private Connection myConn;
	
	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public ClienteDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Returns a list with every customer in the "clientes" table. If there are no customers, an empty list is returned.
	 */
	public List<Cliente> getAllClientes() throws Exception {
		List<Cliente> listaClientes = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from cliente");

			//parse the result returned by the database and converts each entry into a "cliente" object
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
	
	/*
	 * Returns a list of customers which obey the following filtering criteria:
	 * - have the "id" passed as argument and/or;
	 * - have an NIF that contains the characters in the "nif" passed as an argument and/or;
	 * - have a name that contains the substring "nome" passed as an argument and/or;
	 * - have an active field matching the argument "ativo" passed as argument.
	 * If no customers match the criteria, returns an empty list.
	 */
	public List<Cliente> pesquisaCliente(int id, String nif, String nome, String morada, int ativo) throws Exception {
		List<Cliente> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM CLIENTE WHERE ";

		//analyze the arguments passed to the function and build a query based on their value
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id != 0){
				sj.add("ID=?");
				values.add(id);
			}
			if(nif != null) {
				nif += "%";
				nif = "%" + nif;
				sj.add("NIF LIKE ?");
				values.add(nif);
			}
			if(nome != null) {
				nome += "%";
				nome = "%" + nome;
				sj.add("NOME LIKE ?");
				values.add(nome);
			}
			if(morada != null) {
				morada += "%";
				morada = "%" + morada;
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

			//convert the result returned by the database into java objects
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
	
	/*
	 * Returns the first customer found with an NIF containing the substring "nif" passed as an argument.
	 * Returns null if no customer is found.
	 */
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

			//convert the result sent by the database into a java object
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
	
	/*
	 * Returns the customer with the id passed as argument.
	 * Returns null if no such customer exists.
	 */
	public Cliente pesquisaClienteAuxiliarID(int id) throws Exception {
		Cliente cliente = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from cliente where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//convert the result sent by the database into a java object
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
	
	/*
	 * Returns the customer having the login and password passed as arguments. This method encrypts the passed "password" argument before checking the database.
	 * Returns null if no customer exists with the passed credentials.
	 */
	public Cliente pesquisaClienteLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Cliente cliente = null;

		try {
			//building the query
			myStmt = myConn.prepareStatement("SELECT * FROM cliente WHERE login=? AND password=?;");
			myStmt.setString(1, login);

			//encrypting the password before adding it to the query
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));

			myRs = myStmt.executeQuery();
			
			//if myRs.next() = true, there is a customer. Build the corresponding java object
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
	
	/*
	 * Returns the customer with the "login" passed as argument.
	 * Returns null if no customer is found.
	 */
	public Cliente pesquisaClienteLogin(String login) throws Exception {
		Cliente cliente = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from cliente where login=?");
			myStmt.setString(1, login);
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
	
	/*
	 * Creates a new customer entity in the "cliente" table based on the "cliente" passed as argument. If the creation is successful, the JDBC driver will return the id of the new entity,
	 * which will then be added to the "cliente" object passed as an argument to the method. The method returns that "cliente" object.
	 * Afterwards, the method will log the operation using the "funcionario" object passed as argument.
	 * If the creation of the "cliente" entity fails, an exception will be thrown by the JDBC driver.
	 */
	@SuppressWarnings("resource")
	public Cliente criarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		try {
			//Statement.RETURN_GENERATED_KEYS allows the jdbc driver to return the id of the newly created entity
			myStmt = myConn.prepareStatement("INSERT INTO cliente(nome, nif, morada, login, password, ativo, id_pacote_cliente) "
					+ "VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			//encrypt the password before adding it to the query
			cliente.setPassword(PasswordEncryption.get_SHA_512_SecurePassword(cliente.getPassword()));

			//fill in the query with the customer's attributes
			myStmt.setString(1, cliente.getNome());
			myStmt.setLong(2, cliente.getNif());
			myStmt.setString(3, cliente.getMorada());
			myStmt.setString(4, cliente.getLogin());
			myStmt.setString(5, cliente.getPassword());
			myStmt.setBoolean(6, cliente.isAtivo());
			myStmt.setNull(7, java.sql.Types.INTEGER);
			myStmt.executeUpdate();

			//if the entity was created, parse the response from the database to extract the id of the created entity
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//assign the id of the created entity to the already existing "cliente" object
					cliente.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criação de cliente falhou, nenhum ID foi devolvido.");
				}
			}

			//log the operation using the "cliente" and "funcionario" objects
			myStmt = logUpdate(funcionario, cliente, "Criar Cliente");	

			myStmt.executeUpdate();	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return cliente;
	}
	
	/*
	 * Edits the customer in the database with the id matching the id of the "cliente" passed as argument.
	 * If a non-empty "password" string argument is passed, it will also encrypt that string and set it to the edited customer's new password.
	 * Afterwards, proceeds to log the operation using the "funcionario" passed as argument.
	 * Returns the "cliente" object passed as argument.
	 */
	@SuppressWarnings("resource")
	public Cliente editarCliente(Cliente cliente, Funcionario funcionario, String password) throws Exception {
		PreparedStatement myStmt = null;
		String novaPassEncriptada = null;
		
		try {

			//if password is null or blank, don't change it
			if(password == null || password.isBlank()) {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=?, "
						+ "`login`=? WHERE  `id`=?");

				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setString(4, cliente.getLogin());
				myStmt.setInt(5, cliente.getId());
				myStmt.executeUpdate();

			//if password string is not-null and not-blank, encrypt it and set it as the new password
			} else if (password != null && !password.isBlank()) {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=?, "
						+ "`login`=?, `password`=? WHERE  `id`=?");

				novaPassEncriptada = PasswordEncryption.get_SHA_512_SecurePassword(password);
				
				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setString(4, cliente.getLogin());
				myStmt.setString(5, novaPassEncriptada);
				myStmt.setInt(6, cliente.getId());
				myStmt.executeUpdate();
				
				cliente.setPassword(novaPassEncriptada);
			}
			
			//log the operation
			myStmt = logUpdate(funcionario, cliente, "Editar Cliente");	
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return cliente;
	}

	/*
	 * Method that receives a "cliente" as attribute and edits that customer's name, nif and address.
	 * Returns the customer object with the new attributes.
	 */
	@SuppressWarnings("resource")
	public Cliente editarClienteDadosBasicos(Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=? WHERE  `id`=?");
				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setInt(4, cliente.getId());
				
				myStmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return cliente;
	}
	
	/*
	 * Method that receives a "cliente" as attribute and edits that customer's login and password.
	 * Returns the customer object with the new attributes.
	 */
	@SuppressWarnings("resource")
	public Cliente editarClienteDadosLoginEPassword(Cliente cliente, String password) throws Exception {
		PreparedStatement myStmt = null;
		String novaPassEncriptada = null;
		
		try {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `login`=?, `password`=? WHERE  `id`=?");
				novaPassEncriptada = PasswordEncryption.get_SHA_512_SecurePassword(password);
				
				myStmt.setString(1, cliente.getLogin());
				myStmt.setString(2, novaPassEncriptada);
				myStmt.setInt(3, cliente.getId());
				
				myStmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return cliente;
	}
	
	/*
	 * Method to disable the customer with the "id" passed as argument.
	 *  Afterwards, logs the operation using the "funcionario" object passed as argument.
	 */
	@SuppressWarnings("resource")
	public void desativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//find the customer with the given id
			Cliente cliente = pesquisaClienteAuxiliarID(id);

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 0 where id=?");
			myStmt.setLong(1, id);
			myStmt.executeUpdate();

			//log the operation
			myStmt = logUpdate(funcionario, cliente, "Desativar Cliente");	
			myStmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	/*
	 * Method to enable the customer with the "id" passed as argument. Afterwards, logs the operation using the "funcionario" object passed as argument.
	 */
	@SuppressWarnings("resource")
	public void ativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//find the customer with the given id
			Cliente cliente = pesquisaClienteAuxiliarID(id);

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 1 where id=?");
			myStmt.setLong(1, id);
			myStmt.executeUpdate();

			//log the operation
			myStmt = logUpdate(funcionario, cliente, "Ativar Cliente");	
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	/*
	 * Assigns the package "pacoteCliente" passed as first argument to the customer "cliente" passed as second argument.
	 */
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
	
	/*
	 * Retunrs a list containing every log having the "id_cliente" passed as argument currently existing in the "funcionario_log_cliente" table.
	 * If no logs exist, the returned list is empty.
	 */
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

			//parse the returned result to build the HistoricCliente java objects that contain all the information
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
	
	/*
	 * Method that converts each entry of a ResultSet into a "Cliente" Java object
	 */
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
	
	/*
	 * Method used to log the details of any given operation. Takes in the following arguments:
	 * @param funcionario the object "Funcionario" responsible for the operation
	 * @param cliente the object "Cliente" which was part of the operation
	 * @param descricao string describing the operation type
	 */
	private PreparedStatement logUpdate(Funcionario funcionario, Cliente cliente, String descricao) throws SQLException {
		PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");

		myStmt.setInt(1, funcionario.getId());
		myStmt.setInt(2, cliente.getId());
		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		myStmt.setString(4, descricao);
		return myStmt;
	}
	
//	private PreparedStatement logUpdateCliente( Cliente cliente, String descricao) throws SQLException {
//		PreparedStatement myStmt;
//		myStmt = myConn.prepareStatement("insert into funcionario_log_cliente(id_funcionario, id_cliente, data_registo, descricao) VALUES (?, ?, ?, ?)");
//
//		myStmt.setInt(1, cliente.getId());
//		myStmt.setInt(2, cliente.getId());
//		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
//		myStmt.setString(4, descricao);
//		return myStmt;
//	}


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
