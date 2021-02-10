package testes;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import data_acess_object_dao.FuncionarioDAO;
import standard_value_object.Funcionario;
import standard_value_object.Role;

//correr o script para criar a base de dados antes de correr estes testes
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
	public void testGetAllFuncionarioOperador() throws Exception {
		assertEquals(1, funcionarioDAO.getAllFuncionarioOperador().size());
	}
	
	@Test
	public void testPesquisaFuncionarioAdmin() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioAdmin("admin"));
	}
	
	@Test
	public void testPesquisaFuncionarioOperador() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioOperador(0, "235", "", 1));
	}
	
	@Test
	public void testPesquisaTodosFuncionarios() throws Exception {
		assertEquals(5, funcionarioDAO.pesquisaTodosFuncionarios().size());
	}
	
	@Test
	public void testPesquisaFuncionarioByNif() throws Exception {
		assertEquals(2, funcionarioDAO.pesquisaFuncionarioByNif("235").size());
	}
	
	@Test
	public void testPesquisaFuncionarioByNome() throws Exception {
		assertEquals(1, funcionarioDAO.pesquisaFuncionarioByNome("admin").size());
	}
	
	@Test
	public void testPesquisaFuncionarioById() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioById(1));
	}
	
	@Test
	public void testPesquisarFuncionarioLoginPass() throws Exception {
		assertNotNull(funcionarioDAO.pesquisaFuncionarioById(1));
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
		operador = funcionarioDAO.editarFuncionario(operador, admin);
		
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

	//estabelece a ligaçao com a base de dados definida no documento sistema_tele.properties
	private Connection startConnection() throws FileNotFoundException, IOException, SQLException {
//		Properties props = new Properties();
//		props.load(new FileInputStream("sistema_tele.properties"));
//		String user = props.getProperty("user");
//		String password = props.getProperty("password");
//		String dburl = props.getProperty("dburl");
		String user = "hbstudent";
		String password = "hbstudent";
		String dburl = "jdbc:mysql://localhost:3306/sistema_tele?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		Connection connection = DriverManager.getConnection(dburl, user, password);
		connection.setAutoCommit(false);
		return connection;
	}

}
