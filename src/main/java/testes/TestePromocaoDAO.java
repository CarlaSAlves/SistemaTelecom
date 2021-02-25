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

import data_acess_object_dao.PromocaoDAO;
import standard_value_object.PacoteCliente;
import standard_value_object.Promocao;

//correr o script para criar a base de dados antes de correr estes testes
public class TestePromocaoDAO {
	
	private PromocaoDAO promocaoDAO;
	
	public TestePromocaoDAO() throws FileNotFoundException, IOException, SQLException {
		promocaoDAO = new PromocaoDAO(startConnection());
	}
	
	@Test
	public void testGetAllPromocoes() throws Exception {
		assertNotEquals(0, promocaoDAO.getAllPromocoes().size());
	}
	
	@Test
	public void testPesquisaPromocao() throws Exception {
		assertNotEquals(0, promocaoDAO.pesquisaPromocao(1, "", 1));
	}
	
	@Test
	public void testPesquisarPromocaoById() throws Exception {
		assertNotEquals(0, promocaoDAO.pesquisarPromocaoById(1));
	}
	
	@Test
	public void testPesquisarPromocaoNome() throws Exception {
		assertNotEquals(0, promocaoDAO.pesquisarPromocaoNome("Promoção"));
	}
	
	@Test
	public void testPesquisarPromocoesPacoteCliente() throws Exception {
		PacoteCliente pacoteCliente = new PacoteCliente();
		pacoteCliente.setId(1);
		assertNotEquals(0, promocaoDAO.pesquisarPromocoesPacoteCliente(pacoteCliente));
	}
	
	@Test
	public void testCriarPromocao() throws Exception {
		Promocao promocao = new Promocao("promocao TESTE", "descricao teste", true);
		promocaoDAO.criarPromocao(promocao);
		assertNotEquals(0, promocao);
	}
	
	@Test
	public void testEditarPromocao() throws Exception {
		String novoNome = "nome EDITADO";
		Promocao promocao = new Promocao(4, novoNome, "Descriçao EDITADA", true);
		promocaoDAO.editarPromocao(promocao);
		assertEquals(novoNome, promocaoDAO.pesquisarPromocaoById(promocao.getId()).getNome());
	}
	
	@Test
	public void testGetHistoricoPromocao() throws Exception {
		assertNotEquals(0, promocaoDAO.getHistoricoPromocao(3));
	}
	
	//estabelece a ligaçao com a base de dados definida no documento sistema_tele.properties
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
