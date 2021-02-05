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
import java.util.Date;
import java.util.List;
import java.util.Properties;

import standard_value_object_v2.PacoteCliente;
import standard_value_object_v2.PacoteComercial;
import standard_value_object_v2.Promocao;

public class PromocaoDAO {

	private Connection myConn;

	public PromocaoDAO() throws FileNotFoundException, IOException, SQLException {

		Properties props = new Properties();
		props.load(new FileInputStream("sgot.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");


		myConn = DriverManager.getConnection(dburl, user, password);
	}
	
	public List<Promocao> searchAllPromocoes() throws Exception {
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
	
	public Promocao searchPromocaoById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Promocao promocao = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from promocao where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				promocao = new Promocao();
				promocao.setId(myRs.getInt(1));
				promocao.setNome(myRs.getString(2));
				promocao.setDescricao(myRs.getString(3));
				promocao.setAtiva(myRs.getBoolean(4));
				
				promocao.setData_incio( new java.sql.Date(myRs.getTimestamp(5).getTime()));
				if (myRs.getTimestamp(6) != null) {
					promocao.setData_fim( new java.sql.Date(myRs.getTimestamp(6).getTime()));
				}
			}
		}
		finally {
			close(myStmt, myRs);
		}
		
		return promocao;
	}
	
	public List<Promocao> searchPromocaoNome(String nome) throws Exception {
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
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	public void desativarPromocao(int id) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("update promocao SET ativa = '0' where id=?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	public void ativarPromocao(int id) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE promocao SET ativa = '1' where id=?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativa = myRs.getBoolean("ativa");
		
		java.sql.Date data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		
		java.sql.Date data_fim = null;
		if (myRs.getTimestamp(6) != null) {
			data_fim = new java.sql.Date(myRs.getTimestamp("data_fim").getTime());
		}

		Promocao promocao = new Promocao(id, nome, descricao, ativa, data_inicio, data_fim);

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
