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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import standard_value_object_v2.Cliente;
import standard_value_object_v2.PacoteCliente;

public class ClienteDAO {

	private Connection myConn;

	public ClienteDAO() throws FileNotFoundException, IOException, SQLException{

		Properties props = new Properties();
		props.load(new FileInputStream("sgot.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);
	}

	public List<Cliente> searchAllClientes() throws Exception {
		List<Cliente> listaClientes = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from cliente");

			while (myRs.next()) {
				Cliente cliente = convertRowToCliente(myRs);
				listaClientes.add(cliente);
			}

			return listaClientes;		
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public Cliente searchClienteById(int id) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Cliente cliente = null;
		
		try {
			myStmt = myConn.prepareStatement("select * from cliente where id=?");

			myStmt.setInt(1, id);

			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				cliente = new Cliente();
				
				cliente.setId(myRs.getInt(1));
				cliente.setNome(myRs.getString(2));
				cliente.setNif(myRs.getInt(3));
				cliente.setMorada(myRs.getString(4));
				cliente.setLogin(myRs.getString(5));
				cliente.setPassword(myRs.getString(6));
				cliente.setAtivo(myRs.getBoolean(7));
				cliente.setId_pacote_cliente(myRs.getInt(8));
			}

			return cliente;
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public List<Cliente> searchClienteByNif(String nif) throws Exception {
		List<Cliente> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nif += "%";

			myStmt = myConn.prepareStatement("select * from cliente where nif like ?");
			myStmt.setString(1, nif);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Cliente Cliente = convertRowToCliente(myRs);
				list.add(Cliente);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Cliente searchClienteByLoginPass(String login, String pass) throws Exception {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Cliente cliente = null;
		
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM cliente WHERE login=? AND password=?;");
			myStmt.setString(1, login);
			
			//vamos encriptar a palavra pass antes de a enviar
			myStmt.setString(2, PasswordEncryption.get_SHA_512_SecurePassword(pass));
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				cliente = new Cliente();
				
				cliente.setId(myRs.getInt(1));
				cliente.setNome(myRs.getString(2));
				cliente.setNif(myRs.getInt(3));
				cliente.setMorada(myRs.getString(4));
				cliente.setLogin(myRs.getString(5));
				cliente.setPassword(myRs.getString(6));
				cliente.setAtivo(myRs.getInt(7) == 1 ? true : false);
				cliente.setId_pacote_cliente(myRs.getInt(8));
			}
			
		}
		finally {
			close(myStmt, myRs);
		}
		
		return cliente;
	}

	public void criarCliente(Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;
		
		try {
			myStmt = myConn.prepareStatement("insert into cliente(nome,nif,morada,login,password,ativo,id_pacote_cliente) "
					+ "values(?,?,?,?,?,?,?)");
			myStmt.setString(1, cliente.getNome());
			myStmt.setInt(2, cliente.getNif());
			myStmt.setString(3, cliente.getMorada());
			myStmt.setString(4, cliente.getLogin());
			myStmt.setString(5, cliente.getPassword());
			myStmt.setInt(6, '1');
			myStmt.setNull(7, java.sql.Types.INTEGER);
			myStmt.executeUpdate();
		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}
	
	/*Como um cliente so pode ter um unico pacote_cliente e um pacote_cliente so pode pertencer a um cliente,
	 * este metodo apenas cria um pacote_cliente novo caso o cliente em questao nao tenha nenhum.
	 * Para assegurar uma relaçao de um-para-um, este metodo trata de criar o pacote_cliente caso o cliente nao possua nenhum.
	 * Se o cliente ja possuir um pacote_cliente e for necessario modificar esse pacote, entao deve-se chamar
	 * os metodos respetivos do PacoteClienteDAO.
	 */
	//no SistemaService, vamos decompor a operaçao de atribuir um pacote em duas partes: primeira parte cria o pacote_cliente, segunda
	//parte atribui esse pacote ao cliente respetivo (que será feito por este metodo em baixo).
	public void atribuirPacoteCliente(PacoteCliente pacoteCliente, Cliente cliente) throws Exception {
		
		//verificaçao deve ser feita pelo serviço ?????
//		if (cliente.getId_pacote_cliente() > 0) {
//			throw new ClienteJaPossuiPacoteException("Cliente " + cliente.getId() + " já possui um pacote.");
//		}
		
		PreparedStatement myStmt = null;
		
		try {
			myStmt = myConn.prepareStatement("UPDATE `cliente` SET `id_pacote_cliente`=? WHERE `id`=?");
			myStmt.setInt(1, pacoteCliente.getId());
			myStmt.setInt(2, cliente.getId());
			myStmt.executeUpdate();
		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void editarcliente(Cliente cliente) throws Exception {
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("UPDATE cliente SET `id`=?, `nif`=?, `nome`=?, `morada`=?, `login`=?, `password`=?, `id_pacote_cliente`=? WHERE  `id`=?");

			myStmt.setInt(1, cliente.getId());
			myStmt.setInt(2, cliente.getNif());
			myStmt.setString(3, cliente.getNome());
			myStmt.setString(4, cliente.getMorada());
			myStmt.setString(5, cliente.getLogin());
			myStmt.setString(6, cliente.getPassword());
			myStmt.setInt(7, cliente.getId_pacote_cliente());
			myStmt.setInt(8, cliente.getId());
			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}
	}

	public void desativarCliente(int nif) throws SQLException{
		PreparedStatement myStmt = null;
		try {

			myStmt = myConn.prepareStatement("delete from cliente where nif=?");

			myStmt.setInt(1, nif);

			myStmt.executeUpdate();

		}catch(Exception e) {

		}finally {
			myStmt.close();
		}

	}
	private Cliente convertRowToCliente(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		Boolean ativo = myRs.getInt("ativo") == 1 ? true : false;
		int nif = myRs.getInt("nif");
		String nome = myRs.getString("nome");
		String morada = myRs.getString("morada");
		String login = myRs.getString("login");
		String password = myRs.getString("password");
		int id_pacote_comercial = myRs.getInt("id_pacote_cliente");

		Cliente cliente = new Cliente(id, nif, nome, morada, login, password, ativo,id_pacote_comercial);

		return cliente;
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
