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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

import standard_value_object.Promocao;

public class PromocaoDAO {

	private Connection myConn;

	public PromocaoDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
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
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return listaPromocoes;
	}

	public List<Promocao> pesquisaPromocao(int id, String nome, int ativo) throws Exception {
		List<Promocao> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM PROMOCAO WHERE ";
		
		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id!= 0){
				sj.add("ID=?");
				values.add(id);
			}
			if(nome != null) {
				nome += "%";
				sj.add("NOME LIKE ?");
				values.add(nome);
			}
			if(ativo!=0){
				sj.add("ativo=?");
				values.add(ativo);
			}

			query += sj.toString();
		
		
			myStmt = myConn.prepareStatement(query);

			for (int index = 0; index < values.size(); index++){
				myStmt.setObject(index+1 , values.get(index));
			}

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Promocao promocao = converteRowParaPromocoes(myRs);
				list.add(promocao);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	public Promocao pesquisarPromocaoById(int id) throws Exception {
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
				
				if (myRs.getTimestamp(5) != null) {
					promocao.setData_inicio( new java.sql.Date(myRs.getTimestamp(5).getTime()));
				}
				
				if (myRs.getTimestamp(6) != null) {
					promocao.setData_fim( new java.sql.Date(myRs.getTimestamp(6).getTime()));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return promocao;
	}
	
	public List<Promocao> pesquisarPromocaoNome(String nome) throws Exception {
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
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return list;
	}

	public void criarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO promocao(nome, descricao, ativa, data_inicio) VALUES(?,?,?,?)");

			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setBoolean(3, promocao.isAtiva());
			
			//se ativo = true, mudar a data_inicio para agora. De outro modo, colocar nulo na data_inicio
			myStmt.setTimestamp(4, promocao.isAtiva() ? new Timestamp(System.currentTimeMillis()) : null);

			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	// Este metodo serve apenas para editar nome e descriçao. Para Ativar/Desativar, usar os metodos correspondentes
	public void editarPromocao(Promocao promocao) throws Exception {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE `promocao` SET `nome`=?, `descricao`=? WHERE `id`=?");
			myStmt.setString(1, promocao.getNome());
			myStmt.setString(2, promocao.getDescricao());
			myStmt.setInt(3, promocao.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

//	public void desativarPromocao(int id) throws SQLException{
//		PreparedStatement myStmt = null;
//		try {
//			myStmt = myConn.prepareStatement("update promocao SET `ativa`= 0 where id=?");
//
//			myStmt.setInt(1, id);
//
//			myStmt.executeUpdate();
//
//		}catch(Exception e) {
//
//		}finally {
//			myStmt.close();
//		}
//	}
	
	//primeiro ve se a promocao com o id inserido esta ativa, e só depois desativa e insere a data atual
	//no campo data_fim
	public void desativarPromocao (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("Select ativa From promocao Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtiva = true;;
			if(rs.next()) {
				estaAtiva = rs.getBoolean(1);
			}
			
			if(estaAtiva) {
				myState = myConn.prepareStatement("UPDATE promocao SET ativa = 0,"
						+ "data_fim = current_timestamp() WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	//primeiro ve se a promocao com o id inserido esta desativa, e só depois ativa e insere a data atual
	//no campo data_inicio e coloca data_fim a nulo
	public void ativarPromocao (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("Select ativa From promocao Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtiva = false;;
			if(rs.next()) {
				estaAtiva = rs.getBoolean(1);
			}
			
			if(!estaAtiva) {
				myState = myConn.prepareStatement("UPDATE promocao SET ativa = 0,"
						+ "data_inicio = current_timestamp(), data_fim = NULL WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	private Promocao converteRowParaPromocoes(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativa");
		
		java.sql.Date data_inicio = null;
		java.sql.Date data_fim = null;
		
		//datas podem ser nulas, é necessário testar nulidade
		if (myRs.getTimestamp("data_inicio") != null) {
			data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		}
		
		if (myRs.getTimestamp("data_fim") != null) {
			data_fim = new java.sql.Date(myRs.getTimestamp("data_fim").getTime());
		}
		
		Promocao promocao = new Promocao(id, nome, descricao, ativo, data_inicio, data_fim);

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
