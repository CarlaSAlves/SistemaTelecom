package data_acess_object_dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import standard_value_object.Cliente;
import standard_value_object.Funcionario;

public class FuncionarioDAO {
	
	private Connection myConn;

	public FuncionarioDAO() throws Exception {

		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");


		myConn = DriverManager.getConnection(dburl, user, password);

	}

	public List<Funcionario> getAllFuncionarioAdmin() throws Exception {
		List<Funcionario> listaFuncionarioAdmin = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
	
			myRs = myStmt.executeQuery("select * from funcionario where id_role=1");

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionarioAdmin.add(funcionario);
			}

			return listaFuncionarioAdmin;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Funcionario> getAllFuncionarioOperador() throws Exception {
		List<Funcionario> listaFuncionarioOperador = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
	
			myRs = myStmt.executeQuery("select * from funcionario where id_role=2");

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionarioOperador.add(funcionario);
			}

			return listaFuncionarioOperador;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Funcionario> pesquisaFuncionarioAdmin(String nif) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from funcionario where nif LIKE ? AND id_role= 1");

			myStmt.setString(1, nif);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Funcionario> pesquisaFuncionarioOperador(String nif) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from funcionario where nif LIKE ? AND id_role= 2");

			myStmt.setString(1, nif);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	public void criarFuncionario(Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO funcionario(nome, nif, login, password, ativo, id_role) "
					+ "VALUES(?,?,?,?,?,?)");
			
			myStmt.setString(1, funcionario.getNome());
			myStmt.setLong(2, funcionario.getNif());
			myStmt.setString(3, funcionario.getLogin());
			myStmt.setString(4, funcionario.getPassword());
			myStmt.setBoolean(5, funcionario.isAtivo());
			myStmt.setInt(6, funcionario.getId_role());
		
			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}
	
	public void editarFuncionario(Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `nome`=?, `nif`=?, "
					+ "`login`=?, `password`=?, `ativo`=?, `id_role`=? WHERE  `id`=?");
			
			myStmt.setString(1, funcionario.getNome());
			myStmt.setLong(2, funcionario.getNif());
			myStmt.setString(3, funcionario.getLogin());
			myStmt.setString(4, funcionario.getPassword());
			myStmt.setBoolean(5, funcionario.isAtivo());
			myStmt.setInt(6, funcionario.getId_role());
			myStmt.setInt(7, funcionario.getId());
			
			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void desativarFuncionario(int nif) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("update funcionario SET `ativo`= 0 where nif=?");

			myStmt.setInt(1, nif);

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}

	}
	private Funcionario convertRowParaFuncionario(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		long nif = myRs.getInt("nif");
		String login = myRs.getString("login");
		String password = myRs.getString("password");
		boolean ativo = myRs.getBoolean("ativo");
		int id_role = myRs.getInt("id_role");
		
		Funcionario funcionario = new Funcionario(id, nome, nif, login, password, ativo, id_role);

		return funcionario;
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



