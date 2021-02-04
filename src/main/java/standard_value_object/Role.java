package standard_value_object;

public class Role {

	private int id;
	
	private String nome;

	public Role() {
		
	}
	
	public Role(String nome) {
		super();
		this.nome = nome;
	}

	public Role(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	
	
}
