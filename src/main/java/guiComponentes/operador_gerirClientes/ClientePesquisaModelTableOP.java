package guiComponentes.operador_gerirClientes;


import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Cliente;

public class ClientePesquisaModelTableOP extends AbstractTableModel {
	
	/**
	 * Esta classe configura a tabela de pesquisa, onde se mostra os clientes.
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NIF_COL = 1;
	private static final int NOME_COL = 2;
	private static final int MORADA_COL = 3;
	private static final int LOGIN_COL = 4;
	private static final int ATIVO_COL = 5;
	private static final int PACOTE_COL = 6;


	private String[] nomesColunas = { "ID","NIF", "Nome", "Morada",
			"Login", "Ativo", "Pacote Comercial"};

	private List<Cliente> clientes;
	
	/**
	 * Criação das linhas e colunas da tabela, consoante o tamanho 
	 * da lista de clientes dando-lhe o nome da coluna correspondente.
	 * @param osClientes
	 */

	public ClientePesquisaModelTableOP(List<Cliente> osClientes) {
		clientes = osClientes;
	}

	@Override
	public int getColumnCount() {
		return nomesColunas.length;
	}

	@Override
	public int getRowCount() {
		return clientes.size();
	}

	@Override
	public String getColumnName(int col) {
		return nomesColunas[col];
	}
	/**
	 * Dependendo das constantes, vai buscar os valores correspondentes ao cliente.
	 */

	@Override
	public Object getValueAt(int row, int col) {

		Cliente cliente = clientes.get(row);

		switch (col) {
		case ID_COL:
			return cliente.getId();
		case NIF_COL:
			return cliente.getNif();
		case NOME_COL:
			return cliente.getNome();
		case MORADA_COL:
			return cliente.getMorada();
		case LOGIN_COL:
			return cliente.getLogin();
		case ATIVO_COL:
			return cliente.isAtivo();
		case PACOTE_COL:
			if(cliente.getId_pacote_cliente() == 0) {
				return "Não Atribuido";
			} else {
				return "" + cliente.getId_pacote_cliente();
			}
			
		case OBJECT_COL:
			return cliente;
		default:
			return cliente.getId();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();


	}

}
