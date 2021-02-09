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

import data_acess_object_dao.PasswordEncryption;

import historicos.HistoricoOperador;
import standard_value_object.Funcionario;
import standard_value_object.Role;

public class FuncionarioDAO {

	private Connection myConn;
	private Funcionario funcionario = new Funcionario();

	public FuncionarioDAO(Connection connection) throws FileNotFoundException, IOException, SQLException {
		this.myConn = connection;
	}

	public List<Funcionario> getAllFuncionarioAdmin() throws Exception {
		List<Funcionario> listaFuncionarioAdmin = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario where id_role=1");
			
			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionarioAdmin.add(funcionario);
			}

			return listaFuncionarioAdmin;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	private Funcionario pesquisaOperadorAuxiliarID(int id) throws Exception {
		Funcionario funcionario = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				funcionario = convertRowParaFuncionario(myRs);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return funcionario;
	}

	private Funcionario pesquisaOperadorAuxiliarNIF(String nif) throws Exception {
		Funcionario funcionario = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";
			myStmt = myConn.prepareStatement("select * from funcionario where nif like ?");
			myStmt.setString(1, nif);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				funcionario = convertRowParaFuncionario(myRs);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return funcionario;
	}

	public List<Funcionario> getAllFuncionarioOperador() throws Exception {
		List<Funcionario> listaFuncionarioOperador = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery("select * from funcionario where id_role=2");

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionarioOperador.add(funcionario);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return listaFuncionarioOperador;
	}

	//substituir pelo metodo pesquisarFuncionarioLoginPass
	public Funcionario pesquisaFuncionarioAdmin(String login) throws Exception {
		Funcionario funcionario = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			myStmt = myConn.prepareStatement("select * from funcionario where login=? AND id_role=1");

			myStmt.setString(1, login);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				funcionario = convertRowParaFuncionario(myRs);
			}
			return funcionario;
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public List<Funcionario> pesquisaFuncionarioOperador(int id ,String nif, String nome, int ativo) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		StringJoiner sj = new StringJoiner (" AND ");
		String query = "SELECT * FROM FUNCIONARIO WHERE ";

		try {
			@SuppressWarnings("rawtypes")
			List<Comparable> values = new ArrayList<Comparable>();

			if(id!= 0){
				sj.add("ID=?");
				values.add(id);
			}
			if(nif != null) {
				nif += "%";
				sj.add("NIF LIKE ?");
				values.add(nif);
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
			query+= " AND id_role=2";

			myStmt = myConn.prepareStatement(query);

			for (int index = 0; index < values.size(); index++){
				myStmt.setObject(index+1 , values.get(index));
			}

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	public List<Funcionario> pesquisaTodosFuncionarios() throws Exception {
		List<Funcionario> listaFuncionario = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from funcionario");

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				listaFuncionario.add(funcionario);
			}
		
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return listaFuncionario;
	}
	
	public List<Funcionario> pesquisaFuncionarioByNif(String nif) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from funcionario where nif like ?");
			myStmt.setString(1, nif);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	public List<Funcionario> pesquisaFuncionarioByNome(String nome) throws Exception {
		List<Funcionario> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nome += "%";
			myStmt = myConn.prepareStatement("select * from funcionario where nome like ?");
			myStmt.setString(1, nome);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Funcionario funcionario = convertRowParaFuncionario(myRs);
				list.add(funcionario);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return list;
	}
	
	public Funcionario pesquisaFuncionarioById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		Funcionario funcionario = null;

		try {
			myStmt = myConn.prepareStatement("select * from funcionario where id=?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				funcionario = new Funcionario();
				funcionario.setId(myRs.getInt(1));
				funcionario.setNome(myRs.getString(2));
				funcionario.setNif(myRs.getInt(3));
				funcionario.setLogin(myRs.getString(4));
				funcionario.setPassword(myRs.getString(5));
				funcionario.setAtivo(myRs.getInt(6) == 1 ? true : false);
				funcionario.setId_role(myRs.getInt(7));	
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return funcionario;
	}
	
	public Funcionario pesquisarFuncionarioLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Funcionario funcionario = null;
		
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM funcionario WHERE login=? AND password=?;");
			myStmt.setString(1, login);
			
			//vamos encriptar a palavra pass antes de a enviar
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				funcionario = new Funcionario();
				funcionario.setId(myRs.getInt(1));
				funcionario.setNome(myRs.getString(2));
				funcionario.setNif(myRs.getInt(3));
				funcionario.setLogin(myRs.getString(4));
				funcionario.setPassword(myRs.getString(5));
				funcionario.setAtivo(myRs.getInt(6) == 1 ? true : false);
				funcionario.setId_role(myRs.getInt(7));	
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return funcionario;
	}

	@SuppressWarnings("resource")
	public void criarFuncionario(Funcionario operador, Funcionario admin) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO funcionario(nome, nif, login, password, ativo, id_role) "
					+ "VALUES(?,?,?,?,?,?)");
			
			myStmt.setString(1, funcionario.getNome());
			myStmt.setLong(2, funcionario.getNif());
			myStmt.setString(3, funcionario.getLogin());
			myStmt.setString(4, PasswordEncryption.get_SHA_512_SecurePassword(funcionario.getPassword()));
			myStmt.setBoolean(5, funcionario.isAtivo());
			myStmt.setInt(6, funcionario.getId_role());

			myStmt.setString(1, operador.getNome());
			myStmt.setLong(2, operador.getNif());
			myStmt.setString(3, operador.getLogin());
			myStmt.setString(4, operador.getPassword());
			myStmt.setBoolean(5, operador.isAtivo());
			myStmt.setInt(6, operador.getId_role());

			myStmt.executeUpdate();

			Funcionario operadorCriado = pesquisaOperadorAuxiliarNIF(""+operador.getNif());
			myStmt = logUpdate(operadorCriado, admin, "Criar Operador");	

			myStmt.executeUpdate();	

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	@SuppressWarnings("resource")
	public void editarFuncionario(Funcionario operador, Funcionario admin) throws Exception {
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `nome`=?, `nif`=?, "
					+ "`login`=?, `password`=?, `ativo`=?, `id_role`=? WHERE  `id`=?");

			myStmt.setString(1, funcionario .getNome());
			myStmt.setLong(2, funcionario.getNif());
			myStmt.setString(3, funcionario.getLogin());
			myStmt.setString(4, PasswordEncryption.get_SHA_512_SecurePassword(funcionario.getPassword()));
			myStmt.setBoolean(5, funcionario.isAtivo());
			myStmt.setInt(6, funcionario.getId_role());
			myStmt.setInt(7, funcionario.getId());

			myStmt.setString(1, operador.getNome());
			myStmt.setLong(2, operador.getNif());
			myStmt.setString(3, operador.getLogin());
			myStmt.setString(4, operador.getPassword());
			myStmt.setBoolean(5, operador.isAtivo());
			myStmt.setInt(6, operador.getId_role());
			myStmt.setInt(7, operador.getId());

			myStmt.executeUpdate();

			myStmt = logUpdate(operador, admin, "Editar Operador");	

			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}
	
	public void atribuirRoleAFuncionario(Role role, Funcionario funcionario) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
			myStmt = myConn.prepareStatement("UPDATE `funcionario` SET `id_role`=? WHERE `id`=?");
			myStmt.setInt(1, role.getId());
			myStmt.setInt(2, funcionario.getId());
			myStmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			myStmt.close();
		}
	}


	@SuppressWarnings("resource")
	public void desativarFuncionario(int id, Funcionario admin) throws SQLException{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("update funcionario SET `ativo`= 0 where id=?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();


			Funcionario operador = pesquisaOperadorAuxiliarID(id);
			myStmt = logUpdate(operador, admin, "Desativar Operador");	

			myStmt.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			myStmt.close();
		}
	}

	public List<HistoricoOperador> getHistoricoOperador(int id_operador) throws Exception {
		List<HistoricoOperador> list = new ArrayList<HistoricoOperador>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			String sql = "SELECT HistoricoOperador.id_funcionario, HistoricoOperador.id_operador, HistoricoOperador.descricao, "
					+ "HistoricoOperador.data_registo, admin.nome "
					+ "FROM funcionario_log_operador HistoricoOperador, funcionario admin WHERE HistoricoOperador.id_funcionario=admin.id AND HistoricoOperador.id_operador=" + id_operador;

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int id_funcionario = myRs.getInt("HistoricoOperador.id_funcionario");
				String descricao = myRs.getString("HistoricoOperador.descricao");
				Timestamp timestamp = myRs.getTimestamp("HistoricoOperador.data_registo");
				java.sql.Date data_registo = new java.sql.Date(timestamp.getTime());
				String nome = myRs.getString("admin.nome");


				HistoricoOperador historico = new HistoricoOperador(id_operador, id_funcionario, descricao, data_registo, nome);

				list.add(historico);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}

	private Funcionario convertRowParaFuncionario(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String nome = myRs.getString("nome");
		long nif = myRs.getInt("nif");
		String login = myRs.getString("login");
		String password = myRs.getString("password");
		boolean ativo = myRs.getBoolean("ativo");
		int id_role = myRs.getInt("id_role");

		Funcionario funcionario = new Funcionario(id, nome, nif, login, password, ativo, id_role);

		return funcionario;
	}

	private PreparedStatement logUpdate(Funcionario operador, Funcionario admin, String descricao) throws SQLException {
		PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("insert into funcionario_log_operador(id_funcionario, id_operador, data_registo, descricao) VALUES (?, ?, ?, ?)");

		myStmt.setInt(1, admin.getId());
		myStmt.setInt(2, operador.getId());
		myStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		myStmt.setString(4, descricao);
		return myStmt;
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



