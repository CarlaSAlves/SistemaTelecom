package Servico;

 

import java.sql.SQLException;
import java.util.List;
import data_acess_object_dao.ClienteDAO;
import data_acess_object_dao.FuncionarioDAO;
import data_acess_object_dao.PacoteComercialDAO;
import data_acess_object_dao.PromocaoDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

 

public class SistemaTeleServico {
    
    
    private ClienteDAO clienteDAO;
    private FuncionarioDAO funcionarioDAO;
    private PacoteComercialDAO pacoteComercialDAO;
    private PromocaoDAO promocaoDAO;
    
    
    // referência static para o objeto único
    // inicialmente o objeto não está criado
    private static SistemaTeleServico SistemaTeleServicoInstance = null;

 

    // construtor privado classe não pode ser instanciada fora dela
    private SistemaTeleServico() throws Exception {
        clienteDAO = new ClienteDAO();
        funcionarioDAO = new FuncionarioDAO();
        pacoteComercialDAO = new PacoteComercialDAO();
        promocaoDAO = new PromocaoDAO();
        
    }
    
    //com o synchronized o metodo não pode estar acessivel em duas treds ao mesmo tempo, pode ser usado ou não???
    public static synchronized  SistemaTeleServico getSistemaTeleServicoInstance() throws Exception {
        if( SistemaTeleServicoInstance == null ) {
            SistemaTeleServicoInstance = new SistemaTeleServico();
        }
        
            return SistemaTeleServicoInstance;    
    }
    
    
    // Cliente DAO
    public void criarCliente(Cliente cliente) throws Exception {
        clienteDAO.criarCliente(cliente);
    }
    
    public void editarCliente(Cliente cliente) throws Exception {
        clienteDAO.editarCliente(cliente);
    }
    
    public void desativarCliente(int id) throws SQLException {
        clienteDAO.desativarCliente(id);;
    }
    
    public List<Cliente> getAllClientes() throws Exception{
        return clienteDAO.getAllClientes();
        
        
    }
    
    public List<Cliente> pesquisaCliente(String nif) throws Exception{
        return clienteDAO.pesquisaCliente(nif);
        
    }
    
    // Funcionairio DAO
    public void criarFuncionario(Funcionario funcionario) throws Exception {
        funcionarioDAO.criarFuncionario(funcionario);
                
    }
    
    public void editarFuncionario(Funcionario funcionario) throws Exception {
        funcionarioDAO.editarFuncionario(funcionario);
    }
    
    public void desativarFuncionario(int nif) throws Exception {
        funcionarioDAO.desativarFuncionario(nif);
    }
    
    public List<Funcionario> getAllFuncionarioAdmin() throws Exception{
        return funcionarioDAO. getAllFuncionarioAdmin();    

 

    }
    
    public List<Funcionario> getAllFuncionarioOperador() throws Exception {        
        return funcionarioDAO. getAllFuncionarioOperador();
    }
    
    public List<Funcionario> pesquisaFuncionarioAdmin(String nif) throws Exception{        
        return funcionarioDAO.pesquisaFuncionarioAdmin(nif);
    }
    
    public List<Funcionario> pesquisaFuncionarioOperador(String nif) throws Exception{
        return funcionarioDAO.pesquisaFuncionarioOperador(nif);
    }
    //PacoteComercialDAO
    
    public void criarPacoteComercial(PacoteComercial pacoteComercial) throws Exception {
        pacoteComercialDAO.criarPacoteComercial(pacoteComercial);
    }
    
    public void editarPacoteComercial(PacoteComercial pacoteComercial) throws Exception {
        pacoteComercialDAO.editarPacoteComercial(pacoteComercial);
    }
    
    public void eliminarPacoteComercial(String nome) throws Exception {
        pacoteComercialDAO.eliminarPacoteComercial(nome);
    }
    
    public List<PacoteComercial> getAllPacotesComerciais() throws Exception{
        return pacoteComercialDAO.getAllPacotesComerciais();    
    }
    
    public List<PacoteComercial> pesquisaPacoteComercial(String nome){
        return pesquisaPacoteComercial(null);
    }
    
    //Promoções Dao
    public void criarPromocao(Promocao promocao) throws Exception {
        promocaoDAO.criarPromocao(promocao);
    }
    
    public void editarPromocao(Promocao promocao) throws Exception {
        promocaoDAO.editarPromocao(promocao);
    }
    
    public void eliminarPromocao(String nome) throws Exception {
        promocaoDAO.eliminarPromocao(nome);
    }
    
    public List<Promocao> getAllPromocoes() throws Exception {
        return promocaoDAO.getAllPromocoes();
    }
    
    public List<Promocao> pesquisaPromocao(String nome) throws Exception{
        return promocaoDAO.pesquisaPromocao(nome);
    }
        
    
}