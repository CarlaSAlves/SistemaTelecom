package standard_value_object;

public class Funcionario {

	private int id;
	private String nome;
	private long nif;
	private String login;
	private String password;
	private boolean ativo;
	private int id_role;
	
	public Funcionario() {
		
	}
	
	public Funcionario(String nome, int nif, String login, String password, boolean ativo, int id_role) {
		this.nome = nome;
		this.nif = nif;
		this.login = login;
		this.password = password;
		this.ativo = ativo;
		this.id_role = id_role;
	}
	
	public Funcionario(int id, String nome, long nif, String login, String password, boolean ativo, int id_role) {
		super();
		this.id = id;
		this.nome = nome;
		this.nif = nif;
		this.login = login;
		this.password = password;
		this.ativo = ativo;
		this.id_role = id_role;
	}
	
	public Funcionario(String nome, long nif, String login, String password, boolean ativo, int id_role) {
		super();
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

	public String getNome() {
		return nome;
	}

	public long getNif() {
		return nif;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public int getId_role() {
		return id_role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNif(long nif) {
		this.nif = nif;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
