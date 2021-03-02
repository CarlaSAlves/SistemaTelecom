package guiComponentes.operador_gerirClientes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import guiComponentes.GUI_total;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteCliente;
import standard_value_object.PacoteClientePromocao;
import standard_value_object.Promocao;

public class Operador_removerPromoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<Promocao> comboBoxPromo;
	private JLabel labelPacote;
	private Cliente cliente;
	private Operador_gerirClientes operador_gerirClientes;
	private GUI_total guit = new GUI_total();
	private String username;

	/**
	 * Create the dialog.
	 */
	public Operador_removerPromoDialog(Operador_gerirClientes operador_gerirClientes, List<Promocao> promocoes, Cliente cliente) {
		this.operador_gerirClientes = operador_gerirClientes;
		this.cliente = cliente;

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");

		setBounds(500, 300, 450, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setResizable(false);
		setTitle("Remover Promoção");


		labelPacote = new JLabel();
		labelPacote.setForeground(Color.BLACK);
		labelPacote.setFont(new Font("Dubai Light", Font.BOLD, 13));
		labelPacote.setBounds(10, 11, 414, 25);
		contentPanel.add(labelPacote);
		labelPacote.setText("Selecione a Promoção que deseja remover: ");

		// ComboBox 
		
		comboBoxPromo = new JComboBox<Promocao>();
		comboBoxPromo.setRenderer(new PromocaoComboRenderer());
		comboBoxPromo.setBounds(10, 47, 414, 25);
		contentPanel.add(comboBoxPromo);
		for(Promocao p : promocoes) {
			if(p.isAtiva()) {
				comboBoxPromo.addItem(p);
			}
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						username = guit.mandarUsername();

						Cliente clienteTemp = null;
						Funcionario func = null;
						PacoteCliente pacoteCliente = null;

						try {

							clienteTemp = cliente;
							func = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
							Promocao promocao = (Promocao) comboBoxPromo.getSelectedItem();

							GestorDeDAO.getGestorDeDAO().removerPromocao(clienteTemp.getId_pacote_cliente(), promocao.getId(), func, clienteTemp);

						} catch (Exception e1) {
							e1.printStackTrace();
						}

						JOptionPane.showMessageDialog(Operador_removerPromoDialog.this,
								"Promoção Removida com Sucesso!", "Promoção Removida", JOptionPane.INFORMATION_MESSAGE);

						setVisible(false);
						dispose();
					}
				});

				buttonPane.add(okButton);

				JButton cancelButton = new JButton("Cancelar");
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
	 * Creates the renderer of promoção
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
