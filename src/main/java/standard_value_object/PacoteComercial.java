package standard_value_object;

import java.sql.Date;
import java.time.LocalDateTime;

public class PacoteComercial {

	private int id;
	private String nome;
	private String descricao;
	private boolean ativo;
	
	public PacoteComercial(int id, String nome, String descricao, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
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

	@Override
	public String toString() {
		return "PacoteComercial [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", ativo=" + ativo + "]";
	}

	

}
