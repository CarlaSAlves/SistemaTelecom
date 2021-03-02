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
import historicos.HistoricoOperador;
import standard_value_object.Funcionario;
import standard_value_object.Role;

/*
 * Class used to establish connection with the database and interact with the "funcionario" table.
 */
public class FuncionarioDAO {

	private Connection myConn;

	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public FuncionarioDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Returns a list containing every admin in the "funcionario" table.
	 * Returns an empty list if no admins exist.
	 */
	public List<Funcionario> getAllFuncionarioAdmin() throws Exception {
		List<Funcionario> listaFuncionarioAdmin = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario where id_role=1");

			//parse the result returned by the database and converts each entry into a "Funcionario" object
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionarioAdmin.add(funcionario);
			}

			return listaFuncionarioAdmin;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/*
	 * Returns the employee with the id passed as argument.
	 * Returns a null object if no employee exists.
	 */
	private Funcionario pesquisaOperadorAuxiliarID(int id) throws Exception {
		Funcionario funcionario = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			
			//parse the result returned by the database and converts each entry into a "Funcionario" object
			while (myRs.next()) {
				funcionario = convertRowParaFuncionario(myRs);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}

		return funcionario;
	}
	
	/*
	 * Returns the first employee found with an nif containing the "nif" substring passed as argument.
	 * Returns a null object if none is found.
	 */
	@SuppressWarnings("unused")
	private Funcionario pesquisaOperadorAuxiliarNIF(String nif) throws Exception {
		Funcionario funcionario = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";
			myStmt = myConn.prepareStatement("select * from funcionario where nif like ?");
			myStmt.setString(1, nif);
			myRs = myStmt.executeQuery();

			//parse the result returned by the database and converts each entry into a "Funcionario" object
			while (myRs.next()) {
				funcionario = convertRowParaFuncionario(myRs);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}

		return funcionario;
	}
	
	/*
	 * Returns a list containing every employee with the role "operador" (id_role = 2).
	 * If no employee containing this role exists, returns an empty list.
	 */
	public List<Funcionario> getAllFuncionarioOperador() throws Exception {
		List<Funcionario> listaFuncionarioOperador = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario where id_role=2");

			//parse the result returned by the database and converts each entry into a "Funcionario" object
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionarioOperador.add(funcionario);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}

		return listaFuncionarioOperador;
	}
	
	/*
	 * Returns the employee with the "login" string passed as argument.
	 * Returns null if no such employee exists.
	 */
	public Funcionario pesquisaFuncionarioLogin(String login) throws Exception {
		Funcionario funcionario = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where login=?");
			myStmt.setString(1, login);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				funcionario = convertRowParaFuncionario(myRs);
			}
			return funcionario;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/*
	 * Returns a list of employees which obey the following filtering criteria:
	 * - have the "id" passed as argument and/or;
	 * - have an NIF that contains the characters in the "nif" passed as an argument and/or;
	 * - have a name that contains the substring "nome" passed as an argument and/or;
	 * - have an active field matching the argument "ativo" passed as argument.
	 * If no employees match the criteria, returns an empty list.
	 */
	public List<Funcionario> pesquisaFuncionarioOperador(int id ,String nif, String nome, int ativo) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM FUNCIONARIO WHERE ";

		//analyze the arguments passed to the function and build a query based on their value
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id!= 0){
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
			if(ativo!=0){
				sj.add("ativo=?");
				values.add(ativo);
			}

			query += sj.toString();
			query+= " AND id_role=2";

			myStmt = myConn.prepareStatement(query);

			for (int index = 0; index < values.size(); index++){
				myStmt.setObject(index+1 , values.get(index));
			}

			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}

		return list;
	}
	
	/*
	 * Returns a list containing every employee.
	 * If no employee exists, returns an empty list.
	 */
	public List<Funcionario> pesquisaTodosFuncionarios() throws Exception {
		List<Funcionario> listaFuncionario = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario");

			//convert the result returned by the database into java objects
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionario.add(funcionario);
			}

		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}

		return listaFuncionario;
	}
	
	/*
	 * Returns a list containing every employee having an NIF containing the substring "nif" 
	 * passed as argument to the method.
	 * If no employee exists, returns an empty list.
	 */
	public List<Funcionario> pesquisaFuncionarioByNif(String nif) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from funcionario where nif like ?");
			myStmt.setString(1, nif);
			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}

		return list;
	}

	/*
	 * Returns a list containing every employee whose name contains the "nome" substring passed as argument to the method.
	 * If no such employee exists, returns an empty list.
	 */
	public List<Funcionario> pesquisaFuncionarioByNome(String nome) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nome += "%";
			myStmt = myConn.prepareStatement("select * from funcionario where nome like ?");
			myStmt.setString(1, nome);
			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}

		return list;
	}
	
	/*
	 * Returns the employee with the id matching the "id" passed as argument.
	 * Returns a null object if no such employee exists.
	 */
	public Funcionario pesquisaFuncionarioById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		Funcionario funcionario = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			if (myRs.next()) {
				funcionario = new Funcionario();
				funcionario.setId(myRs.getInt(1));
				funcionario.setNome(myRs.getString(2));
				funcionario.setNif(myRs.getInt(3));
				funcionario.setLogin(myRs.getString(4));
				funcionario.setPassword(myRs.getString(5));
				funcionario.setAtivo(myRs.getInt(6) == 1 ? true : false);
				funcionario.setId_role(myRs.getInt(7));	
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}

		return funcionario;
	}
	
	/*
	 * Returns the employee with the login and password passed as arguments.
	 * If no such employee is found, returns a null object.
	 */
	public Funcionario pesquisarFuncionarioLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Funcionario funcionario = null;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM funcionario WHERE login=? AND password=?;");
			myStmt.setString(1, login);

			//encrypt the password before adding it to the query
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));

			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			if (myRs.next()) {
				funcionario = new Funcionario();
				funcionario.setId(myRs.getInt(1));
				funcionario.setNome(myRs.getString(2));
				funcionario.setNif(myRs.getInt(3));
				funcionario.setLogin(myRs.getString(4));
				funcionario.setPassword(myRs.getString(5));
				funcionario.setAtivo(myRs.getInt(6) == 1 ? true : false);
				funcionario.setId_role(myRs.getInt(7));	
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}

		return funcionario;
	}
	
	/*
	 * Creates a new employee entity in the "funcionario" table based on the "funcionario" passed as argument. If the creation is successful, the JDBC driver will return the id of the new entity,
	 * which will then be added to the "funcionario" object passed as an argument to the method. The method returns that "funcionario" object.
	 * Afterwards, the method will log the operation using the "funcionario" object passed as argument.
	 * If the creation of the "funcionario" entity fails, an exception will be thrown by the JDBC driver.
	 */
	@SuppressWarnings("resource")
	public Funcionario criarFuncionario(Funcionario operador, Funcionario admin) throws Exception {
		PreparedStatement myStmt = null;

		try {
			//Statement.RETURN_GENERATED_KEYS allows the jdbc driver to return the id of the newly created entity
			myStmt = myConn.prepareStatement("INSERT INTO funcionario(nome, nif, login, password, ativo, id_role) "
					+ "VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			//encrypt the password before adding it to the query
			operador.setPassword(PasswordEncryption.get_SHA_512_SecurePassword(operador.getPassword()));

			//fill in the query with the customer's attributes
			myStmt.setString(1, operador.getNome());
			myStmt.setLong(2, operador.getNif());
			myStmt.setString(3, operador.getLogin());
			myStmt.setString(4,  operador.getPassword());
			myStmt.setBoolean(5, operador.isAtivo());
			myStmt.setInt(6, 2);

			myStmt.executeUpdate();

			//if the entity was created, parse the response from the database to extract the id of the created entity
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//assign the id of the created entity to the already existing "Funcionario" object
					operador.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criação de operador falhou, nenhum ID foi devolvido.");
				}
			}

			//log the operation using both "funcionario" objects
			myStmt = logUpdate(operador, admin, "Criar Operador");	
			myStmt.executeUpdate();	

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return operador;
	}

	/*
	 * Edits the employee in the database with the id matching the id of the "funcionario" passed as argument.
	 * If a non-empty "password" string argument is passed, it will also encrypt that string and set it to the edited employee's new password.
	 * Afterwards, proceeds to log the operation using the "funcionario" passed as argument.
	 * Returns the "funcionario" object passed as argument.
	 */
	@SuppressWarnings("resource")
	public Funcionario editarFuncionario(Funcionario operador, Funcionario admin, String novaPass) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
			//if password is null or blank, don't change it
			if(novaPass == null) {
				myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `nome`=?, `nif`=?, `login`=? WHERE  `id`=?");

				myStmt.setString(1, operador.getNome());
				myStmt.setLong(2, operador.getNif());
				myStmt.setString(3, operador.getLogin());
				myStmt.setInt(4, operador.getId());

				myStmt.executeUpdate();
			
			//if password string is not-null and not-blank, encrypt it and set it as the new password
			}else {

				myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `nome`=?, `nif`=?, `login`=?, `password`=? WHERE  `id`=?");

				myStmt.setString(1, operador.getNome());
				myStmt.setLong(2, operador.getNif());
				myStmt.setString(3, operador.getLogin());
				myStmt.setString(4, PasswordEncryption.get_SHA_512_SecurePassword(novaPass));
				myStmt.setInt(5, operador.getId());

				myStmt.executeUpdate();
			}
			
			//log the operation
			myStmt = logUpdate(operador, admin, "Editar Operador");	
			myStmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return operador;
	}

	/*
	 * Method used to assign a role passed as argument to the "funcionario" passed as second argument.
	 * @param role role to assign to the employee passed as second argument
	 * @param funcionario employee which will be assigned the role
	 */
	public void atribuirRoleAFuncionario(Role role, Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `id_role`=? WHERE `id`=?");
			myStmt.setInt(1, role.getId());
			myStmt.setInt(2, funcionario.getId());
			myStmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myStmt.close();
		}
	}
	
	/*
	 * Method to disable the funcionario with the "id" passed as argument.
	 * Afterwards, logs the operation using the second "funcionario" object passed as argument.
	 */
	@SuppressWarnings("resource")
	public void desativarFuncionario(int id, Funcionario admin) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//find the customer with the given id
			Funcionario operador = pesquisaOperadorAuxiliarID(id);

			myStmt = myConn.prepareStatement("update funcionario SET `ativo`= 0 where id=?");
			myStmt.setInt(1, id);

			myStmt.executeUpdate();

			//log the operation
			myStmt = logUpdate(operador, admin, "Desativar Operador");	
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	/*
	 * Method to enable the employee with the "id" passed as argument. 
	 * Afterwards, logs the operation using the "funcionario" object passed as second argument.
	 */
	@SuppressWarnings("resource")
	public void ativarFuncionario(int id, Funcionario admin) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//find the customer with the given id
			Funcionario operador = pesquisaOperadorAuxiliarID(id);

			myStmt = myConn.prepareStatement("update funcionario SET `ativo`= 1 where id=?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();

			//log the operation
			myStmt = logUpdate(operador, admin, "Ativar Operador");	
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

	}
	
	/*
	 * Retunrs a list containing every log having the "id_operador" passed as argument currently existing in the "funcionario_log_operador" table.
	 * If no logs exist, the returned list is empty.
	 */
	public List<HistoricoOperador> getHistoricoOperador(int id_operador) throws Exception {
		List<HistoricoOperador> list = new ArrayList<HistoricoOperador>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoOperador.id_funcionario, HistoricoOperador.id_operador, HistoricoOperador.descricao, "
					+ "HistoricoOperador.data_registo, admin.nome "
					+ "FROM funcionario_log_operador HistoricoOperador, funcionario admin WHERE HistoricoOperador.id_funcionario=admin.id AND HistoricoOperador.id_operador=" + id_operador;

			myRs = myStmt.executeQuery(sql);

			//parse the returned result to build the HistoricCliente java objects that contain all the information
			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoOperador.id_funcionario");
				String descricao = myRs.getString("HistoricoOperador.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoOperador.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");

				HistoricoOperador historico = new HistoricoOperador(id_operador, id_funcionario, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}

	/*
	 * Method that converts each entry of a ResultSet into a "Funcionario" Java object
	 */
	private Funcionario convertRowParaFuncionario(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		long nif = myRs.getInt("nif");
		String login = myRs.getString("login");
		String password = myRs.getString("password");
		boolean ativo = myRs.getBoolean("ativo");
		int id_role = myRs.getInt("id_role");

		Funcionario funcionario = new Funcionario(id, nome, nif, login, password, ativo, id_role);

		return funcionario;
	}

	/*
	 * Method used to log the details of any given operation. Takes in the following arguments:
	 * @param operador the object "Funcionario" responsible for the operation
	 * @param admin the object "Funcionario" responsible for logging the operation
	 * @param descricao string describing the operation type
	 */
	private PreparedStatement logUpdate(Funcionario operador, Funcionario admin, String descricao) throws SQLException {
		PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("insert into funcionario_log_operador(id_funcionario, id_operador, data_registo, descricao) VALUES (?, ?, ?, ?)");

		myStmt.setInt(1, admin.getId());
		myStmt.setInt(2, operador.getId());
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



