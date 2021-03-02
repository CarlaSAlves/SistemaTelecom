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
import historicos.HistoricoPromocoes;
import standard_value_object.PacoteCliente;
import standard_value_object.Promocao;

/*
 * Class used to establish connection with the database and interact with the "promocao" table.
 */
public class PromocaoDAO {

	private Connection myConn;

	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public PromocaoDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Returns a list containing every promotion in the database.
	 * The returned list is empty if no promotions exist.
	 */
	public List<Promocao> getAllPromocoes() throws Exception {
		List<Promocao> listaPromocoes = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from promocao");
			
			//parse the result returned by the database and converts each entry into a "Promocao" object
			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				listaPromocoes.add(promocao);
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return listaPromocoes;
	}

	/*
	 * Returns a list of promotions which obey the following filtering criteria:
	 * - have the "id" passed as argument and/or;
	 * - have a name that contains the substring "nome" passed as an argument and/or;
	 * - have an active field matching the argument "ativo" passed as argument.
	 * If no packages match the criteria, returns an empty list.
	 */
	public List<Promocao> pesquisaPromocao(int id, String nome, int ativo) throws Exception {
		List<Promocao> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM PROMOCAO WHERE ";
		
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
				Promocao promocao = converteRowParaPromocoes(myRs);
				list.add(promocao);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	/*
	 * Returns the promotion with the id passed as argument.
	 * Returns a null object if no such package exists.
	 */
	public Promocao pesquisarPromocaoById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Promocao promocao = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from promocao where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			if (myRs.next()) {
				promocao = new Promocao();
				promocao.setId(myRs.getInt(1));
				promocao.setNome(myRs.getString(2));
				promocao.setDescricao(myRs.getString(3));
				promocao.setAtiva(myRs.getBoolean(4));
				
				if (myRs.getTimestamp(5) != null) {
					promocao.setData_inicio( new java.sql.Date(myRs.getTimestamp(5).getTime()));
				}
				
				if (myRs.getTimestamp(6) != null) {
					promocao.setData_fim( new java.sql.Date(myRs.getTimestamp(6).getTime()));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return promocao;
	}
	
	/*
	 * Returns a list containing every promotion with a name containing the substring "nome" passed as argument.
	 * If no promotion exists, returns an empty list.
	 */
	public List<Promocao> pesquisarPromocaoNome(String nome) throws Exception {
		List<Promocao> list = new ArrayList<>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nome += "%";
			myStmt = myConn.prepareStatement("select * from promocao where nome like ?");
			myStmt.setString(1, nome);
			myRs = myStmt.executeQuery();

			//convert the result returned by the database into java objects
			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				list.add(promocao);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	/*
	 * Returns a list containing every promotion assigned to the "PacoteCliente" object passed as argument.
	 * Returns an empty list if no promotions are found.
	 */
	public List<Promocao> pesquisarPromocoesPacoteCliente(PacoteCliente pacote) throws Exception {
		List<Promocao> list = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("Select p.id, p.nome, p.descricao, p.ativa, p.data_inicio, p.data_fim\r\n" + 
					"			From pacote_cliente_promoçao pcp JOIN promocao p ON pcp.id_promocao = p.id\r\n" + 
					"			Where pcp.id_pacote_cliente =" + pacote.getId() + ";");
			myRs = myStmt.executeQuery();
			
			list = new ArrayList<Promocao>();
			
			//convert the result returned by the database into java objects
			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				list.add(promocao);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	/*
	 * Creates a new promotion entity in the "promocao" table based on the "Promocao" passed as argument. If the creation is successful, the JDBC driver will return the id of the new entity,
	 * which will then be added to the "promoca" object passed as an argument to the method.
	 * If the creation of the "pacote_comercial" entity fails, an exception will be thrown by the JDBC driver.
	 */
	public void criarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;

		try {
			//Statement.RETURN_GENERATED_KEYS allows the jdbc driver to return the id of the newly created entity
			myStmt = myConn.prepareStatement("INSERT INTO promocao(nome, descricao, ativa, data_inicio) VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setBoolean(3, promocao.isAtiva());
			
			//if active = true, update the data_inicio field to now. Otherwise, set it to null.
			myStmt.setTimestamp(4, promocao.isAtiva() ? new Timestamp(System.currentTimeMillis()) : null);
			myStmt.executeUpdate();
			
			//if the entity was created, parse the response from the database to extract the id of the created entity
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//assign the id of the created entity to the already existing "PacoteComercial" object
					promocao.setId((int)generatedKeys.getLong(1));
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
	}

	/*
	 * Edits the promotion in the database with the id matching the id of the "promocao" passed as argument.
	 * The fields in the database entity will be updated with the values present in the Java object passed as argument.
	 */
	public void editarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE `promocao` SET `nome`=?, `descricao`=? WHERE `id`=?");
			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setInt(3, promocao.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	/*
	 * Returns a list containing the log history for the promotion with the id passed as argument.
	 * If no logs exist, returns an empty list.
	 */
	public List<HistoricoPromocoes> getHistoricoPromocao(int id_promocao) throws Exception {
		List<HistoricoPromocoes> list = new ArrayList<HistoricoPromocoes>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoPromocao.id_funcionario, HistoricoPromocao.id_promocao, HistoricoPromocao.descricao, "
					+ "HistoricoPromocao.data_registo, admin.nome "
					+ "FROM funcionario_log_promocao HistoricoPromocao, funcionario admin WHERE HistoricoPromocao.id_funcionario=admin.id AND HistoricoPromocao.id_promocao=" + id_promocao;

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoPromocao.id_funcionario");
				String descricao = myRs.getString("HistoricoPromocao.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoPromocao.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");


				HistoricoPromocoes historico = new HistoricoPromocoes(id_promocao, id_funcionario, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/*
	 * Disables the promotion with the id passed as argument.
	 */
	@SuppressWarnings("resource")
	public void desativarPromocao (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			//check if promotion is enabled
			myState = myConn.prepareStatement("Select ativa From promocao Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtiva = true;;
			if(rs.next()) {
				estaAtiva = rs.getBoolean(1);
			}
			
			//if it's disabled, disable it and update the date fields
			if(estaAtiva) {
				myState = myConn.prepareStatement("UPDATE promocao SET ativa = 0,"
						+ "data_fim = current_timestamp() WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	/*
	 * Enables the promotion with the id passed as argument.
	 */
	@SuppressWarnings("resource")
	public void ativarPromocao (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			//check if promotion is enabled
			myState = myConn.prepareStatement("Select ativa From promocao Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtiva = false;;
			if(rs.next()) {
				estaAtiva = rs.getBoolean(1);
			}
			
			//if it's disabled, enable it and update the date fields
			if(!estaAtiva) {
				myState = myConn.prepareStatement("UPDATE promocao SET ativa = 1,"
						+ "data_inicio = current_timestamp(), data_fim = NULL WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	/*
	 * Method that converts each entry of a ResultSet into a "Promocao" Java object
	 */
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {
		java.sql.Date data_fim;
		java.sql.Date data_inicio;
		
		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativa");
		
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
		
		Promocao promocao = new Promocao(id, nome, descricao, ativo, data_inicio, data_fim);

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
