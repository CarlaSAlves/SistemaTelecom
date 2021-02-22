package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import data_acess_object_dao.RoleDAO;

//correr o script para criar a base de dados antes de correr estes testes
public class TesteRoleDAO {

	private RoleDAO roleDAO;
	
	public TesteRoleDAO() throws FileNotFoundException, IOException, SQLException {
		roleDAO = new RoleDAO(startConnection());
	}
	
	@Test
	public void testGetRoleById() throws Exception {
		assertNotNull(roleDAO.getRoleById(1));
	}
	
	@Test
	public void testGetAllRole() throws Exception {
		assertEquals(2, roleDAO.getAllRole().size());
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
