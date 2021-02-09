package standard_value_object_v2;

public class Funcionario {

	private int id;
	
	private String nome;
	
	private int nif;
	
	private String login;
	
	private String password;
	
	private Boolean ativo;
	
	private int id_role;
	
	public Funcionario() {
		
	}

	public Funcionario(String nome, int nif, String login, String password, Boolean ativo, int id_role) {
		super();
		this.nome = nome;
		this.nif = nif;
		this.login = login;
		this.password = password;
		this.ativo = ativo;
		this.id_role = id_role;
	}

	public Funcionario(int id, String nome, int nif, String login, String password, Boolean ativo, int id_role) {
		super();
		this.id = id;
		this.nome = nome;
		this.nif = nif;
		this.login = login;
		this.password = password;
		this.ativo = ativo;
		this.id_role = id_role;
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

	public int getNif() {
		return nif;
	}

	public void setNif(int nif) {
		this.nif = nif;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public int getId_role() {
		return id_role;
	}

	public void setId_role(int id_role) {
		this.id_role = id_role;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", nif=" + nif + ", login=" + login + ", password="
				+ password + ", ativo=" + ativo + ", id_role=" + id_role + "]";
	}
	
}
