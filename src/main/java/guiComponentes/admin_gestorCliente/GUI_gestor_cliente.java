package guiComponentes.admin_gestorCliente;

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
	private JPanel contentPane, painelPesquisa ;	
	private JLabel lblResultados, lblUsernameLogged, lblCampoPesquisa, labelID, labelNIF, labelNome, labelMorada, lblTempoSessao, lblHoraSistema ;
	private JButton botaoDesativarCliente, botaoEditarCliente, botaoVisualizarHistorico, botaoPesquisa, btVoltarGestorCliente;
	private JTextField textFieldID, textFieldNIF,textFieldNome,textFieldMorada;
	private JCheckBox checkBoxAtivo;

	/**
	 * Construtor que inicia com o método que configura o painel base e o método inicialize, 
	 * que contém todos os métodos e elementos que compõem a página 
	 */
	public GUI_gestor_cliente() {

		ativarNimbusLookAndFeel();
		setTitle("Pesquisa de Clientes");
		setBounds(100, 30, 1400, 800);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setBackground(Color.WHITE);

		inicialize();
	}

	/**
	 * Contém o corpo da página
	 */
	private void inicialize() {

		/*
		 *  Botões da página:
		 *  
		 *  Criar Cliente 
		 *  Editar Cliente
		 *  Desativar Cliente
		 *  Visualizar Historico
		 *  Voltar 
		 */

		JButton botaoCriarCliente = botaoCriarClienteSetup();
		getContentPane().add(botaoCriarCliente);

		botaoEditarClienteSetup();
		getContentPane().add(botaoEditarCliente);

		botaoDesativarClienteSetup();
		getContentPane().add(botaoDesativarCliente);

		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);

		btVoltarGestorClienteSetup();
		getContentPane().add(btVoltarGestorCliente);

		/**
		 *  Campos de Pesquisa:
		 *  
		 *  ID
		 *  NIF
		 *  Nome
		 *  Morada 
		 *  Ativo
		 *  Botão Pesquisar
		 */

		lblCampoPesquisa = new JLabel("Campo de Pesquisa");
		lblCampoPesquisa.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCampoPesquisa.setBounds(98, 26, 294, 26);
		contentPane.add(lblCampoPesquisa);

		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 63, 453, 221);
		contentPane.add(painelPesquisa);
		
		labelsPesquisaSetup();
		
		textFieldsPesquisaSetup();

		botaoPesquisaSetup();
		painelPesquisa.add(botaoPesquisa);
		

		/**
		 * Tabela de Resultados:
		 * 
		 * Tabela
		 * ScrollPane
		 * Label Resultados
		 */

		JPanel panelDaTable = panelDaTableSetup();
		getContentPane().add(panelDaTable);

		JScrollPane scrollPane = scrollPaneSetup();
		panelDaTable.add(scrollPane);

		tableSetup();
		scrollPane.setViewportView(table);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblResultados.setBounds(33, 6, 136, 25);
		panelDaTable.add(lblResultados);		

		/**
		 * Footer:
		 * 
		 * Imagem de rodapé
		 * Temporizador
		 */

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_cliente.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 214, 65);
		contentPane.add(lbFooter);

		timerSetup();
	}

	/**
	 * Configurar interface, look and feel Nimbus 
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
	 *
	 * Configuração do botão criar cliente.
	 * Quando premido, abre a janela Dialog "CriarClienteDialog", 
	 * para preencher os dados do novo cliente.
	 * @return botaoCriarCliente 
	 * @botaoCriarCliente - abre janela CriarClienteDialog
	 * 
	 */
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

	/**
	 * Configuração do botão editar cliente.
	 * Quando premido, abre a janela Dialog "CriarClienteDialog", 
	 * com os campos pré-preenchidos com a informação anterior mas editáveis, para se poder alterar.
	 * Se não está nenhum cliente selecionado, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja um cliente selecionado.
	 * @botaoEditarCliente - abre a CriarClienteDialog editável
	 */
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

	/**
	 * Configuração do botão desativar cliente.
	 * Quando premido, faz a confirmação da ação. 
	 * Se o cliente estiver ativo o botão indica "desativar", se o cliente está desativo
	 * o botão indica "ativar".
	 * Se não está nenhum cliente selecionado, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja um cliente selecionado.
	 * @botaoDesativarCliente - botão dinâmico (ativo/desativo)
	 */
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


	/**
	 * Criação do botão Visualizar Histórico. 
	 * Quando premido, desde que um cliente esteja selecionado, abre uma tabela com o 
	 * histórico das alterações feitas no cliente selecionado. 
	 * Se não está nenhum cliente selecionado, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja um cliente selecionado. 
	 * @botaoVisualizarHistorico - abre janela com histórico de operações
	 */
	private void botaoVisualizarHistoricoSetup() {
		botaoVisualizarHistorico = new JButton("Ver Historico");
		botaoVisualizarHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoVisualizarHistorico.setBackground(SystemColor.activeCaption);
		botaoVisualizarHistorico.setBounds(655, 263, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
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

	}

	/**
	 * Configuração do botão voltar.
	 * Quando premido volta à homepage de administrador.
	 * @btVoltarGestorCliente - volta à homepage de administrador
	 */
	private void btVoltarGestorClienteSetup() {
		btVoltarGestorCliente = new JButton("Voltar");
		btVoltarGestorCliente.setBounds(6, 709, 119, 38);
		btVoltarGestorCliente.setFont(font);
		btVoltarGestorCliente.setBackground(SystemColor.activeCaption);
		btVoltarGestorCliente.setFocusPainted(false);
	}


	/**
	 * Configuração das labels de pesquisa
	 * @labelID
	 * @labelNome
	 * @labelNIF
	 * @labelMorada
	 */
	private void labelsPesquisaSetup() {
		labelID = new JLabel("ID");
		labelID.setBounds(6, 15, 39, 18);
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		painelPesquisa.add(labelID);
		
		labelNIF = new JLabel("NIF");
		labelNIF.setBounds(6, 49, 56, 18);
		labelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		painelPesquisa.add(labelNIF);

		labelNome = new JLabel("Nome");
		labelNome.setBounds(6, 87, 56, 18);
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		painelPesquisa.add(labelNome);
		
		labelMorada = new JLabel("Morada");
		labelMorada.setBounds(6, 116, 56, 27);
		labelMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		painelPesquisa.add(labelMorada);
	}

	/**
	 * Configuração das textFields de Pesquisa e checkbox Ativo
	 * @textFieldID
	 * @textFieldNome
	 * @textFieldNIF
	 * @textFieldMorada
	 * @checkboxAtivo
	 */
	private void textFieldsPesquisaSetup() {
		textFieldID = new JTextField();
		textFieldID.setBounds(72, 6, 371, 27);
		textFieldID.setColumns(10);
		painelPesquisa.add(textFieldID);
		
		textFieldNIF = new JTextField();
		textFieldNIF.setBounds(72, 40, 371, 27);
		textFieldNIF.setColumns(10);
		painelPesquisa.add(textFieldNIF);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(72, 78, 371, 27);
		textFieldNome.setColumns(10);
		painelPesquisa.add(textFieldNome);
		
		textFieldMorada = new JTextField();
		textFieldMorada.setBounds(72, 116, 371, 27);
		textFieldMorada.setColumns(10);
		painelPesquisa.add(textFieldMorada);
		
		// Checkbox Ativo

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBounds(234, 150, 69, 24);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		painelPesquisa.add(checkBoxAtivo);
	}

	/**
	 * Configuração do botão de pesquisa dinâmico. 
	 * É possível pesquisar por vários campos ao mesmo tempo.
	 * @botaoPesquisa 
	 */
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

					if(!textFieldID.getText().isBlank()) {
						id = Integer.parseInt(textFieldID.getText().trim());
					}

					if(!textFieldNIF.getText().isBlank()) {
						nif = textFieldNIF.getText().trim();
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

	/**
	 * Configuração do painel que contém tabela de resultados
	 * @return painel da Tabela
	 */
	private JPanel panelDaTableSetup() {
		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(Color.WHITE);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		return painelTabela;
	}

	/**
	 * ScrollPane da tabela.
	 * @return scrollPane
	 */
	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		return scrollPane;
	}



	/**
	 * Configuração da tabela de resultados.
	 * Configuração de quais os botões selecionáveis consoante 
	 * exista cliente selecionado ou não. 
	 * Aqui está também referenciado o método do botão dinâmico (ativar/desativar).
	 * @table
	 */
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

	/**
	 * Botão ativo/desativo dinâmico. 
	 * Quando o cliente está ativo o botão apresenta "desativar".
	 * Quando o cliente está desativo o botão apresenta "ativar".
	 * @botaoDesativarCliente - botao dinâmico
	 */
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

	/**
	 * Configuração das labels de username e temporização.
	 * @lblUsernameLogged apresenta o username que está logado
	 * @lblTempoSessao apresenta o tempo de sessão desde o momento que faz login
	 * @lblHoraSistema apresenta a hora atual do sistema 
	 */
	protected void timerSetup() {

		// Label Username
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblUsernameLogged);

		// Label Tempo de Sessão 
		lblTempoSessao = new JLabel();
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblTempoSessao);

		// Label Hora de Sistema 
		lblHoraSistema = new JLabel();
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblHoraSistema);

	}

	/**
	 * Ao fazer alterações no cliente a tabela é atualizada. 
	 */
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


	/**
	 * Retorna a tabela
	 * @return table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @return botão voltar
	 */
	public JButton btVoltarGestorCliente() {
		return btVoltarGestorCliente;
	}

	/**
	 * @return botao Voltar
	 * @param botaoVoltar
	 */
	public void setBtVoltarGestorCliente(JButton botaoVoltar) {
		this.btVoltarGestorCliente = botaoVoltar;
	}

	/**
	 * @return o painel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	/**
	 * Configura a label usernameLogged 
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);

	}

	/**
	 * Configura a label de temporizador. 
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * Configura a label de hora de sistema
	 * @param agora
	 */
	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);
	}

	/**
	 * @return lblResultados
	 */
	public JLabel getLblResultados() {
		return lblResultados;
	}

}
