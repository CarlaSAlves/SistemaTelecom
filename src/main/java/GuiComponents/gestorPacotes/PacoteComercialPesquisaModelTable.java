package GuiComponents.gestorPacotes;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;


@SuppressWarnings("serial")
public class PacoteComercialPesquisaModelTable extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NOME_COL = 1;
	
	private static final int DESCRICAO_COL = 2;	
	private static final int ATIVO_COL = 3;


	
	private String[] nomesColunas = { "ID", "Nome", "Descricao", "Ativo" };
	
	private List<PacoteComercial> pacotesComerciais;

	public PacoteComercialPesquisaModelTable(List<PacoteComercial> pacotesComerciais) {
		this.pacotesComerciais = pacotesComerciais;
	}

	@Override
	public int getColumnCount() {
		return nomesColunas.length;
		
	}

	@Override
	public int getRowCount() {
		return pacotesComerciais.size();
	}

	@Override
	public String getColumnName(int col) {
		return nomesColunas[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		PacoteComercial pacoteComercial = pacotesComerciais.get(row);

		switch (col) {
		case ID_COL:
			return pacoteComercial.getId();
		case NOME_COL:
			return pacoteComercial.getNome();
		case DESCRICAO_COL:
			return pacoteComercial.getDescricao();
		case ATIVO_COL:
			return pacoteComercial.isAtivo();
		case OBJECT_COL:
			return pacoteComercial;
		default:
			return pacoteComercial.getId();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
		

}

}
