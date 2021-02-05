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
import standard_value_object.PacoteComercial;


public class PacoteComercialDAO {

	private Connection myConn;

	public PacoteComercialDAO() throws Exception  {

		Properties props = new Properties(); 
		props.load(new FileInputStream("sistema_tele.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);

	}

	public List<PacoteComercial> getAllPacotesComerciais() throws Exception {
		List<PacoteComercial> listaPacotes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_comercial");

			while (myRs.next()) {
				PacoteComercial pacote = converteRowParaPacote(myRs);
				listaPacotes.add(pacote);
			}

			return listaPacotes;
		}
		finally {
			close(myStmt, myRs);
		}

	}

	public List<PacoteComercial> pesquisaPacoteComercial(String id) throws Exception {
		List<PacoteComercial> list = new ArrayList<>();

		PreparedStatement myState = null; 
		ResultSet myRes = null; 

		try {
			id += "%";

			myState = myConn.prepareStatement("select * from pacote_comercial where id like ?");

			myState.setString(1, id);

			myRes = myState.executeQuery();

			while (myRes.next()) {
				PacoteComercial pacote = converteRowParaPacote(myRes);
				list.add(pacote);
			}

			return list;
		}

		finally {
			close (myState, myRes);
		}
	}

	public void criarPacoteComercial (PacoteComercial pacote) throws Exception {

		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("INSERT INTO pacote_comercial (nome, descricao, ativo)"
					+ "VALUES(?,?,?,?,?)");

			myState.setString(1, pacote.getNome());
			myState.setString(2, pacote.getDescricao());
			myState.setBoolean(3, pacote.isAtivo());

			myState.executeUpdate();
		} catch(Exception e) {

		} finally {
			myState.close();
		}
	}

	public void editarPacoteComercial(PacoteComercial pacote) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("UPDATE pacote_comercial SET nome=?, descricao=?, ativo=? WHERE id=?");

			myState.setString(1, pacote.getNome());
			myState.setString(2, pacote.getDescricao());
			myState.setBoolean(3, pacote.isAtivo());

			myState.executeUpdate();

		} catch(Exception e) {

		} finally {

			myState.close();
		}
	}

	public void desativarPacoteComercial (String nome) throws Exception {
		PreparedStatement myState = null; 

		try {

			myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo=0 WHERE id=?");

			myState.setString(1, nome);
			myState.executeUpdate();

		} catch(Exception e) {

		} finally {

			myState.close();
		}

	}


	private PacoteComercial converteRowParaPacote(ResultSet myRes) throws Exception {
		
		int id = myRes.getInt("id");
		String nome = myRes.getString("nome");
		String descricao = myRes.getString("descricao");
		boolean ativo = myRes.getBoolean("ativo");
		
		PacoteComercial pacote = new PacoteComercial (id, nome, descricao, ativo);
		
		return pacote;
	}

	private void close(Statement myState, ResultSet myRes) throws SQLException {
		close(null, myState, myRes);

	}

	private void close(Connection myConn, Statement myState, ResultSet myRes) throws SQLException {
		
		if (myRes != null) {
			myRes.close();
		}

		if (myState != null) {
			myState.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}
}
