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
 * Class used to establish connection with the database and interact with the "pacote_cliente" table.
 */
public class PacoteClienteDAO {

	private Connection myConn;

	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public PacoteClienteDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	/*
	 * Returns a list containing every customer package existing in the table "pacote_cliente".
	 * Returns an empty list if no package exists.
	 */
	public List<PacoteCliente> pesquisarTodosPacotesCliente() throws Exception {
		List<PacoteCliente> listaClientes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_cliente");

			//parse the result returned by the database and converts each entry into a "pacote_cliente" object
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


	/*
	 * Returns the the information about the package with the id passed as an argument to this method.
	 * If no package exists, returns a null object.
	 */
	public PacoteComercial getPacoteClienteInfo(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		PacoteComercial pacoteComercial = null;

		try {
			myStmt = myConn.prepareStatement("SELECT pacote.nome, pacote.descricao, pacoteCliente.id, pacote.ativo  "
					+ "FROM pacote_comercial pacote INNER JOIN pacote_cliente pacoteCliente ON pacote.id = pacoteCliente.id_pacote_comercial WHERE pacoteCliente.id = ?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//parse the result returned by the database and converts each entry into a "pacote_cliente" object
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
	 * Returns the package with the id passed as argument.
	 * Returns null if no package is found.
	 * @param id id of the package to return
	 */
	public PacoteCliente pesquisarPacoteClienteId(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		PacoteCliente pacoteCliente = null;

		try {
			myStmt = myConn.prepareStatement("select * from pacote_cliente where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			//parse the result returned by the database and converts each entry into a "pacote_cliente" object
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
	
	/*
	 * Creates a new package entity based on the "pacoteCliente" argument passed.
	 * Afterwards, proceeds to log the operation.
	 * @param pacoteCliente is a Java object representing the package that will be persisted in the database
	 * @param cliente the customer who will receive the package
	 * @param funcionario the employee who performs the operation
	 */
	@SuppressWarnings("resource")
	public PacoteCliente criarPacoteCliente(PacoteCliente pacoteCliente, Cliente cliente, Funcionario funcionario) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			//Statement.RETURN_GENERATED_KEYS allows the jdbc driver to return the id of the newly created entity
			myStmt = myConn.prepareStatement("insert into pacote_cliente(id_pacote_comercial, data_inicio, id_criado_por) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			myStmt.setInt(1, pacoteCliente.getId_pacote_comercial());
			myStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			myStmt.setInt(3, pacoteCliente.getId_criado_por());
			myStmt.executeUpdate();

			//if the entity was created, parse the response from the database to extract the id of the created entity
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					//assign the id of the created entity to the already existing "pacote_cliente" object
					pacoteCliente.setId((int)generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Criação de pacote falhou. Nenhum ID foi devolvido.");
				}
			}

			//update the customer to set the id_pacote_cliente field to the id of the newly created package
			myStmt =  myConn.prepareStatement("UPDATE `cliente` SET `id_pacote_cliente`=? WHERE  `id`=?");

			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, cliente.getId());

			myStmt.executeUpdate();	

			//log the operation
			myStmt = logUpdate(funcionario, cliente, "Atribuir Pacote Comercial");	

			myStmt.executeUpdate();	

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return pacoteCliente;
	}

	/*
	 * Add a promotion passed as argument to the package passed as 1st argument.
	 * @param pacoteCliente package which will receive the sale/promotion
	 * @param promocao promotion to be assigned to the package
	 * Returns the value 1 if successful.
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
	 * Removes the promotion with id "id_promocao" from the customer package with id "id_pacote_cliente".
	 * Aftewards, logs the operation using the employee and customer passed as arguments.
	 * @param id_pacote_cliente id of the package we wish to remove the promotion from
	 * @param id_promocao id of the promotion we wish to remove
	 * @param funcionario employee who performs the operation
	 * @param cliente customer who owns the package being modified.
	 * Returns the value 1 if successful.
	 */
	@SuppressWarnings("resource")
	public int removerPromocao(int id_pacote_cliente, int id_promocao, Funcionario funcionario, Cliente cliente) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("DELETE FROM `sistema_tele`.`pacote_cliente_promocao` WHERE (`id_pacote_cliente` = ?) and (`id_promocao` = ?);");
			myStmt.setInt(1, id_pacote_cliente);
			myStmt.setInt(2, id_promocao);

			myStmt.executeUpdate();

			//log the operation
			myStmt = logUpdate(funcionario, cliente, "Remover Promoção");	

			myStmt.executeUpdate();	

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return 1;
	}

	//Recebe como parametro um PacoteCliente e altera o pacote comercial associado a esse pacote cliente.
	/*
	 * Method that receives a "PacoteCliente" object and updates the values of 
	 * the corresponding entity in the database.
	 * Returns the value 1 if successful.
	 */
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

//	/*
//	 * Elimina o pacote cliente com o id enviado como parametro da base de dados, bem como todas as promoçoes a ele associadas.
//	 * Coloca o campo id_pacote_cliente do cliente que possui esse pacote a nulo.
//	 * Caso a operaçao seja bem sucedida, devolve o valor 1. De outro modo, devolve 0.
//	 */
//	//para apagar um pacote_cliente, vai ser necessario remover todas as promo�oes associadas a esse pacote.
//	// Vai ser tambem necessario remover o pacote do cliente que o det�m. S� depois � possivel apagar o pacote cliente.
	
	/*
	 * Deletes the package with id passed as argument. Afterwards. logs the operation using the employee
	 * and customer passed as arguments.
	 * @param id id of the package to delete
	 * @param funcionario employee who performs the operation
	 * @param cliente customer who owns the package being deleted
	 * Returns the value 1 if successful.
	 */
	@SuppressWarnings("resource")
	public int eliminarPacoteById(int id, Funcionario funcionario, Cliente cliente) throws SQLException{
		PreparedStatement myStmt = null;

		if (id <= 0) {
			return 0;
		}

		try {
			//removes the package from the customer who owns it
			String query1 = "Update `cliente` Set `id_pacote_cliente` = NULL Where (`id_pacote_cliente` =" + id + ");";

			//deletes the package from the database
			String query2 = "DELETE from `pacote_cliente` where (`id`=" + id + ");";

			//delete any promotion assigned to the package in question
			String query3 = "DELETE from `pacote_cliente_promocao` where (`id_pacote_cliente` =" + id + ");";

			myStmt = myConn.prepareStatement(query1);
			myStmt.executeUpdate();

			myStmt = myConn.prepareStatement(query2);
			myStmt.executeUpdate();

			myStmt = myConn.prepareStatement(query3);
			myStmt.executeUpdate();

			//log the operation
			myStmt = logUpdate(funcionario, cliente, "Remover Pacote Comercial");
			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}

		return 1;
	}

	/*
	 * Method that converts each entry of a ResultSet into a "PacoteCliente" Java object
	 */
	private PacoteCliente convertRowToPacoteCliente(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		int id_pacote_comercial = myRs.getInt("id_pacote_comercial");
		java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		int id_criado_por = myRs.getInt("id_criado_por");

		PacoteCliente pacoteCliente = new PacoteCliente(id, id_pacote_comercial, data_inicio, id_criado_por);

		return pacoteCliente;
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
