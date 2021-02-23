package guiComponentes.admin_gestorPromocao;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import guiComponentes.admin_gestorPacoteComercial.GUI_gestor_pacotes;
import guiComponentes.admin_gestorPacoteComercial.HistoricoPacoteComercialDialog;
import guiComponentes.admin_gestorPacoteComercial.PacoteComercialPesquisaModelTable;
import historicos.HistoricoPacoteComercial;
import historicos.HistoricoPromocoes;
import servico.GestorDeDAO;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;
import javax.swing.JCheckBox;
import java.awt.Color;


public class GUI_gestor_promocao extends JFrame {

	private static final long serialVersionUID = 1L;
	private int numberRows;
	private int indice;
	private JPanel contentPane, painelPesquisa;
	private JTable table;
	private JButton btVoltarGestorPromocao, btPesquisar, botaoDesativarPromocao, botaoEditarPromocao,botaoVisualizarHistorico;
	private JLabel lblResultados, lblUsernameLogged, lblTempoSessao, lblHoraSistema, labelID, labelNome;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextField textPesquisaID, textFieldNome;
	private JCheckBox checkBoxAtiva;
	private JTextArea textAreaDescricao;

	/**
	 * Construtor que inicia com o método que configura o painel base e o método inicialize, 
	 * que contém todos os métodos e elementos que compõem a página 
	 */
	public  GUI_gestor_promocao() {

		ativarNimbusLookAndFeel();

		setTitle("Pesquisa de Promocao");
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

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");
		
		/*
		 *  Botões da página:
		 *  
		 *  Criar Promoção 
		 *  Editar Promoção
		 *  Desativar Promoção
		 *  Visualizar Historico
		 *  Voltar 
		 *  TextArea Descrição
		 */

		JButton botaoCriarPromocao = botaoCriarPromocaoSetup();
		getContentPane().add(botaoCriarPromocao);

		botaoEditarPromocaoSetup();
		getContentPane().add(botaoEditarPromocao);

		botaoDesativarPromocaoSetup();
		getContentPane().add(botaoDesativarPromocao);

		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);

		btVoltarGestorPromocaoSetup();
		getContentPane().add(btVoltarGestorPromocao);

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
		lblCamposPesquisa.setBounds(98, 26, 294, 26);
		contentPane.add(lblCamposPesquisa);

		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 87, 453, 171);
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
		lbFooter.setIcon(new ImageIcon(GUI_gestor_promocao.class.getResource("/guiComponentes/img/Altran1.1.png")));
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
	 * Configuração do botão criar Promoção.
	 * Quando premido, abre a janela Dialog "CriarPromocaoDialog", 
	 * para preencher os dados da nova Promoção.
	 * 
	 * @return botaoCriarPromocao
	 * @botaoCriarPacotes - abre janela CriarPromocaoDialog
	 */
	private JButton botaoCriarPromocaoSetup() {
		JButton botaoCriarPromocao = new JButton("Criar Promoção");
		botaoCriarPromocao.setBounds(609, 179, 231, 43);
		botaoCriarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoCriarPromocao.setBackground(SystemColor.activeCaption);
		botaoCriarPromocao.setFocusPainted(false);
		botaoCriarPromocao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarPromocaoDialog dialog = new CriarPromocaoDialog(GUI_gestor_promocao.this);
				dialog.setVisible(true);
				dialog.setResizable(false);
			}
		});

		return botaoCriarPromocao;
	}

	/**
	 * Configuração do botão editar Promoção.
	 * Quando premido, abre a janela Dialog "CriarPromocaoDialog", 
	 * com os campos pré-preenchidos com a informação anterior mas editáveis.
	 * Se não está nenhuma Promoção selecionada, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja uma promoção selecionada.
	 * @botaoEditarPromocao - abre a CriarPromocaoDialog editável
	 */
	private void botaoEditarPromocaoSetup() {
		botaoEditarPromocao = new JButton("Editar Promoção");
		botaoEditarPromocao.setBounds(609, 125, 231, 43);
		botaoEditarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoEditarPromocao.setBackground(SystemColor.activeCaption);
		botaoEditarPromocao.setFocusPainted(false);
		botaoEditarPromocao.setEnabled(false);
		botaoEditarPromocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
							"Por favor selecione um Promocao", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Promocao PromocaoTemp = (Promocao) table.getValueAt(row, PromocaoPesquisaModelTable.OBJECT_COL);

				CriarPromocaoDialog dialog = new CriarPromocaoDialog(GUI_gestor_promocao.this, PromocaoTemp, true);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
		});
	}

	/**
	 * Configuração do botão desativar Promoção.
	 * Quando premido, faz a confirmação da ação. 
	 * Se a Promoção estiver ativa o botão indica "desativar", se a Promoção está desativa
	 * o botão indica "ativar".
	 * Se não está nenhuma Promoção selecionada, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja uma Promoção selecionada.
	 * @botaoDesativarPromocao - botão dinâmico (ativo/desativo)
	 */
	private void botaoDesativarPromocaoSetup() {
		botaoDesativarPromocao = new JButton("Desativar Promoção");
		botaoDesativarPromocao.setBounds(609, 71, 231, 43);
		botaoDesativarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoDesativarPromocao.setBackground(SystemColor.activeCaption);
		botaoDesativarPromocao.setFocusPainted(false);
		botaoDesativarPromocao.setEnabled(false);
		botaoDesativarPromocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					indice = table.getSelectedRow();

					if (indice < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
								"Por favor selecione um Promocao", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					Promocao PromocaoTemp = (Promocao) table.getValueAt(indice, PromocaoPesquisaModelTable.OBJECT_COL);
					if(PromocaoTemp.isAtiva()){

						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_promocao.this,
								"Desativar este Promocao?", "Confirmar Desativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						GestorDeDAO.getGestorDeDAO().desativarPromocao(PromocaoTemp.getId());

						JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
								"Promocao Desativada com sucesso", "Promocao Desativada",
								JOptionPane.INFORMATION_MESSAGE);

						refreshPromocaoTable();

					}else {

						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_promocao.this,
								"Ativar este Promocao?", "Confirmar Ativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						GestorDeDAO.getGestorDeDAO().ativarPromocao(PromocaoTemp.getId());

						JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
								"Promocao Ativada com sucesso", "Promocao Ativada",
								JOptionPane.INFORMATION_MESSAGE);

						refreshPromocaoTable();

					}

				} catch (Exception e1) {

				}
			}

		});
	}

	/**
	 * Criação do botão Visualizar Histórico. 
	 * Quando premido, desde que uma Promoção esteja selecionada, abre uma tabela com o 
	 * histórico das alterações feitas na Promoção selecionada. 
	 * Se não está nenhuma Promoção selecionada, o botão não está selecionável, no entanto, 
	 * existe a validação para dar uma mensagem de erro caso não haja uma Promoção selecionado. 
	 * @botaoVisualizarHistorico - abre janela com histórico de operações
	 */
	private void botaoVisualizarHistoricoSetup() {
		botaoVisualizarHistorico = new JButton("Visualizar Historico");
		botaoVisualizarHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();


				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
							"Por favor selecione uma Promoção", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Promocao promocao = (Promocao) table.getValueAt(row, PromocaoPesquisaModelTable.OBJECT_COL);
				List<HistoricoPromocoes> list;

				try {

					list = GestorDeDAO.getGestorDeDAO().getHistoricoPromocao(promocao.getId());
					HistoricoPromocaoDialog dialogHistorico = new HistoricoPromocaoDialog();
					dialogHistorico.preencherTable(promocao, list);
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
	 * @btVoltarGestorPromocao - volta à homepage de Administrador
	 */
	private void btVoltarGestorPromocaoSetup() {
		btVoltarGestorPromocao = new JButton("Voltar");
		btVoltarGestorPromocao.setBounds(6, 709, 119, 38);
		btVoltarGestorPromocao.setFont(font);
		btVoltarGestorPromocao.setBackground(SystemColor.activeCaption);
		btVoltarGestorPromocao.setFocusPainted(false);
	}

	/**
	 * /**
	 * Configuração das labels de pesquisa e do botão de pesquisa dinâmico. 
	 * @labelID
	 * @labelNome
	 * @textPesquisaID
	 * @textFieldNome
	 * @checkBoxAtiva
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
		textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 44, 371, 27);
		painelPesquisa.add(textFieldNome);

		// checkBox Ativa
		
		checkBoxAtiva = new JCheckBox("Ativa");
		checkBoxAtiva.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtiva.setBackground(Color.WHITE);
		checkBoxAtiva.setBounds(232, 78, 69, 24);
		painelPesquisa.add(checkBoxAtiva);

		btPesquisar = new JButton("Pesquisar");
		btPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = 0;
					String nome = null;
					int ativo = checkBoxAtiva.isSelected()? 1:0;

					if(!textPesquisaID.getText().isBlank()) {
						id = Integer.parseInt(textPesquisaID.getText().trim());
					}

					if(!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText().trim();
					}

					List<Promocao> Promocoes = null;

					if ((id != 0) || (nome != null) || (ativo!=0) ) {

						Promocoes = GestorDeDAO.getGestorDeDAO().pesquisaPromocao(id, nome, ativo);
					} else  {
						Promocoes = GestorDeDAO.getGestorDeDAO().getAllPromocoes();
					}

					PromocaoPesquisaModelTable model = new PromocaoPesquisaModelTable(Promocoes);
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
	 * exista uma promoção selecionado ou não. 
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
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					botaoEditarPromocao.setEnabled(false);
					botaoDesativarPromocao.setEnabled(true);
				}
				else if (table.getSelectedRows().length==1) {
					int row = table.getSelectedRow();
					botaoEditarPromocao.setEnabled(true);
					botaoDesativarPromocao.setEnabled(true);
					Promocao pacoteComercial = (Promocao) table.getValueAt(row, PromocaoPesquisaModelTable.OBJECT_COL);
					textAreaDescricao.setText(pacoteComercial.getDescricao());
					botaoVisualizarHistorico.setEnabled(true);
					botaoAtivarDinamico();
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarPromocao.setEnabled(false);
					botaoDesativarPromocao.setEnabled(false);
				}
			}
		});
	}

	/**
	 * Botão ativo/desativo dinâmico. 
	 * Quando a promoção está ativa o botão apresenta "desativar".
	 * Quando a promoção está desativo o botão apresenta "ativar".
	 * @botaoDesativarPromocao - botao dinâmico
	 */
	private void botaoAtivarDinamico() {

		try {
			int row = table.getSelectedRow();
			Promocao PromocaoTemp = (Promocao) table.getValueAt(row, PromocaoPesquisaModelTable.OBJECT_COL);
			if (PromocaoTemp.isAtiva())
				botaoDesativarPromocao.setText("Desativar Promocao");
			else
				botaoDesativarPromocao.setText("Ativar Promocao");
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
	private void timerSetup() {
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblTempoSessao);
		
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblUsernameLogged);
		
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10 ));
		contentPane.add(lblHoraSistema);

	}
 
	/**
	 * Ao fazer alterações na promoção a tabela é atualizada. 
	 */
	public void refreshPromocaoTable() {
		try {
			List<Promocao> promocoes = GestorDeDAO.getGestorDeDAO().getAllPromocoes();
			PromocaoPesquisaModelTable model = new PromocaoPesquisaModelTable(promocoes);
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
	 * @return o painel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
	
	/**
	 * @return botão Voltar Gestor Promocao
	 */
	public JButton getBtVoltarGestorPromocao() {
		return btVoltarGestorPromocao;
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
	
	/** 
	 * @return textAreaDescricao
	 */
	public JTextArea getTextAreaDescricao() {
		return textAreaDescricao;
	}
}
