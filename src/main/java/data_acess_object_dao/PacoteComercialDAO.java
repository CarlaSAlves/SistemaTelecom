package data_acess_object_dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.StringJoiner;

import historicos.HistoricoCliente;
import historicos.HistoricoPacoteComercial;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;

public class PacoteComercialDAO {

	private Connection myConn;

	public PacoteComercialDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}
		return listaPacotes;
	}

	public List<PacoteComercial> pesquisaPacoteComercial(int id, String nome, int ativo) throws Exception {
		List<PacoteComercial> list = new ArrayList<>();

		PreparedStatement myStmt = null; 
		ResultSet myRs = null; 
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM PACOTE_COMERCIAL WHERE ";
		
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
				PacoteComercial pacote = converteRowParaPacoteComercial(myRs);
				list.add(pacote);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close (myStmt, myRs);
		}
		return list;
	}
	
	public PacoteComercial pesquisaPacoteComercialById(int id) throws Exception {
		PacoteComercial pacoteComercial = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from pacote_comercial where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				pacoteComercial = new PacoteComercial();
				pacoteComercial.setId(myRs.getInt(1));
				pacoteComercial.setNome(myRs.getString(2));
				pacoteComercial.setDescricao(myRs.getString(3));
				pacoteComercial.setAtivo(myRs.getBoolean(4));
				
				if (myRs.getTimestamp(5) != null) {
					pacoteComercial.setData_inicio( new java.sql.Date(myRs.getTimestamp(5).getTime()));
				}
				
				if (myRs.getTimestamp(6) != null) {
					pacoteComercial.setData_fim( new java.sql.Date(myRs.getTimestamp(6).getTime()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}
		
		return pacoteComercial;
	}

	public void criarPacoteComercial (PacoteComercial pacote,Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;
		PacoteComercial pacoteComercial = null;

		//se se pode criar um pacoteComercial com ativo = false, entao nao faz sentido ter data_inicio definida de forma automatica
		//pelo mysql. É preciso que seja definida manualmente
		try {
			myStmt = myConn.prepareStatement("INSERT INTO pacote_comercial(nome, descricao, ativo, data_inicio) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			myStmt.setString(1, pacote.getNome());
			myStmt.setString(2, pacote.getDescricao());
			myStmt.setBoolean(3, pacote.isAtivo());
			//se ativo = true, mudar a data_inicio para agora. De outro modo, colocar nulo na data_inicio
			myStmt.setTimestamp(4, pacote.isAtivo() ? new Timestamp(System.currentTimeMillis()) : null);
			myStmt.executeUpdate();
			
			try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) { 
	            	myStmt = myConn.prepareStatement("insert into funcionario_log_pacote_comercial(id_funcionario, id_pacote_comercial, data_registo, descricao) VALUES (?, ?, ?, ?)");

	        		myStmt.setInt(1, funcionario.getId());
	        		myStmt.setInt(2, (int)generatedKeys.getLong(1));
	        		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
	        		myStmt.setString(4, "Criar Pacote Comercial");
	        		myStmt.executeUpdate();	
		
	            }
	            else{
	                throw new SQLException("Criacao de Pacote Comercial falhou, nenhum ID foi devolvido.");
	            }
	            	
	            }
	            }catch (Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	// Este metodo serve apenas para editar nome e descriçao. Para Ativar/Desativar, usar os metodos correspondentes
	public void editarPacoteComercial(PacoteComercial pacote,Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("UPDATE pacote_comercial SET nome=?, descricao=?WHERE id=?");

			myState.setString(1, pacote.getNome());
			myState.setString(2, pacote.getDescricao());
			myState.setInt(3, pacote.getId());
			myState.executeUpdate();
			
			myState = logUpdate(pacote, funcionario, "Editar Cliente");	

			myState.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	public List<HistoricoPacoteComercial> getHistoricoPacoteComercial(int id_pacote) throws Exception {
		List<HistoricoPacoteComercial> list = new ArrayList<HistoricoPacoteComercial>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoPacoteComercial.id_funcionario, HistoricoPacoteComercial.id_pacote_comercial, HistoricoPacoteComercial.descricao, "
					+ "HistoricoPacoteComercial.data_registo, admin.nome "
					+ "FROM funcionario_log_pacote_comercial HistoricoPacoteComercial, funcionario admin WHERE HistoricoPacoteComercial.id_funcionario=admin.id AND HistoricoPacoteComercial.id_pacote_comercial=" + id_pacote;

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoPacoteComercial.id_funcionario");
				String descricao = myRs.getString("HistoricoPacoteComercial.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoPacoteComercial.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");


				HistoricoPacoteComercial historico = new HistoricoPacoteComercial(id_pacote, id_funcionario, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	private PacoteComercial pesquisaOperadorAuxiliarNIF(String id) throws Exception {
		PacoteComercial pacoteComercial = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			id += "%";
			myStmt = myConn.prepareStatement("select * from pacote_comercial where nif like ?");
			myStmt.setString(1, id);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				pacoteComercial = convertRowParaPacoteComercial(myRs);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return pacoteComercial;
	}
	
	private PreparedStatement logUpdate(PacoteComercial pacote,Funcionario funcionario, String descricao) throws SQLException {
		PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("insert into funcionario_log_pacote_comercial(id_funcionario, id_pacote_comercial, data_registo, descricao) VALUES (?, ?, ?, ?)");

		myStmt.setInt(1, funcionario.getId());
		myStmt.setInt(2, pacote.getId());
		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		myStmt.setString(4, descricao);
		return myStmt;
	}

	//primeiro ve se o pacote com o id inserido esta ativo, e só depois desativa e insere a data atual
	//no campo data_fim
	public void desativarPacoteComercial (int id,Funcionario funcionario) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("Select ativo From pacote_comercial Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtivo = true;;
			if(rs.next()) {
				estaAtivo = rs.getBoolean(1);
			}
			
			if(estaAtivo) {
				myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo = 0,"
						+ "data_fim = current_timestamp() WHERE id=?");
				myState.setInt(1, id);
				myState.executeUpdate();
				
				PacoteComercial pacoteComercial  = pesquisaOperadorAuxiliarID(id);
				myState = logUpdate(pacoteComercial, funcionario, "Desativar Operador");	

				myState.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myState.close();
		}
	}
	
	private PacoteComercial pesquisaOperadorAuxiliarID(int id) throws Exception {
		PacoteComercial pacoteComercial = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				pacoteComercial = convertRowParaPacoteComercial(myRs);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return pacoteComercial;
	}

	private PacoteComercial convertRowParaPacoteComercial(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");
		Date data_Inicio = myRs.getDate("data_inicio");
		Date data_Fim = myRs.getDate("data_fim");
		

		PacoteComercial pacoteComercial = new PacoteComercial(id, nome, descricao, ativo, data_Inicio, data_Fim);

		return pacoteComercial;
	}
	
	//primeiro ve se o pacote com o id inserido esta inativo, e só depois ativa e insere a data atual
	//no campo data_inicio e faz set a data_fim para null
	public void ativarPacoteComercial (int id) throws Exception {
		PreparedStatement myState = null; 

		try {
			myState = myConn.prepareStatement("Select ativo From pacote_comercial Where id =" + id + ";");
			ResultSet rs = myState.executeQuery();
			
			boolean estaAtivo = false;;
			if(rs.next()) {
				estaAtivo = rs.getBoolean(1);
			}
			
			if(!estaAtivo) {
				myState = myConn.prepareStatement("UPDATE pacote_comercial SET ativo = 1,"
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
	
	private PacoteComercial converteRowParaPacoteComercial(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		String descricao = myRs.getString("descricao");
		boolean ativo = myRs.getBoolean("ativo");

		java.sql.Date data_inicio = null;
		java.sql.Date data_fim = null;
		
		//datas podem ser nulas, é necessário testar nulidade
		if (myRs.getTimestamp("data_inicio") != null) {
			data_inicio = new java.sql.Date(myRs.getTimestamp("data_inicio").getTime());
		}
		
		if (myRs.getTimestamp("data_fim") != null) {
			data_fim = new java.sql.Date(myRs.getTimestamp("data_fim").getTime());
		}

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
