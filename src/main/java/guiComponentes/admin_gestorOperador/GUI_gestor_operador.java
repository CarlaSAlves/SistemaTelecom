package guiComponentes.admin_gestorOperador;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import guiComponentes.GUI_total;
import historicos.HistoricoOperador;
import javax.swing.JCheckBox;
import java.awt.Color;

@SuppressWarnings("serial")
public class GUI_gestor_operador extends JFrame {

	private int numberRows;
	private JTable table;
	private JPanel contentPane, painelPesquisa;
	private JLabel lblResultados, lblCamposPesquisas, lblUsernameLogged, lblTempoSessao, lblHoraSistema, labelID, labelNIF, labelNome;
	private JButton botaoDesativarOperador, botaoEditarOperador, btVoltarGestorOperador, botaoPesquisa, botaoVisualizarHistorico;
	private int indice;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextField textPesquisaID, textPesquisaNIF, textFieldNome;
	private JCheckBox checkBoxAtivo;

	
	/**

	 * Contructor that initializes with the method that configs the panel base and the initialize method which contains all elements that compose the page.
	 */
	public GUI_gestor_operador() {
		
		ativarNimbusLookAndFeel();
		setTitle("Pesquisa de Operador");
		setBounds(100, 30, 1400, 800);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setBackground(SystemColor.white);
		setResizable(false);
		inicialize();
	}
	
	/**
	 *Contains the body of the page
	 */
	protected void inicialize() {
		
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");

		/*
		 *  page buttons:
		 *  
		 *  Criar Operador//Create operator 
		 *  Editar Operador//Edit operator
		 *  Desativar Operador//deactivate operator
		 *  Visualizar Historico// see history
		 *  Voltar //return
		 */

		JButton botaoCriarOperador = botaoCriarOperadorSetup();
		getContentPane().add(botaoCriarOperador);

		botaoEditarOperadorSetup();
		getContentPane().add(botaoEditarOperador);

		botaoDesativarOperadorSetup();
		getContentPane().add(botaoDesativarOperador);
		
		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);

		btVoltarGestorOperadorSetup();
		getContentPane().add(btVoltarGestorOperador);

		/**
		 *  search fields:
		 *  
		 *  ID
		 *  NIF
		 *  Nome
		 *  Ativo
		 *  search button
		 */ 

		lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 29, 294, 26);
		contentPane.add(lblCamposPesquisas);

		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 75, 453, 221);
		contentPane.add(painelPesquisa);
		
		labelsPesquisaSetup();
		textFieldsPesquisaSetup();

		botaoPesquisaSetup();

		/**
		 * result table:
		 * 
		 * table
		 * ScrollPane
		 *  Result label
		 */
		
		JPanel panelDaTable = panelDaTableSetup();
		getContentPane().add(panelDaTable);

		tableSetup();
		
		JScrollPane scrollPane = scrollPaneSetup();
		panelDaTable.add(scrollPane);
		
		
		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
		panelDaTable.add(lblResultados);
		
		/**
		 * Footer: 
		 * 
		 * footer image
		 * timer
		 * 
		 */

		JLabel lbFooter = new JLabel();
		lbFooter.setIcon(new ImageIcon(GUI_gestor_operador.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		contentPane.add(lbFooter);
		
		timerSetup();

	} 

	/**
	 * Configs interface, look and feel Nimbus 
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
	 * Configs the button create oprator
	 * When pressed opens "CriarOperadorDialog" dialog, 
	 * to fill the details of the new operator
	 * 
	 * @return botaoCriarOperador
	 * @botaoCriarOperador - opens "CriarOperadorDialog" dialog, 
	 */
	private JButton botaoCriarOperadorSetup() {
		JButton botaoCriarOperador = new JButton("Criar Operador");
		botaoCriarOperador.setBounds(1168, 264, 152, 32);
		botaoCriarOperador.setFont(font);
		botaoCriarOperador.setBackground(Color.LIGHT_GRAY);
		botaoCriarOperador.setFocusPainted(false);
		botaoCriarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CriarOperadorDialog dialog = new CriarOperadorDialog(GUI_gestor_operador.this);
				dialog.setVisible(true);
				dialog.setResizable(false);
			}
		});
		return botaoCriarOperador;
	}
	
	/**
	 *
	 * Configs the edit operator button
	 * When pressed opens "CriarOperadorDialog" dialog.
	 * with the fields filled with the information of the selected operator.
	 * If no operator has been selected this button is disabled.
	 * However there is a validation if it were to be pressed with no operator selected.

	 * @botaoEditarOperador - opens a CriarOperadorDialog 
	 */
	private void botaoEditarOperadorSetup() {
		botaoEditarOperador = new JButton("Editar Operador");
		botaoEditarOperador.setBounds(826, 264, 161, 33);
		botaoEditarOperador.setFont(font);
		botaoEditarOperador.setBackground(Color.LIGHT_GRAY);
		botaoEditarOperador.setFocusPainted(false);
		botaoEditarOperador.setEnabled(false);
		botaoEditarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_operador.this,
							"Por favor selecione um Operador", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Funcionario funcionarioTemp = (Funcionario) table.getValueAt(row, OperadorPesquisaModelTable.OBJECT_COL);

				CriarOperadorDialog dialog = new CriarOperadorDialog(GUI_gestor_operador.this,funcionarioTemp, true);
				dialog.setResizable(false);
				dialog.setVisible(true);

			}
		});
	}
	
	/**
	  *
	 * Configs the edit operator button
	 * When pressed opens "CriarOperadorDialog" dialog.
	 * If the selected operator is active button reads "disable operator" otherwise shows "activate operator"
	 * If no operator has been selected this button is disabled.
	 * However there is a validation if it were to be pressed with no operator selected.
	 */
	private void botaoDesativarOperadorSetup() {
		botaoDesativarOperador = new JButton("Desativar Operador");
		botaoDesativarOperador.setBounds(997, 264, 161, 33);
		botaoDesativarOperador.setFont(font);
		botaoDesativarOperador.setBackground(Color.LIGHT_GRAY);
		botaoDesativarOperador.setFocusPainted(false);
		botaoDesativarOperador.setEnabled(false);
		botaoDesativarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					indice = table.getSelectedRow();

					if (indice < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_operador.this,
								"Por favor selecione um Operador", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Funcionario funcionarioTemp = (Funcionario) table.getValueAt(indice, OperadorPesquisaModelTable.OBJECT_COL);

					if(funcionarioTemp.isAtivo()) {
						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_operador.this,
								"Desativar este Operador?", "Confirmar Desactivar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().desativarFuncionario(funcionarioTemp.getId(),admin );

						JOptionPane.showMessageDialog(GUI_gestor_operador.this,
								"Operador desativado com sucesso", "Operador Desativado",
								JOptionPane.INFORMATION_MESSAGE);

						refreshOperadorTable();

					}else {

						int resposta = JOptionPane.showConfirmDialog(GUI_gestor_operador.this,
								"Ativar este Operador?", "Confirmar Ativar",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (resposta != JOptionPane.YES_OPTION) {
							return;
						}

						Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().ativarFuncionario(funcionarioTemp.getId(), admin);

						JOptionPane.showMessageDialog(GUI_gestor_operador.this,
								"Operador Ativado com sucesso", "Operador Ativado",
								JOptionPane.INFORMATION_MESSAGE);

						refreshOperadorTable();

					}
				} catch (Exception e1) {

				}

			}

		});
	}
	
	/**
	 * Sets up the view history button 
	 * When pressed as long as an operator was selected opens a dialog with the history of the operator.
	 * If no operator has been selected this button is disabled.
	 * However there is a validation if it were to be pressed with no operator selected.
	 * @botaoVisualizarHistorico - opens a window with the history of selected operator
	 */
	private void botaoVisualizarHistoricoSetup() {
		botaoVisualizarHistorico = new JButton("Ver Historico");
		botaoVisualizarHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_operador.this,
							"Por favor selecione um Operador", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Funcionario operadorTemp = (Funcionario) table.getValueAt(row, OperadorPesquisaModelTable.OBJECT_COL);
				List<HistoricoOperador> list;

				try {

					list = GestorDeDAO.getGestorDeDAO().getHistoricoOperador(operadorTemp.getId());
					HistoricoOperadorDialog dialogHistorico = new HistoricoOperadorDialog();
					dialogHistorico.preencherTable(operadorTemp, list);
					dialogHistorico.setVisible(true);
					dialogHistorico.setResizable(false);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		botaoVisualizarHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoVisualizarHistorico.setBackground(Color.LIGHT_GRAY);
		botaoVisualizarHistorico.setBounds(651, 264, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
	}
	
	/**
	 * configs the return button
	 * when pressed returns to the admin homepage
	 * @btVoltarGestorOperador -returns to the admin homepage
	 */
	private void btVoltarGestorOperadorSetup() {
		btVoltarGestorOperador = new JButton("Voltar");
		btVoltarGestorOperador.setBounds(6, 709, 119, 38);
		btVoltarGestorOperador.setFont(font);
		btVoltarGestorOperador.setBackground(Color.LIGHT_GRAY);
		btVoltarGestorOperador.setFocusPainted(false);
	}

	/**
	 * /**
	 * Configs the search labels
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
		labelNome.setBounds(6, 78, 56, 27);
		painelPesquisa.add(labelNome);
	
	}
	
	/**
	 * Configuração the search textFields and the "ativo" checkbox
	 * @textPesquisaID
	 * @textFieldNome
	 * @textPesquisaNIF
	 * @checkboxAtivo
	 */
	private void textFieldsPesquisaSetup() {
		
		textPesquisaID = new JTextField();
		textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);
		
		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 78, 371, 27);
		painelPesquisa.add(textFieldNome);
		
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textPesquisaNIF.setColumns(10);
		textPesquisaNIF.setBounds(72, 40, 371, 27);
		painelPesquisa.add(textPesquisaNIF);
		
		// Checkbox Ativo
		
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setBounds(235, 112, 69, 24);
		painelPesquisa.add(checkBoxAtivo);
		
	}
	
	/**
	 * Configs the dynamic search button
	 * It is possible to search using several fields at the same time
	 * @botaoPesquisa 
	 */
	 
	private void botaoPesquisaSetup() {
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		botaoPesquisa.setBackground(Color.LIGHT_GRAY);
		botaoPesquisa.setBounds(72, 143, 371, 27);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int id = 0;
					String nif = null;
					String nome = null;
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

					List<Funcionario> operadores = null;

					if ((id != 0) || (nif != null) || (nome != null) || (ativo!=0) ) {	
						operadores = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioOperador(id, nif, nome, ativo);
					} else {
						operadores = GestorDeDAO.getGestorDeDAO().getAllFuncionarioOperador();
					}

					OperadorPesquisaModelTable model = new OperadorPesquisaModelTable(operadores);
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
	 * configs the panel that contains the result table
	 * @return table panel
	 */
	private JPanel panelDaTableSetup() {
		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(SystemColor.window);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		return painelTabela;
	}
	
	/**
	 * table ScrollPane 
	 * @return scrollPane
	 */
	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		scrollPane.setViewportView(table);
		return scrollPane;
	}
	
	/**
	 * configs the result table
	 * configs which button are active depending on selection
	 * 
	 * Here is also referenced the dynamic activate/deactivate button
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
					botaoEditarOperador.setEnabled(false);
					botaoDesativarOperador.setEnabled(true);
					botaoVisualizarHistorico.setEnabled(false);
				}
				else if (table.getSelectedRows().length==1) {
					botaoEditarOperador.setEnabled(true);
					botaoDesativarOperador.setEnabled(true);
					botaoVisualizarHistorico.setEnabled(true);
					botaoAtivarDinamico();
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarOperador.setEnabled(false);
					botaoDesativarOperador.setEnabled(false);
					botaoVisualizarHistorico.setEnabled(false);
				}
				
			}
		});
		
	}

	/**
	 * dynamic activate/deactivate button
	 * When the operator is active shows deactivate
	 * When the operator is disabled shows activate
	 * @botaoDesativarOperador - dynamic button
	 */
	private void botaoAtivarDinamico() {

		try {
			int row = table.getSelectedRow();
			Funcionario funcionario = (Funcionario) table.getValueAt(row, OperadorPesquisaModelTable.OBJECT_COL);
			if (funcionario.isAtivo())
				botaoDesativarOperador.setText("Desativar operador");
			else
				botaoDesativarOperador.setText("Ativar operador");
		} catch  (Exception e) {
		}
	}

	/**
	 * configs username and timer labels
	 * @lblUsernameLogged shows the current logged user
	 * @lblTempoSessao shows the total time since login
	 * @lblHoraSistema shows system time
	 */
	protected void timerSetup() {
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
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblHoraSistema);
	}
	
	/**
	 * when an operator is updated also updates table
	 */
	public void refreshOperadorTable() {

		try {
			List<Funcionario> funcionarios = GestorDeDAO.getGestorDeDAO().getAllFuncionarioOperador();
			OperadorPesquisaModelTable model = new OperadorPesquisaModelTable(funcionarios);
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
	 * @return return button
	 */
	public JButton btVoltarGestorOperador() {
		return btVoltarGestorOperador;
	}

	/**
	 * @return return button2
	 * @param botaoVoltar2
	 */
	public void setBtVoltarGestorOperador(JButton botaoVoltar2) {
		this.btVoltarGestorOperador = botaoVoltar2;
	}

	/**
	 * @returns panel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
	
	/**
	 * Configura the loggedUser label
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);

	}
	
	/**
	 * returns the timer label
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * Configura the system time label
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
	 * @return  textPesquisaID
	 */
	public JTextField getTextPesquisaID() {
		return textPesquisaID;
	}

	/**
	 * @return  textPesquisaNIF
	 */
	public JTextField getTextPesquisaNIF() {
		return textPesquisaNIF;
	}

	/**
	 * @return textFieldNome
	 */
	public JTextField getTextFieldNome() {
		return textFieldNome;
	}
	
	
}
