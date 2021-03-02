package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import data_acess_object_dao.ClienteDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;

/*
 * Class containing tests for the methods in the ClienteDAO class.
 * It's advisable to run the database creating script before running any tests.
 */
public class TesteClienteDAO {
	
	private ClienteDAO clienteDAO;

	public TesteClienteDAO() throws FileNotFoundException, IOException, SQLException {
		clienteDAO = new ClienteDAO(startConnection());
	}
	
	@Test
	public void testGetAllClientes() throws Exception{
		assertEquals(6, clienteDAO.getAllClientes().size());
	}
	
	@Test
	public void testPesquisaCliente() throws Exception{
		List<Cliente> listClientes = clienteDAO.pesquisaCliente(0, "214", null, null, 0);
		assertEquals(2, listClientes.size());
	}
	
	@Test
	public void testPesquisaClienteAuxiliarID() throws Exception{
		Cliente cliente = clienteDAO.pesquisaClienteAuxiliarID(1);
		assertNotNull(cliente);
	}
	
	@Test
	public void testPesquisaClienteAuxiliarIDNulo() throws Exception{
		assertNull(clienteDAO.pesquisaClienteAuxiliarID(0));
	}
	
	@Test
	public void testPesquisaClienteLoginPass() throws Exception{
		Cliente cliente = clienteDAO.pesquisaClienteLoginPass("AnaSousa", "pass1");
		assertNotNull(cliente);
	}
	
	@Test
	public void testPesquisaClienteLoginPassNulo() throws Exception{
		assertNull(clienteDAO.pesquisaClienteLoginPass("1111", "1111"));
	}
	
	@Test
	public void testCriarCliente() throws Exception{
		Cliente cliente = new Cliente(110011, "JUnitTest", "JUnitMorada", "JUnitLogin", "pass1", true);
		Funcionario funcionario = new Funcionario(1, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		assertNotEquals(0, clienteDAO.criarCliente(cliente, funcionario).getId());
		
	}
	
	@Test
	public void testEditarCliente() throws Exception{
		String novoNome = "testEditarCliente";
		
		Funcionario funcionario = new Funcionario(1, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		Cliente cliente = clienteDAO.pesquisaClienteAuxiliarID(5);
//		System.out.println("Velha passe: " + cliente.getPassword());
		
		cliente.setNome(novoNome);
		
		cliente = clienteDAO.editarCliente(cliente, funcionario, "123");
//		System.out.println("Nova passe: " + cliente.getPassword());
		
		assertTrue(novoNome.equals(cliente.getNome()));
	}
	
	@Test
	public void testEditarClientePassNull() throws Exception{
		String novoNome = "testEditarCliente";
		
		Funcionario funcionario = new Funcionario(4, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		Cliente cliente = clienteDAO.pesquisaClienteAuxiliarID(3);
//		System.out.println("Velha passe: " + cliente.getPassword());
		
		cliente.setNome(novoNome);
		
		cliente = clienteDAO.editarCliente(cliente, funcionario, null);
//		System.out.println("Nova passe: " + cliente.getPassword());
		
		assertTrue(novoNome.equals(cliente.getNome()));
	}
	
	@Test
	public void testDesativarCliente() throws Exception{
		Funcionario funcionario = new Funcionario(1, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		clienteDAO.desativarCliente(2, funcionario);
		assertEquals(false, clienteDAO.pesquisaClienteAuxiliarID(2).isAtivo());
	}
	
	@Test
	public void testAtribuirPacoteCliente() throws Exception{
		Cliente cliente = clienteDAO.pesquisaClienteAuxiliarID(1);
		PacoteCliente pacoteCliente = new PacoteCliente();
		pacoteCliente.setId(3);
		clienteDAO.atribuirPacoteCliente(pacoteCliente, cliente);
		
		assertEquals(3, clienteDAO.pesquisaClienteAuxiliarID(1).getId_pacote_cliente());
	}
	
	@Test
	public void testGetHistoricoCliente() throws Exception{
		assertNotEquals(0, clienteDAO.getHistoricoCliente(1).size());
	}
	
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
