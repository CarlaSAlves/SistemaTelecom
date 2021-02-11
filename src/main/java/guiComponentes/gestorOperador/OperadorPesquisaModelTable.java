package guiComponentes.gestorOperador;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Funcionario;

public class OperadorPesquisaModelTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NIF_COL = 1;
	private static final int NOME_COL = 2;
	private static final int LOGIN_COL = 3;
	private static final int ATIVO_COL = 4;
	
	private String[] nomesColunas = { "ID", "NIF", "Nome", "Login", "Ativo" };
	
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
		case ATIVO_COL:
			return funcionario.isAtivo();
		case OBJECT_COL:
			return funcionario;
		default:
			return funcionario.getId();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
		

}

}
