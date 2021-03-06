
package guiComponentes.admin_gestorPacoteComercial;

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
import javax.swing.JTextArea;
import guiComponentes.GUI_total;
import historicos.HistoricoPacoteComercial;
import javax.swing.JCheckBox;
import java.awt.Color;

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
	 * Contructor that initializes with the method that configs the base panel and the method initialize
	 * which contains all the methods and elements that compose the page
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
	 * contains the body of the page
	 */
	protected void inicialize() {

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");
		
		/*
		 *  Page buttons:
		 *  
		 *  Creates "Pacote Comercial"
		 *  Edits " Pacote Comercial"
		 *  Deactivates  "Pacote Comercial"
		 *  View history
		 *  return 
		 *  TextArea Description
		 */

		JButton botaoCriarPacotes = botaoCriarPacotesSetup();
		getContentPane().add(botaoCriarPacotes);
		setResizable(false);
		

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
		 *  Search fields:
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
		 * timer
		 * 
		 */

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_pacotes.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		contentPane.add(lbFooter);

		timerSetup();

	}

	/**
	 * configs interface, look and feel Nimbus
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

	 * Configs the create client button
	 * When pressed, opens a window Dialog "CriarPacotesDialog"
	 * to input the data of the new "Pacote Comercial"
	 * 
	 * @return botaoCriarPacotes
	 * @botaoCriarPacotes - opens a window Dialog "CriarPacotesDialog"
	 */
	private JButton botaoCriarPacotesSetup() {
		JButton botaoCriarPacotes = new JButton("Criar Pacote Comercial");
		botaoCriarPacotes.setBounds(609, 179, 218, 43);
		botaoCriarPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoCriarPacotes.setBackground(Color.LIGHT_GRAY);
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
	* Configs the edit "Pacote Comercial" button
	 * When pressed it opens the "CriarPacotesDialog" Dialog
	 * with the pre-existing data about the "Pacote Comercial" but editable, which can be changed.
	 * If no client is selected this button is not enabled, however
	 * there is a validation to give an error in such a case
	 * @botaoEditarPacoteComercial -  opens the CriarPacotesDialog editable.
	 */
	private void botaoEditarPacoteComercialSetup() {
		botaoEditarPacoteComercial = new JButton("Editar Pacote Comercial");
		botaoEditarPacoteComercial.setBounds(609, 125, 218, 43);
		botaoEditarPacoteComercial.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoEditarPacoteComercial.setBackground(Color.LIGHT_GRAY);
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
	 * Configs the deactivate "Pacote Comercial" button.
	 * When pressed asks to confirm the action.
	 * If the client is active the button reads "deactivate" and if the "Pacote Comercial" is inactive it reads "activate"
	 * If there is no selected client, this button is disabled, however there is a validation which gives an error if that were to happen.
		 * @botaoDesativarPacoteComercial -dynamic button "activate/deactivate"
	 */
	private void botaoDesativarPacoteComercialSetup() {
		botaoDesativarPacoteComercial = new JButton("Desativar Pacote Comercial");
		botaoDesativarPacoteComercial.setBounds(609, 71, 218, 43);
		botaoDesativarPacoteComercial.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoDesativarPacoteComercial.setBackground(Color.LIGHT_GRAY);
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
	 * Creates the visualise history button
	 * When pressed, as long as there is a selected "Pacote Comercial", opens a window with the history of all actions taken on this client.
	 * When no "Pacote Comercial" has been selected this button is disabled, however there is a validation in case it would be able to press it so it shows an error.
	 * @botaoVisualizarHistorico - opens the window with the history of actions taken by user 
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
		botaoVisualizarHistorico.setBackground(Color.LIGHT_GRAY);
		botaoVisualizarHistorico.setBounds(609, 236, 218, 43);
		botaoVisualizarHistorico.setEnabled(false);
	}
	
	/**
	 * configs the return button
	 * when pressed it returns to the admin homepage 
	 * @btVoltarGestorPacotes -  returns to the admin homepage
	 */
	private void btVoltarGestorPacotesSetup() {
		btVoltarGestorPacotes = new JButton("Voltar");
		btVoltarGestorPacotes.setBounds(6, 709, 119, 38);
		btVoltarGestorPacotes.setFont(font);
		btVoltarGestorPacotes.setBackground(Color.LIGHT_GRAY);
		btVoltarGestorPacotes.setFocusPainted(false);
	}

	/**
	 * /**
	 * configs the search labels
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
		textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 12));
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
	 *table  ScrollPane 
	 * @return scrollPane
	 */
	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		return scrollPane;
	}
	
	/**
	 * Configs the result table from a search
	 * Configs which button are enabled depending if the is a "Pacote Comercial" selected or not
	 * Here is also referenced the dynamic "activate/deactivate" button
	 * @table
	 */
	private void tableSetup() {
		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setFocusable(false);
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
	 * * "activate/deactivate" dynamic button
	 * When the pacote comercial is active the button shows "deactivate"
	 * When the pacote comercial is inactive the button shows "activate"
	 * @botaoDesativarPacoteComercial - dynamic button
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
	 * Configs the labels username and timer
	 * @lblUsernameLogged shows the current logged username
	 * @lblTempoSessao shows the total time since the moment of the login
	 * @lblHoraSistema shows the system time
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
	 * When changes on the pacote comercial are made, this refreshes the result table.
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
	 * configs usernameLogged label
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}
	
	/**
	 * Configs timer label
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText(
				"Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart());;
	}

	/**
	 * configs system time label
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
