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


public class GUI_gestor_cliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textPesquisaNIF;
	private JTable table;
	private JButton btVoltarGestorCliente;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados;
	private JButton botaoDesativarCliente;
	private JButton botaoEditarCliente;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 17);
	private JLabel lblUsernameLogged;
	private String username;
	private JButton botaoVisualizarHistorico;

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

		JLabel lblDeProcura = new JLabel("Introduza o NIF: ");
		lblDeProcura.setBounds(66, 18, 134, 26);
		lblDeProcura.setFont(font);
		getContentPane().add(lblDeProcura);

		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setBounds(210, 18, 161, 26);
		textPesquisaNIF.setFont(font);
		getContentPane().add(textPesquisaNIF);
		textPesquisaNIF.setColumns(10);

		JButton botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setBounds(411, 15, 119, 32);
		botaoPesquisa.setFont(font);
		botaoPesquisa.setFocusPainted(false);
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
		getContentPane().add(botaoPesquisa);

		JButton botaoCriarCliente = new JButton("Criar Cliente");
		botaoCriarCliente.setBounds(1250, 15, 152, 32);
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

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 74, 1366, 721);
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
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblResultados.setBounds(10, 4, 136, 25);
		panel.add(lblResultados);

		botaoEditarCliente = new JButton("Editar Cliente");
		botaoEditarCliente.setBounds(852, 15, 161, 33);
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
		botaoDesativarCliente.setBounds(1053, 15, 161, 33);
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
		botaoVisualizarHistorico.setBounds(658, 15, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
		contentPane.add(botaoVisualizarHistorico);

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
