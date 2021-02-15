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
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "funcionario"
 */
public class FuncionarioDAO {

	private Connection myConn;

	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public FuncionarioDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	/*
	 * Método que devolve uma lista com todos os admins existentes na tabela "funcionario". Caso não existam admins, é devolvida uma lista vazia.
	 */
	public List<Funcionario> getAllFuncionarioAdmin() throws Exception {
		List<Funcionario> listaFuncionarioAdmin = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario where id_role=1");

			//faz parse ao resultado enviado pela base de dados e converte cada entrada num objeto funcionario
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
	 * Pesquisa e devolve o funcionario com o id enviado como parametro.
	 * Devolve funcionario nulo se nenhum for encontrado.
	 */
	private Funcionario pesquisaOperadorAuxiliarID(int id) throws Exception {
		Funcionario funcionario = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			
			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Pesquisa e devolve o primeiro funcionario encontrado que possua um nif que contenha o substring nif enviado como parametro da funçao.
	 * Devolve um funcionario nulo se nenhum funcionario for encontrado.
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

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Método que devolve uma lista com todos os operadores existentes na tabela "funcionario". 
	 * Caso não existam operadores, é devolvida uma lista vazia.
	 */
	public List<Funcionario> getAllFuncionarioOperador() throws Exception {
		List<Funcionario> listaFuncionarioOperador = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario where id_role=2");

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Método temporário que pesquisa e devolve o funcionario com o login inserido.
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
	 * Método que devolve uma lista de todos os operadores que respeitem os seguintes critérios:
	 * - possuam o id passado como parametro e/ou
	 * - possuam um nif que contenha os carateres no nif enviado como parametro e/ou
	 * - possuam um nome que contenha o substring nome enviado no parametro da funçao e/ou
	 * - possuam o campo ativo igual ao parametro ativo enviado para a função.
	 * Caso não existam operadores que satisfaçam os critérios inseridos nos parâmetros da função, devolve uma lista vazia.
	 */
	public List<Funcionario> pesquisaFuncionarioOperador(int id ,String nif, String nome, int ativo) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM FUNCIONARIO WHERE ";

		// analisar os parametros enviados para a funçao e construir uma query com base na sua existencia (ou nao)
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

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

			//converter o resultado devolvido pela base de dados em objetos java
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
	 * Pesquisa e devolve todos os funcionários existentes, independentemente do seu id.
	 * Caso não existam funcionarios, devolve uma lista vazia.
	 */
	public List<Funcionario> pesquisaTodosFuncionarios() throws Exception {
		List<Funcionario> listaFuncionario = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario");

			//converter o resultado devolvido pela base de dados em objetos java
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
	 * Pesquisa e devolve todos os funcionarios encontrados que possuam um nif 
	 * que contenha o substring nif enviado como parametro da funçao.
	 * Devolve uma lista vazia se nenhum funcionario for encontrado.
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

			//converter o resultado devolvido pela base de dados em objetos java
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
	 * Pesquisa e devolve todos os funcionarios encontrados que possuam nome 
	 * que contenha o substring nif enviado como parametro da funçao.
	 * Devolve uma lista vazia se nenhum funcionario for encontrado.
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

			//converter o resultado devolvido pela base de dados em objetos java
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
	 * Pesquisa e devolve o funcionario com o id enviado como parametro.
	 * Devolve um funcionario nulo se nenhum for encontrado.
	 */
	public Funcionario pesquisaFuncionarioById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		Funcionario funcionario = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Pesquisa e devolve o funcionario com o login e a password enviados como parametros. A função trata de encriptar a password antes de consultar a base de dados
	 * Devolve um funcionario nulo se não existir um funcionario com essas credenciais na base de dados.
	 */
	public Funcionario pesquisarFuncionarioLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Funcionario funcionario = null;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM funcionario WHERE login=? AND password=?;");
			myStmt.setString(1, login);

			//vamos encriptar a palavra pass antes de a enviar
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));

			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Cria um novo funcionario na tabela "funcionario" com base no primeiro objeto funcionario (operador) enviado como parâmetro. 
	 * O id dessa nova entidade é automaticamente gerado pela base de dados
	 * e é de seguida devolvido pelo driver JDBC para poder ser colocado no mesmo objeto funcionario (operador) passado como parâmetro. De seguida, faz log à operação efetuada usando os dados
	 * do funcionário enviado como segundo parâmetro à função.
	 * Devolve um funcionario com o id gerado automaticamente pela base de dados. Caso a criação falhe irá ser propagada uma exceção pelo JDBC.
	 */
	@SuppressWarnings("resource")
	public Funcionario criarFuncionario(Funcionario operador, Funcionario admin) throws Exception {
		PreparedStatement myStmt = null;

		try {
			//Statement.RETURN_GENERATED_KEYS permite ao driver jdbc devolver o id da entidade criada, caso a criaçao seja bem sucedida
			myStmt = myConn.prepareStatement("INSERT INTO funcionario(nome, nif, login, password, ativo, id_role) "
					+ "VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			//encriptar palavra pass do funcionario antes da cria��o
			operador.setPassword(PasswordEncryption.get_SHA_512_SecurePassword(operador.getPassword()));

			//preencher a query com os atributos do objeto cliente passado como parametro da funçao
			myStmt.setString(1, operador.getNome());
			myStmt.setLong(2, operador.getNif());
			myStmt.setString(3, operador.getLogin());
			myStmt.setString(4,  operador.getPassword());
			myStmt.setBoolean(5, operador.isAtivo());
			myStmt.setInt(6, 2);

			myStmt.executeUpdate();

			// se criação foi bem sucedida, vamos fazer parse à resposta enviada pela base de dados para extratir o id da entidade criada
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//recuperamos o id do funcionario recém-criado e vamos atribui-lo ao objeto funcionario enviado como parametro nesta funçao, só para o reaproveitar
					operador.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criação de operador falhou, nenhum ID foi devolvido.");
				}
			}

			//registo da operação
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
	 * Edita o funcionario com id igual ao funcionario (operador) passado como argumento à função. De seguida, regista a operação usando o funcionário passado como segundo argumento.
	 * Caso seja passado um argumento password não-nulo, troca a password. Caso não seja passado, mantém a password anterior.
	 * Devolve o objeto funcionario enviado como argumento (incluindo nova password, caso esta tenha sido modificada).
	 */
	@SuppressWarnings("resource")
	public Funcionario editarFuncionario(Funcionario operador, Funcionario admin, String novaPass) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
			//se password é nula ou em branco, não troca a pass
			if(novaPass == null) {
				myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `nome`=?, `nif`=?, `login`=? WHERE  `id`=?");

				myStmt.setString(1, operador.getNome());
				myStmt.setLong(2, operador.getNif());
				myStmt.setString(3, operador.getLogin());
				myStmt.setInt(4, operador.getId());

				myStmt.executeUpdate();
			
			//se string não-nulo e não-vazio for passado como argumento, encripta essa string e usa-a como nova password para o cliente
			}else {

				myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `nome`=?, `nif`=?, `login`=?, `password`=? WHERE  `id`=?");

				myStmt.setString(1, operador.getNome());
				myStmt.setLong(2, operador.getNif());
				myStmt.setString(3, operador.getLogin());
				myStmt.setString(4, PasswordEncryption.get_SHA_512_SecurePassword(novaPass));
				myStmt.setInt(5, operador.getId());

				myStmt.executeUpdate();
			}
			
			//efetua log
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
	 * Atribui a funçao role passada como primeiro argumento ao funcionario passado como segundo argumento.
	 * Caso a role nao exista na base de dados, ira gerar uma exceçao.
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
	 * Desativa o funcionario com o id enviado como parametro e, caso a desativaçao seja bem sucedida, regista a operaçao
	 * usando o funcionario passado como parametro.
	 */
	@SuppressWarnings("resource")
	public void desativarFuncionario(int id, Funcionario admin) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//obtem o funcionario com o id enviado
			Funcionario operador = pesquisaOperadorAuxiliarID(id);

			myStmt = myConn.prepareStatement("update funcionario SET `ativo`= 0 where id=?");
			myStmt.setInt(1, id);

			myStmt.executeUpdate();

			//regista a operaçao
			myStmt = logUpdate(operador, admin, "Desativar Operador");	
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

	}

	/*
	 * Ativa o funcionario com o id enviado como parametro e, caso a ativaçao seja bem sucedida, regista a operaçao
	 * usando o funcionario passado como parametro.
	 */
	@SuppressWarnings("resource")
	public void ativarFuncionario(int id, Funcionario admin) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//obtem o funcionario com o id enviado
			Funcionario operador = pesquisaOperadorAuxiliarID(id);

			myStmt = myConn.prepareStatement("update funcionario SET `ativo`= 1 where id=?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();

			//regista a operaçao
			myStmt = logUpdate(operador, admin, "Ativar Operador");	
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

	}

	/*
	 * Pesquisa a tabela funcionario_log_operador e devolve todos os logs contendo o operador com id_operador.
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

			//fazer parse ao resultado para construir os objetos HistoricoOperador que contém informação de registo
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
	 * Método para converter cada entrada de um ResultSet num objeto java representando um funcionario.
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
	 * Função para registar is detalhes de uma operação, incluindo o funcionario operador e funcionario admin envolvidos.
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



