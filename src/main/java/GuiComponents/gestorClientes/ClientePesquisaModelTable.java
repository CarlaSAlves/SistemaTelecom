package GuiComponents.gestorClientes;

import java.awt.Component;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import standard_value_object.Cliente;


@SuppressWarnings("serial")
public class ClientePesquisaModelTable extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NIF_COL = 1;
	private static final int NOME_COL = 2;
	private static final int MORADA_COL = 3;
	private static final int LOGIN_COL = 4;
	private static final int PASSWORD_COL = 5;
	private static final int ATIVO_COL = 6;
	private static final int ID_PACOTE_COMERCIAL_COL = 7;

	
	private String[] nomesColunas = { "ID","NIF", "Nome", "Morada",
			"Login", "Password", "Ativo", "Pacote Comercial ID" };
	
	private List<Cliente> clientes;

	public ClientePesquisaModelTable(List<Cliente> osClientes) {
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
		case PASSWORD_COL:
			return cliente.getPassword();
		case ATIVO_COL:
			return cliente.isAtivo();
		case ID_PACOTE_COMERCIAL_COL:
			return cliente.getId_pacote_cliente();
		case OBJECT_COL:
			return cliente;
		default:
			return cliente.getNif();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
		

}

}
