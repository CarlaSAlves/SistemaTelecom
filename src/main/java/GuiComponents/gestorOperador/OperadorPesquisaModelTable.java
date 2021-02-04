package GuiComponents.gestorOperador;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

public class OperadorPesquisaModelTable extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NOME_COL = 1;
	private static final int NIF_COL = 2;
	private static final int LOGIN_COL = 3;
	private static final int PASSWORD_COL = 4;
	private static final int ATIVO_COL = 5;
	
	private String[] nomesColunas = { "ID", "Nome", "NIF", "Login", "Password", "Ativo" };
	
	private List<Funcionario> funcionarios;
	
	public OperadorPesquisaModelTable(List<Funcionario> operadores) {
		funcionarios = operadores;
	}

	@Override
	public int getColumnCount() {
		return nomesColunas.length;
		
	}

	@Override
	public int getRowCount() {
		return funcionarios.size();
	}

	@Override
	public String getColumnName(int col) {
		return nomesColunas[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Funcionario funcionario = funcionarios.get(row);

		switch (col) {
		case ID_COL:
			return funcionario.getId();
		case NOME_COL:
			return funcionario.getNome();
		case NIF_COL:
			return funcionario.getNif();
		case LOGIN_COL:
			return funcionario.getLogin();
		case PASSWORD_COL:
			return funcionario.getPassword();
		case ATIVO_COL:
			return funcionario.isAtivo();
		case OBJECT_COL:
			return funcionario;
		default:
			return funcionario.getId();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
		

}

}
