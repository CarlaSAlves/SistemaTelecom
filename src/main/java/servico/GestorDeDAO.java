package servico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import data_acess_object_dao.ClienteDAO;
import data_acess_object_dao.FuncionarioDAO;
import data_acess_object_dao.PacoteClienteDAO;
import data_acess_object_dao.PacoteComercialDAO;
import data_acess_object_dao.PromocaoDAO;
import historicos.HistoricoCliente;
import historicos.HistoricoOperador;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

public class GestorDeDAO {

	private ClienteDAO clienteDAO;
	private FuncionarioDAO funcionarioDAO;
	private PacoteComercialDAO pacoteComercialDAO;
	private PromocaoDAO promocaoDAO;
	private PacoteClienteDAO pacoteClienteDAO;
	private static GestorDeDAO GestorDeDAOInstance = null;
	private Connection connection;

	private GestorDeDAO() throws Exception {
		this.startConnection();
		clienteDAO = new ClienteDAO(this.connection);
		funcionarioDAO = new FuncionarioDAO(this.connection);
		pacoteComercialDAO = new PacoteComercialDAO(this.connection);
		promocaoDAO = new PromocaoDAO(this.connection);
		pacoteClienteDAO = new PacoteClienteDAO(connection);
	}

	public static synchronized GestorDeDAO getGestorDeDAO() throws Exception {
		if( GestorDeDAOInstance == null ) {
			GestorDeDAOInstance = new GestorDeDAO();
		}
		return GestorDeDAOInstance;    
	}
	
	private void startConnection() throws FileNotFoundException, IOException, SQLException {
		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		this.connection = DriverManager.getConnection(dburl, user, password);
	}

	public void criarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
		clienteDAO.criarCliente(cliente, funcionario);
	}

	public void editarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
		clienteDAO.editarCliente(cliente, funcionario);
	}

	public void desativarCliente(int id, Funcionario funcionario) throws SQLException {
		clienteDAO.desativarCliente(id, funcionario);;
	}

	public List<Cliente> getAllClientes() throws Exception{
		return clienteDAO.getAllClientes();

	}

	public List<Cliente> pesquisaCliente(int id, String nif, String nome, String morada, int ativo) throws Exception{
		return clienteDAO.pesquisaCliente(id, nif, nome, morada, ativo);
	}


	public List<HistoricoCliente> getHistoricoCliente(int id_cliente) throws Exception {
		return clienteDAO.getHistoricoCliente(id_cliente);
	}
	
	public void criarFuncionario(Funcionario funcionario, Funcionario admin) throws Exception {
		funcionarioDAO.criarFuncionario(funcionario, admin);
	}

	public void editarFuncionario(Funcionario funcionario, Funcionario admin) throws Exception {
		funcionarioDAO.editarFuncionario(funcionario, admin);
	}

	public void desativarFuncionario(int id, Funcionario admin) throws Exception {
		funcionarioDAO.desativarFuncionario(id, admin);
	}
	
	public Funcionario pesquisaFuncionarioAdmin(String login) throws Exception {
		return this.funcionarioDAO.pesquisaFuncionarioAdmin(login);
		
	}
	
	public List<HistoricoOperador> getHistoricoOperador(int id_operador) throws Exception {
		return funcionarioDAO.getHistoricoOperador(id_operador);
		
	}
	public List<Funcionario> getAllFuncionarioAdmin() throws Exception{
		return funcionarioDAO. getAllFuncionarioAdmin();    
	}

	public List<Funcionario> getAllFuncionarioOperador() throws Exception {        
		return funcionarioDAO. getAllFuncionarioOperador();
	}

	//se o login falhar, olhem aqui
	public Funcionario pesquisarFuncionarioLoginPass(String username, String password) throws Exception{        
		return funcionarioDAO.pesquisarFuncionarioLoginPass(username, password);
	}

	public List<Funcionario> pesquisaFuncionarioOperador(int id ,String nif, String nome, int ativo) throws Exception{
		return funcionarioDAO.pesquisaFuncionarioOperador(id, nif, nome, ativo);
	}

	//metodo que vai ver se o cliente nao possui um pacote cliente e , caso nao tenha, vai criar um pacote_cliente novo e
	//atribui-lo ao mesmo cliente. Deste modo assegura-se que nao ha pacotes sem clientes e que cada cliente tem apenas um pacote (e que cada pacote tem um cliente so)
	//VEJAM QUAL � A MELHOR MANEIRA DE CRIAR E ATRIBUIR UM PACOTE, SABENDO QUE UM PACOTE SO PODE TER UM CLIENTE, E VICE-VERSA, E NAO PODEM HAVER PACOTES SEM CLIENTES
	public void criarEAtribuirPacoteCliente(Cliente cliente, PacoteCliente pacoteCliente) throws SQLException, Exception {
		if (cliente.getId_pacote_cliente() > 0) {
			throw new RuntimeException("Cliente com id " + cliente.getId() + " j� tem um pacote cliente.");
		}
		
		clienteDAO.atribuirPacoteCliente(pacoteClienteDAO.criarPacoteCliente(pacoteCliente), cliente);
	}
	
	public void criarPacoteComercial(PacoteComercial pacoteComercial) throws Exception {
		pacoteComercialDAO.criarPacoteComercial(pacoteComercial);
	}

	public void editarPacoteComercial(PacoteComercial pacoteComercial) throws Exception {
		pacoteComercialDAO.editarPacoteComercial(pacoteComercial);
	}


	public void desativarPacoteComercial(int i) throws Exception {
		pacoteComercialDAO.desativarPacoteComercial(i);
	}

	public List<PacoteComercial> getAllPacotesComerciais() throws Exception{
		return pacoteComercialDAO.getAllPacotesComerciais();    
	}

	public List<PacoteComercial> pesquisaPacoteComercial(int id, String nome, int ativo)  throws Exception{
		return pacoteComercialDAO.pesquisaPacoteComercial(id, nome, ativo);
	}

	public void criarPromocao(Promocao promocao) throws Exception {
		promocaoDAO.criarPromocao(promocao);
	}

	public void editarPromocao(Promocao promocao) throws Exception {
		promocaoDAO.editarPromocao(promocao);
	}

	public void desativarPromocao(int id) throws Exception {
		promocaoDAO.desativarPromocao(id);
	}

	public List<Promocao> getAllPromocoes() throws Exception {
		return promocaoDAO.getAllPromocoes();
	}

	public List<Promocao> pesquisaPromocao(int id, String nome, int ativo) throws Exception{
		return promocaoDAO.pesquisaPromocao(id, nome, ativo);
	}


}