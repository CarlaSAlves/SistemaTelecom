package guiComponentes.gestorCliente;


import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

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

import historico.cliente.HistoricoCliente;
import servico.GestorDeDAO;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;


public class GUI_gestor_cliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btVoltarGestorCliente;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados;
	private JButton botaoDesativarCliente;
	private JButton botaoEditarCliente;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JLabel lblUsernameLogged;
	private String username;
	private JButton botaoVisualizarHistorico;
	private JLabel lblCamposPesquisas;
	private JPanel panel;
	private JLabel lblNewLabelID;
	private JLabel lblNewLabelNIF;
	private JLabel lblNome;
	private JLabel lblMorada;
	private JTextField textPesquisaID;
	private JTextField textPesquisaNIF;
	private JTextField textFieldNome;
	private JTextField textFieldMorada;
	private JCheckBox checkBoxAtivo;
	private JButton botaoPesquisa;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					GUI_gestor_cliente frame = new GUI_gestor_cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_gestor_cliente() {

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Clientes");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane.setBackground(SystemColor.inactiveCaption);

		JButton botaoCriarCliente = new JButton("Criar Cliente");
		botaoCriarCliente.setBounds(1252, 267, 152, 32);
		botaoCriarCliente.setFont(font);
		botaoCriarCliente.setBackground(SystemColor.activeCaption);
		botaoCriarCliente.setFocusPainted(false);
		botaoCriarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CriarClienteDialog dialog = new CriarClienteDialog(GUI_gestor_cliente.this, username);
				dialog.setVisible(true);

			}
		});
		getContentPane().add(botaoCriarCliente);

		JPanel panelDaTable = new JPanel();
		panelDaTable.setBackground(SystemColor.inactiveCaption);
		panelDaTable.setBounds(66, 310, 1366, 488);
		panelDaTable.setFont(font);
		getContentPane().add(panelDaTable);
		panelDaTable.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 488);
		panelDaTable.add(scrollPane);

		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					botaoVisualizarHistorico.setEnabled(false);
					botaoEditarCliente.setEnabled(false);
					botaoDesativarCliente.setEnabled(true);
				}
				else if (table.getSelectedRows().length==1) {
					botaoEditarCliente.setEnabled(true);
					botaoDesativarCliente.setEnabled(true);
					botaoVisualizarHistorico.setEnabled(true);
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarCliente.setEnabled(false);
					botaoDesativarCliente.setEnabled(false);
					botaoVisualizarHistorico.setEnabled(false);
				}

			}
		});
		scrollPane.setViewportView(table);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblResultados.setBounds(10, 4, 136, 25);
		panelDaTable.add(lblResultados);

		botaoEditarCliente = new JButton("Editar Cliente");
		botaoEditarCliente.setBounds(910, 267, 161, 33);
		botaoEditarCliente.setFont(font);
		botaoEditarCliente.setEnabled(false);
		botaoEditarCliente.setBackground(SystemColor.activeCaption);
		botaoEditarCliente.setFocusPainted(false);
		botaoEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
							"Por favor selecione um Cliente", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTable.OBJECT_COL);
				CriarClienteDialog dialog = new CriarClienteDialog(GUI_gestor_cliente.this,clienteTemp, true, username );
				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoEditarCliente);

		botaoDesativarCliente = new JButton("Desativar Cliente");
		botaoDesativarCliente.setBounds(1081, 267, 161, 33);
		botaoDesativarCliente.setFont(font);
		botaoDesativarCliente.setEnabled(false);
		botaoDesativarCliente.setBackground(SystemColor.activeCaption);
		botaoDesativarCliente.setFocusPainted(false);
		botaoDesativarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
								"Por favor selecione um Cliente", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_cliente.this,
							"Desativar Cliente(s)?", "Confirmar Desativar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					Funcionario funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(username);
					for(int i = 0; i < indices.length; i++) {
						Cliente clienteTemp = (Cliente) table.getValueAt(indices[i], ClientePesquisaModelTable.OBJECT_COL);
						GestorDeDAO.getGestorDeDAO().desativarCliente(clienteTemp.getId(), funcionario);
					}
					JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
							"Cliente(s) Desativado(s) com sucesso", "Cliente(s) Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshClienteTable();
				} catch (Exception e1) {

				}

			}

		});
		getContentPane().add(botaoDesativarCliente);

		btVoltarGestorCliente = new JButton("Voltar");
		btVoltarGestorCliente.setBounds(26, 818, 119, 32);
		btVoltarGestorCliente.setFont(font);
		btVoltarGestorCliente.setBackground(SystemColor.activeCaption);
		btVoltarGestorCliente.setFocusPainted(false);
		getContentPane().add(btVoltarGestorCliente);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_cliente.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		contentPane.add(lbFooter);

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsernameLogged.setBounds(1315, 818, 159, 32);
		contentPane.add(lblUsernameLogged);

		botaoVisualizarHistorico = new JButton("Ver Historico");
		botaoVisualizarHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();


				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
							"Por favor selecione um Cliente", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
	
				Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTable.OBJECT_COL);
				List<HistoricoCliente> list;
				
				try {

					list = GestorDeDAO.getGestorDeDAO().getHistoricoCliente(clienteTemp.getId());
					HistoricoClienteDialog dialogHistorico = new HistoricoClienteDialog();
					dialogHistorico.preencherTable(clienteTemp, list);
					dialogHistorico.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		botaoVisualizarHistorico.setFont(new Font("Dialog", Font.PLAIN, 17));
		botaoVisualizarHistorico.setBackground(SystemColor.activeCaption);
		botaoVisualizarHistorico.setBounds(739, 266, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
		contentPane.add(botaoVisualizarHistorico);
		
		lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 294, 26);
		contentPane.add(lblCamposPesquisas);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 63, 437, 189);
		contentPane.add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblNewLabelID = new JLabel("ID");
		lblNewLabelID.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel.add(lblNewLabelID, "2, 2, left, default");
		
		textPesquisaID = new JTextField();
		textPesquisaID.setColumns(10);
		panel.add(textPesquisaID, "4, 2, fill, default");
		
		lblNewLabelNIF = new JLabel("NIF");
		lblNewLabelNIF.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel.add(lblNewLabelNIF, "2, 4, left, default");
		
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setColumns(10);
		panel.add(textPesquisaNIF, "4, 4, fill, default");
		
		lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel.add(lblNome, "2, 6, left, default");
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		panel.add(textFieldNome, "4, 6, fill, default");
		
		lblMorada = new JLabel("Morada");
		lblMorada.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel.add(lblMorada, "2, 8, left, default");
		
		textFieldMorada = new JTextField();
		textFieldMorada.setColumns(10);
		panel.add(textFieldMorada, "4, 8, fill, default");
		
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		panel.add(checkBoxAtivo, "4, 10, center, default");
		
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dialog", Font.PLAIN, 15));
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					String nif = textPesquisaNIF.getText();

					List<Cliente> clientes = null;

					if (nif != null && nif.trim().length() > 0) {
						clientes = GestorDeDAO.getGestorDeDAO().pesquisaCliente(nif);
					} else {
						clientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
					}

					ClientePesquisaModelTable model = new ClientePesquisaModelTable(clientes);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}



			}
		});
		panel.add(botaoPesquisa, "4, 12");

	}



	public void refreshClienteTable() {

		try {
			List<Cliente> clientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
			ClientePesquisaModelTable model = new ClientePesquisaModelTable(clientes);
			table.setModel(model);
			numberRows = table.getRowCount();
			lblResultados.setText("Resultados: " + numberRows);
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

	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Logged in : " + username);

	}

	public void recebeUsernameDaPaginaLogin(String username) {
		this.username = username;
	}
}
