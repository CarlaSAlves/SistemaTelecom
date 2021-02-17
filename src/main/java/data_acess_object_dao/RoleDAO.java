package data_acess_object_dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import standard_value_object.Role;

/*
 * Classe que vai estabelecer a ligaçao com a base de dados e interagir principalmente com a tabela "role"
 */
public class RoleDAO {

	private Connection myConn;
	
	/*
	 * Construtor que recebe um objeto do tipo java.sql.Connection, a ser fornecido pela classe servico.GestorDeDAO
	 */
	public RoleDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Pesquisa e devolve a funçao com o id enviado como parametro.
	 * Devolve um objeto nulo se nenhum for encontrado.
	 */
	public Role getRoleById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Role role = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from role where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			
			//converter o resultado devolvido pela base de dados num objeto java
			if (myRs.next()) {
				role = new Role();
				role.setId(myRs.getInt(1));
				role.setNome(myRs.getString(2));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return role;
	}

	/*
	 * Método que devolve uma lista com todos as funçoes existentes na tabela "funcao". 
	 * Caso não existam funçoes, é devolvida uma lista vazia.
	 */
	public List<Role> getAllRole() throws Exception {
		List<Role> listaRoles = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from role");
			
			//faz parse ao resultado enviado pela base de dados e converte cada entrada num objeto funcionario
			while (myRs.next()) {
				Role role = converteRowParaRoles(myRs);
				listaRoles.add(role);
			}
	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return listaRoles;	
	}
	
	/*
	 * Converte cada entrade de um ResultSet num objeto Role
	 */
	private Role converteRowParaRoles(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		
		Role role = new Role(id, nome);

		return role;
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
