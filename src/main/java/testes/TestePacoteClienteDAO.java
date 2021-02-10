package testes;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import data_acess_object_dao.PacoteClienteDAO;
import standard_value_object.PacoteCliente;
import standard_value_object.Promocao;

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
	
	@Test
	public void testCriarPacoteCliente() throws Exception {
		PacoteCliente pacoteCliente = new PacoteCliente(1, 5);
		pacoteCliente = pacoteClienteDAO.criarPacoteCliente(pacoteCliente);
		assertNotEquals(0, pacoteCliente.getId());
	}
	
	@Test
	public void testAdicionarPromocao() throws Exception {
		Promocao promocao = new Promocao(4, "", "", true, null, null);
		PacoteCliente pacoteCliente = pacoteClienteDAO.pesquisarPacoteClienteId(2);
		
		assertEquals(1, pacoteClienteDAO.adicionarPromocao(pacoteCliente, promocao));
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
