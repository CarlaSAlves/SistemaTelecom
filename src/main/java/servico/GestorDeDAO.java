package servico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import data_acess_object_dao.ClienteDAO;
import data_acess_object_dao.FuncionarioDAO;
import data_acess_object_dao.PacoteClienteDAO;
import data_acess_object_dao.PacoteClientePromocaoDAO;
import data_acess_object_dao.PacoteComercialDAO;
import data_acess_object_dao.PromocaoDAO;
import historicos.HistoricoCliente;
import historicos.HistoricoOperador;
import historicos.HistoricoPacoteComercial;
import historicos.HistoricoPromocoes;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;
import standard_value_object.PacoteClientePromocao;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;


/*
 * Class responsible for grouping and initializing every Data Access Object used to interact with the database.
 * This class will receive calls for the methods inside each DAO and delegate them accordingly (DAO methods are never called directly).
 * 
 * The database url and credential values are stored in the "sistema_tele.properties" text file in the root folder of the project.
 * This class is a singleton, meaning only one instance can exist at any given time.
 */
public class GestorDeDAO {

	private ClienteDAO clienteDAO;
	private FuncionarioDAO funcionarioDAO;
	private PacoteComercialDAO pacoteComercialDAO;
	private PromocaoDAO promocaoDAO;
	private PacoteClienteDAO pacoteClienteDAO;
	private PacoteClientePromocaoDAO pacoteClientePromocaoDAO;
	private static GestorDeDAO GestorDeDAOInstance = null;
	private Connection connection;

	/*
	 * Constructor which will provide a java.sql.Connection object to every DAO. 
	 */
	private GestorDeDAO() throws Exception {
		this.startConnection();
		clienteDAO = new ClienteDAO(this.connection);
		funcionarioDAO = new FuncionarioDAO(this.connection);
		pacoteComercialDAO = new PacoteComercialDAO(this.connection);
		promocaoDAO = new PromocaoDAO(this.connection);
		pacoteClienteDAO = new PacoteClienteDAO(connection);
		pacoteClientePromocaoDAO = new PacoteClientePromocaoDAO(connection);
	}

	/*
	 * Method to access the instance of GestorDeDAO if it's already created, or to instance one if it isn't. 
	 * Since the constructor is private, this is the only way to access an instance of GestorDeDAO.
	 */
	public static synchronized GestorDeDAO getGestorDeDAO() throws Exception {
		if( GestorDeDAOInstance == null ) {
			GestorDeDAOInstance = new GestorDeDAO();
		}
		return GestorDeDAOInstance;    
	}

	/*
	 * Method which will establish a connection with the database by instancing a java.sql.Connection object and assigning it to the this.connection attribute.
	 * The database url and credentials are stored in the "sistema_tele.propeties" file in the root folder of the project.
	 */
	private void startConnection() throws FileNotFoundException, IOException, SQLException {
		Properties props = new Properties();
		props.load(new FileInputStream("sistema_tele.properties"));
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
//		String user = "root";
//		String password = "";
//		String dburl = "jdbc:mysql://localhost:3306/sistema_tele";

		this.connection = DriverManager.getConnection(dburl, user, password);
	}

	public void criarCliente(Cliente cliente, Funcionario funcionario) throws Exception {
		clienteDAO.criarCliente(cliente, funcionario);
	}


	public void editarCliente(Cliente cliente, Funcionario funcionario, String novaPass) throws Exception {
		clienteDAO.editarCliente(cliente, funcionario, novaPass);
	}

	public void desativarCliente(int id, Funcionario funcionario) throws SQLException {
		clienteDAO.desativarCliente(id, funcionario);;
	}

	public void ativarCliente(int id, Funcionario funcionario ) throws SQLException{
		clienteDAO.ativarCliente(id, funcionario);
	}

	public List<Cliente> getAllClientes() throws Exception{
		return clienteDAO.getAllClientes();

	}

	public List<Cliente> pesquisaCliente(int id, String nif, String nome, String morada, int ativo) throws Exception{
		return clienteDAO.pesquisaCliente(id, nif, nome, morada, ativo);
	}

	//metodo para fazer login como cliente
	public Cliente pesquisaClienteLoginPass(String login, String password) throws Exception {
		return clienteDAO.pesquisaClienteLoginPass(login, password);
	}
	
	public Cliente pesquisaClienteLogin(String login) throws Exception  {
		return clienteDAO.pesquisaClienteLogin(login);
		
	}
	

	public List<HistoricoCliente> getHistoricoCliente(int id_cliente) throws Exception {
		return clienteDAO.getHistoricoCliente(id_cliente);
	}
	
	public Cliente editarClienteDadosBasicos(Cliente cliente) throws Exception {
		return clienteDAO.editarClienteDadosBasicos(cliente);
	}
	
	public Cliente editarClienteDadosLoginEPassword(Cliente cliente, String password) throws Exception {
		return clienteDAO.editarClienteDadosLoginEPassword(cliente, password);
	}

	public List<HistoricoPacoteComercial> getHistoricoPacoteComercial(int id_pacote) throws Exception {
		return pacoteComercialDAO.getHistoricoPacoteComercial(id_pacote);
	}
	
	public List<HistoricoPromocoes> getHistoricoPromocao(int id_promocao) throws Exception {
		return promocaoDAO.getHistoricoPromocao(id_promocao);
	}
	
	public void criarFuncionario(Funcionario funcionario, Funcionario admin) throws Exception {
		funcionarioDAO.criarFuncionario(funcionario, admin);
	}

	public void editarFuncionario(Funcionario funcionario, Funcionario admin, String novaPass) throws Exception {
		funcionarioDAO.editarFuncionario(funcionario, admin, novaPass);
	}

	public void desativarFuncionario(int id, Funcionario admin) throws Exception {
		funcionarioDAO.desativarFuncionario(id, admin);
	}

	public void ativarFuncionario(int id, Funcionario admin) throws SQLException{
		funcionarioDAO.ativarFuncionario(id, admin);
	}

	public Funcionario pesquisaFuncionarioLogin(String login) throws Exception {
		return funcionarioDAO.pesquisaFuncionarioLogin(login);

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
	//VEJAM QUAL é A MELHOR MANEIRA DE CRIAR E ATRIBUIR UM PACOTE, SABENDO QUE UM PACOTE SO PODE TER UM CLIENTE, E VICE-VERSA, E NAO PODEM HAVER PACOTES SEM CLIENTES
	//	public void criarEAtribuirPacoteCliente(Cliente cliente, PacoteCliente pacoteCliente) throws SQLException, Exception {
	//		if (cliente.getId_pacote_cliente() > 0) {
	//			throw new RuntimeException("Cliente com id " + cliente.getId() + " já tem um pacote cliente.");
	//		}
	//		
	//		clienteDAO.atribuirPacoteCliente(pacoteClienteDAO.criarPacoteCliente(pacoteCliente), cliente);
	//	}

	public void criarPacoteComercial(PacoteComercial pacoteComercial, Funcionario funcionario) throws Exception {
		pacoteComercialDAO.criarPacoteComercial(pacoteComercial,funcionario);
	}

	public void editarPacoteComercial(PacoteComercial pacoteComercial, Funcionario funcionario) throws Exception {
		pacoteComercialDAO.editarPacoteComercial(pacoteComercial,funcionario);
	}


	public void desativarPacoteComercial(int id, Funcionario funcionario) throws Exception {
		pacoteComercialDAO.desativarPacoteComercial(id,funcionario);
	}

	public void ativarPacoteComercial (int id, Funcionario funcionario) throws Exception {
		pacoteComercialDAO.ativarPacoteComercial(id, funcionario);
	}

	public List<PacoteComercial> getAllPacotesComerciais() throws Exception {
		return pacoteComercialDAO.getAllPacotesComerciais();    
	}

	public List<PacoteComercial> pesquisaPacoteComercial(int id, String nome, int ativo)  throws Exception{
		return pacoteComercialDAO.pesquisaPacoteComercial(id, nome, ativo);
	}

	public PacoteComercial convertRowToPacoteComercial(ResultSet myRs) throws SQLException {
		return pacoteComercialDAO.converteRowParaPacoteComercial(myRs);
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

	public void ativarPromocao (int id) throws Exception {
		promocaoDAO.ativarPromocao(id);
	}

	public List<Promocao> getAllPromocoes() throws Exception {
		return promocaoDAO.getAllPromocoes();
	}

	public List<Promocao> pesquisaPromocao(int id, String nome, int ativo) throws Exception{
		return promocaoDAO.pesquisaPromocao(id, nome, ativo);
	}

	public PacoteComercial getPacoteClienteInfo(int id) throws Exception {
		return pacoteClienteDAO.getPacoteClienteInfo(id);
	}
	
	public PacoteCliente pesquisarPacoteClienteId(int id) throws Exception {
		return pacoteClienteDAO.pesquisarPacoteClienteId(id);
	}

	public PacoteCliente criarPacoteCliente(PacoteCliente pacoteCliente, Cliente cliente, Funcionario funcionario) throws SQLException{
		return pacoteClienteDAO.criarPacoteCliente(pacoteCliente, cliente, funcionario);
	}

	public int eliminarPacoteById(int id, Funcionario funcionario, Cliente cliente) throws SQLException{
		return pacoteClienteDAO.eliminarPacoteById(id, funcionario, cliente);
	}
	
	public int removerPromocao(int id_pacote_cliente, int id_promocao, Funcionario funcionario, Cliente cliente) throws SQLException{
		return pacoteClienteDAO.removerPromocao(id_pacote_cliente, id_promocao, funcionario, cliente);
	}
	
	public PacoteClientePromocao criarPacoteClientePromocao(PacoteClientePromocao pacoteClientePromocao, Cliente cliente, Funcionario funcionario) throws SQLException{
		return pacoteClientePromocaoDAO.criarPacoteClientePromocao(pacoteClientePromocao, cliente, funcionario);
	}
	public List<Promocao> getPacoteClientePromocaoInfo(int id) throws Exception {
		return pacoteClientePromocaoDAO.getPacoteClientePromocaoInfo(id);
	}

	public List<PacoteClientePromocao> pesquisarTodosPacotesClientePromocao() throws Exception {
		return pacoteClientePromocaoDAO.pesquisarTodosPacotesClientePromocao();
	}


}