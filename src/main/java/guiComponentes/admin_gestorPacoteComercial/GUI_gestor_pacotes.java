
package guiComponentes.admin_gestorPacoteComercial;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import servico.GestorDeDAO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;

import guiComponentes.GUI_total;
import historicos.HistoricoPacoteComercial;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Component;

public class GUI_gestor_pacotes extends JFrame {
	private static final long serialVersionUID = 1L;
	private int numberRows;
	private int indice;
	private JTable table;
	private JButton btVoltarGestorPacotes, botaoDesativarPacoteComercial, botaoEditarPacoteComercial, botaoVisualizarHistorico, btPesquisar;
	private JPanel contentPane, painelPesquisa;
	private JLabel lblResultados, lblUsernameLogged, lblTempoSessao, lblHoraSistema, labelID, labelNome;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextField textPesquisaID, textFieldNome;
	private JCheckBox checkBoxAtivo;
	private JTextArea textAreaDescricao;

	/**
	 * Construtor que inicia com o método que configura o painel base e o método inicialize, 
	 * que contém todos os métodos e elementos que compõem a página 
	 */
	public GUI_gestor_pacotes() {
		
		ativarNimbusLookAndFeel();
		
		setTitle("Pesquisa de Pacotes Comerciais");
		setBounds(100, 30, 1400, 800);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setBackground(Color.WHITE);
		
		inicialize();
	}
	
	/**
	 * Contém o corpo da página
	 */
	protected void inicialize() {

		/*
		 *  Botões da página:
		 *  
		 *  Criar Pacote Comercial 
		 *  Editar Pacote Comercial
		 *  Desativar Pacote Comercial
		 *  Visualizar Historico
		 *  Voltar 
		 *  TextArea Descrição
		 */

		JButton botaoCriarPacotes = botaoCriarPacotesSetup();
		getContentPane().add(botaoCriarPacotes);

		botaoEditarPacoteComercialSetup();
		getContentPane().add(botaoEditarPacoteComercial);

		botaoDesativarPacoteComercialSetup();
		getContentPane().add(botaoDesativarPacoteComercial);
		
		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);

		btVoltarGestorPacotesSetup();
		getContentPane().add(btVoltarGestorPacotes);

		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(905, 71, 367, 151);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setEditable(false);
		contentPane.add(textAreaDescricao);

		/**
		 *  Campos de Pesquisa:
		 *  
		 *  ID
		 *  Nome
		 *  Ativo
		 *  Botão Pesquisar
		 */ 
		
		JLabel lblCamposPesquisa = new JLabel("Campo de Pesquisa");
		lblCamposPesquisa.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisa.setBounds(98, 50, 294, 26);
		contentPane.add(lblCamposPesquisa);
		
		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 104, 453, 175);
		contentPane.add(painelPesquisa);

		painelPesquisa();

		/**
		 * Tabela de Resultados:
		 * 
		 * Tabela
		 * ScrollPane
		 * Label Resultados
		 */

		JPanel panel = paneldaTabelaSetup();
		getContentPane().add(panel);

		JScrollPane scrollPane = scrollPaneSetup();
		panel.add(scrollPane);
		scrollPane.setViewportView(table);
		
		tableSetup();

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
		panel.add(lblResultados);
	
		/**
		 * Footer: 
		 * 
		 * Imagem de rodapé
		 * Temporizador
		 * 
		 */

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_pacotes.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 213, 65);
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
	 * Configuração do botão criar Pacote Comercial.
	 * Quando premido, abre a janela Dialog "CriarPacotesDialog", 
	 * para preencher os dados do novo Pacote Comercial.
	 * 
	 * @return botaoCriarPacotes
	 * @botaoCriarPacotes - abre janela CriarPacotesDialog
	 */
	private JButton botaoCriarPacotesSetup() {
		JButton botaoCriarPacotes = new JButton("Criar Pacote Comercial");
		botaoCriarPacotes.setBounds(609, 179, 218, 43);
		botaoCriarPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoCriarPacotes.setBackground(SystemColor.activeCaption);
		botaoCriarPacotes.setFocusPainted(false);
		botaoCriarPacotes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarPacotesDialog dialog = new CriarPacotesDialog(GUI_gestor_pacotes.this);
				dialog.setVisible(true);
				dialog.setResizable(false);
			}
		});
		return botaoCriarPacotes;
	}
	
	/**
	 * Configuração do botão editar Pacote Comercial.
	 * Quando premido, abre a janela Dialog "CriarPacotesDialog", 
	 * com os campos pré-preenchidos com a informação anterior mas editáveis.
	 * Se não está nenhum pacote selecionado, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja um pacote selecionado.
	 * @botaoEditarPacoteComercial - abre a CriarPacotesDialog editável
	 */
	private void botaoEditarPacoteComercialSetup() {
		botaoEditarPacoteComercial = new JButton("Editar Pacote Comercial");
		botaoEditarPacoteComercial.setBounds(609, 125, 218, 43);
		botaoEditarPacoteComercial.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoEditarPacoteComercial.setBackground(SystemColor.activeCaption);
		botaoEditarPacoteComercial.setFocusPainted(false);
		botaoEditarPacoteComercial.setEnabled(false);
		botaoEditarPacoteComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Por favor selecione um Pacote Comercial", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				PacoteComercial pacoteComercialTemp = (PacoteComercial) table.getValueAt(row,
						PacoteComercialPesquisaModelTable.OBJECT_COL);

				CriarPacotesDialog dialog =	new CriarPacotesDialog(GUI_gestor_pacotes.this, pacoteComercialTemp, true);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
		});
	}
	
	/**
	 * Configuração do botão desativar Pacote Comercial.
	 * Quando premido, faz a confirmação da ação. 
	 * Se o Pacote Comercial estiver ativo o botão indica "desativar", se o Pacote Comercial está desativo
	 * o botão indica "ativar".
	 * Se não está nenhum Pacote Comercial selecionado, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja um Pacote Comercial selecionado.
	 * @botaoDesativarPacoteComercial - botão dinâmico (ativo/desativo)
	 */
	private void botaoDesativarPacoteComercialSetup() {
		botaoDesativarPacoteComercial = new JButton("Desativar Pacote Comercial");
		botaoDesativarPacoteComercial.setBounds(609, 71, 218, 43);
		botaoDesativarPacoteComercial.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoDesativarPacoteComercial.setBackground(SystemColor.activeCaption);
		botaoDesativarPacoteComercial.setFocusPainted(false);
		botaoDesativarPacoteComercial.setEnabled(false);
		botaoDesativarPacoteComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					indice = table.getSelectedRow();

					if (indice < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
								"Por favor selecione um Pacote", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					PacoteComercial pacoteTemp = (PacoteComercial) table.getValueAt(indice,	PacoteComercialPesquisaModelTable.OBJECT_COL);

					if(pacoteTemp.isAtivo()) {

						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_pacotes.this,
								"Desativar este Pacote Comercial?", "Confirmar Desativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().desativarPacoteComercial(pacoteTemp.getId(),admin );

						JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
								"Pacote Comercial Desativado com sucesso", "Pacote Comercial Desativado",
								JOptionPane.INFORMATION_MESSAGE);

						refreshPacotesTable();
					} else {

						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_pacotes.this,
								"Ativar este Pacote Comercial?", "Confirmar Ativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().ativarPacoteComercial(pacoteTemp.getId(), admin );

						JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
								"Pacote Comercial Ativado com sucesso", "Pacote Comercial Ativado",
								JOptionPane.INFORMATION_MESSAGE);

						refreshPacotesTable();
					}
				} catch (Exception e1) {

				}
			}

		});
	}
	
	/**
	 * Criação do botão Visualizar Histórico. 
	 * Quando premido, desde que um Pacote Comercial esteja selecionado, abre uma tabela com o 
	 * histórico das alterações feitas no Pacote Comercial selecionado. 
	 * Se não está nenhum Pacote Comercial selecionado, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja um Pacote Comercial selecionado. 
	 * @botaoVisualizarHistorico - abre janela com histórico de operações
	 */
	private void botaoVisualizarHistoricoSetup() {
		botaoVisualizarHistorico = new JButton("Visualizar Historico");
		botaoVisualizarHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();


				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Por favor selecione um Pacote Comercial", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				PacoteComercial pacoteComercial = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);
				List<HistoricoPacoteComercial> list;

				try {

					list = GestorDeDAO.getGestorDeDAO().getHistoricoPacoteComercial(pacoteComercial.getId());
					HistoricoPacoteComercialDialog dialogHistorico = new HistoricoPacoteComercialDialog();
					dialogHistorico.preencherTable(pacoteComercial, list);
					dialogHistorico.setVisible(true);
					dialogHistorico.setResizable(false);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		botaoVisualizarHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoVisualizarHistorico.setBackground(SystemColor.activeCaption);
		botaoVisualizarHistorico.setBounds(609, 236, 218, 43);
		botaoVisualizarHistorico.setEnabled(false);
	}
	
	/**
	 * Configuração do botão voltar.
	 * Quando premido volta à homepage do Administrador.
	 * @btVoltarGestorPacotes - volta à homepage de Administrador
	 */
	private void btVoltarGestorPacotesSetup() {
		btVoltarGestorPacotes = new JButton("Voltar");
		btVoltarGestorPacotes.setBounds(6, 709, 119, 38);
		btVoltarGestorPacotes.setFont(font);
		btVoltarGestorPacotes.setBackground(SystemColor.activeCaption);
		btVoltarGestorPacotes.setFocusPainted(false);
	}

	/**
	 * /**
	 * Configuração das labels de pesquisa e do botão de pesquisa dinâmico. 
	 * @labelID
	 * @labelNome
	 * @textPesquisaID
	 * @textFieldNome
	 * @checkBoxAtivo
	 * @btPesquisar
	 */
	protected void painelPesquisa() {

		labelID = new JLabel("ID");
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelID.setBounds(6, 15, 39, 18);
		painelPesquisa.add(labelID);
		
		labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelNome.setBounds(6, 44, 56, 27);
		painelPesquisa.add(labelNome);

		textPesquisaID = new JTextField();
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);

		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 44, 371, 27);
		painelPesquisa.add(textFieldNome);

		// CheckBox Ativo 
		
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setBounds(232, 78, 69, 24);
		painelPesquisa.add(checkBoxAtivo);

		btPesquisar = new JButton("Pesquisar");
		btPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = 0;
					String nome = null;
					int ativo = checkBoxAtivo.isSelected() ? 1 : 0;

					if (!textPesquisaID.getText().isBlank()) {
						id = Integer.parseInt(textPesquisaID.getText().trim());
					}

					if (!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText().trim();
					}

					List<PacoteComercial> pacotesComerciais = null;

					if ((id != 0) || (nome != null) || (ativo != 0)) {
						pacotesComerciais = GestorDeDAO.getGestorDeDAO().pesquisaPacoteComercial(id,
								nome, ativo);
					} else {
						pacotesComerciais = GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
					}

					PacoteComercialPesquisaModelTable model =
							new PacoteComercialPesquisaModelTable(pacotesComerciais);
					table.setModel(model);

					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}
			}
		});
		btPesquisar.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btPesquisar.setBackground(SystemColor.activeCaption);
		btPesquisar.setBounds(72, 121, 371, 27);
		painelPesquisa.add(btPesquisar);
	}
	
	/**
	 * Configuração do painel que contém tabela de resultados
	 * @return painel da Tabela
	 */
	private JPanel paneldaTabelaSetup() {
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
	 * exista um pacote comercial selecionado ou não. 
	 * Aqui está também referenciado o método do botão dinâmico (ativar/desativar).
	 * @table
	 */
	private void tableSetup() {
		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
				new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount() > 1) {
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(true);
					botaoVisualizarHistorico.setEnabled(false);
				} else if (table.getSelectedRows().length == 1) {
					int row = table.getSelectedRow();
					botaoEditarPacoteComercial.setEnabled(true);
					botaoDesativarPacoteComercial.setEnabled(true);
					PacoteComercial pacoteComercial = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);
					textAreaDescricao.setText(pacoteComercial.getDescricao());
					botaoAtivarDinamico();
					botaoVisualizarHistorico.setEnabled(true);
				} else if (table.getSelectedRowCount() == 0) {
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(false);
					botaoVisualizarHistorico.setEnabled(false);
				}

			}
		});
	}
	
	/**
	 * Botão ativo/desativo dinâmico. 
	 * Quando o pacote comercial está ativo o botão apresenta "desativar".
	 * Quando o pacote comercial está desativo o botão apresenta "ativar".
	 * @botaoDesativarPacoteComercial - botao dinâmico
	 */
	private void botaoAtivarDinamico() {

		try {
			int row = table.getSelectedRow();
			PacoteComercial pacoteComercial = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);
			if (pacoteComercial.isAtivo())
				botaoDesativarPacoteComercial.setText("Desativar Pacote Comercial");
			else
				botaoDesativarPacoteComercial.setText("Ativar Pacote Comercial");
		} catch  (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configuração das labels de username e temporização.
	 * @lblUsernameLogged apresenta o username que está logado
	 * @lblTempoSessao apresenta o tempo de sessão desde o momento que faz login
	 * @lblHoraSistema apresenta a hora atual do sistema 
	 */
	protected void timerSetup() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblUsernameLogged);
		
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblTempoSessao);
		
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblHoraSistema);
	}
	
	/**
	 * Ao fazer alterações no pacote comercial a tabela é atualizada. 
	 */
	public void refreshPacotesTable() {

		try {
			List<PacoteComercial> pacotesComerciais =
					GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
			PacoteComercialPesquisaModelTable model =
					new PacoteComercialPesquisaModelTable(pacotesComerciais);
			table.setModel(model);

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
	 * @return botão Voltar Gestor Pacotes
	 */
	public JButton btVoltarGestorPacotes() {
		return btVoltarGestorPacotes;
	}

	/**
	 * @return botao Voltar
	 * @param botaoVoltar2
	 */
	public void setBtVoltarGestorPacotes(JButton botaoVoltar2) {
		this.btVoltarGestorPacotes = botaoVoltar2;
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
		lblTempoSessao.setText(
				"Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart());;
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
	
	/** 
	 * @return textAreaDescricao
	 */
	public JTextArea getTextAreaDescricao() {
		return textAreaDescricao;
	}

}
