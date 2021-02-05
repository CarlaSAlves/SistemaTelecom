
package guiComponentes.gestorPacoteComercial;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import standard_value_object.PacoteComercial;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import servico.GestorDeDAO;

import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;

public class GUI_gestor_pacotes extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private JTextField textPesquisaId;
	private JTable table;
	private JButton btVoltarGestorCliente;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados;
	private JButton botaoDesativarPacoteComercial;
	private JButton botaoEditarPacoteComercial;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 20);
	private JTextField textField;
	private JTextField textField_1;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_gestor_pacotes frame = new GUI_gestor_pacotes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_gestor_pacotes() {

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Pacotes Comerciais");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane.setBackground(SystemColor.inactiveCaption);

		JLabel lblDeProcura = new JLabel("Id do Pacote: ");
		lblDeProcura.setBounds(66, 68, 134, 26);
		lblDeProcura.setFont(font);
		getContentPane().add(lblDeProcura);

		textPesquisaId = new JTextField();
		textPesquisaId.setBounds(210, 105, 161, 26);
		textPesquisaId.setFont(font);
		getContentPane().add(textPesquisaId);
		textPesquisaId.setColumns(10);


		JButton botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setBounds(210, 196, 119, 32);
		botaoPesquisa.setFont(font);
		botaoPesquisa.setFocusPainted(false);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					String id = textPesquisaId.getText();

					List<PacoteComercial> pacotesComerciais = null;


					if (!id.isBlank()) {
						pacotesComerciais = GestorDeDAO.getGestorDeDAO().pesquisaPacoteComercial(id);
					} else  {
						pacotesComerciais = GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
					}

					PacoteComercialPesquisaModelTable model = new PacoteComercialPesquisaModelTable(pacotesComerciais);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}

			}
		});
		getContentPane().add(botaoPesquisa);

		JButton botaoCriarCliente = new JButton("Criar Cliente");
		botaoCriarCliente.setBounds(435, 152, 152, 32);
		botaoCriarCliente.setFont(font);
		botaoCriarCliente.setBackground(SystemColor.activeCaption);
		botaoCriarCliente.setFocusPainted(false);
		botaoCriarCliente.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarPacotesDialog dialog = new CriarPacotesDialog(GUI_gestor_pacotes.this);
				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoCriarCliente);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 296, 1366, 499);
		panel.setFont(font);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 660);
		panel.add(scrollPane);

		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		table.setRowHeight(20);


		scrollPane.setViewportView(table);


		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblResultados.setBounds(10, 4, 136, 25);
		panel.add(lblResultados);

		botaoEditarPacoteComercial = new JButton("Editar Cliente");
		botaoEditarPacoteComercial.setBounds(435, 82, 161, 33);
		botaoEditarPacoteComercial.setFont(font);
		botaoEditarPacoteComercial.setBackground(SystemColor.activeCaption);
		botaoEditarPacoteComercial.setFocusPainted(false);
		botaoEditarPacoteComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Por favor selecione um Pacote Comercial", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				PacoteComercial pacoteComercialTemp = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);

				CriarPacotesDialog dialog = new CriarPacotesDialog(GUI_gestor_pacotes.this, pacoteComercialTemp, true);

				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoEditarPacoteComercial);

		botaoDesativarPacoteComercial = new JButton("Desativar Cliente");
		botaoDesativarPacoteComercial.setBounds(426, 38, 170, 33);
		botaoDesativarPacoteComercial.setFont(font);
		botaoDesativarPacoteComercial.setBackground(SystemColor.activeCaption);
		botaoDesativarPacoteComercial.setFocusPainted(false);
		botaoDesativarPacoteComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
								"Por favor selecione um Cliente", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_pacotes.this,
							"Desativar este Cliente?", "Confirmar Desativar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					for(int i = 0; i < indices.length; i++) {
						PacoteComercial pacoteTemp = (PacoteComercial) table.getValueAt(indices[i], PacoteComercialPesquisaModelTable.OBJECT_COL);
						GestorDeDAO.getGestorDeDAO().desativarCliente(pacoteTemp.getId());

					}
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Cliente Desativado com sucesso", "Cliente Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshClienteTable();
				} catch (Exception e1) {

				}
			}

		});
		getContentPane().add(botaoDesativarPacoteComercial);

		btVoltarGestorCliente = new JButton("Voltar");
		btVoltarGestorCliente.setBounds(26, 818, 119, 32);
		btVoltarGestorCliente.setFont(font);
		btVoltarGestorCliente.setBackground(SystemColor.activeCaption);
		btVoltarGestorCliente.setFocusPainted(false);
		getContentPane().add(btVoltarGestorCliente);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_pacotes.class.getResource("/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		contentPane.add(lbFooter);
		btVoltarGestorCliente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {



			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(true);
				}
				else if (table.getSelectedRows().length==1) {
					botaoEditarPacoteComercial.setEnabled(true);
					botaoDesativarPacoteComercial.setEnabled(true);
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(false);
				}
			}
		});

		botaoEditarPacoteComercial.setEnabled(false);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblNome.setBounds(66, 105, 134, 26);
		contentPane.add(lblNome);

		textField = new JTextField();
		textField.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		textField.setColumns(10);
		textField.setBounds(210, 68, 161, 26);
		contentPane.add(textField);

		JLabel lblCamposPesquisas = new JLabel("Campos Pesquisas");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 168, 26);
		contentPane.add(lblCamposPesquisas);

		JLabel lblAtivo = new JLabel("Ativo");
		lblAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblAtivo.setBounds(66, 142, 134, 26);
		contentPane.add(lblAtivo);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		textField_1.setColumns(10);
		textField_1.setBounds(210, 142, 161, 26);
		contentPane.add(textField_1);
		botaoDesativarPacoteComercial.setEnabled(false);

		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(699, 71, 499, 178);
		contentPane.add(textAreaDescricao);
	}

	public void refreshClienteTable() {
		
		try {
			List<PacoteComercial> pacotesComerciais = GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
			PacoteComercialPesquisaModelTable model = new PacoteComercialPesquisaModelTable(pacotesComerciais);
			table.setModel(model);

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public JButton btVoltarGestorCliente() {
		return btVoltarGestorCliente;
	}

	public void setBtVoltarGestorCliente(JButton btnNewButtonVoltar2) {
		this.btVoltarGestorCliente = btnNewButtonVoltar2;
	}

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
