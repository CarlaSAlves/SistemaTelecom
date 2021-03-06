package testes;

import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.Test;

import data_acess_object_dao.PacoteClienteDAO;
import standard_value_object.PacoteCliente;
import standard_value_object.Promocao;

/*
 * Class containing tests for the methods in the PacoteClienteDAO class.
 * It's advisable to run the database creating script before running any tests.
 */
public class TestePacoteClienteDAO {

	private PacoteClienteDAO pacoteClienteDAO;
	
	public TestePacoteClienteDAO() throws FileNotFoundException, IOException, SQLException {
		pacoteClienteDAO = new PacoteClienteDAO(startConnection());
	}
	
	@Test
	public void testPesquisarTodosPacotesCliente() throws Exception {
		assertNotEquals(0, pacoteClienteDAO.pesquisarTodosPacotesCliente().size());
	}
	
	@Test
	public void testPesquisarPacoteClienteId() throws Exception {
		assertNotNull(pacoteClienteDAO.pesquisarPacoteClienteId(1));
	}
	
//	@Test
//	public void testCriarPacoteCliente() throws Exception {
//		PacoteCliente pacoteCliente = new PacoteCliente(1, 5);
//		pacoteCliente = pacoteClienteDAO.criarPacoteCliente(pacoteCliente);
//		assertNotEquals(0, pacoteCliente.getId());
//	}
	
	@Test
	public void testAdicionarPromocao() throws Exception {
		Promocao promocao = new Promocao(4, "", "", true, null, null);
		PacoteCliente pacoteCliente = pacoteClienteDAO.pesquisarPacoteClienteId(1);
		
		assertEquals(1, pacoteClienteDAO.adicionarPromocao(pacoteCliente, promocao));
	}
	
//	@Test
//	public void testRemoverPromocao() throws Exception {
//		Promocao promocao = new Promocao(1, "", "", true, null, null);
//		PacoteCliente pacoteCliente = pacoteClienteDAO.pesquisarPacoteClienteId(2);
//		
//		assertEquals(1, pacoteClienteDAO.removerPromocao(pacoteCliente, promocao));
//	}
	
	@Test
	public void testEditarPacoteCliente() throws Exception {
		PacoteCliente pacoteCliente = pacoteClienteDAO.pesquisarPacoteClienteId(3);
		pacoteCliente.setId_pacote_comercial(1);
		
		assertEquals(1, pacoteClienteDAO.editarPacoteCliente(pacoteCliente));
	}
	
//	@Test
//	public void testEliminarPacoteById() throws Exception {
//		assertEquals(1, pacoteClienteDAO.eliminarPacoteById(4));
//	}
//	
//	@Test
//	public void testEliminarPacoteByIdNull() throws Exception {
//		assertEquals(0, pacoteClienteDAO.eliminarPacoteById(0));
//	}
	
	/*
	 * Method which will establish a connection with the database by instancing a java.sql.Connection object and assigning it to the this.connection attribute.
	 * The database url and credentials are stored in the "sistema_tele.propeties" file in the root folder of the project.
	 */
	private Connection startConnection() throws FileNotFoundException, IOException, SQLException {
		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");


		Connection connection = DriverManager.getConnection(dburl, user, password);
		connection.setAutoCommit(false);
		return connection;
	}

}
