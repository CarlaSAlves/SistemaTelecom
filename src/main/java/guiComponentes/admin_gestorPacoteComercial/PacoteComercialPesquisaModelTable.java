package guiComponentes.admin_gestorPacoteComercial;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import standard_value_object.PacoteComercial;

/**
 * Esta classe configura a tabela de pesquisa, onde se mostra os pacotes comerciais.
 * 
 */

public class PacoteComercialPesquisaModelTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NOME_COL = 1;
	private static final int DESCRICAO_COL = 2;	
	private static final int ATIVO_COL = 3;
	public static final int DATA_INICIO_COL = 4;
	public static final int DATA_FIM_COL = 5;


	private String[] nomesColunas = { "ID", "Nome", "Descricao", "Ativo","Data de Inicio", "Data de Fim" };

	private List<PacoteComercial> pacotesComerciais;

	/**
	 * Criação das linhas e colunas da tabela, consoante o tamanho 
	 * da lista de pacotes comerciais dando-lhe o nome da coluna correspondente.
	 * @param pacotesComerciais
	 */
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

	/**
	 * Dependendo das constantes, vai buscar os valores correspondentes ao pacote comercial.
	 */
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
		case DATA_INICIO_COL:
			return pacoteComercial.getData_inicio();
		case DATA_FIM_COL:
			if(pacoteComercial.getData_fim()==null) {
				return "Nao Atribuido";
			}else {
				return "" + pacoteComercial.getData_fim();		
			}
		case OBJECT_COL:
			return pacoteComercial;
		default:
			return pacoteComercial.getId();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();


	}

}
