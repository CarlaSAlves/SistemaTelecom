package testes;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import data_acess_object_dao.PromocaoDAO;

//correr o script para criar a base de dados antes de correr estes testes
public class TestePromocaoDAO {
	
	private PromocaoDAO promocaoDAO;
	
	public TestePromocaoDAO() throws FileNotFoundException, IOException, SQLException {
		promocaoDAO = new PromocaoDAO(startConnection());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
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
