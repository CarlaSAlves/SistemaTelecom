package guiComponentes.admin_gestorPromocao;

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
import historicos.HistoricoPromocoes;
import servico.GestorDeDAO;
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
	 * Contructor that initializes with the method that configs the base panel and the method initialize
	 * which contains all the methods and elements that compose the page
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
	 * contains the body of the page
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
	 * Contém o corpo da página
	 */
	protected void inicialize() {

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");
		
		/*
		 *  Page buttons:
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
		setResizable(false);
		

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
		 *  Search fields:
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
		 * Result table:
		 * 
		 * Tabela
		 * ScrollPane
		 * Label Resultados
		 */

		JPanel panel = paneldaTabelaSetup();
		getContentPane().add(panel);

		tableSetup(); 
		
		JScrollPane scrollPane = scrollPaneSetup();
		panel.add(scrollPane);
		scrollPane.setViewportView(table);

		

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
		panel.add(lblResultados);

		/**
		 * Footer:
		 * footer image
		 * timer		 * 
		 */

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_promocao.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		contentPane.add(lbFooter);	

		timerSetup();

	}	

	/** * 
	 * Configs the create client button
	 * When pressed, opens a window Dialog "CriarPromocaoDialog"
	 * to input the data of the new package
	 * 
	 * @return botaoCriarCliente 
	 * @botaoCriarCliente - opens a window Dialog "CriarPromocaoDialog"

	
	 */
	private JButton botaoCriarPromocaoSetup() {
		JButton botaoCriarPromocao = new JButton("Criar Promoção");
		botaoCriarPromocao.setBounds(609, 179, 231, 43);
		botaoCriarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoCriarPromocao.setBackground(Color.LIGHT_GRAY);
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
	 * 
	 * Configs the edit "pacote" button
	 * When pressed it opens the "CriarPromocaoDialog" Dialog
	 * with the pre-existing data about the pacote but editable, which can be changed.
	 * If no client is selected this button is not enabled, however
	 * there is a validation to give an error in such a case
	* @botaoEditarPromocao- abre a CriarPromocaoDialog editável // opens the CriarClienteDialog editable.
	 */

	
	private void botaoEditarPromocaoSetup() {
		botaoEditarPromocao = new JButton("Editar Promoção");
		botaoEditarPromocao.setBounds(609, 125, 231, 43);
		botaoEditarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoEditarPromocao.setBackground(Color.LIGHT_GRAY);
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
	 *  * Configs the deactivate Promoção button.
	 * When pressed asks to confirm the action.
	 * If the Promoção is active the button reads "deactivate" and if the client is inactive it reads "activate"
	 * If there is no selected Promoção, this button is disabled, however there is a validation which gives an error if that were to happen.
	 * @botaoDesativarCliente - dynamic button "activate/deactivate"
	 * 
	 */
	private void botaoDesativarPromocaoSetup() {
		botaoDesativarPromocao = new JButton("Desativar Promoção");
		botaoDesativarPromocao.setBounds(609, 71, 231, 43);
		botaoDesativarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoDesativarPromocao.setBackground(Color.LIGHT_GRAY);
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
	 * Creates the visualise history button
	 * When pressed, as long as there is a selected cliente, opens a window with the history of all actions taken on this client.
	 * When no cliente has been selected this button is disabled, however there is a validation in case it would be able to press it so it shows an error.
	 * @botaoVisualizarHistorico - opens the window with the history of actions taken by user on a specific client
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
		botaoVisualizarHistorico.setBackground(Color.LIGHT_GRAY);
		botaoVisualizarHistorico.setBounds(609, 236, 231, 43);
		botaoVisualizarHistorico.setEnabled(false);
	}

	/**
	 * configs the return button
	 * when pressed it returns to the admin homepage 
	 * @btVoltarGestorPromocao - returns to the admin homepage
	 */
	private void btVoltarGestorPromocaoSetup() {
		btVoltarGestorPromocao = new JButton("Voltar");
		btVoltarGestorPromocao.setBounds(6, 709, 119, 38);
		btVoltarGestorPromocao.setFont(font);
		btVoltarGestorPromocao.setBackground(Color.LIGHT_GRAY);
		btVoltarGestorPromocao.setFocusPainted(false);
	}

	/**
	 * /**
	 * configs the search labels 
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
		btPesquisar.setBackground(Color.LIGHT_GRAY);
		btPesquisar.setBounds(72, 121, 371, 27);
		painelPesquisa.add(btPesquisar);
	}

	/**
	 * Configs the panel that contains the result table.
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
	 * Configs the result table from a search
	 * Configs which button are enabled depending if the is a promocao selected or not
	 * Here is also referenced the dynamic "activate/deactivate" button
	 * @table
	 */
	private void tableSetup() {
		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setFocusable(false);
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
	* "activate/deactivate" dynamic button
	 * When the client is active the button shows "deactivate"
	 * When the client is inactive the button shows "activate"
	 * @botaoDesativarPromocao - dynamic button
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
	 * Configs the labels username and timer
	 * @lblUsernameLogged shows the current logged username
	 * @lblTempoSessao shows the total time since the moment of the login
	 * @lblHoraSistema shows the system time
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
	 * When changes on the client are made, this refreshes the result table.
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
	 *  configs the timer
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * configs the label which shows the system time
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

	/**
	 * @return the textPesquisaID
	 */
	public JTextField getTextPesquisaID() {
		return textPesquisaID;
	}

	/**
	 * @return the textFieldNome
	 */
	public JTextField getTextFieldNome() {
		return textFieldNome;
	}
}
