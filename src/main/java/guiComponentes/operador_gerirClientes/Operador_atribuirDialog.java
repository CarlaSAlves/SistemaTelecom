package guiComponentes.operador_gerirClientes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import guiComponentes.GUI_total;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;
import standard_value_object.PacoteClientePromocao;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.awt.Color;
import java.awt.Component;

@SuppressWarnings("serial")
public class Operador_atribuirDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Operador_gerirClientes operador_gerirClientes;
	private boolean modoPromocao = false;
	private JComboBox<PacoteComercial> comboBox;
	private JComboBox<Promocao> comboBoxPromo;
	private JLabel labelPacote;
	private String username;
	private GUI_total guit = new GUI_total();
	private Cliente cliente;

	/**
	 * Create the dialog.
	 * @param operador_gerirClientes 
	 * @param string 
	 */
	public Operador_atribuirDialog( Operador_gerirClientes operador_gerirClientes, List<PacoteComercial> pacotes, Cliente cliente) {
		this();
		this.operador_gerirClientes = operador_gerirClientes;
		this.cliente = cliente;
		setTitle("Atribuir Pacote Comercial");
		labelPacote.setText("Selecione o Pacote Comercial que deseja atribuir: ");
		comboBox = new JComboBox<PacoteComercial>();
		comboBox.setRenderer(new PacoteComboRenderer());
		comboBox.setBounds(10, 47, 414, 25);
		contentPanel.add(comboBox);
		for(PacoteComercial pc : pacotes) {
			if(pc.isAtivo()) {
				comboBox.addItem(pc);
			}
		}

	}

	/**
	 * Assigns a pacote comercial or a promoção to the cliente
	 * Validations for the ok button 
	 * 
	 */
	public Operador_atribuirDialog() {
		setBounds(500, 300, 450, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		labelPacote = new JLabel();
		labelPacote.setForeground(Color.BLACK);
		labelPacote.setFont(new Font("Dubai Light", Font.BOLD, 13));
		labelPacote.setBounds(10, 11, 414, 25);
		setResizable(false);


		contentPanel.add(labelPacote);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						username = guit.mandarUsername();

						Cliente clienteTemp = null;
						Funcionario func = null;
						PacoteCliente pacoteCliente = null;
						PacoteClientePromocao pacoteClientePromocao = null;

						try {
							if(!modoPromocao) {
								PacoteComercial pacoteComercial = (PacoteComercial) comboBox.getSelectedItem();
								clienteTemp = cliente;
								func = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
								pacoteCliente = new PacoteCliente(pacoteComercial.getId(), func.getId());
								pacoteCliente = GestorDeDAO.getGestorDeDAO().criarPacoteCliente(pacoteCliente, clienteTemp, func);
								clienteTemp.setId_pacote_cliente(pacoteCliente.getId());
								JOptionPane.showMessageDialog(Operador_atribuirDialog.this,
										"Pacote Comercial Atribuido com Sucesso!", "Pacote Comercial Atribuido", JOptionPane.INFORMATION_MESSAGE);

							}else {
								Promocao promocao = (Promocao) comboBoxPromo.getSelectedItem();
					
								clienteTemp = cliente;
								func = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
								pacoteClientePromocao = new PacoteClientePromocao(clienteTemp.getId_pacote_cliente(), promocao.getId());
								List<PacoteClientePromocao> listaClientesPromocao = GestorDeDAO.getGestorDeDAO().pesquisarTodosPacotesClientePromocao();

								for(PacoteClientePromocao p : listaClientesPromocao) {
									if(p.getId_pacote_cliente()==pacoteClientePromocao.getId_pacote_cliente() && p.getId_promocao()==pacoteClientePromocao.getId_promocao()) {
										JOptionPane.showMessageDialog(Operador_atribuirDialog.this,
												"Promoção já se encontra atribuida a este Pacote Comercial!", "Erro", JOptionPane.ERROR_MESSAGE);
										return;
									}									
								}
								GestorDeDAO.getGestorDeDAO().criarPacoteClientePromocao(pacoteClientePromocao, clienteTemp, func);
								JOptionPane.showMessageDialog(Operador_atribuirDialog.this,
										"Promoção Atribuida com Sucesso!", "Promoção Atribuida", JOptionPane.INFORMATION_MESSAGE);
							}

						} catch(Exception e1) {
							e1.printStackTrace();
						}
						operador_gerirClientes.refreshClienteTable();
						setVisible(false);
						dispose();


					}
				});
				buttonPane.add(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setBackground(Color.LIGHT_GRAY);
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * Settings for the promoção mode 
	 * @param operador_gerirClientes
	 * @param promocoes
	 * @param cliente
	 * @param modoPromocao
	 */
	public Operador_atribuirDialog( Operador_gerirClientes operador_gerirClientes, List<Promocao> promocoes, Cliente cliente, boolean modoPromocao) {
		this();
		this.operador_gerirClientes = operador_gerirClientes;
		this.cliente = cliente;
		this.modoPromocao = modoPromocao;
		setTitle("Atribuir Promoção");
		if(modoPromocao) {
			labelPacote.setText("Selecione a Promoção que deseja atribuir: ");
			comboBoxPromo = new JComboBox<Promocao>();
			comboBoxPromo.setRenderer(new PromocaoComboRenderer());
			comboBoxPromo.setBounds(10, 47, 414, 25);
			contentPanel.add(comboBoxPromo);
			for(Promocao p : promocoes) {
				if(p.isAtiva()) {
					comboBoxPromo.addItem(p);
				}
			}
		}
	}

	/**
	 * Creates the renderer for the comboBox of pacotes comerciais
	 */
	private class PacoteComboRenderer implements ListCellRenderer<PacoteComercial> {

		private JLabel display;

		PacoteComboRenderer(){
			display = new JLabel();
			display.setFont(new Font("Dubai Light", Font.PLAIN, 11));
			display.setOpaque( true );
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends PacoteComercial> list, PacoteComercial value,
				int index, boolean isSelected, boolean cellHasFocus) {
			display.setText("  " + value.getNome());
			if (isSelected) {
				display.setBackground(new Color(250,235,70));
			} else 
				display.setBackground(null);
			return display;
			
		}
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Creates the renderer for the comboBox of promoções
	 */
	private class PromocaoComboRenderer implements ListCellRenderer<Promocao> {

		private JLabel display;

		PromocaoComboRenderer(){
			display = new JLabel();
			display.setFont(new Font("Dubai Light", Font.PLAIN, 11));
			display.setOpaque( true );
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Promocao> list, Promocao value, int index,
				boolean isSelected, boolean cellHasFocus) {
			display.setText("  " + value.getNome());
			if (isSelected) {
				display.setBackground(new Color(250,235,70));
			} else 
				display.setBackground(null);
			return display;
		}
	}

}
