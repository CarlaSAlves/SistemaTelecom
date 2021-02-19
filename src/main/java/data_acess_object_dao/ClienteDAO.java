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
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "cliente"
 */
public class ClienteDAO {

	private Connection myConn;

	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public ClienteDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	/*
	 * Método que devolve uma lista com todos os clientes existentes na tabela "clientes". Caso não existam clientes, é devolvida uma lista vazia.
	 */
	public List<Cliente> getAllClientes() throws Exception {
		List<Cliente> listaClientes = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from cliente");

			//faz parse ao resultado enviado pela base de dados e converte cada entrada num objeto cliente
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
	 * Método que devolve uma lista de todos os clientes que respeitem os seguintes critérios:
	 * - possuam o id passado como parametro e/ou
	 * - possuam um nif que contenha os carateres no nif enviado como parametro e/ou
	 * - possuam um nome que contenha o substring nome enviado no parametro da funçao e/ou
	 * - possuam uma morada que contenha o substring morada enviado no parametro da funçao e/ou
	 * - possuam o campo ativo igual ao parametro ativo enviado para a função.
	 * Caso não existam clientes que satisfaçam os critérios inseridos nos parâmetros da função, devolve uma lista vazia.
	 */
	public List<Cliente> pesquisaCliente(int id, String nif, String nome, String morada, int ativo) throws Exception {
		List<Cliente> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM CLIENTE WHERE ";

		// analisar os parametros enviados para a funçao e construir uma query com base na sua existencia (ou nao)
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

			//converter o resultado devolvido pela base de dados em objetos java
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
	 * Pesquisa e devolve o primeiro cliente encontrado que possua um nif que contenha o substring nif enviado como parametro da funçao.
	 * Devolve um cliente nulo se nenhum cliente for encontrado.
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

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Pesquisa e devolve o cliente com o id enviado como parametro.
	 * Devolve cliente nulo se nenhum for encontrado.
	 */
	public Cliente pesquisaClienteAuxiliarID(int id) throws Exception {
		Cliente cliente = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from cliente where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
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
	 * Pesquisa e devolve o cliente com o login e a password enviados como parametros. A função trata de encriptar a password antes de consultar a base de dados
	 * Devolve um cliente nulo se não existir um cliente com essas credenciais na base de dados.
	 */
	public Cliente pesquisaClienteLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Cliente cliente = null;

		try {
			//construçao da query
			myStmt = myConn.prepareStatement("SELECT * FROM cliente WHERE login=? AND password=?;");
			myStmt.setString(1, login);

			//vamos encriptar a palavra pass antes de a colocar na query
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));

			myRs = myStmt.executeQuery();
			
			//se myRs.next() = true, então for detetado um cliente. Vamos produzir um objeto em java que o represente
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
	 * Método temporário que pesquisa e devolve o Cliente com o login inserido.
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
	 * Cria um novo cliente na tabela "cliente" com base no objeto cliente enviado como parâmetro. O id dessa nova entidade é automaticamente gerado pela base de dados
	 * e é de seguida devolvido pelo driver JDBC para poder ser colocado no mesmo objeto cliente passado como parâmetro. De seguida, faz log à operação efetuada usando os dados
	 * do funcionário enviado como parâmetro na função.
	 * Devolve um cliente com o id gerado automaticamente pela base de dados. Caso a criação falhe irá ser propagada uma exceção pelo JDBC.
	 */
	@SuppressWarnings("resource")
	public Cliente criarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		try {
			//Statement.RETURN_GENERATED_KEYS permite ao driver jdbc devolver o id da entidade criada, caso a criaçao seja bem sucedida
			myStmt = myConn.prepareStatement("INSERT INTO cliente(nome, nif, morada, login, password, ativo, id_pacote_cliente) "
					+ "VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			//encriptar palavra pass do cliente antes da cria��o
			cliente.setPassword(PasswordEncryption.get_SHA_512_SecurePassword(cliente.getPassword()));

			//preencher a query com os atributos do objeto cliente passado como parametro da funçao
			myStmt.setString(1, cliente.getNome());
			myStmt.setLong(2, cliente.getNif());
			myStmt.setString(3, cliente.getMorada());
			myStmt.setString(4, cliente.getLogin());
			myStmt.setString(5, cliente.getPassword());
			myStmt.setBoolean(6, cliente.isAtivo());
			myStmt.setNull(7, java.sql.Types.INTEGER);
			myStmt.executeUpdate();

			// se criação foi bem sucedida, vamos fazer parse à resposta enviada pela base de dados para extratir o id da entidade criada
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

	/*
	 * Edita o cliente com id igual ao cliente passado como argumento à função. De seguida, regista a operação usando o funcionário passado como argumento.
	 * Caso seja passado um argumento password não-nulo, troca a password. Caso não seja passado, mantém a password anterior.
	 * Devolve o objeto cliente enviado como argumento (incluindo nova password, caso esta tenha sido modificada).
	 */
	@SuppressWarnings("resource")
	public Cliente editarCliente(Cliente cliente, Funcionario funcionario, String password) throws Exception {
		PreparedStatement myStmt = null;
		String novaPassEncriptada = null;
		
		try {

			//se password é nula ou em branco, não troca a pass
			if(password == null || password.isBlank()) {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=?, "
						+ "`login`=? WHERE  `id`=?");

				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setString(4, cliente.getLogin());
				myStmt.setInt(5, cliente.getId());
				myStmt.executeUpdate();

			//se string não-nulo e não-vazio for passado como argumento, encripta essa string e usa-a como nova password para o cliente
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

			//efetua log
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
	public Cliente editarClienteDadosBasicos(Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
				myStmt = myConn.prepareStatement("UPDATE `cliente` SET `nome`=?, `nif`=?, `morada`=? WHERE  `id`=?");

				myStmt.setString(1, cliente.getNome());
				myStmt.setLong(2, cliente.getNif());
				myStmt.setString(3, cliente.getMorada());
				myStmt.setInt(4, cliente.getId());
				
				myStmt.executeUpdate();
				
//				myStmt = logUpdateCliente(cliente, "Editar Cliente Dados Basicos");	
//				myStmt.executeUpdate();
				
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return cliente;
	}
	
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
				
//				myStmt = logUpdateCliente(cliente, "Editar Cliente Login & Password");	
//				myStmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return cliente;
	}
	
	/*
	 * Desativa o cliente com o id enviado como argumento, e de seguida regista a operação usando o mesmo cliente e o
	 * funcionário passado como argumento à função.
	 */
	@SuppressWarnings("resource")
	public void desativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//obter o cliente com base no id passado à função
			Cliente cliente = pesquisaClienteAuxiliarID(id);

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 0 where id=?");
			myStmt.setLong(1, id);
			myStmt.executeUpdate();

			//regista a operação
			myStmt = logUpdate(funcionario, cliente, "Desativar Cliente");	
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	/*
	 * Ativa o cliente com o id enviado como argumento, e de seguida regista a operação usando o mesmo cliente e o
	 * funcionário passado como argumento à função.
	 */
	@SuppressWarnings("resource")
	public void ativarCliente(int id, Funcionario funcionario ) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			//obter o cliente com base no id passado à função
			Cliente cliente = pesquisaClienteAuxiliarID(id);

			myStmt = myConn.prepareStatement("update cliente SET `ativo`= 1 where id=?");
			myStmt.setLong(1, id);
			myStmt.executeUpdate();

			//regista a operação
			myStmt = logUpdate(funcionario, cliente, "Ativar Cliente");	
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	/*/
	 * Atribui o pacote cliente passado como argumento ao cliente passado como argumento, fazendo com que o id_pacote cliente deste último
	 * iguale o id do pacote enviado.
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
	 * Pesquisa a tabela funcionario_log_cliente e devolve todos os logs contendo o cliente com id_cliente.
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

			//fazer parse ao resultado para construir os objetos HistoricoCliente que contém informação de registo
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
	 * Método para converter cada entrada de um ResultSet num objeto java representando um cliente.
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
	 * Função para registar is detalhes de uma operação, incluindo o cliente e funcionario envolvidos.
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
