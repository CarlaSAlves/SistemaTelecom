package standard_value_object_v2;

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
	
	public PacoteComercial(String nome, String descricao, boolean ativo) {
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
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

	public PacoteComercial(int id, String nome, String descricao, boolean ativo, Date data_inicio,
			Date data_fim) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getData_incio() {
		return data_inicio;
	}

	public void setData_incio(Date data_inicio) {
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
		return "PacoteComercial [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", ativo=" + ativo
				+ ", data_inicio=" + data_inicio + ", data_fim=" + data_fim + "]";
	}

	
}
