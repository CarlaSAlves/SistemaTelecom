package standard_value_object;

public class PacoteClientePromocao {
	private int id_pacote_cliente;
	private int id_promocao;
	
	public PacoteClientePromocao(int id_pacote_cliente, int id_promocao) {
		super();
		this.id_pacote_cliente = id_pacote_cliente;
		this.id_promocao = id_promocao;
	}
	
	public int getId_pacote_cliente() {
		return id_pacote_cliente;
	}
	public void setId_pacote_cliente(int id_pacote_cliente) {
		this.id_pacote_cliente = id_pacote_cliente;
	}
	public int getId_promocao() {
		return id_promocao;
	}
	public void setId_promocao(int id_promocao) {
		this.id_promocao = id_promocao;
	}

	@Override
	public String toString() {
		return "PacoteClientePromocao [id_pacote_cliente=" + id_pacote_cliente + ", id_promocao=" + id_promocao + "]";
	}

	
}
