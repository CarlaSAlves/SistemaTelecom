
package guiComponentes.gestorPacoteComercial;

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

import guiComponentes.GUI_total;
import guiComponentes.gestorCliente.ClientePesquisaModelTable;
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
		
		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(905, 71, 367, 151);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setEditable(false);
		contentPane.add(textAreaDescricao);

		// Tabela

		JPanel panel = panelSetup();
		getContentPane().add(panel);

		JScrollPane scrollPane = scrollPaneSetup();
		panel.add(scrollPane);

		tableSetup();
		scrollPane.setViewportView(table);

		lblResultadosSetup();
		panel.add(lblResultados);

		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);


		// Campo de pesquisa 

		JLabel lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 50, 294, 26);
		contentPane.add(lblCamposPesquisas);
		
		painelPesquisa();

		
		// Footer

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		contentPane.add(lblUsernameLogged);
		
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		contentPane.add(lblTempoSessao);
		
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		contentPane.add(lblHoraSistema);
		
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		
	}
	/**
	 * 
	 */
	protected void painelPesquisa() {
		{
			painelPesquisa = new JPanel();
			painelPesquisa.setLayout(null);
			painelPesquisa.setBackground(Color.WHITE);
			painelPesquisa.setBounds(98, 104, 453, 175);
			contentPane.add(painelPesquisa);
			{
				labelID = new JLabel("ID");
				labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				labelID.setBounds(6, 15, 39, 18);
				painelPesquisa.add(labelID);
			}
			{
				textPesquisaID = new JTextField();
				textPesquisaID.setColumns(10);
				textPesquisaID.setBounds(72, 6, 371, 27);
				painelPesquisa.add(textPesquisaID);
			}
			{
				labelNome = new JLabel("Nome");
				labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				labelNome.setBounds(6, 44, 56, 27);
				painelPesquisa.add(labelNome);
			}
			{
				textFieldNome = new JTextField();
				textFieldNome.setColumns(10);
				textFieldNome.setBounds(72, 44, 371, 27);
				painelPesquisa.add(textFieldNome);
			}
			{
				checkBoxAtivo = new JCheckBox("Ativo");
				checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				checkBoxAtivo.setBackground(Color.WHITE);
				checkBoxAtivo.setBounds(232, 78, 69, 24);
				painelPesquisa.add(checkBoxAtivo);
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
				btPesquisar.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				btPesquisar.setBackground(SystemColor.activeCaption);
				btPesquisar.setBounds(72, 121, 371, 27);
				painelPesquisa.add(btPesquisar);
			}
		}
	}
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
					indice = table.getSelectedRow();

					if (indice < 0) {
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

					
						PacoteComercial pacoteTemp = (PacoteComercial) table.getValueAt(indice,
								PacoteComercialPesquisaModelTable.OBJECT_COL);
						Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().desativarPacoteComercial(pacoteTemp.getId(),admin );
						
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

	private void botaoAtivarDinamico() {

		try {
			int row = table.getSelectedRow();
			PacoteComercial pacoteComercial = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);
			if (pacoteComercial.isAtivo())
				botaoDesativarPacoteComercial.setText("Desativa pacote");
			else
				botaoDesativarPacoteComercial.setText("Ativa pacote");
		} catch  (Exception e) {

		}
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
				botaoAtivarDinamico();
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
