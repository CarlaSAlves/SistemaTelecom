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

import data_acess_object_dao.FuncionarioDAO;
import standard_value_object.Funcionario;
import standard_value_object.Role;

/*
 * Class containing tests for the methods in the FuncionarioDAO class.
 * It's advisable to run the database creating script before running any tests.
 */
public class TesteFuncionarioDAO {

	private FuncionarioDAO funcionarioDAO;

	public TesteFuncionarioDAO() throws FileNotFoundException, IOException, SQLException {
		funcionarioDAO = new FuncionarioDAO(startConnection());
	}
	
	@Test
	public void testGetAllFuncionarioAdmin() throws Exception {
		assertEquals(4, funcionarioDAO.getAllFuncionarioAdmin().size());
	}
	
	@Test
	public void testPesquisaOperadorAuxiliarID() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioById(1));
	}
	
	@Test
	public void testPesquisaOperadorAuxiliarIDNulo() throws Exception {
		assertNull(funcionarioDAO.pesquisaFuncionarioById(0));
	}
	
	
	@Test
	public void testGetAllFuncionarioOperador() throws Exception {
		assertEquals(3, funcionarioDAO.getAllFuncionarioOperador().size());
	}
	
	@Test
	public void testPesquisaFuncionarioAdmin() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioLogin("admin"));
	}
	
	@Test
	public void testPesquisaFuncionarioOperador() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioOperador(0, "235", "", 1));
	}
	
	@Test
	public void testPesquisaTodosFuncionarios() throws Exception {
		assertEquals(7, funcionarioDAO.pesquisaTodosFuncionarios().size());
	}
	
	@Test
	public void testPesquisaFuncionarioByNif() throws Exception {
		assertEquals(2, funcionarioDAO.pesquisaFuncionarioByNif("235").size());
	}
	
	@Test
	public void testPesquisaFuncionarioByNifNulo() throws Exception {
		assertEquals(0, funcionarioDAO.pesquisaFuncionarioByNif("99999999").size());
	}
	
	@Test
	public void testPesquisaFuncionarioByNome() throws Exception {
		assertEquals(1, funcionarioDAO.pesquisaFuncionarioByNome("admin").size());
	}
	
	@Test
	public void testPesquisaFuncionarioByNomeNulo() throws Exception {
		assertEquals(0, funcionarioDAO.pesquisaFuncionarioByNome("999999999").size());
	}
	
	@Test
	public void testPesquisaFuncionarioById() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioById(1));
	}
	
	@Test
	public void testPesquisaFuncionarioByIdNulo() throws Exception {
		assertNull(funcionarioDAO.pesquisaFuncionarioById(999));
	}
	
	@Test
	public void testPesquisarFuncionarioLoginPass() throws Exception {
		assertNotNull(funcionarioDAO.pesquisarFuncionarioLoginPass("admin", "func1"));
	}
	
	@Test
	public void testPesquisarFuncionarioLoginPassNulo() throws Exception {
		assertNull(funcionarioDAO.pesquisarFuncionarioLoginPass("123", "678"));
	}
	
	@Test
	public void testCriarFuncionario() throws Exception {
		Funcionario operador = new Funcionario("TesteCriarFunc", 555666, "testeLoginFunc", 
				"testPass", true, 2);
		assertNotEquals(0, funcionarioDAO.criarFuncionario(operador, funcionarioDAO.pesquisaFuncionarioById(1)).getId()  );
	}
	
	@Test
	public void testEditarFuncionario() throws Exception {
		String novoNome = "testeEditar";
		
		//funcionario com id 5 é um operador
		Funcionario operador = funcionarioDAO.pesquisaFuncionarioById(5);
		operador.setNome(novoNome);
		
		//funcionario com id 1 é um admin
		Funcionario admin = funcionarioDAO.pesquisaFuncionarioById(1);
		operador = funcionarioDAO.editarFuncionario(operador, admin, "000");
		
		assertTrue(novoNome.equals(operador.getNome()));
	}
	
	@Test
	public void testEditarFuncionarioPassNull() throws Exception {
		String novoNome = "testeEditar";
		
		//funcionario com id 5 é um operador
		Funcionario operador = funcionarioDAO.pesquisaFuncionarioById(6);
		operador.setNome(novoNome);
		
		//funcionario com id 1 é um admin
		Funcionario admin = funcionarioDAO.pesquisaFuncionarioById(3);
		operador = funcionarioDAO.editarFuncionario(operador, admin, null);
		
		assertTrue(novoNome.equals(operador.getNome()));
	}
	
	@Test
	public void testAtribuirRoleAFuncionario() throws Exception {
		//vamos atribuir a role Operador a um admin
		//funcionario com id 2 é um admin
		Funcionario admin = funcionarioDAO.pesquisaFuncionarioById(2);
		funcionarioDAO.atribuirRoleAFuncionario(new Role(2, "Operador"), admin);
	
		assertEquals(2, funcionarioDAO.pesquisaFuncionarioById(5).getId_role());
	}
	
	@Test
	public void testAtivarFuncionario() throws Exception {
		Funcionario admin = funcionarioDAO.pesquisaFuncionarioById(3);
		Funcionario funcionarioAtivar = funcionarioDAO.pesquisaFuncionarioById(4);
		boolean isAtivado = funcionarioAtivar.isAtivo();
		
		funcionarioDAO.desativarFuncionario(4, admin);
	
		assertEquals(!isAtivado, funcionarioDAO.pesquisaFuncionarioById(4).isAtivo());
	}
	
	@Test
	public void getHistoricoOperador() throws Exception {
		assertNotEquals(0, funcionarioDAO.getHistoricoOperador(3).size());
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
