package guiComponentes.operador_gerirClientes;


import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import guiComponentes.admin_gestorCliente.ClientePesquisaModelTable;
import guiComponentes.admin_gestorCliente.HistoricoClienteDialog;
import historicos.HistoricoCliente;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

@SuppressWarnings("serial")
public class Operador_gerirClientes extends JFrame {

	private int numberRows;
	private JPanel pane, painelPesquisa;
	private JLabel lblCampoPesquisa, lblTempoSessao, lblUsernameLogged, lblHoraSistema, lblResultados, labelID, labelNIF, labelNome, labelMorada;
	private JButton btAtribuirPacote, btAtribuirPromocao, btVisualizarPromocao, btVoltarOperador, btnVisualizarPacote, btnRemoverPromo ;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTable table;
	private JTextField textPesquisaID, textPesquisaNIF, textFieldNome, textFieldMorada ;
	private JButton btnVerHistorico, botaoPesquisa;
	private String username;
	private JCheckBox checkBoxAtivo;


	/**
	 * Constructor that starts with the method that sets up the base panel and the initialize method,
	 * which contains all the methods and elements that make up the page 
	 */
	public Operador_gerirClientes() {

		ativarNimbusLookAndFeel();
		
		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		setTitle("Operador - Gerir Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		pane.setBackground(SystemColor.text);
		setResizable(false);
		
		inicialize();

	}

	/**
	 * Body of the page 
	 */
	private void inicialize() { 

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");

		/**
		 *  Research Fields:
		 *  
		 *  ID
		 *  NIF
		 *  Nome
		 *  Morada
		 *  Ativo
		 *  Botão Pesquisar
		 */ 
		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(96, 77, 453, 221);
		pane.add(painelPesquisa);
		
		labelsPesquisaSetup();
		textFieldsPesquisaSetup();
		
		lblCampoPesquisa = new JLabel("Campo de Pesquisa");
		lblCampoPesquisa.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCampoPesquisa.setBounds(96, 39, 294, 26);
		pane.add(lblCampoPesquisa);

		painelPesquisa();
		botaoPesquisaSetup();

		/*
		 *  Buttons of the página:
		 *  
		 *  Atribuir Pacote
		 *  Atribuir Promoção
		 *  Visualizar Pacote
		 *  Visualizar Promoção 
		 *  Remover Promoção
		 *  Ver Historico
		 *  Voltar
		 *  
		 */

		botaoAtribuirPacote();
		botaoAtribuirPromocao();	
		botaoVisualizarPacote();
		botaoVisualizarPromocao();
		botaoRemoverPromocao();
		botaoVisualizarHistoricoSetup();
		btVoltarOperadorGerirClientes();
		
		/**
		 * Results table:
		 * 
		 * Tabela
		 * ScrollPane
		 * Label Resultados
		 */

		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(SystemColor.window);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		pane.add(painelTabela);
		scrollPaneSetup();
		JScrollPane scrollPane = scrollPaneSetup();
		painelTabela.add(scrollPane);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
		painelTabela.add(lblResultados);

		/**
		 * Footer: 
		 * 
		 * Imagem de rodapé
		 * Temporizador
		 * 
		 */

		JLabel lbFooter = new JLabel();
		lbFooter.setIcon(new ImageIcon(Operador_gerirClientes.class.getResource("/guiComponentes/img/AltranOperadorFooter.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		pane.add(lbFooter);

		timerSetup();

	}// end initialize

	/**
	 * Interface setup, look and feel Nimbus 
	 */
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

	/**
	 * /**
	 * Configuration of search labels
	 * @labelID
	 * @labelNIF
	 * @labelNome
	 */
	private void labelsPesquisaSetup() {
		labelID = new JLabel("ID");
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelID.setBounds(6, 15, 39, 18);
		painelPesquisa.add(labelID);

		labelNIF = new JLabel("NIF");
		labelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelNIF.setBounds(6, 49, 56, 18);
		painelPesquisa.add(labelNIF);

		labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelNome.setBounds(6, 87, 56, 18);
		painelPesquisa.add(labelNome);

		labelMorada = new JLabel("Morada");
		labelMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelMorada.setBounds(6, 116, 56, 27);
		painelPesquisa.add(labelMorada);

	}

	/**
	 * Configuration of the textFields search and "Activo" checkbox
	 * @textPesquisaID
	 * @textFieldNome
	 * @textPesquisaNIF
	 * @checkboxAtivo
	 */

	private void textFieldsPesquisaSetup() {
		textPesquisaID = new JTextField("");
		textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);

		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textPesquisaNIF.setColumns(10);
		textPesquisaNIF.setBounds(72, 40, 371, 27);
		painelPesquisa.add(textPesquisaNIF);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 78, 371, 27);
		painelPesquisa.add(textFieldNome);

		textFieldMorada = new JTextField();
		textFieldMorada.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textFieldMorada.setColumns(10);
		textFieldMorada.setBounds(72, 116, 371, 27);
		painelPesquisa.add(textFieldMorada);

		// Checkbox Ativo

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setBounds(234, 150, 69, 24);
		painelPesquisa.add(checkBoxAtivo);

	}


	/**
	 * Dynamic search button configuration. 
	 * It's possible to search for multiple fields at the same time.
	 * @botaoPesquisa 
	 */
	private void botaoPesquisaSetup() {
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBackground(Color.LIGHT_GRAY);
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		botaoPesquisa.setBounds(72, 181, 371, 27);
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

					ClientePesquisaModelTableOP model = new ClientePesquisaModelTableOP(clientes);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}



			}
		});
		painelPesquisa.add(botaoPesquisa);

	}


	/**
	 * Creation of the Visualizar Pacote Button. 
	 * When pressed, provided that a cliente is selected and has a pacote comercial assigned,
	 * opens a window with the name and description of the pacote. 
	 * If no cliente is selected, the button is not selectable either and it gives an error message. 
	 * @botaoVisualizarPacote - opens Visualizar Pacotes window
	 */
	public void botaoVisualizarPacote() {
		btnVisualizarPacote = new JButton("Visualizar Pacote Comercial");
		btnVisualizarPacote.setBackground(Color.LIGHT_GRAY);
		btnVisualizarPacote.setEnabled(false);
		btnVisualizarPacote.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btnVisualizarPacote.setBounds(1113, 256, 209, 40);
		pane.add(btnVisualizarPacote);
		btnVisualizarPacote.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getValueAt(table.getSelectedRow(), 6 ).equals("Não Atribuido")) {

					JOptionPane.showMessageDialog(Operador_gerirClientes.this,
							"Não existe Pacote Comercial Atribuido", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					int row = table.getSelectedRow();
					Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTableOP.OBJECT_COL);
					PacoteComercial pacoteComercial = null;

					try {
						pacoteComercial = GestorDeDAO.getGestorDeDAO().getPacoteClienteInfo(clienteTemp.getId_pacote_cliente());
					} catch (Exception e1) {

						e1.printStackTrace();
					}

					Operador_visualizarDialog dialog = new Operador_visualizarDialog(pacoteComercial, clienteTemp.getNome());
					dialog.setVisible(true);
					dialog.setResizable(false);
				}


			}
		});
	}

	/**
	 * Creation of the button Visualizar Promoção
	 * When pressed, provided that a customer is selected and has promoções, opens
	 * a window with the name and description of the promoção.
	 * If no cliente is selected, the button is not selectable either and it gives an error message. 
	 * @botaoVisualizarPromocao - opens Visualizar Promoções window
	 */
	public void botaoVisualizarPromocao() {
		btVisualizarPromocao = new JButton("Visualizar Promoções");
		btVisualizarPromocao.setBackground(Color.LIGHT_GRAY);
		btVisualizarPromocao.setEnabled(false);
		btVisualizarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btVisualizarPromocao.setBounds(1113, 205, 209, 40);
		pane.add(btVisualizarPromocao);
		btVisualizarPromocao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTableOP.OBJECT_COL);
				List<Promocao> listaPromocao = null;

				try {
					listaPromocao = GestorDeDAO.getGestorDeDAO().getPacoteClientePromocaoInfo(clienteTemp.getId_pacote_cliente());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if(listaPromocao.isEmpty()) {
					JOptionPane.showMessageDialog(Operador_gerirClientes.this,
							"Não existem Promoções Atribuidas!", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					Operador_visualizarDialog dialog = new Operador_visualizarDialog(listaPromocao, true, clienteTemp.getNome());
					dialog.setVisible(true);
					dialog.setResizable(false);
				}


			}
		});
	}

	/**
	 * RemoverPromocao button setup. 
	 * If the cliente has a promoção, a window opens that allows the selection of the promoção to remove. 
	 * Sends message "Cliente removido com sucesso". 
	 * If no cliente is selected, the button is not selectable either and it gives an error message. 
	 * @btnRemoverPromo - Dynamic button (Ativo/Desativo)
	 */
	public void botaoRemoverPromocao() {
		btnRemoverPromo = new JButton("Remover Promoção");
		btnRemoverPromo.setBackground(Color.LIGHT_GRAY);
		btnRemoverPromo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btnRemoverPromo.setEnabled(false);
		btnRemoverPromo.setBounds(719, 205, 187, 40);
		pane.add(btnRemoverPromo);
		btnRemoverPromo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<Promocao> promocoes = null;
				int row = table.getSelectedRow();
				Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTableOP.OBJECT_COL);

				try {
					promocoes = GestorDeDAO.getGestorDeDAO().getPacoteClientePromocaoInfo(clienteTemp.getId_pacote_cliente());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if(!promocoes.isEmpty()) {
					Operador_removerPromoDialog dialog = new Operador_removerPromoDialog(Operador_gerirClientes.this, promocoes, clienteTemp);
					dialog.setVisible(true);
					dialog.setResizable(false);
				}else {
					JOptionPane.showMessageDialog(Operador_gerirClientes.this,
							"Não existem Promoções Atribuidas!", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
	}

	/**
	 * Atribuir Promoção button setup. 
	 * When pressed, opens the "Atribuir Promoção" dialog window, and displays a comboBox
	 * where the available promoções can be selected. 
	 * If no cliente is selected, the button is not selectable either and it gives an error message. 
	 * @botaoAtribuirPromocao - opens the Operador_atribuir Dialog window 
	 */

	public void botaoAtribuirPromocao() {
		btAtribuirPromocao = new JButton("Atribuir Promoção");
		btAtribuirPromocao.setBackground(Color.LIGHT_GRAY);
		btAtribuirPromocao.setEnabled(false);
		btAtribuirPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btAtribuirPromocao.setBounds(916, 205, 187, 40);
		pane.add(btAtribuirPromocao);
		btAtribuirPromocao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<Promocao> promocoes = null;
				int row = table.getSelectedRow();
				Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTableOP.OBJECT_COL);

				try {
					promocoes = GestorDeDAO.getGestorDeDAO().getAllPromocoes();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				Operador_atribuirDialog dialog = new Operador_atribuirDialog(Operador_gerirClientes.this, promocoes, clienteTemp, true);
				dialog.setVisible(true);
				dialog.setResizable(false);

			}
		});
	}

	/**
	 * Atribuir pacote button setup. 
	 * When pressed, opens the "Atribuir Pacote Comercial" dialog window, that displays a comboBox where the available pacotes comerciais can be selected. 
	 * If no cliente is selected, the button is not selectable either and it gives an error message. 
	 * @btAtribuirPacote - opens the Operador_atribuir dialog window 
	 */
	private void botaoAtribuirPacote() {
		btAtribuirPacote = new JButton("Atribuir Pacote Comercial");
		btAtribuirPacote.setBackground(Color.LIGHT_GRAY);
		btAtribuirPacote.setEnabled(false);
		btAtribuirPacote.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btAtribuirPacote.setBounds(916, 256, 187, 40);
		pane.add(btAtribuirPacote);

		btAtribuirPacote.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<PacoteComercial> pacotes = null;
				int row = table.getSelectedRow();
				Cliente clienteTemp = (Cliente) table.getValueAt(row, ClientePesquisaModelTableOP.OBJECT_COL);
				Funcionario func = null;
				
				if(!clienteTemp.isAtivo()) {
					JOptionPane.showMessageDialog(Operador_gerirClientes.this,
							"Não possivel é atribuir Pacote Comercial pois o cliente não se encontra ativo!", "Cliente Inativo",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(clienteTemp.getId_pacote_cliente()==0) {
					try {
						pacotes = GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					Operador_atribuirDialog dialog = new Operador_atribuirDialog(Operador_gerirClientes.this, pacotes, clienteTemp);
					dialog.setVisible(true);
					dialog.setResizable(false);
				} else {
					int resposta = JOptionPane.showConfirmDialog(Operador_gerirClientes.this,
							"Remover Pacote Comercial?\nINFO: Todas as Promoções associadas também serão removidas!", "Confirmar Remover Pacote Comercial",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}else {

						try {
							func = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
							GestorDeDAO.getGestorDeDAO().eliminarPacoteById(clienteTemp.getId_pacote_cliente(), func, clienteTemp);
							JOptionPane.showMessageDialog(Operador_gerirClientes.this,
									"Pacote Comercial removido com sucesso!", "Pacote Comercial Removido",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							e1.printStackTrace();
						}


					}

					refreshClienteTable();
				}
			}
		});

	}

	/**
	 * Visualizar Historico button setup. 
	 * When pressed, provided that a cliente is selected, it opens a table with the 
	 * history of all the changes made to the cliente.
	 * If no cliente is selected, the button is not selectable either and it gives an error message. 
	 * 
	 * @botaoVisualizarHistorico - opens histórico do cliente window
	 */
	private void botaoVisualizarHistoricoSetup() {
		btnVerHistorico = new JButton("Ver Historico");
		btnVerHistorico.setBackground(Color.LIGHT_GRAY);
		btnVerHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btnVerHistorico.setEnabled(false);
		btnVerHistorico.setBounds(719, 256, 187, 40);
		btnVerHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();


				if (row < 0) {
					JOptionPane.showMessageDialog(Operador_gerirClientes.this,
							"Por favor selecione um Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
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
		pane.add(btnVerHistorico);
	}

	

	/**
	 * Voltar button setup.
	 * When pressed it returns to the Operador homepage.
	 * @btVoltarGerirClientes - returns to the Operador homepage.
	 */
	private void btVoltarOperadorGerirClientes() {
		btVoltarOperador = new JButton("Voltar");
		btVoltarOperador.setBounds(6, 709, 119, 38);
		btVoltarOperador.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btVoltarOperador.setBackground(Color.LIGHT_GRAY);
		btVoltarOperador.setFocusPainted(false);
		pane.add(btVoltarOperador);

	}


	/**
	 * ScrollPane of the table
	 * @return scrollPane
	 */
	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setFocusable(false);
		table.setSelectionBackground(new Color(250,235,70));
		table.setSelectionForeground(Color.black);
		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					btAtribuirPacote.setEnabled(false);
					btAtribuirPromocao.setEnabled(false);
					btVisualizarPromocao.setEnabled(false);
					btnVerHistorico.setEnabled(false);
					btnVisualizarPacote.setEnabled(false);
					btnRemoverPromo.setEnabled(false);
				}
				else if (table.getSelectedRows().length==1) {


					if(table.getValueAt(table.getSelectedRow(), 6 ).equals("Não Atribuido")) {
						btAtribuirPromocao.setEnabled(false);
						btnVisualizarPacote.setEnabled(false);
						btnRemoverPromo.setEnabled(false);
						btVisualizarPromocao.setEnabled(false);
						btnVisualizarPacote.setEnabled(false);
					}else {
						btAtribuirPromocao.setEnabled(true);
						btnVisualizarPacote.setEnabled(true);
						btnRemoverPromo.setEnabled(true);
						btVisualizarPromocao.setEnabled(true);
						btnVisualizarPacote.setEnabled(true);
					}

					btAtribuirPacote.setEnabled(true);
					btnVerHistorico.setEnabled(true);
					botaoAtribuirDinamico();
				}
				else if (table.getSelectedRowCount()==0)
				{
					btnVisualizarPacote.setEnabled(false);
					btAtribuirPacote.setEnabled(false);
					btAtribuirPromocao.setEnabled(false);
					btVisualizarPromocao.setEnabled(false);
					btnVerHistorico.setEnabled(false);
					btnRemoverPromo.setEnabled(false);
				}

			}	
		});
		return scrollPane;
	}

	/**
	 * When changes are made in the cliente the table is updated. 
	 */
	public void refreshClienteTable() {

		try {
			List<Cliente> clientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
			ClientePesquisaModelTableOP model = new ClientePesquisaModelTableOP(clientes);
			table.setModel(model);
			numberRows = table.getRowCount();
			lblResultados.setText("Resultados: " + numberRows);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Erro: " + exc, "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Dynamic button remover/atribuir pacote.
	 * When the cliente is ativo, the button shows "Remover Pacote Comercial", 
	 * when the cliente is desativo, the button shows "Atribuir Pacote Comercial"
	 * @botaoAtribuirDinamico - Dynamic button
	 */
	private void botaoAtribuirDinamico() {

		try {
			int row = table.getSelectedRow();
			Cliente cliente = (Cliente) table.getValueAt(row, ClientePesquisaModelTable.OBJECT_COL);
			if (cliente.getId_pacote_cliente()!=0)
				btAtribuirPacote.setText("Remover Pacote Comercial");
			else
				btAtribuirPacote.setText("Atribuir Pacote Comercial");
		} catch  (Exception e) {
		}
	}
	
	/**
	 * Labels username and tempo de sessão setup.
	 * @lblUsernameLogged displays the username that is logged in 
	 * @lblTempoSessao displays the session time since the moment that the user logged
	 * @lblHoraSistema displays the actual hour of the system 
	 */
	private void timerSetup() {
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		pane.add(lblTempoSessao);

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		pane.add(lblUsernameLogged);

		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		pane.add(lblHoraSistema);

	}
	

	/**
	 * Painel de Pesquisa
	 */
	protected void painelPesquisa() {
	}
	
	/**
	 * @return table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * 
	 * @return btVoltarOperador
	 */
	public JButton btVoltarOperador() {
		return btVoltarOperador;
	}
	
	/**
	 * 
	 * @param botaoVoltar2
	 * @return botaoVoltar2
	 */
	public void setBtVoltarOperador(JButton botaoVoltar2) {
		this.btVoltarOperador = botaoVoltar2;
	}

	/**
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);

	}

	/**
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * @param agora
	 */
	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}

	/**
	 * 
	 * @return panel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	/**
	 * @return lblResultados
	 */
	public JLabel getLblResultados() {
		return lblResultados;
	}
	
	/***
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the textPesquisaID
	 */
	public JTextField getTextPesquisaID() {
		return textPesquisaID;
	}

	/**
	 * @return the textPesquisaNIF
	 */
	public JTextField getTextPesquisaNIF() {
		return textPesquisaNIF;
	}

	/**
	 * @return the textFieldNome
	 */
	public JTextField getTextFieldNome() {
		return textFieldNome;
	}

	/**
	 * @return the textFieldMorada
	 */
	public JTextField getTextFieldMorada() {
		return textFieldMorada;
	}


}
