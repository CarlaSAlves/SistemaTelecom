package standard_value_object;

import java.sql.Date;
import java.time.LocalDateTime;

public class Promocao {

	private int id;
	private String nome;
	private String descricao;
	private boolean ativa;
	
	private Date data_inicio; 
	
	private Date data_fim;
	
	public Promocao(int id, String nome, String descricao, boolean ativa, Date data_inicio, Date data_fim) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ativa = ativa;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim; 
	}

	public Promocao(String nome, String descricao, boolean ativa) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativa = ativa;
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

	public boolean isAtiva() {
		return ativa;
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

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	

	/**
	 * @return the data_inicio
	 */
	public Date getData_inicio() {
		return data_inicio;
	}

	/**
	 * @param data_inicio the data_inicio to set
	 */
	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}

	/**
	 * @return the data_fim
	 */
	public Date getData_fim() {
		return data_fim;
	}

	/**
	 * @param data_fim the data_fim to set
	 */
	public void setData_fim(Date data_fim) {
		this.data_fim = data_fim;
	}

	@Override
	public String toString() {
		return "Promocao [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", ativa="
				+ ativa + ", data_inicio=" + data_inicio + ", data_fim=" + data_fim + "]";
	}

	
	
	
}
