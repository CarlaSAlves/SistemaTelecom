package guiComponentes.operador_gerirClientes;


import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Cliente;

public class ClientePesquisaModelTableOP extends AbstractTableModel {
	
	/**
	 * This class sets up the tabela de Pesquisa, where the clientes are shown.
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
	 * Creating the rows and columns of the table, depending on the size 
	 * of the Clientes List, by naming the corresponding column.
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
	 * Depending on the constants, it fetches the corresponding values from the cliente.
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
