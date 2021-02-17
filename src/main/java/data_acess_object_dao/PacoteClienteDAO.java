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

import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

/*
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "pacote_cliente"
 */
public class PacoteClienteDAO {

	private Connection myConn;

	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public PacoteClienteDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Método que devolve uma lista com todos os pacotes existentes na tabela "pacote_cliente". 
	 * Caso não existam pacotes, é devolvida uma lista vazia.
	 */
	public List<PacoteCliente> pesquisarTodosPacotesCliente() throws Exception {
		List<PacoteCliente> listaClientes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_cliente");

			while (myRs.next()) {
				PacoteCliente dacoteCliente = convertRowToPacoteCliente(myRs);
				listaClientes.add(dacoteCliente);
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return listaClientes;	
	}
	
	
	public PacoteComercial getPacoteClienteInfo(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		PacoteComercial pacoteComercial = null;
		
		try {
			myStmt = myConn.prepareStatement("SELECT pacote.nome, pacote.descricao, pacoteCliente.id, pacote.ativo  "
					+ "FROM pacote_comercial pacote INNER JOIN pacote_cliente pacoteCliente ON pacote.id = pacoteCliente.id_pacote_comercial WHERE pacoteCliente.id = ?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
			if (myRs.next()) {
				pacoteComercial = new PacoteComercial(myRs.getString(1), myRs.getString(2), myRs.getBoolean(4));
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return pacoteComercial;
	}
	
	
	/**
	 * Pesquisa e devolve o pacote com o id enviado como parametro.
	 * Devolve pacote nulo se nenhum for encontrado.
	 * @param id do cliente
	 */
	public PacoteCliente pesquisarPacoteClienteId(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		PacoteCliente pacoteCliente = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from pacote_cliente where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//converter o resultado devolvido pela base de dados num objeto java
			if (myRs.next()) {
				pacoteCliente = new PacoteCliente();
				pacoteCliente.setId(myRs.getInt(1));
				pacoteCliente.setId_pacote_comercial(myRs.getInt(2));
				java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp(3).getTime());
				pacoteCliente.setData_inicio(data_inicio);
				
				pacoteCliente.setId_criado_por(myRs.getInt(4));
			}
			return pacoteCliente;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return pacoteCliente;
	}
	
	/*
	 * Cria um novo pacote_cliente na base de dados com base do PacoteCliente enviado como parametro.
	 * Caso a criaçao falhe, ira propagar uma exceçao.
	 */
	@SuppressWarnings("resource")
	public PacoteCliente criarPacoteCliente(PacoteCliente pacoteCliente, Cliente cliente, Funcionario funcionario) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			//Statement.RETURN_GENERATED_KEYS permite ao driver jdbc devolver o id da entidade criada, caso a criaçao seja bem sucedida
			myStmt = myConn.prepareStatement("insert into pacote_cliente(id_pacote_comercial, data_inicio, id_criado_por) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			myStmt.setInt(1, pacoteCliente.getId_pacote_comercial());
			myStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			myStmt.setInt(3, pacoteCliente.getId_criado_por());
			myStmt.executeUpdate();
			
			// se criação foi bem sucedida, vamos fazer parse à resposta enviada pela base de dados para extratir o id da entidade criada
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	//recuperamos o id do funcionario recém-criado e vamos atribui-lo ao objeto funcionario enviado como parametro nesta funçao, só para o reaproveitar
	                pacoteCliente.setId((int)generatedKeys.getLong(1));
	            }
	            else {
	                throw new SQLException("Criação de pacote falhou. Nenhum ID foi devolvido.");
	            }
	        }
			
			myStmt =  myConn.prepareStatement("UPDATE `cliente` SET `id_pacote_cliente`=? WHERE  `id`=?");
			
			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, cliente.getId());
					
			myStmt.executeUpdate();	
			
			myStmt = logUpdate(funcionario, cliente, "Pacote Comercial atribuido");	

			myStmt.executeUpdate();	
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return pacoteCliente;
	}
	
	/*
	 * Adiciona ao PacoteCliente enviado como parametro a promoçao enviada como segundo parametro.
	 */
	public int adicionarPromocao(PacoteCliente pacoteCliente, Promocao promocao) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO pacote_cliente_promoçao(id_pacote_cliente, id_promocao) VALUES(?, ?);");
			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, promocao.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return 1;
	}
	
	/*
	 * Remove a promoçao enviada como parametro do PacoteCliente enviado como primeiro parametro.
	 */
	@SuppressWarnings("resource")
	public int removerPromocao(int id_pacote_cliente, int id_promocao, Funcionario funcionario, Cliente cliente) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("DELETE FROM `sistema_tele`.`pacote_cliente_promocao` WHERE (`id_pacote_cliente` = ?) and (`id_promocao` = ?);");
		
			myStmt.setInt(1, id_pacote_cliente);
			myStmt.setInt(2, id_promocao);
			
			myStmt.executeUpdate();
			
			myStmt = logUpdate(funcionario, cliente, "Promoção Removida");	

			myStmt.executeUpdate();	
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return 1;
	}
	
	//Recebe como parametro um PacoteCliente e altera o pacote comercial associado a esse pacote cliente.
	public int editarPacoteCliente(PacoteCliente pacoteCliente) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE pacote_cliente "
					+ "SET `id_pacote_comercial`=? WHERE `id`=?");
			myStmt.setInt(1, pacoteCliente.getId_pacote_comercial());
			myStmt.setInt(2, pacoteCliente.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		return 1;
	}
	
	/*
	 * Elimina o pacote cliente com o id enviado como parametro da base de dados, bem como todas as promoçoes a ele associadas.
	 * Coloca o campo id_pacote_cliente do cliente que possui esse pacote a nulo.
	 * Caso a operaçao seja bem sucedida, devolve o valor 1. De outro modo, devolve 0.
	 */
	//para apagar um pacote_cliente, vai ser necessario remover todas as promo�oes associadas a esse pacote.
	// Vai ser tambem necessario remover o pacote do cliente que o det�m. S� depois � possivel apagar o pacote cliente.
	@SuppressWarnings("resource")
	public int eliminarPacoteById(int id, Funcionario funcionario, Cliente cliente) throws SQLException{
		PreparedStatement myStmt = null;
		
		if (id <= 0) {
			return 0;
		}
		
		try {
			//remove o pacote cliente do cliente que o possui
			String query1 = "Update `cliente` Set `id_pacote_cliente` = NULL Where (`id_pacote_cliente` =" + id + ");";
			
			//elimina o pacote cliente da base de dados
			String query2 = "DELETE from `pacote_cliente` where (`id`=" + id + ");";
			
			//elimina promocoes associadas da base de dados
			String query3 = "DELETE from `pacote_cliente_promocao` where (`id_pacote_cliente` =" + id + ");";
			
			myStmt = myConn.prepareStatement(query1);
			myStmt.executeUpdate();
			
			myStmt = myConn.prepareStatement(query2);
			myStmt.executeUpdate();
			
			myStmt = myConn.prepareStatement(query3);
			myStmt.executeUpdate();
			
			myStmt = logUpdate(funcionario, cliente, "Pacote Comercial Removido");
			myStmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
		
		return 1;
	}
	
	/*
	 * Convert cada entrada de um ResultSet num objeto do tipo PacoteCliente.
	 */
	private PacoteCliente convertRowToPacoteCliente(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		int id_pacote_comercial = myRs.getInt("id_pacote_comercial");
		java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		int id_criado_por = myRs.getInt("id_criado_por");

		PacoteCliente pacoteCliente = new PacoteCliente(id, id_pacote_comercial, data_inicio, id_criado_por);

		return pacoteCliente;
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
