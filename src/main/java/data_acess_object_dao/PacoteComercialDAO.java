package data_acess_object_dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import standard_value_object.Cliente;
import standard_value_object.PacoteComercial;

public class PacoteComercialDAO {

	private Connection myConn;

	public PacoteComercialDAO() throws Exception {

		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");


		myConn = DriverManager.getConnection(dburl, user, password);

	}

	public List<PacoteComercial> getAllPacotesComerciais() throws Exception {
		List<PacoteComercial> listaPacotesComerciais = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from pacote_comercial");

			while (myRs.next()) {
				PacoteComercial pacoteComercial = converteRowParaPacoteComercial(myRs);
				listaPacotesComerciais.add(pacoteComercial);
			}

			return listaPacotesComerciais;		
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public List<PacoteComercial> pesquisaPacoteComercial(String nome) throws Exception {
		List<PacoteComercial> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nome += "%";

			myStmt = myConn.prepareStatement("select * from pacote_comercial where nome like ?");

			myStmt.setString(1, nome);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				PacoteComercial pacoteComercial = converteRowParaPacoteComercial(myRs);
				list.add(pacoteComercial);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public void criarPacoteComercial(PacoteComercial pacoteComercial) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO pacote_comercial(nome, descricao, ativo) VALUES(?,?,?)");

			myStmt.setString(1, pacoteComercial.getNome());
			myStmt.setString(2, pacoteComercial.getDescricao());
			myStmt.setBoolean(3, pacoteComercial.isAtivo());


			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void editarPacoteComercial(PacoteComercial pacoteComercial) throws Exception {
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("UPDATE `pacote_comercial` SET `nome`=?, `descricao`=?, `ativo`=?  WHERE  `id`=?");

			myStmt.setString(1, pacoteComercial.getNome());
			myStmt.setString(2, pacoteComercial.getDescricao());
			myStmt.setBoolean(3, pacoteComercial.isAtivo());
			myStmt.setInt(4, pacoteComercial.getId());

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void getDescricaoPacoteComercial(PacoteComercial pacoteComercial) {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("select `descricao` FROM `pacote_comercial` WHERE 'id' = ? ");

			myStmt.setString(1, pacoteComercial.getDescricao());
		} catch (SQLException e) {

			e.printStackTrace();
		}


	}

	public void eliminarPacoteComercial(String nome) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("update pacote_comercial SET `ativo`= 0 where nome=?");

			myStmt.setString(1, nome);

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}

	}
	private PacoteComercial converteRowParaPacoteComercial(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");


		PacoteComercial pacoteComercial = new PacoteComercial(id, nome, descricao, ativo);

		return pacoteComercial;
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
