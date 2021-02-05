package data_acess_object_dao_v2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import standard_value_object_v2.PacoteComercial;

public class PacoteComercialDAO {

	private Connection myConn;

	public PacoteComercialDAO() throws FileNotFoundException, IOException, SQLException {

		Properties props = new Properties();
		props.load(new FileInputStream("sgot.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		myConn = DriverManager.getConnection(dburl, user, password);
	}

	public List<PacoteComercial> searchAllPacotesComerciais() throws Exception {
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
	
	public PacoteComercial searchPacoteComercialById(int id) throws Exception {

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			PacoteComercial pacoteComercial = null;
			
			myStmt = myConn.prepareStatement("select * from pacote_comercial where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				pacoteComercial = new PacoteComercial();
				pacoteComercial.setId(myRs.getInt(1));
				pacoteComercial.setNome(myRs.getString(2));
				pacoteComercial.setDescricao(myRs.getString(3));
				pacoteComercial.setAtivo(myRs.getBoolean(4));
				pacoteComercial.setData_incio( new java.sql.Date(myRs.getTimestamp(5).getTime()));
				if (myRs.getTimestamp(6) != null) {
					pacoteComercial.setData_fim( new java.sql.Date(myRs.getTimestamp(6).getTime()));
				}
			}

			return pacoteComercial;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<PacoteComercial> searchPacoteComercialByNome(String nome) throws Exception {
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
	
	public void desativarPacoteComercialById(int id) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("update pacote_comercial SET `ativo`= 0 where id=?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void desativarPacoteComercialByName(String nome) throws SQLException{
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
		java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		
		java.sql.Date data_fim = null;
		if (myRs.getTimestamp(6) != null) {
			data_fim = new java.sql.Date(myRs.getTimestamp("data_fim").getTime());
		}
		
		PacoteComercial pacoteComercial = new PacoteComercial(id, nome, descricao, ativo, data_inicio, data_fim);
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
