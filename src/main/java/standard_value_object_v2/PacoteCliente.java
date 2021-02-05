package standard_value_object_v2;

import java.sql.Date;

public class PacoteCliente {

	private int id;
	
	private int id_pacote_comercial;
	
	private Date data_inicio;
	
	private int id_criado_por;
	
	public PacoteCliente() {
		
	}
	
	public PacoteCliente(int id_pacote_comercial, int id_criado_por) {
		super();
		this.id_pacote_comercial = id_pacote_comercial;
		this.id_criado_por = id_criado_por;
	}

	public PacoteCliente(int id_pacote_comercial, Date data_inicio,
			int id_criado_por) {
		super();
		this.id_pacote_comercial = id_pacote_comercial;
		this.data_inicio = data_inicio;
//		this.data_fim = data_fim;
		this.id_criado_por = id_criado_por;
	}

	public PacoteCliente(int id, int id_pacote_comercial, Date data_inicio,
			int id_criado_por) {
		super();
		this.id = id;
		this.id_pacote_comercial = id_pacote_comercial;
		this.data_inicio = data_inicio;
//		this.data_fim = data_fim;
		this.id_criado_por = id_criado_por;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_pacote_comercial() {
		return id_pacote_comercial;
	}

	public void setId_pacote_comercial(int id_pacote_comercial) {
		this.id_pacote_comercial = id_pacote_comercial;
	}

	public Date getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}

	public int getId_criado_por() {
		return id_criado_por;
	}

	public void setId_criado_por(int id_criado_por) {
		this.id_criado_por = id_criado_por;
	}

	@Override
	public String toString() {
		return "PacoteCliente [id=" + id + ", id_pacote_comercial=" + id_pacote_comercial + ", data_inicio="
				+ data_inicio + ", id_criado_por=" + id_criado_por + "]";
	}
	
}
