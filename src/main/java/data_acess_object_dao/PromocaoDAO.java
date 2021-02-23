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
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "promocao"
 */
public class PromocaoDAO {

	private Connection myConn;

	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public PromocaoDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Método que devolve uma lista com todos os pacotes existentes na tabela "promocao". 
	 * Caso não existam promoçoes, é devolvida uma lista vazia.
	 */
	public List<Promocao> getAllPromocoes() throws Exception {
		List<Promocao> listaPromocoes = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from promocao");
			
			//faz parse ao resultado enviado pela base de dados e converte cada entrada num objeto funcionario
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
	 * Método que devolve uma lista de todos as promoçoes que respeitem os seguintes critérios:
	 * - possuam o id passado como parametro e/ou
	 * - possuam um nome que contenha o substring nome enviado no parametro da funçao e/ou
	 * - possuam o campo ativo igual ao parametro ativo enviado para a função.
	 * Caso não existam promoçoes que satisfaçam os critérios inseridos nos parâmetros da função, devolve uma lista vazia.
	 */
	public List<Promocao> pesquisaPromocao(int id, String nome, int ativo) throws Exception {
		List<Promocao> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM PROMOCAO WHERE ";
		
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
	 * Pesquisa e devolve a promocao com o id enviado como parametro.
	 * Devolve um objeto nulo se nenhum for encontrado.
	 */
	public Promocao pesquisarPromocaoById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Promocao promocao = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from promocao where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Devolve uma lista com todas as promoçoes que contenham o string passado como atributo no seu nome.
	 * Devolve uma lista vazia caso nao existam promoçoes contendo esse substring.
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

			//converter os resultados devolvidos pela base de dados num objeto java
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
	 * Devolve uma lista contendo todas as promoçoes pertencentes ao objeto PacoteCliente enviado.
	 * Caso esse pacote nao tenha promoçoes, devolve uma lista vazia.
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
			
			//converter os resultados devolvidos pela base de dados num objeto java
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
	 * Metodo para persistir o objeto promoçao recebido na base de dados. Se a promocao a ser criada estiver ativa,
	 * coloca a data atual no campo data_inicio. De outro modo, deixa esse campo a nulo.
	 * Caso a criaçao de uma nova entidade falhe, ira propagar uma exceçao.
	 */
	public void criarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO promocao(nome, descricao, ativa, data_inicio) VALUES(?,?,?,?)");

			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setBoolean(3, promocao.isAtiva());
			
			//se ativo = true, mudar a data_inicio para agora. De outro modo, colocar nulo na data_inicio
			myStmt.setTimestamp(4, promocao.isAtiva() ? new Timestamp(System.currentTimeMillis()) : null);

			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	/*
	 * Metodo para alterar o nome e/ou a descriçao de uma promoçao ja existente. Recebe um objeto promoçao e de seguida
	 * atualiza o nome e/ou a descriçao dessa entidade na base de dados com esse id. 
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
	 * Devolve o histórico de operaçoes para as promocoes com o id passado como parametro.
	 * Caso não existam operaçoes, devolve uma lista vazia.
	 */
	public List<HistoricoPromocoes> getHistoricoPromocao(int id_promocao) throws Exception {
		List<HistoricoPromocoes> list = new ArrayList<HistoricoPromocoes>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoPromocao.id_funcionario, HistoricoPromocao.id_promocao, HistoricoPromocao.descricao, "
					+ "HistoricoPromocao.data_registo, admin.nome "
					+ "FROM funcionario_log_promocao HistoricoPromocao, funcionario admin WHERE HistoricoPromocao.id_funcionario=admin.id AND HistoricoPromocao.id_pacote_comercial=" + id_promocao;

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
	
	//primeiro ve se a promocao com o id inserido esta ativa, e s� depois desativa e insere a data atual
	//no campo data_fim
	@SuppressWarnings("resource")
	public void desativarPromocao (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			//verificar se a promoçao esta de facto ativada
			myState = myConn.prepareStatement("Select ativa From promocao Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtiva = true;;
			if(rs.next()) {
				estaAtiva = rs.getBoolean(1);
			}
			
			//se esta desativado, vamos desativar e colocar as datas corretas
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
	
	//primeiro ve se a promocao com o id inserido esta desativa, e s� depois ativa e insere a data atual
	//no campo data_inicio e coloca data_fim a nulo
	@SuppressWarnings("resource")
	public void ativarPromocao (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			//verificar se o pacote comercial esta de facto sativada
			myState = myConn.prepareStatement("Select ativa From promocao Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtiva = false;;
			if(rs.next()) {
				estaAtiva = rs.getBoolean(1);
			}
			
			//se esta desativado, vamos ativar e colocar as datas corretas
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
	 * Converte cada entrade de um ResultSet num objeto Promoçao
	 */
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {
		java.sql.Date data_fim;
		java.sql.Date data_inicio;
		
		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativa");
		
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
