package guiComponentes.gestorPromocao;

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
import servico.GestorDeDAO;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import guiComponentes.gestorPacoteComercial.PacoteComercialPesquisaModelTable;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import java.awt.Color;


public class GUI_gestor_promocao extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int numberRows;
	private int indices[];
	private JPanel contentPane, painelPesquisa;
	private JTable table;
	private JButton btVoltarGestorPromocao, btPesquisar, botaoDesativarPromocao, botaoEditarPromocao;
	private JLabel lblResultados, lblUsernameLogged, lblTempoSessao, lblHoraSistema, labelID, labelNome;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextField textPesquisaID, textFieldNome;
	private JCheckBox checkBoxAtivo;
	private JTextArea textAreaDescricao;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_gestor_promocao frame = new GUI_gestor_promocao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public  GUI_gestor_promocao() {
		contentPaneSetup();
		inicialize();
	}

	protected void inicialize() {

		// look and feel Nimbus 

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

		// Campo de pesquisa 

		JLabel lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 26, 294, 26);
		contentPane.add(lblCamposPesquisas);
		
		painelPesquisa();

		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(905, 71, 367, 151);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setEditable(false);
		contentPane.add(textAreaDescricao);

		// Botões 

		JButton botaoCriarPromocao = botaoCriarPromocaoSetup();
		getContentPane().add(botaoCriarPromocao);

		botaoEditarPromocaoSetup();
		getContentPane().add(botaoEditarPromocao);

		botaoDesativarPromocaoSetup();
		getContentPane().add(botaoDesativarPromocao);

		btVoltarGestorPromocaoSetup();
		getContentPane().add(btVoltarGestorPromocao);

		// table 

		tableSetup();

		JPanel panel = panelSetup();
		getContentPane().add(panel);

		JScrollPane scrollPane = scrollPaneSetup();
		panel.add(scrollPane);

		scrollPane.setViewportView(table);

		lblResultadosSetup();
		panel.add(lblResultados);

		// Footer

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);	

		setUpUserSessao();

	}

	private void setUpUserSessao() {
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		contentPane.add(lblTempoSessao);
		
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		contentPane.add(lblUsernameLogged);
		
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 12));
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
			painelPesquisa.setBounds(98, 87, 453, 171);
			contentPane.add(painelPesquisa);
			{
				labelID = new JLabel("ID");
				labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				labelID.setBounds(6, 15, 39, 18);
				painelPesquisa.add(labelID);
			}
			{
				textPesquisaID = new JTextField();
				textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
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
				textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				textFieldNome.setColumns(10);
				textFieldNome.setBounds(72, 44, 371, 27);
				painelPesquisa.add(textFieldNome);
			}
			{
				checkBoxAtivo = new JCheckBox("Ativa");
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
							int ativo = checkBoxAtivo.isSelected()? 1:0;
							
							if(!textPesquisaID.getText().isBlank()) {
								id = Integer.parseInt(textPesquisaID.getText());
							}

							if(!textFieldNome.getText().isBlank()) {
								nome = textFieldNome.getText();
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
		}
	}



	private void panel_1ContentSetup(JPanel panel_1) {

		textPesquisaIDSetup();

		textFieldNomeSetup();
	}



	private void textFieldNomeSetup() {
	}


	private void textPesquisaIDSetup() {
	}


	private JLabel lbFooterSetup() {
		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_promocao.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		return lbFooter;
	}

	private void btVoltarGestorPromocaoSetup() {
		btVoltarGestorPromocao = new JButton("Voltar");
		btVoltarGestorPromocao.setBounds(6, 709, 119, 38);
		btVoltarGestorPromocao.setFont(font);
		btVoltarGestorPromocao.setBackground(SystemColor.activeCaption);
		btVoltarGestorPromocao.setFocusPainted(false);
	}

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
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
								"Por favor selecione um Promocao", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_promocao.this,
							"Desativar este Promocao?", "Confirmar Desativar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					for(int i = 0; i < indices.length; i++) {
						Promocao PromocaoTemp = (Promocao) table.getValueAt(indices[i], PromocaoPesquisaModelTable.OBJECT_COL);
						GestorDeDAO.getGestorDeDAO().desativarPromocao(PromocaoTemp.getId());

					}
					JOptionPane.showMessageDialog(GUI_gestor_promocao.this,
							"Promocao Desativada com sucesso", "Promocao Desativada",
							JOptionPane.INFORMATION_MESSAGE);

					refreshPromocaoTable();
				} catch (Exception e1) {

				}
			}

		});
	}

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
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarPromocao.setEnabled(false);
					botaoDesativarPromocao.setEnabled(false);
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
			}
		});
		return botaoCriarPromocao;
	}

	private void contentPaneSetup() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Promocao");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane.setBackground(Color.WHITE);
	}

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

	public JTable getTable() {
		return table;
	}

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username : " + username);

	}

	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}


	public JButton getBtVoltarGestorPromocao() {
		return btVoltarGestorPromocao;
	}
}
