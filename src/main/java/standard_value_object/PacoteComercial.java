package standard_value_object;

import java.sql.Date;

public class PacoteComercial {

	private int id;
	private String nome;
	private String descricao;
	private boolean ativo;
	private Date data_inicio;
	private Date data_fim;

	public PacoteComercial() {
		
	}
	
	public PacoteComercial(String nome, String descricao, boolean ativo, Date data_inicio,
			Date data_fim) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim;
	}

	public PacoteComercial(int id, String nome, String descricao, boolean ativo, Date data_inicio, Date data_fim) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim;
	}

	public PacoteComercial(String nome, String descricao, boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Date getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}

	public Date getData_fim() {
		return data_fim;
	}

	public void setData_fim(Date data_fim) {
		this.data_fim = data_fim;
	}

	@Override
	public String toString() {
		return "PacoteComercial [id=" + id + ", nome=" + nome + ", descricao=" + descricao
				+ ", ativo=" + ativo + ", data_inicio=" + data_inicio + ", data_fim=" + data_fim
				+ "]";
	}
}
