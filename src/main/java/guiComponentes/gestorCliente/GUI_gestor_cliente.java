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
import java.time.Duration;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import servico.GestorDeDAO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import guiComponentes.GUI_total;
import historicos.HistoricoCliente;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import java.awt.Color;


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
	private JButton botaoVisualizarHistorico;
	private JLabel lblCamposPesquisas;
	private JPanel painelPesquisa;
	private JLabel labelID;
	private JLabel labelNIF;
	private JLabel labelNome;
	private JLabel labelMorada;
	private JTextField textPesquisaID;
	private JTextField textPesquisaNIF;
	private JTextField textFieldNome;
	private JTextField textFieldMorada;
	private JCheckBox checkBoxAtivo;
	private JButton botaoPesquisa;
	private JLabel lblTempoSessao;
	private JLabel lblHoraSistema;



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
		
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if ("Nimbus".equals(info.getName())) {
	            try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
	            break;
	        }
		}
		

		contentPaneSetup();

		JButton botaoCriarCliente = botaoCriarClienteSetup();
		getContentPane().add(botaoCriarCliente);

		JPanel panelDaTable = panelDaTableSetup();
		getContentPane().add(panelDaTable);

		JScrollPane scrollPane = scrollPaneSetup();
		panelDaTable.add(scrollPane);

		tableSetup();
		scrollPane.setViewportView(table);

		lblResultadosSetup();
		panelDaTable.add(lblResultados);

		botaoEditarClienteSetup();
		getContentPane().add(botaoEditarCliente);

		botaoDesativarClienteSetup();
		getContentPane().add(botaoDesativarCliente);

		btVoltarGestorClienteSetup();
		getContentPane().add(btVoltarGestorCliente);

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);

		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);

		lblCamposPesquisasSetup();
		contentPane.add(lblCamposPesquisas);

		panelSetup();
		panelContentSetup();
		contentPane.add(painelPesquisa);
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1219, 729, 159, 18);
		contentPane.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1219, 709, 159, 18);
		contentPane.add(lblTempoSessao);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1219, 690, 159, 18);
		contentPane.add(lblUsernameLogged);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 12));

		panelUserESessaoSetup();
		panelUserESessaoContentSetup();



	}
	
	public JTable getTable() {
		return table;
	}

	private void panelUserESessaoContentSetup() {
		lblUsernameLoggedSetup();

		lblTempoSessaoSetup();

		lblHoraSistemaSetup();
	}

	private void lblHoraSistemaSetup() {
	}

	private void lblTempoSessaoSetup() {
	}

	private void lblUsernameLoggedSetup() {
	}

	private void panelUserESessaoSetup() {
	}

	private void panelContentSetup() {
		lblNewLabelIDSetup();
		painelPesquisa.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("39px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("400px"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("24px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("38px"),}));
		painelPesquisa.add(labelID, "2, 2, left, center");

		textPesquisaIDSetup();
		painelPesquisa.add(textPesquisaID, "4, 2, fill, top");

		lblNewLabelNIFSetup();
		painelPesquisa.add(labelNIF, "2, 4, left, center");

		textPesquisaNIFSetup();
		painelPesquisa.add(textPesquisaNIF, "4, 4, fill, top");

		lblNomeSetup();
		painelPesquisa.add(labelNome, "2, 6, left, center");

		textFieldNomeSetup();
		painelPesquisa.add(textFieldNome, "4, 6, fill, top");

		lblMoradaSetup();
		painelPesquisa.add(labelMorada, "2, 8, left, center");

		textFieldMoradaSetup();
		painelPesquisa.add(textFieldMorada, "4, 8, fill, top");

		checkBoxAtivoSetup();
		painelPesquisa.add(checkBoxAtivo, "4, 10, center, top");

		botaoPesquisaSetup();
		painelPesquisa.add(botaoPesquisa, "4, 12, fill, top");
	}

	private void botaoPesquisaSetup() {
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int id = 0;
					String nif = null;
					String nome = null;
					String morada = null;
					int ativo = checkBoxAtivo.isSelected()? 1:0;

					if(!textPesquisaID.getText().isBlank()) {
						id = Integer.parseInt(textPesquisaID.getText());
					}

					if(!textPesquisaNIF.getText().isBlank()) {
						nif = textPesquisaNIF.getText();
					}

					if(!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText();
					}

					if(!textFieldMorada.getText().isBlank()) {
						morada = textFieldMorada.getText();
					}


					List<Cliente> clientes = null;

					if ((id != 0) || (nif != null) || (nome != null) || (morada != null) || (ativo!=0) ) {
						clientes = GestorDeDAO.getGestorDeDAO().pesquisaCliente(id, nif, nome, morada, ativo);
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
	}

	private void checkBoxAtivoSetup() {
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
	}

	private void textFieldMoradaSetup() {
		textFieldMorada = new JTextField();
		textFieldMorada.setColumns(10);
	}

	private void lblMoradaSetup() {
		labelMorada = new JLabel("Morada");
		labelMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textFieldNomeSetup() {
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
	}

	private void lblNomeSetup() {
		labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textPesquisaNIFSetup() {
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setColumns(10);
	}

	private void lblNewLabelNIFSetup() {
		labelNIF = new JLabel("NIF");
		labelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textPesquisaIDSetup() {
		textPesquisaID = new JTextField();
		textPesquisaID.setColumns(10);
	}

	private void lblNewLabelIDSetup() {
		labelID = new JLabel("ID");
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void panelSetup() {
		painelPesquisa = new JPanel();
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 63, 453, 221);
	}

	private void lblCamposPesquisasSetup() {
		lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 26, 294, 26);
	}

	private void botaoVisualizarHistoricoSetup() {
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
		botaoVisualizarHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoVisualizarHistorico.setBackground(SystemColor.activeCaption);
		botaoVisualizarHistorico.setBounds(655, 263, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
	}

	private JLabel lbFooterSetup() {
		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_cliente.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 214, 65);
		return lbFooter;
	}

	private void btVoltarGestorClienteSetup() {
		btVoltarGestorCliente = new JButton("Voltar");
		btVoltarGestorCliente.setBounds(6, 709, 119, 38);
		btVoltarGestorCliente.setFont(font);
		btVoltarGestorCliente.setBackground(SystemColor.activeCaption);
		btVoltarGestorCliente.setFocusPainted(false);
		btVoltarGestorCliente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(null);
				
			}
		});
	}

	private void botaoDesativarClienteSetup() {
		botaoDesativarCliente = new JButton("Desativar Cliente");
		botaoDesativarCliente.setBounds(997, 264, 161, 33);
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

					Funcionario funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(GUI_total.getUsername());
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
	}

	private void botaoEditarClienteSetup() {
		botaoEditarCliente = new JButton("Editar Cliente");
		botaoEditarCliente.setBounds(826, 264, 161, 33);
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
				CriarClienteDialog dialog = new CriarClienteDialog(GUI_gestor_cliente.this,clienteTemp, true, GUI_total.getUsername() );
				dialog.setVisible(true);
			}
		});
	}

	private void lblResultadosSetup() {
		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblResultados.setBounds(33, 6, 136, 25);
	}

	private void tableSetup() {
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
	}

	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		return scrollPane;
	}

	private JPanel panelDaTableSetup() {
		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(Color.WHITE);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		return painelTabela;
	}

	private JButton botaoCriarClienteSetup() {
		JButton botaoCriarCliente = new JButton("Criar Cliente");
		botaoCriarCliente.setBounds(1168, 264, 152, 32);
		botaoCriarCliente.setFont(font);
		botaoCriarCliente.setBackground(SystemColor.activeCaption);
		botaoCriarCliente.setFocusPainted(false);
		botaoCriarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CriarClienteDialog dialog = new CriarClienteDialog(GUI_gestor_cliente.this, GUI_total.getUsername());
				dialog.setVisible(true);

			}
		});
		return botaoCriarCliente;
	}

	private void contentPaneSetup() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Clientes");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane.setBackground(Color.WHITE);
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
		lblUsernameLogged.setText("Username: " + username);

	}

	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}
}
