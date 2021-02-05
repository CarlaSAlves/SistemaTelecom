package data_acess_object_dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
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
				PacoteComercial pacote = converteRowParaPacoteComercial(myRs);
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
				PacoteComercial pacote = converteRowParaPacoteComercial(myRes);
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

	
	public void desativarPacoteComercial (int id) throws Exception {
		PreparedStatement myState = null; 

		try {

			myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo=0 WHERE id=?");

			myState.setInt(1, id);
			myState.executeUpdate();

		} catch(Exception e) {

		} finally {

			myState.close();
		}

	}
	private PacoteComercial converteRowParaPacoteComercial(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");
		Date data_inicio = myRs.getDate("data_inicio");
		Date data_fim = myRs.getDate("data_fim");


		PacoteComercial pacoteComercial = new PacoteComercial(id, nome, descricao, ativo, data_inicio, data_fim);

		return pacoteComercial;
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
