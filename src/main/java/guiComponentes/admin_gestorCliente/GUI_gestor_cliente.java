package guiComponentes.admin_gestorCliente;


import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
import guiComponentes.GUI_total;
import historicos.HistoricoCliente;
import javax.swing.JCheckBox;
import java.awt.Color;


public class GUI_gestor_cliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private int numberRows;
	private int indice;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTable table;
	private JButton btVoltarGestorCliente;
	private JPanel contentPane, painelPesquisa ;	
	private JLabel lblResultados, lblUsernameLogged, lblCamposPesquisas, labelID, labelNIF, labelNome, labelMorada, lblTempoSessao, lblHoraSistema ;
	private JButton botaoDesativarCliente, botaoEditarCliente, botaoVisualizarHistorico, botaoPesquisa;
	private JTextField textPesquisaID, textPesquisaNIF,textFieldNome,textFieldMorada;
	private JCheckBox checkBoxAtivo;


	public GUI_gestor_cliente() {

		ativarNimbusLookAndFeel();


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
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		contentPane.add(lblHoraSistema);

		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		contentPane.add(lblTempoSessao);

		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		contentPane.add(lblUsernameLogged);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));



	}

	private void ativarNimbusLookAndFeel() {
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
	}

	private void panelContentSetup() {
		lblNewLabelIDSetup();
		painelPesquisa.setLayout(null);
		painelPesquisa.add(labelID);

		textPesquisaIDSetup();
		painelPesquisa.add(textPesquisaID);

		lblNewLabelNIFSetup();
		painelPesquisa.add(labelNIF);

		textPesquisaNIFSetup();
		painelPesquisa.add(textPesquisaNIF);

		lblNomeSetup();
		painelPesquisa.add(labelNome);

		textFieldNomeSetup();
		painelPesquisa.add(textFieldNome);

		lblMoradaSetup();
		painelPesquisa.add(labelMorada);

		textFieldMoradaSetup();
		painelPesquisa.add(textFieldMorada);

		checkBoxAtivoSetup();
		painelPesquisa.add(checkBoxAtivo);

		botaoPesquisaSetup();
		painelPesquisa.add(botaoPesquisa);
	}

	private void botaoPesquisaSetup() {
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBounds(72, 181, 371, 27);
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 13));
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
						id = Integer.parseInt(textPesquisaID.getText().trim());
					}

					if(!textPesquisaNIF.getText().isBlank()) {
						nif = textPesquisaNIF.getText().trim();
					}

					if(!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText().trim();
					}

					if(!textFieldMorada.getText().isBlank()) {
						morada = textFieldMorada.getText().trim();
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
		checkBoxAtivo.setBounds(234, 150, 69, 24);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
	}

	private void textFieldMoradaSetup() {
		textFieldMorada = new JTextField();
		textFieldMorada.setBounds(72, 116, 371, 27);
		textFieldMorada.setColumns(10);
	}

	private void lblMoradaSetup() {
		labelMorada = new JLabel("Morada");
		labelMorada.setBounds(6, 116, 56, 27);
		labelMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textFieldNomeSetup() {
		textFieldNome = new JTextField();
		textFieldNome.setBounds(72, 78, 371, 27);
		textFieldNome.setColumns(10);
	}

	private void lblNomeSetup() {
		labelNome = new JLabel("Nome");
		labelNome.setBounds(6, 87, 56, 18);
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textPesquisaNIFSetup() {
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setBounds(72, 40, 371, 27);
		textPesquisaNIF.setColumns(10);
	}

	private void lblNewLabelNIFSetup() {
		labelNIF = new JLabel("NIF");
		labelNIF.setBounds(6, 49, 56, 18);
		labelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textPesquisaIDSetup() {
		textPesquisaID = new JTextField();
		textPesquisaID.setBounds(72, 6, 371, 27);
		textPesquisaID.setColumns(10);
	}

	private void lblNewLabelIDSetup() {
		labelID = new JLabel("ID");
		labelID.setBounds(6, 15, 39, 18);
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
					dialogHistorico.setResizable(false);

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
					indice = table.getSelectedRow();

					if (indice < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
								"Por favor selecione um Cliente", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Cliente clienteTemp = (Cliente) table.getValueAt(indice, ClientePesquisaModelTable.OBJECT_COL);

					if(clienteTemp.isAtivo()) {
						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_cliente.this,
								"Desativar Cliente(s)?", "Confirmar Desativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						Funcionario funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());


						GestorDeDAO.getGestorDeDAO().desativarCliente(clienteTemp.getId(), funcionario);

						JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
								"Cliente(s) Desativado(s) com sucesso", "Cliente(s) Desativado",
								JOptionPane.INFORMATION_MESSAGE);

						refreshClienteTable();
					}else {
						
						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_cliente.this,
								"Ativar Cliente(s)?", "Confirmar Ativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						Funcionario funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().ativarCliente(clienteTemp.getId(), funcionario);

						JOptionPane.showMessageDialog(GUI_gestor_cliente.this,
								"Cliente(s) Ativado(s) com sucesso", "Cliente(s) Ativado",
								JOptionPane.INFORMATION_MESSAGE);

						refreshClienteTable();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
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
				dialog.setResizable(false);
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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
					botaoAtivarDinamico();
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

	private void botaoAtivarDinamico() {

		try {
			int row = table.getSelectedRow();
			Cliente cliente = (Cliente) table.getValueAt(row, ClientePesquisaModelTable.OBJECT_COL);
			if (cliente.isAtivo())
				botaoDesativarCliente.setText("Desativar cliente");
			else
				botaoDesativarCliente.setText("Ativar Cliente");
		} catch  (Exception e) {
		}
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
				dialog.setResizable(false);

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


	public JTable getTable() {
		return table;
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
	
	public JLabel getLblResultados() {
		return lblResultados;
	}
	
}
