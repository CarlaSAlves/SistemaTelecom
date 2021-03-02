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
 * Class used to establish connection with the database and interact with the "role" table.
 */
public class RoleDAO {

	private Connection myConn;
	
	/*
	 * Constructor which takes a java.sql.Connection object, to be supplied by the class servico.GestorDeDAO.
	 */
	public RoleDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	/*
	 * Returns a Java object corresponding to the role with the id passed as argument.
	 * If no role is found in the database, returns a null object.
	 */
	public Role getRoleById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Role role = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from role where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			
			//parse the result returned by the database and converts each entry into a "Role" object
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
	 * Returns a list containing every role in the database, as Java objects.
	 * If no roles exist in the database, returns an empty list.
	 */
	public List<Role> getAllRole() throws Exception {
		List<Role> listaRoles = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from role");
			
			//parse the result returned by the database and converts each entry into a "Role" object
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
	 * Method that converts each entry of a ResultSet into a "Role" Java object
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
