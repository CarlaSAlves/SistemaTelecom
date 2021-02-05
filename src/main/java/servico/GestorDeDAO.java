package servico;



import java.sql.SQLException;
import java.util.List;
import data_acess_object_dao.ClienteDAO;
import data_acess_object_dao.FuncionarioDAO;
import data_acess_object_dao.PacoteComercialDAO;
import data_acess_object_dao.PromocaoDAO;
import historico.cliente.HistoricoCliente;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

public class GestorDeDAO {


	private ClienteDAO clienteDAO;
	private FuncionarioDAO funcionarioDAO;
	private PacoteComercialDAO pacoteComercialDAO;
	private PromocaoDAO promocaoDAO;
	private static GestorDeDAO GestorDeDAOInstance = null;


	private GestorDeDAO() throws Exception {
		clienteDAO = new ClienteDAO();
		funcionarioDAO = new FuncionarioDAO();
		pacoteComercialDAO = new PacoteComercialDAO();
		promocaoDAO = new PromocaoDAO();
	}

	public static synchronized  GestorDeDAO getGestorDeDAO() throws Exception {
		if( GestorDeDAOInstance == null ) {
			GestorDeDAOInstance = new GestorDeDAO();
		}
		return GestorDeDAOInstance;    
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

	public List<Cliente> pesquisaCliente(String nif) throws Exception{
		return clienteDAO.pesquisaCliente(nif);
	}


	public List<HistoricoCliente> getHistoricoCliente(int id_cliente) throws Exception {
		return clienteDAO.getHistoricoCliente(id_cliente);
	}
	
	public void criarFuncionario(Funcionario funcionario) throws Exception {
		funcionarioDAO.criarFuncionario(funcionario);
	}

	public void editarFuncionario(Funcionario funcionario) throws Exception {
		funcionarioDAO.editarFuncionario(funcionario);
	}

	public void desativarFuncionario(int id) throws Exception {
		funcionarioDAO.desativarFuncionario(id);
	}

	public List<Funcionario> getAllFuncionarioAdmin() throws Exception{
		return funcionarioDAO. getAllFuncionarioAdmin();    
	}

	public List<Funcionario> getAllFuncionarioOperador() throws Exception {        
		return funcionarioDAO. getAllFuncionarioOperador();
	}

	public Funcionario pesquisaFuncionarioAdmin(String username) throws Exception{        
		return funcionarioDAO.pesquisaFuncionarioAdmin(username);
	}

	public List<Funcionario> pesquisaFuncionarioOperador(String nif) throws Exception{
		return funcionarioDAO.pesquisaFuncionarioOperador(nif);
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

	public List<PacoteComercial> pesquisaPacoteComercial(String id){
		return pesquisaPacoteComercial(id);
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

	public List<Promocao> pesquisaPromocao(String nome) throws Exception{
		return promocaoDAO.pesquisaPromocao(nome);
	}


}