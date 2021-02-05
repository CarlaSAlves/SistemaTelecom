package data_acess_object_dao_v2;

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

import standard_value_object_v2.Funcionario;
import standard_value_object_v2.Role;

public class FuncionarioDAO {

	private Connection myConn;

	public FuncionarioDAO() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("sgot.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);
	}
	
	public List<Funcionario> searchAllFuncionarios() throws Exception {
		List<Funcionario> listaFuncionario = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario");

			while (myRs.next()) {
				Funcionario funcionario = convertRowToFuncionario(myRs);
				listaFuncionario.add(funcionario);
			}

			return listaFuncionario;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	//nif é um int, ao enviar um nif para este metodo é necessario converte-lo em string, fazendo por exemplo "" + 12345
	public List<Funcionario> searchFuncionarioByNif(String nif) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from funcionario where nif like ?");
			myStmt.setString(1, nif);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowToFuncionario(myRs);
				list.add(funcionario);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Funcionario> searchFuncionarioByNome(String nome) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nome += "%";

			myStmt = myConn.prepareStatement("select * from funcionario where nome like ?");
			myStmt.setString(1, nome);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowToFuncionario(myRs);
				list.add(funcionario);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Funcionario searchFuncionarioById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		Funcionario funcionario = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				funcionario = new Funcionario();
				funcionario.setId(myRs.getInt(1));
				funcionario.setNome(myRs.getString(2));
				funcionario.setNif(myRs.getInt(3));
				funcionario.setLogin(myRs.getString(4));
				funcionario.setPassword(myRs.getString(5));
				funcionario.setAtivo(myRs.getInt(6) == 1 ? true : false);
				funcionario.setId_role(myRs.getInt(7));	
			}
		}
		finally {
			close(myStmt, myRs);
		}
		
		return funcionario;
	}
	
	public Funcionario searchFuncionarioByLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Funcionario funcionario = null;
		
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM funcionario WHERE login=? AND password=?;");
			myStmt.setString(1, login);
			myStmt.setString(2, pass);
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				funcionario = new Funcionario();
				
				funcionario.setId(myRs.getInt(1));
				funcionario.setNome(myRs.getString(2));
				funcionario.setNif(myRs.getInt(3));
				funcionario.setLogin(myRs.getString(4));
				funcionario.setPassword(myRs.getString(5));
				funcionario.setAtivo(myRs.getInt(6) == 1 ? true : false);
				funcionario.setId_role(myRs.getInt(7));	
			}
			
		}
		finally {
			close(myStmt, myRs);
		}
		
		return funcionario;
	}
	
	//metodo devolve o funcionario colocado só para testar se resultou
	public void criarFuncionario(Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("insert into funcionario(nome,nif,login,password,ativo,id_role) values(?,?,?,?,?,?)");

			myStmt.setString(1, funcionario.getNome());
			myStmt.setInt(2, funcionario.getNif());
			myStmt.setString(3, funcionario.getLogin());
			myStmt.setString(4, funcionario.getPassword());
			myStmt.setInt(5, funcionario.isAtivo() == true ? 1 : 0);
			myStmt.setInt(6, funcionario.getId_role());

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}
	
	public void atribuirRoleAFuncionario(Role role, Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
			myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `id_role`=? WHERE `id`=?");
			myStmt.setInt(1, role.getId());
			myStmt.setInt(2, funcionario.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}
	
	private Funcionario convertRowToFuncionario(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		boolean ativo = (myRs.getInt("ativo") == 1);
		int nif = myRs.getInt("nif");
		
		String nome = myRs.getString("nome");
		String login = myRs.getString("login");
		String password = myRs.getString("password");
		
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
