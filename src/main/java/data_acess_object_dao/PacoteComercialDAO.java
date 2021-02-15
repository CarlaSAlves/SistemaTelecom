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
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "pacote_comercial"
 */
public class PacoteComercialDAO {

	private Connection myConn;

	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public PacoteComercialDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	/*
	 * Método que devolve uma lista com todos os pacotes existentes na tabela "pacote_comercial". 
	 * Caso não existam pacotes, é devolvida uma lista vazia.
	 */
	public List<PacoteComercial> getAllPacotesComerciais() throws Exception {
		List<PacoteComercial> listaPacotes = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_comercial");

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
	 * Método que devolve uma lista de todos os pacotes comercials que respeitem os seguintes critérios:
	 * - possuam o id passado como parametro e/ou
	 * - possuam um nome que contenha o substring nome enviado no parametro da funçao e/ou
	 * - possuam o campo ativo igual ao parametro ativo enviado para a função.
	 * Caso não existam operadores que satisfaçam os critérios inseridos nos parâmetros da função, devolve uma lista vazia.
	 */
	public List<PacoteComercial> pesquisaPacoteComercial(int id, String nome, int ativo) throws Exception {
		List<PacoteComercial> list = new ArrayList<>();

		PreparedStatement myStmt = null; 
		ResultSet myRs = null; 
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM PACOTE_COMERCIAL WHERE ";

		// analisar os parametros enviados para a funçao e construir uma query com base na sua existencia (ou nao)
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id!= 0){
				sj.add("ID=?");
				values.add(id);
			}
			if(nome != null) {
				nome += "%";
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

			//converter o resultado devolvido pela base de dados em objetos java
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
	 * Pesquisa e devolve o pacote comercial com o id enviado como parametro.
	 * Devolve um objeto nulo se nenhum for encontrado.
	 */
	public PacoteComercial pesquisaPacoteComercialById(int id) throws Exception {
		PacoteComercial pacoteComercial = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from pacote_comercial where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
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

	/*
	 * Cria um novo pacote comercial na tabela "pacote_comercial" com base no primeiro objeto PacoteComercial enviado como parâmetro. 
	 * O id dessa nova entidade é automaticamente gerado pela base de dados
	 * e é de seguida devolvido pelo driver JDBC para poder ser colocado no mesmo objeto PacoteComercial passado como parâmetro. De seguida, faz log à operação efetuada usando os dados
	 * do funcionário enviado como segundo parâmetro à função.
	 * Devolve um pacote comercial  com o id gerado automaticamente pela base de dados. Caso a criação falhe irá ser propagada uma exceção pelo JDBC.
	 */
	@SuppressWarnings("resource")
	public PacoteComercial criarPacoteComercial (PacoteComercial pacote , Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		//se se pode criar um pacoteComercial com ativo = false, entao nao faz sentido ter data_inicio definida de forma automatica
		//pelo mysql. � preciso que seja definida manualmente
		try {
			myStmt = myConn.prepareStatement("INSERT INTO pacote_comercial(nome, descricao, ativo, data_inicio, data_fim) VALUES(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			myStmt.setString(1, pacote.getNome());
			myStmt.setString(2, pacote.getDescricao());
			myStmt.setBoolean(3, pacote.isAtivo());
			//se ativo = true, mudar a data_inicio para agora. De outro modo, colocar nulo na data_inicio
			myStmt.setTimestamp(4, pacote.isAtivo() ? new Timestamp(System.currentTimeMillis()) : null);
			myStmt.setTimestamp(5, pacote.isAtivo() ? null : new Timestamp(System.currentTimeMillis()));
			myStmt.executeUpdate();

			// se criação foi bem sucedida, vamos fazer parse à resposta enviada pela base de dados para extratir o id da entidade criada
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//recuperamos o id do pacote comercial  recém-criado e vamos atribui-lo ao objeto pacote comercial enviado como parametro nesta funçao
					pacote.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criacao do Pacote Comercial falhou, nenhum ID foi devolvido.");
				}
			}
			
			//registo da operação
			myStmt = logUpdate(funcionario, pacote, "Criar Pacote Comercial");	
			myStmt.executeUpdate();	

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return pacote;
	}

	// Este metodo serve apenas para editar nome e descri�ao. Para Ativar/Desativar, usar os metodos correspondentes
	@SuppressWarnings("resource")
	public PacoteComercial editarPacoteComercial(PacoteComercial pacote, Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("UPDATE pacote_comercial SET nome=?, descricao=? WHERE id=?");

			myState.setString(1, pacote.getNome());
			myState.setString(2, pacote.getDescricao());
			myState.setInt(3, pacote.getId());

			myState.executeUpdate();

			//o nosso objeto cliente já contém o id, por isso podemos usa-lo diretamente na funçao seguinte
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
	 * Devolve o histórico de operaçoes para o pacote comercial com o id passado como parametro.
	 * Caso não existam operaçoes, devolve uma lista vazia.
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
	 * Regista a operação efetuada envolvendo o funcionário e o pacote enviados como parâmetros na tabela funcionario_log_pacote_comercial.
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
	 * Desativa o pacote comercial com o id enviado como parâmetro. Caso a operação seja bem sucedida, coloca a data atual no campo "data_fim"
	 * do pacote desativado na base de dados. De seguida, regista a operação usando os dados do funcionario passado como segundo parâmetro.
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

			//regista a operaçao
			myState = logUpdate(funcionario, pacote, "Desativar Pacote Comercial");	
			myState.executeUpdate();	

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}

	/*
	 * Ativa o pacote comercial com o id enviado como parâmetro. Caso a operação seja bem sucedida, coloca a data atual no campo "data_inicio" e null
	 * no campo "data_fim" do pacote desativado na base de dados. 
	 * De seguida, regista a operação usando os dados do funcionario passado como segundo parâmetro.
	 */
	@SuppressWarnings("resource")
	public void ativarPacoteComercial (int id, Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 
		PacoteComercial pacote = pesquisaPacoteComercialAuxiliarID(id);
		try {
			//verificar se o pacote comercial esta de facto desativado
			myState = myConn.prepareStatement("Select ativo From pacote_comercial Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();

			boolean estaAtivo = false;;
			if(rs.next()) {
				estaAtivo = rs.getBoolean(1);
			}

			//se esta desativado, vamos ativar e colocar as datas corretas
			if(!estaAtivo) {
				myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo = 1,"
						+ "data_inicio = current_timestamp(), data_fim = NULL WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
			}

			//regista a operaçao
			myState = logUpdate(funcionario, pacote, "Ativar Pacote Comercial");	

			myState.executeUpdate();	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}

	/*
	 * Obtem uma lista com todas as operaçoes envolvendo o pacote comercial com o id enviado como parametro.
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
	 * Pesquisa e devolve o PacoteComercial com o id enviado como parametro. Caso nao exista, devolve um objeto nulo.
	 */
	public PacoteComercial pesquisaPacoteComercialAuxiliarID(int id) throws Exception {
		PacoteComercial pacoteComercial = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from pacote_comercial where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

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
	 * Converte cada entrade de um ResultSet num objeto PacoteComercial
	 */
	public PacoteComercial converteRowParaPacoteComercial(ResultSet myRs) throws SQLException {
		java.sql.Date data_fim;
		java.sql.Date data_inicio;

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");

		Timestamp timestamp_inicio = myRs.getTimestamp("data_inicio");
		if(timestamp_inicio != null ) {
			data_inicio = new java.sql.Date(timestamp_inicio.getTime());
		}else {
			data_inicio = null;
		}
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
