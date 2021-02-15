package guiComponentes.admin_gestorPacoteComercial;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import historicos.HistoricoPacoteComercial;


public class HistoricoPacoteComercialTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	public static final int DATA_COL = 0;
	private static final int REGISTO_COL = 1;
	private static final int FUNCIONARIO_COL = 2;
	private static final int ID_COL = 3;


	private String[] columnNames = { "Data", "Registo", "Funcionario", "ID Admin" };

	private List<HistoricoPacoteComercial> historicoLista;

	public HistoricoPacoteComercialTableModel(List<HistoricoPacoteComercial> historicoLista) {
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

		HistoricoPacoteComercial historicoPacoteComercial = historicoLista.get(row);

		switch (col) {
		case DATA_COL:			
			return historicoPacoteComercial.getData_registo();
		case REGISTO_COL:
			return historicoPacoteComercial.getDescricao();
		case FUNCIONARIO_COL:
			return historicoPacoteComercial.getNome_funcionario();
		case ID_COL:
			return historicoPacoteComercial.getId_funcionario();
		case OBJECT_COL:
			return historicoPacoteComercial;
		default:
			return historicoPacoteComercial.getNome_funcionario();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
