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

import historicos.HistoricoPacoteComercial;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;

/*
 * Class used to establish connection with the database and interact with the "pacote_comercial" table.
 */
public class PacoteComercialDAO {

	private Connection myConn;

	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public PacoteComercialDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	/*
	 * Returns a list containing every commercial package in the database.
	 * Returns an empty list if no packages exist.
	 */
	public List<PacoteComercial> getAllPacotesComerciais() throws Exception {
		List<PacoteComercial> listaPacotes = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_comercial");

			//parse the result returned by the database and converts each entry into a "PacoteComercial" object
			while (myRs.next()) {
				PacoteComercial pacote = converteRowParaPacoteComercial(myRs);
				listaPacotes.add(pacote);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}
		return listaPacotes;
	}

	/*
	 * Returns a list of packages which obey the following filtering criteria:
	 * - have the "id" passed as argument and/or;
	 * - have a name that contains the substring "nome" passed as an argument and/or;
	 * - have an active field matching the argument "ativo" passed as argument.
	 * If no packages match the criteria, returns an empty list.
	 */
	public List<PacoteComercial> pesquisaPacoteComercial(int id, String nome, int ativo) throws Exception {
		List<PacoteComercial> list = new ArrayList<>();

		PreparedStatement myStmt = null; 
		ResultSet myRs = null; 
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM PACOTE_COMERCIAL WHERE ";

		//analyze the arguments passed to the function and build a query based on their value
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id!= 0){
				sj.add("ID=?");
				values.add(id);
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


			myStmt = myConn.prepareStatement(query);

			for (int index = 0; index < values.size(); index++){
				myStmt.setObject(index+1 , values.get(index));
			}

			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			while (myRs.next()) {
				PacoteComercial pacote = converteRowParaPacoteComercial(myRs);
				list.add(pacote);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close (myStmt, myRs);
		}
		return list;
	}

	/*
	 * Returns the package with the id passed as argument.
	 * Returns a null object if no such package exists.
	 */
	public PacoteComercial pesquisaPacoteComercialById(int id) throws Exception {
		PacoteComercial pacoteComercial = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from pacote_comercial where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			if (myRs.next()) {
				pacoteComercial = new PacoteComercial();
				pacoteComercial.setId(myRs.getInt(1));
				pacoteComercial.setNome(myRs.getString(2));
				pacoteComercial.setDescricao(myRs.getString(3));
				pacoteComercial.setAtivo(myRs.getBoolean(4));

				if (myRs.getTimestamp(5) != null) {
					pacoteComercial.setData_inicio( new java.sql.Date(myRs.getTimestamp(5).getTime()));
				}

				if (myRs.getTimestamp(6) != null) {
					pacoteComercial.setData_fim( new java.sql.Date(myRs.getTimestamp(6).getTime()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

		return pacoteComercial;
	}

//	/*
//	 * Cria um novo pacote comercial na tabela "pacote_comercial" com base no primeiro objeto PacoteComercial enviado como parâmetro. 
//	 * O id dessa nova entidade é automaticamente gerado pela base de dados
//	 * e é de seguida devolvido pelo driver JDBC para poder ser colocado no mesmo objeto PacoteComercial passado como parâmetro. De seguida, faz log à operação efetuada usando os dados
//	 * do funcionário enviado como segundo parâmetro à função.
//	 * Devolve um pacote comercial  com o id gerado automaticamente pela base de dados. Caso a criação falhe irá ser propagada uma exceção pelo JDBC.
//	 */
	
	/*
	 * Creates a new package entity in the "pacote_comercial" table based on the "pacote" passed as argument. If the creation is successful, the JDBC driver will return the id of the new entity,
	 * which will then be added to the "pacote" object passed as an argument to the method. The method returns that "pacote" object.
	 * Afterwards, the method will log the operation using the "funcionario" object passed as argument.
	 * If the creation of the "pacote_comercial" entity fails, an exception will be thrown by the JDBC driver.
	 */
	@SuppressWarnings("resource")
	public PacoteComercial criarPacoteComercial (PacoteComercial pacote , Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		//Statement.RETURN_GENERATED_KEYS allows the jdbc driver to return the id of the newly created entity
		try {
			myStmt = myConn.prepareStatement("INSERT INTO pacote_comercial(nome, descricao, ativo, data_inicio, data_fim) VALUES(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			myStmt.setString(1, pacote.getNome());
			myStmt.setString(2, pacote.getDescricao());
			myStmt.setBoolean(3, pacote.isAtivo());
			
			//if active = true, update the data_inicio field to now. Otherwise, set it to null.
			myStmt.setTimestamp(4, pacote.isAtivo() ? new Timestamp(System.currentTimeMillis()) : null);
			myStmt.setTimestamp(5, pacote.isAtivo() ? null : new Timestamp(System.currentTimeMillis()));
			myStmt.executeUpdate();

			//if the entity was created, parse the response from the database to extract the id of the created entity
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//assign the id of the created entity to the already existing "PacoteComercial" object
					pacote.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criacao do Pacote Comercial falhou, nenhum ID foi devolvido.");
				}
			}
			
			//log the operation
			myStmt = logUpdate(funcionario, pacote, "Criar Pacote Comercial");	
			myStmt.executeUpdate();	

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return pacote;
	}

	/*
	 * Edits the package in the database with the id matching the id of the "pacote" passed as argument.
	 * The fields in the database entity will be updated with the values present in the Java object passed as argument.
	 * 
	 * This method should only be used to update the name and description of a package.
	 * 
	 * Afterwards, proceeds to log the operation using the "funcionario" passed as argument.
	 * 
	 * Returns the "PacoteComercial" object passed as argument with updated fields.
	 */
	@SuppressWarnings("resource")
	public PacoteComercial editarPacoteComercial(PacoteComercial pacote, Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("UPDATE pacote_comercial SET nome=?, descricao=? WHERE id=?");

			myState.setString(1, pacote.getNome());
			myState.setString(2, pacote.getDescricao());
			myState.setInt(3, pacote.getId());

			myState.executeUpdate();

			//log the operation
			myState = logUpdate(funcionario, pacote, "Editar Pacote Comercial");	
			myState.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
		return pacote;
	}

	/*
	 * Returns a list containing the log history for the commercial package with the id passed as argument.
	 * If no logs exist, returns an empty list.
	 */
	public List<HistoricoPacoteComercial> getHistoricoPacoteComercial(int id_pacote) throws Exception {
		List<HistoricoPacoteComercial> list = new ArrayList<HistoricoPacoteComercial>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoPacote_comercial.id_funcionario, HistoricoPacote_comercial.id_pacote_comercial, HistoricoPacote_comercial.descricao, "
					+ "HistoricoPacote_comercial.data_registo, admin.nome "
					+ "FROM funcionario_log_pacote_comercial HistoricoPacote_comercial, funcionario admin WHERE HistoricoPacote_comercial.id_funcionario=admin.id AND HistoricoPacote_comercial.id_pacote_comercial=" + id_pacote;

			myRs = myStmt.executeQuery(sql);

			//convert the result returned by the database into java objects
			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoPacote_comercial.id_funcionario");
				String descricao = myRs.getString("HistoricoPacote_comercial.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoPacote_comercial.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");


				HistoricoPacoteComercial historico = new HistoricoPacoteComercial(id_pacote, id_funcionario, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}

	/*
	 * Method used to log the details of any given operation. Takes in the following arguments:
	 * @param funcionario the object "Funcionario" responsible for the operation
	 * @param pacote the object "PacoteComercial" involved in the operation
	 * @param descricao string describing the operation type
	 */
	private PreparedStatement logUpdate(Funcionario funcionario, PacoteComercial pacote, String descricao) throws SQLException {
		PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("insert into funcionario_log_pacote_comercial(id_funcionario, id_pacote_comercial, data_registo, descricao) VALUES (?, ?, ?, ?)");

		myStmt.setInt(1, funcionario.getId());
		myStmt.setInt(2, pacote.getId());
		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		myStmt.setString(4, descricao);
		return myStmt;
	}
	
	/*
	 * Disables the package with id matching the "id" argument passed.
	 * Afterwards, proceeds to log the operation using the "funcionario" object passed.
	 */
	@SuppressWarnings("resource")
	public void desativarPacoteComercial (int id, Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 
		PacoteComercial pacote = pesquisaPacoteComercialAuxiliarID(id);
		try {
			myState = myConn.prepareStatement("Select ativo From pacote_comercial Where id =" + id + ";");
			myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo = 0,"
					+ "data_fim = current_timestamp() WHERE id=?");

			myState.setInt(1, id);
			myState.executeUpdate();

			//log the operation
			myState = logUpdate(funcionario, pacote, "Desativar Pacote Comercial");	
			myState.executeUpdate();	

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}

//	/*
//	 * Ativa o pacote comercial com o id enviado como parâmetro. Caso a operação seja bem sucedida, coloca a data atual no campo "data_inicio" e null
//	 * no campo "data_fim" do pacote desativado na base de dados. 
//	 * De seguida, regista a operação usando os dados do funcionario passado como segundo parâmetro.
//	 */
	
	/*
	 * Enables the commercial package with id matching the "id" value passed as argument.
	 * Afterwards, proceeds to log the operation using the "funcionario" object passed.
	 */
	@SuppressWarnings("resource")
	public void ativarPacoteComercial (int id, Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 
		PacoteComercial pacote = pesquisaPacoteComercialAuxiliarID(id);
		try {
			//check if package is enabled
			myState = myConn.prepareStatement("Select ativo From pacote_comercial Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();

			boolean estaAtivo = false;;
			if(rs.next()) {
				estaAtivo = rs.getBoolean(1);
			}

			//if it's disabled, enable it and update the date fields
			if(!estaAtivo) {
				myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo = 1,"
						+ "data_inicio = current_timestamp(), data_fim = NULL WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
			}

			//log the operation
			myState = logUpdate(funcionario, pacote, "Ativar Pacote Comercial");	

			myState.executeUpdate();	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	/*
	 * Returns a list containing every operation involving the package with the id passed as argument.
	 * Returns an empty list if no logs exist.
	 */
	public List<HistoricoPacoteComercial> getHistoricoOperador(int id_pacoteComercial) throws Exception {
		List<HistoricoPacoteComercial> list = new ArrayList<HistoricoPacoteComercial>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoPacoteComercial.id_funcionario, HistoricoPacoteComercial.id_pacote_comercial, HistoricoPacoteComercial.descricao, "
					+ "HistoricoPacoteComercial.data_registo, admin.nome "
					+ "FROM funcionario_log_pacote_comercial HistoricoPacoteComercial, funcionario admin WHERE HistoricoPacoteComercial.id_funcionario = admin.id AND HistoricoPacoteComercial.id_pacote_comercial= " + id_pacoteComercial;

			myRs = myStmt.executeQuery(sql);

			//converte o resultado em objetos HistoricoPacoteComercial
			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoPacoteComercial.id_funcionario");
				String descricao = myRs.getString("HistoricoPacoteComercial.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoPacoteComercial.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");

				HistoricoPacoteComercial historico = new HistoricoPacoteComercial(id_pacoteComercial, id_funcionario, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/*
	 * Returns the commercial package with the id passed as argument.
	 * Returns a null object if no such package exists,
	 */
	public PacoteComercial pesquisaPacoteComercialAuxiliarID(int id) throws Exception {
		PacoteComercial pacoteComercial = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from pacote_comercial where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//convert the entries in the result set into java objects
			while (myRs.next()) {
				pacoteComercial = converteRowParaPacoteComercial(myRs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

		return pacoteComercial;
	}
	
	/*
	 * Method that converts each entry of a ResultSet into a "PacoteComercial" Java object
	 */
	public PacoteComercial converteRowParaPacoteComercial(ResultSet myRs) throws SQLException {
		java.sql.Date data_fim;
		java.sql.Date data_inicio;

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");

		//set the data_inicio field
		Timestamp timestamp_inicio = myRs.getTimestamp("data_inicio");
		if(timestamp_inicio != null ) {
			data_inicio = new java.sql.Date(timestamp_inicio.getTime());
		}else {
			data_inicio = null;
		}
		
		//set the data_fim field
		Timestamp timestamp_fim = myRs.getTimestamp("data_fim");
		if(timestamp_fim != null ) {
			data_fim = new java.sql.Date(timestamp_fim.getTime());
		}else {
			data_fim = null;
		}

		PacoteComercial pacoteComercial = new PacoteComercial(id, nome, descricao, ativo, data_inicio, data_fim);

		return pacoteComercial;
	}

	private void close(Statement myState, ResultSet myRes) throws SQLException {
		close(null, myState, myRes);

	}

	private void close(Connection myConn, Statement myState, ResultSet myRes) throws SQLException {

		if (myRes != null) {
			myRes.close();
		}

		if (myState != null) {
			myState.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}
}
