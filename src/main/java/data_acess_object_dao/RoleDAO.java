package data_acess_object_dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import standard_value_object.Role;

public class RoleDAO {

	private Connection myConn;
	
	public RoleDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}
	
	public Role getRoleById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Role role = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from role where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
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

	public List<Role> getAllRole() throws Exception {
		List<Role> listaRoles = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from role");
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
