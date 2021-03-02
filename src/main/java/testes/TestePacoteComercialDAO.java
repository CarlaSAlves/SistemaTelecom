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

import data_acess_object_dao.PacoteComercialDAO;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;

/*
 * Class containing tests for the methods in the PacoteComercialDAO class.
 * It's advisable to run the database creating script before running any tests.
 */
public class TestePacoteComercialDAO {

	private PacoteComercialDAO pacoteComercialDAO;
	
	public TestePacoteComercialDAO() throws FileNotFoundException, IOException, SQLException {
		pacoteComercialDAO = new PacoteComercialDAO(startConnection());
	}
	
	@Test
	public void testGetAllPacotesComerciais() throws Exception {
		assertNotEquals(0, pacoteComercialDAO.getAllPacotesComerciais().size());
	}
	
	@Test
	public void testPesquisaPacoteComercial() throws Exception {
		assertEquals(2, pacoteComercialDAO.pesquisaPacoteComercial(0, "Telemovel", 1).size());
	}
	
	@Test
	public void testPesquisaPacoteComercialById() throws Exception {
		assertEquals(1, pacoteComercialDAO.pesquisaPacoteComercialById(1).getId());
	}
	
	@Test
	public void testPesquisaPacoteComercialByIdNull() throws Exception {
		assertNull( pacoteComercialDAO.pesquisaPacoteComercialById(0));
	}
	
	@Test
	public void testCriarPacoteComercial() throws Exception {
		PacoteComercial pacoteComercial = new PacoteComercial("pacoteTeste", "descricaoTeste", true);
		Funcionario funcionario = new Funcionario(1, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		
		pacoteComercial = pacoteComercialDAO.criarPacoteComercial(pacoteComercial, funcionario);
		System.out.println(pacoteComercial);
		assertNotEquals(0, pacoteComercial.getId());
	}
	
	@Test
	public void testeEditarPacoteComercial() throws Exception {
		String novaDescricao = "testeEditarDescricao";
		
		PacoteComercial pacoteComercial = pacoteComercialDAO.pesquisaPacoteComercialAuxiliarID(3);
		pacoteComercial.setDescricao("testeEditarDescricao");
		
		Funcionario funcionario = new Funcionario(5, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		
		assertTrue(novaDescricao.equals(pacoteComercialDAO.editarPacoteComercial(pacoteComercial, funcionario).getDescricao()));
	}
	
	@Test
	public void testGetHistoricoPacoteComercial() throws Exception {
		assertEquals(0, pacoteComercialDAO.getHistoricoPacoteComercial(1).size());
	}
	
	@Test
	public void testDesativarPacoteComercial() throws Exception {
		Funcionario funcionario = new Funcionario(6, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		pacoteComercialDAO.desativarPacoteComercial(4, funcionario);
		assertTrue(!pacoteComercialDAO.pesquisaPacoteComercialAuxiliarID(4).isAtivo());
	}
	
	@Test
	public void testAtivarPacoteComercial() throws Exception {
		Funcionario funcionario = new Funcionario(5, "JUnitCriarClienteTeste", 112233, "JUnitTest", "func10", true, 1);
		pacoteComercialDAO.ativarPacoteComercial(6, funcionario);
		assertTrue(pacoteComercialDAO.pesquisaPacoteComercialAuxiliarID(6).isAtivo());
	}
	
	@Test
	public void testGetHistoricoOperador() throws Exception {
		assertEquals(0, pacoteComercialDAO.getHistoricoOperador(1).size());
	}
	
	@Test
	public void testPesquisaPacoteComercialAuxiliarID() throws Exception {
		assertEquals(2, pacoteComercialDAO.pesquisaPacoteComercialAuxiliarID(2).getId());
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
