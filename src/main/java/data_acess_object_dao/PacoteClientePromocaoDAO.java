package data_acess_object_dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import guiComponentes.operador_gerirClientes.Operador_atribuirDialog;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;
import standard_value_object.PacoteClientePromocao;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

/*
 * Class used to establish connection with the database and interact with the "pacote_cliente_promocao" table.
 */
public class PacoteClientePromocaoDAO {

	private Connection myConn;

	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public PacoteClientePromocaoDAO(Connection connection) throws FileNotFoundException, IOException, SQLException, SQLIntegrityConstraintViolationException {
		this.myConn = connection;
	}


	/*
	 * Returns a list containing a java object representing a pair package-promotion.
	 * If there are no entities, returns an empty list.
	 */
	public List<PacoteClientePromocao> pesquisarTodosPacotesClientePromocao() throws Exception {
		List<PacoteClientePromocao> listaClientesPromocao = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_cliente_promocao");

			//parse the result returned by the database and converts each entry into a "PacoteClientePromocao" object
			while (myRs.next()) {
				PacoteClientePromocao pacoteClientePromocao = converteRowParaPacoteClientePromocao(myRs);
				listaClientesPromocao.add(pacoteClientePromocao);
			}	

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}

		return listaClientesPromocao;	
	}

	/*
	 * Returns a list containing every promotion assigned to the customer package with the id passed as argument.
	 * Returns an empty list if no promotions exists.
	 */
	public List<Promocao> getPacoteClientePromocaoInfo(int id) throws Exception {
		List<Promocao> listaPromocao =new ArrayList<>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;


		try {
			myStmt = myConn.prepareStatement("SELECT promocaoCliente.nome, promocaoCliente.descricao, promocaoCliente.ativa, promocaoCliente.id "
					+ "FROM pacote_cliente pacoteCliente INNER JOIN pacote_cliente_promocao pacoteClientePromocao on pacoteCliente.id = pacoteClientePromocao.id_pacote_cliente "
					+ "INNER JOIN promocao promocaoCliente ON pacoteClientePromocao.id_promocao = promocaoCliente.id "
					+ "WHERE pacoteCliente.id = ?");

			myStmt.setInt(1, id);

			myRs = myStmt.executeQuery();

			//parse the result returned by the database and converts each entry into a "Promocao" object
			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				listaPromocao.add(promocao);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		finally {
			close(myStmt, myRs);
		}

		return listaPromocao;
	}

	/*
	 * Creates a new entry in the "pacote_cliente_promocao" table.
	 * Afterwards, logs the operation.
	 * @param pacoteClientePromocao new entity to create
	 * @param cliente the customer who will receive the package
	 * @param funcionario the employee who performs the operation.
	 * Returns a Java object representing the newly created "PacoteClientePromocao" object.
	 */
	@SuppressWarnings("resource")
	public PacoteClientePromocao criarPacoteClientePromocao(PacoteClientePromocao pacoteClientePromocao, Cliente cliente, Funcionario funcionario) throws SQLException{
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("insert into pacote_cliente_promocao(id_pacote_cliente, id_promocao) VALUES (?,?)");

			myStmt.setInt(1, pacoteClientePromocao.getId_pacote_cliente());
			myStmt.setInt(2, pacoteClientePromocao.getId_promocao());

			myStmt.executeUpdate();

			myStmt = logUpdate(funcionario, cliente, "Atribuir Promoção");	

			myStmt.executeUpdate();	

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return pacoteClientePromocao;
	}


	/*
	 * Method that converts each entry of a ResultSet into a "Promocao" Java object
	 */
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {

		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativa");
		int id = myRs.getInt("id");

		Promocao promocao = new Promocao(id ,nome, descricao, ativo);

		return promocao;
	}

	/*
	 * Method that converts each entry of a ResultSet into a "PacoteClientePromocao" Java object
	 */
	private PacoteClientePromocao converteRowParaPacoteClientePromocao(ResultSet myRs) throws SQLException {

		int id_pacote_cliente = myRs.getInt(1);
		int id_promocao = myRs.getInt(2);

		PacoteClientePromocao pacoteClientePromocao = new PacoteClientePromocao(id_pacote_cliente, id_promocao);

		return pacoteClientePromocao;
	}

	/*
	 * Method used to log the details of any given operation. Takes in the following arguments:
	 * @param funcionario the object "Funcionario" responsible for the operation
	 * @param cliente the object "Cliente" who owns the package
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
