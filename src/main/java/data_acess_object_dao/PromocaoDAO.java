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
import standard_value_object.Promocao;

public class PromocaoDAO {

	private Connection myConn;

	public PromocaoDAO() throws Exception {

		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");


		myConn = DriverManager.getConnection(dburl, user, password);

	}
	public List<Promocao> getAllPromocoes() throws Exception {
		List<Promocao> listaPromocoes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from promocao");

			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				listaPromocoes.add(promocao);
			}

			return listaPromocoes;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Promocao> pesquisaPromocao(String nome) throws Exception {
		List<Promocao> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nome += "%";

			myStmt = myConn.prepareStatement("select * from promocao where nome like ?");

			myStmt.setString(1, nome);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				list.add(promocao);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void criarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO promocao(nome, descricao, ativa) VALUES(?,?,?)");
			
			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setBoolean(3, promocao.isAtiva());


			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}
	
	public void editarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("UPDATE `promocao` SET `nome`=?, `descricao`=?, `ativa`=?  WHERE  `id`=?");
			
			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setBoolean(3, promocao.isAtiva());
			myStmt.setInt(4, promocao.getId());
			
			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void eliminarPromocao(String nome) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("update promocao SET `ativa`= 0 where nome=?");

			myStmt.setString(1, nome);

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}

	}
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");

		
		Promocao promocao = new Promocao(id, nome, descricao, ativo);

		return promocao;
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
