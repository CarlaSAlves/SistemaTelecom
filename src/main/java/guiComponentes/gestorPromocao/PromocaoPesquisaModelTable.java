package guiComponentes.gestorPromocao;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Promocao;

public class PromocaoPesquisaModelTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NOME_COL = 1;
	private static final int DESCRICAO_COL = 2;	
	private static final int ATIVO_COL = 3;


	
	private String[] nomesColunas = { "ID", "Nome", "Descricao", "Ativo" };
	
	private List<Promocao> promocoes;

	public PromocaoPesquisaModelTable(List<Promocao> promocoes) {
		this.promocoes = promocoes;
	}

	@Override
	public int getColumnCount() {
		return nomesColunas.length;
		
	}

	@Override
	public int getRowCount() {
		return promocoes.size();
	}

	@Override
	public String getColumnName(int col) {
		return nomesColunas[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Promocao promocao = promocoes.get(row);

		switch (col) {
		case ID_COL:
			return promocao.getId();
		case NOME_COL:
			return promocao.getNome();
		case DESCRICAO_COL:
			return promocao.getDescricao();
		case ATIVO_COL:
			return promocao.isAtiva();
		case OBJECT_COL:
			return promocao;
		default:
			return promocao.getId();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
		

}

}
