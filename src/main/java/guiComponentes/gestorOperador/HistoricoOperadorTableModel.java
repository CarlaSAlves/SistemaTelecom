package guiComponentes.gestorOperador;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import historicos.HistoricoOperador;


public class HistoricoOperadorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	public static final int DATA_COL = 0;
	private static final int REGISTO_COL = 1;
	private static final int FUNCIONARIO_COL = 2;
	private static final int ID_COL = 3;


	private String[] columnNames = { "Data", "Registo", "Funcionario", "ID Admin" };

	private List<HistoricoOperador> historicoLista;

	public HistoricoOperadorTableModel(List<HistoricoOperador> historicoLista) {
		this.historicoLista = historicoLista;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return historicoLista.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		HistoricoOperador historicoOperador = historicoLista.get(row);

		switch (col) {
		case DATA_COL:			
			return historicoOperador.getData_registo();
		case REGISTO_COL:
			return historicoOperador.getDescricao();
		case FUNCIONARIO_COL:
			return historicoOperador.getNome_funcionario();
		case ID_COL:
			return historicoOperador.getId_funcionario();
		case OBJECT_COL:
			return historicoOperador;
		default:
			return historicoOperador.getNome_funcionario();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
