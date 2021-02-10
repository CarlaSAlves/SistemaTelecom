
package guiComponentes.gestorPacoteComercial;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.awt.TextArea;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import servico.GestorDeDAO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import guiComponentes.gestorCliente.ClientePesquisaModelTable;
import historicos.HistoricoPacoteComercial;
import javax.swing.JCheckBox;
import java.awt.Color;

public class GUI_gestor_pacotes extends JFrame {


	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btVoltarGestorPacotes;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados, lblUsernameLogged;
	private JButton botaoDesativarPacoteComercial;
	private JButton botaoEditarPacoteComercial;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JLabel lblTempoSessao;
	private JLabel lblHoraSistema;
	private JButton botaoVisualizarHistorico;
	private JPanel painelPesquisa;
	private JPanel painelPesquisa_1;
	private JLabel labelID_1;
	private JTextField textPesquisaID;
	private JLabel labelNome_1;
	private JTextField textFieldNome;
	private JCheckBox checkBoxAtivo;
	private JButton btPesquisar;
	JTextArea textAreaDescricao;




	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_gestor_pacotes frame = new GUI_gestor_pacotes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_gestor_pacotes() {
		contentPaneSetup();
		inicialize();

	}

	/**
	 * 
	 */
	protected void inicialize() {

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


		// Botões

		JButton botaoCriarPacotes = botaoCriarPacotesSetup();
		getContentPane().add(botaoCriarPacotes);

		botaoEditarPacoteComercialSetup();
		getContentPane().add(botaoEditarPacoteComercial);

		botaoDesativarPacoteComercialSetup();
		getContentPane().add(botaoDesativarPacoteComercial);

		btVoltarGestorPacotesSetup();
		getContentPane().add(btVoltarGestorPacotes);

		// Tabela

		JPanel panel = panelSetup();
		getContentPane().add(panel);

		JScrollPane scrollPane = scrollPaneSetup();
		panel.add(scrollPane);

		tableSetup();
		scrollPane.setViewportView(table);

		lblResultadosSetup();
		panel.add(lblResultados);



		// Campo de pesquisa 
		
		JLabel lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 26, 294, 26);
		contentPane.add(lblCamposPesquisas);
		
		
		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(905, 71, 367, 151);
		textAreaDescricao.setLineWrap(true);
		contentPane.add(textAreaDescricao);
		
	//	JPanel panel_1 = panel_1Setup();
	//	panel_1ContentSetup(panel_1);
		//contentPane.add(panel_1);
		
		// Footer

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);

		panelUserESessaoSetup();
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		contentPane.add(lblUsernameLogged);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		contentPane.add(lblTempoSessao);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		contentPane.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dialog", Font.PLAIN, 10));
		{
			painelPesquisa_1 = new JPanel();
			painelPesquisa_1.setLayout(null);
			painelPesquisa_1.setBackground(Color.WHITE);
			painelPesquisa_1.setBounds(98, 63, 453, 221);
			contentPane.add(painelPesquisa_1);
			{
				labelID_1 = new JLabel("ID");
				labelID_1.setFont(new Font("Dialog", Font.BOLD, 13));
				labelID_1.setBounds(6, 15, 39, 18);
				painelPesquisa_1.add(labelID_1);
			}
			{
				textPesquisaID = new JTextField();
				textPesquisaID.setColumns(10);
				textPesquisaID.setBounds(72, 6, 371, 27);
				painelPesquisa_1.add(textPesquisaID);
			}
			{
				labelNome_1 = new JLabel("Nome");
				labelNome_1.setFont(new Font("Dialog", Font.BOLD, 13));
				labelNome_1.setBounds(6, 44, 56, 27);
				painelPesquisa_1.add(labelNome_1);
			}
			{
				textFieldNome = new JTextField();
				textFieldNome.setColumns(10);
				textFieldNome.setBounds(72, 44, 371, 27);
				painelPesquisa_1.add(textFieldNome);
			}
			{
				checkBoxAtivo = new JCheckBox("Ativo");
				checkBoxAtivo.setFont(new Font("Dialog", Font.BOLD, 13));
				checkBoxAtivo.setBackground(Color.WHITE);
				checkBoxAtivo.setBounds(232, 78, 69, 24);
				painelPesquisa_1.add(checkBoxAtivo);
			}
			{
				btPesquisar = new JButton("Pesquisar");
				btPesquisar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int id = 0;
							String nome = null;
							int ativo = checkBoxAtivo.isSelected() ? 1 : 0;

							if (!textPesquisaID.getText().isBlank()) {
								id = Integer.parseInt(textPesquisaID.getText());
							}

							if (!textFieldNome.getText().isBlank()) {
								nome = textFieldNome.getText();
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
				btPesquisar.setFont(new Font("Dialog", Font.PLAIN, 15));
				btPesquisar.setBackground(SystemColor.activeCaption);
				btPesquisar.setBounds(72, 109, 371, 27);
				painelPesquisa_1.add(btPesquisar);
			}
		}

		panelUserESessaoContentSetup();
	}
	
private void panel_1ContentSetup(JPanel panel_1) {
		
		painelPesquisa.setLayout(null);
		
		JLabel lblDeProcura = lblNewLabelIDSetup();
		panel_1.add(lblDeProcura);

		

		JLabel lblNome = lblNewLabelNomeSetup();
		panel_1.add(lblNome);


		JCheckBox checkBoxAtivo = checkBoxAtivoSetup();
		panel_1.add(checkBoxAtivo);


		JButton botaoPesquisa = btnNewButtonPesquisarSetup(checkBoxAtivo);
		panel_1.add(botaoPesquisa);
	}

	private void panelUserESessaoContentSetup() {
		lblUsernameLoggedSetup();

		lblTempoSessaoSetup();

		lblHoraSistemaSetup();
	}

	private void lblHoraSistemaSetup() {}

	private void lblTempoSessaoSetup() {}

	private void lblUsernameLoggedSetup() {}

	private void panelUserESessaoSetup() {}

	private JButton btnNewButtonPesquisarSetup(JCheckBox checkBoxAtivo) {
		JButton btPesquisar = new JButton("Pesquisar");
		btPesquisar.setBackground(SystemColor.activeCaption);
		btPesquisar.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		
		
		return btPesquisar;
	}

	private JCheckBox checkBoxAtivoSetup() {
		JCheckBox checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBounds(207, 77, 50, 23);
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setForeground(SystemColor.desktop);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		return checkBoxAtivo;
	}

	

	private JLabel lblNewLabelNomeSetup() {
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		return labelNome;
	}

	

	private JLabel lblNewLabelIDSetup() {
		JLabel labelID = new JLabel("ID");
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		return labelID;
	}

//	private JPanel panel_1Setup() {
//		JPanel painelPesquisa = new JPanel();
//		painelPesquisa.setBackground(Color.WHITE);
//		painelPesquisa.setBounds(99, 71, 344, 177);
//		
//		painelPesquisa.setLayout(new FormLayout(new ColumnSpec[] {
//				FormSpecs.RELATED_GAP_COLSPEC,
//				FormSpecs.DEFAULT_COLSPEC,
//				FormSpecs.RELATED_GAP_COLSPEC,
//				ColumnSpec.decode("default:grow"),},
//			new RowSpec[] {
//				FormSpecs.RELATED_GAP_ROWSPEC,
//				FormSpecs.DEFAULT_ROWSPEC,
//				FormSpecs.RELATED_GAP_ROWSPEC,
//				FormSpecs.DEFAULT_ROWSPEC,
//				FormSpecs.RELATED_GAP_ROWSPEC,
//				FormSpecs.DEFAULT_ROWSPEC,
//				FormSpecs.RELATED_GAP_ROWSPEC,
//				FormSpecs.DEFAULT_ROWSPEC,}));
//		return painelPesquisa;
//	}

	private JLabel lbFooterSetup() {
		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_pacotes.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		return lbFooter;
	}
	
	private void botaoVisualizarHistoricoSetup() {
		botaoVisualizarHistorico = new JButton("Visualizar Historico");
		botaoVisualizarHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();


				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Por favor selecione um Cliente", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				PacoteComercial pacoteComercial = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);
				List<HistoricoPacoteComercial> list;

				try {

					list = GestorDeDAO.getGestorDeDAO().getHistoricoPacoteComercial(pacoteComercial.getId());
					HistoricoPacoteComercialDialog dialogHistorico = new HistoricoPacoteComercialDialog();
					dialogHistorico.preencherTable(pacoteComercial, list);
					dialogHistorico.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		botaoVisualizarHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoVisualizarHistorico.setBackground(SystemColor.activeCaption);
		botaoVisualizarHistorico.setBounds(609, 236, 218, 43);
		botaoVisualizarHistorico.setEnabled(true);
	}

	private void btVoltarGestorPacotesSetup() {
		btVoltarGestorPacotes = new JButton("Voltar");
		btVoltarGestorPacotes.setBounds(6, 709, 119, 38);
		btVoltarGestorPacotes.setFont(font);
		btVoltarGestorPacotes.setBackground(SystemColor.activeCaption);
		btVoltarGestorPacotes.setFocusPainted(false);
	}

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
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
								"Por favor selecione um Pacote", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_pacotes.this,
							"Desativar este Pacote?", "Confirmar Desativar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					for (int i = 0; i < indices.length; i++) {
						PacoteComercial pacoteTemp = (PacoteComercial) table.getValueAt(indices[i],
								PacoteComercialPesquisaModelTable.OBJECT_COL);
					//	GestorDeDAO.getGestorDeDAO().desativarPacoteComercial(pacoteTemp.getId());

					}
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Pacotes Desativado com sucesso", "Pacote Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshPacotesTable();
				} catch (Exception e1) {

				}
			}

		});
	}

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

				CriarPacotesDialog dialog =
						new CriarPacotesDialog(GUI_gestor_pacotes.this, pacoteComercialTemp, true);

				dialog.setVisible(true);
			}
		});
	}

	private void lblResultadosSetup() {
		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
	}

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
				} else if (table.getSelectedRows().length == 1) {
					int row = table.getSelectedRow();
					botaoEditarPacoteComercial.setEnabled(true);
					botaoDesativarPacoteComercial.setEnabled(true);
					PacoteComercial pacoteComercial = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);
					textAreaDescricao.setText(pacoteComercial.getDescricao());
					
					
				} else if (table.getSelectedRowCount() == 0) {
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(false);
				}
			}
		});
	}

	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		return scrollPane;
	}

	private JPanel panelSetup() {
		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(Color.WHITE);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		return painelTabela;
	}

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
			}
		});
		return botaoCriarPacotes;
	}

	private void contentPaneSetup() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Pacotes Comerciais");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane.setBackground(Color.WHITE);
	}

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


	public JTable getTable() {
		return table;
	}

	public JButton btVoltarGestorPacotes() {
		return btVoltarGestorPacotes;
	}


	public void setBtVoltarGestorPacotes(JButton btnNewButtonVoltar2) {
		this.btVoltarGestorPacotes = btnNewButtonVoltar2;
	}

	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username : " + username);

	}

	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText(
				"Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart());;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
