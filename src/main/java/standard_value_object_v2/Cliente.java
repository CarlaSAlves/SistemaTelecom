package standard_value_object_v2;

public class Cliente {

	private int id;
	private int nif;
	private String nome;
	private String morada;
	private String login;
	private String password;
	private int id_pacote_cliente;
	private boolean ativo;
	
	public Cliente() {
		
	}
	
	public Cliente( int nif, String nome, String morada, String login, String password, boolean ativo,
			int id_pacote_cliente) {
		super();

		this.ativo = ativo;
		this.nif = nif;
		this.nome = nome;
		this.morada = morada;
		this.login = login;
		this.password = password;
		this.id_pacote_cliente = id_pacote_cliente;
	}
	
	//para quando quisermos criar um cliente sem pacote 
	public Cliente( int nif, String nome, String morada, String login, String password, boolean ativo) {
		super();

		this.ativo = ativo;
		this.nif = nif;
		this.nome = nome;
		this.morada = morada;
		this.login = login;
		this.password = password;
	}
	
	public Cliente(int id, int nif, String nome, String morada, String login, String password, boolean ativo,
			int id_pacote_cliente) {
		super();
		this.id = id;
		this.ativo = ativo;
		this.nif = nif;
		this.nome = nome;
		this.morada = morada;
		this.login = login;
		this.password = password;
		this.id_pacote_cliente = id_pacote_cliente;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getId() {
		return id;
	}

	public int getNif() {
		return nif;
	}

	public String getNome() {
		return nome;
	}

	public String getMorada() {
		return morada;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public int getId_pacote_cliente() {
		return id_pacote_cliente;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNif(int nif) {
		this.nif = nif;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMorada(String morada) {
		this.morada = morada;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId_pacote_cliente(int id_pacote_cliente) {
		this.id_pacote_cliente = id_pacote_cliente;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nif=" + nif + ", nome=" + nome + ", morada=" + morada + ", login=" + login
				+ ", password=" + password + ", id_pacote_comercial=" + id_pacote_cliente + "]";
	}
	
	
	
}
