package standard_value_object;

public class Cliente {

	private int id;
	private String nome;
	private long nif;
	private String morada;
	private String login;
	private String password;
	private boolean ativo;
	private int id_pacote_cliente;
	
	public Cliente() {
		
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
	
	public Cliente(int id, String nome, long nif, String morada, String login, String password, boolean ativo,
			int id_pacote_cliente) {
		super();
		this.id = id;
		this.nome = nome;
		this.nif = nif;
		this.morada = morada;
		this.login = login;
		this.password = password;
		this.ativo = ativo;
		this.id_pacote_cliente = id_pacote_cliente;
	}
	
	
	public Cliente(String nome, long nif, String morada, String login, String password, boolean ativo,
			int id_pacote_cliente) {
		super();
		this.nome = nome;
		this.nif = nif;
		this.morada = morada;
		this.login = login;
		this.password = password;
		this.ativo = ativo;
		this.id_pacote_cliente = id_pacote_cliente;
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
	public String getMorada() {
		return morada;
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
	public int getId_pacote_cliente() {
		return id_pacote_cliente;
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
	public void setMorada(String morada) {
		this.morada = morada;
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
	public void setId_pacote_cliente(int id_pacote_cliente) {
		this.id_pacote_cliente = id_pacote_cliente;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", nif=" + nif + ", morada=" + morada + ", login=" + login
				+ ", password=" + password + ", ativo=" + ativo + ", id_pacote_cliente=" + id_pacote_cliente + "]";
	}
	
	

}
