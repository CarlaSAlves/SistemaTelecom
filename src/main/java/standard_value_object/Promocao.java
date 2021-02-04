package standard_value_object;

public class Promocao {

	private int id;
	private String nome;
	private String descricao;
	private boolean ativa;
	
	public Promocao(int id, String nome, String descricao, boolean ativa) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ativa = ativa;
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

	@Override
	public String toString() {
		return "Promocao [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", ativa=" + ativa + "]";
	}
	
	
}
