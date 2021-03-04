package guiComponentes.admin_gestorPromocao;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import historicos.HistoricoOperador;
import historicos.HistoricoPromocoes;


public class HistoricoPromocaoTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	public static final int DATA_COL = 0;
	private static final int REGISTO_COL = 1;
	private static final int FUNCIONARIO_COL = 2;
	private static final int ID_COL = 3;


	private String[] columnNames = { "Data", "Registo", "Funcionario", "ID Admin" };

	private List<HistoricoPromocoes> historicoLista;

	/**
	  * Creates the rows and columns depending on the size of the result set
	 * and names each column accordingly
	 * @param historicoLista
	 */
	public HistoricoPromocaoTableModel(List<HistoricoPromocoes> historicoLista) {
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

	/**
	 * depends on the constants,  retrieves the data corresponding the the registries
	 */
	@Override
	public Object getValueAt(int row, int col) {

		HistoricoPromocoes historicoPromocoes = historicoLista.get(row);

		switch (col) {
		case DATA_COL:			
			return historicoPromocoes.getData_registo();
		case REGISTO_COL:
			return historicoPromocoes.getDescricao();
		case FUNCIONARIO_COL:
			return historicoPromocoes.getNome_funcionario();
		case ID_COL:
			return historicoPromocoes.getId_funcionario();
		case OBJECT_COL:
			return historicoPromocoes;
		default:
			return historicoPromocoes.getNome_funcionario();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
