package historicos;

import java.sql.Date;

public class HistoricoPacoteComercial {
	
	
	private int id_funcionario;
	private int id_pacoteComercial;
	private String descricao;
	private Date data_registo;
	private String nome_funcionario;
	
	public HistoricoPacoteComercial(int id_pacoteComercial, int id_funcionario, String descricao, Date data_registo,
			String nome_funcionario) {
		super();
		this.id_pacoteComercial = id_pacoteComercial;
		this.id_funcionario = id_funcionario;
		this.descricao = descricao;
		this.data_registo = data_registo;
		this.nome_funcionario = nome_funcionario;
	}

	public int getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(int id_funcionario) {
		this.id_funcionario = id_funcionario;
	}

	public int getId_pacoteComercial() {
		return id_pacoteComercial;
	}

	public void setId_pacoteComercial(int id_pacoteComercial) {
		this.id_pacoteComercial = id_pacoteComercial;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData_registo() {
		return data_registo;
	}

	public void setData_registo(Date data_registo) {
		this.data_registo = data_registo;
	}

	public String getNome_funcionario() {
		return nome_funcionario;
	}

	public void setNome_funcionario(String nome_funcionario) {
		this.nome_funcionario = nome_funcionario;
	}
	
	

}
