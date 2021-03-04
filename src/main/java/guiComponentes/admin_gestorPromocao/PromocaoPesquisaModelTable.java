package guiComponentes.admin_gestorPromocao;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.Promocao;

/**
 * Esta classe configura a tabela de pesquisa, onde se mostra as promoções.
 * 
 */

public class PromocaoPesquisaModelTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NOME_COL = 1;
	private static final int DESCRICAO_COL = 2;	
	private static final int ATIVO_COL = 3;
	public static final int DATA_INICIO_COL = 4;
	public static final int DATA_FIM_COL = 5;

	private String[] nomesColunas = { "ID", "Nome", "Descricao", "Ativo", "Data de Inicio", "Data de Fim"};

	private List<Promocao> promocoes;

	/**
	* Creation of the rows and columns of the table depending on its size
	 * from the list of clients giving them the name of the correspondent column
	 * @param pacotesComerciais
	 */
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

	/**
		  * Depending on the constant, it will get the values for each client
	 */
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
		case DATA_INICIO_COL:
			return promocao.getData_inicio();
		case DATA_FIM_COL:
			if(promocao.getData_fim()==null) {
				return "Nao Atribuido";
			}else {
				return "" + promocao.getData_fim();		
			}
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
