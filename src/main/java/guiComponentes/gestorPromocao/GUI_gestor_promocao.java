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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import servico.GestorDeDAO;
import standard_value_object.Promocao;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import java.awt.Color;


public class GUI_gestor_promocao extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btVoltarGestorPromocao;
	private int numberRows;
	private JLabel lblResultados, lblUsernameLogged;
	private JButton botaoDesativarPromocao;
	private JButton botaoEditarPromocao;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextField textPesquisaID;
	private JTextField textFieldNome;
	private JPanel panelUserESessao;
	private JLabel lblTempoSessao;
	private JLabel lblHoraSistema;


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
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Promocao");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane.setBackground(Color.WHITE);

		/* Lbl e botão de Procura */

		JButton botaoCriarPromocao = new JButton("Criar Promoção");
		botaoCriarPromocao.setBounds(697, 179, 231, 43);
		botaoCriarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoCriarPromocao.setBackground(SystemColor.activeCaption);
		botaoCriarPromocao.setFocusPainted(false);
		botaoCriarPromocao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarPromocaoDialog dialog = new CriarPromocaoDialog(GUI_gestor_promocao.this);
				dialog.setVisible(true);
			}
		});
		
				btVoltarGestorPromocao = new JButton("Voltar");
				btVoltarGestorPromocao.setBounds(10, 704, 86, 34);
				btVoltarGestorPromocao.setFont(font);
				btVoltarGestorPromocao.setBackground(SystemColor.activeCaption);
				btVoltarGestorPromocao.setFocusPainted(false);
				getContentPane().add(btVoltarGestorPromocao);
				btVoltarGestorPromocao.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {



					}
				});
		getContentPane().add(botaoCriarPromocao);

		botaoEditarPromocao = new JButton("Editar Promoção");
		botaoEditarPromocao.setBounds(697, 125, 231, 43);
		botaoEditarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoEditarPromocao.setBackground(SystemColor.activeCaption);
		botaoEditarPromocao.setFocusPainted(false);
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
		getContentPane().add(botaoEditarPromocao);

		botaoDesativarPromocao = new JButton("Desativar Promoção");
		botaoDesativarPromocao.setBounds(697, 71, 231, 43);
		botaoDesativarPromocao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoDesativarPromocao.setBackground(SystemColor.activeCaption);
		botaoDesativarPromocao.setFocusPainted(false);
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
		getContentPane().add(botaoDesativarPromocao);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_promocao.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		contentPane.add(lbFooter);

		botaoEditarPromocao.setEnabled(false);

		JLabel lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 294, 26);
		contentPane.add(lblCamposPesquisas);
		botaoDesativarPromocao.setEnabled(false);

		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(966, 71, 365, 151);
		contentPane.add(textAreaDescricao);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setForeground(SystemColor.desktop);
		panel_1.setBounds(66, 63, 380, 177);
		contentPane.add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("34px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("166px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("32px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("32px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("31px"),
						RowSpec.decode("26px"),}));
		JLabel lblDeProcura = new JLabel("ID");
		panel_1.add(lblDeProcura, "2, 2, left, center");
		lblDeProcura.setFont(new Font("Dubai Light", Font.PLAIN, 14));

		textPesquisaID = new JTextField();
		panel_1.add(textPesquisaID, "4, 2, fill, default");
		textPesquisaID.setColumns(10);

		JLabel lblNome = new JLabel("Nome");
		panel_1.add(lblNome, "2, 4, left, center");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 14));

		textFieldNome = new JTextField();
		panel_1.add(textFieldNome, "4, 4, fill, default");
		textFieldNome.setColumns(10);

		JCheckBox checkBoxAtivo = new JCheckBox("Ativa");
		panel_1.add(checkBoxAtivo, "4, 6, center, default");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);


		JButton botaoPesquisa = new JButton("Pesquisar");
		panel_1.add(botaoPesquisa, "4, 9");
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		botaoPesquisa.setFocusPainted(false);
		
	
		
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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

		panelUserESessao = new JPanel();
		panelUserESessao.setBackground(Color.WHITE);
		panelUserESessao.setBounds(1106, 7, 268, 53);
		contentPane.add(panelUserESessao);
		panelUserESessao.setLayout(null);

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(0, 0, 159, 16);
		panelUserESessao.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 15));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setBounds(0, 15, 159, 15);
		panelUserESessao.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 15));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(0, 29, 159, 16);
		panelUserESessao.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 15));
												
														JPanel panel = new JPanel();
														panel.setBounds(66, 267, 1263, 407);
														contentPane.add(panel);
														panel.setBackground(SystemColor.inactiveCaption);
														panel.setFont(font);
														panel.setLayout(null);
														
														
																lblResultados = new JLabel("Resultados: ");
																lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 15));
																lblResultados.setBounds(10, 4, 136, 25);
																panel.add(lblResultados);
																
																		table = new JTable();
																		table.setBounds(10, 33, 1243, 352);
																		panel.add(table);
																		table.setRowSelectionAllowed(true);
																		table.setColumnSelectionAllowed(false);
																		table.setFillsViewportHeight(true);
																		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
																		table.setForeground(SystemColor.desktop);
																		table.setBackground(UIManager.getColor("CheckBox.light"));
																		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
																		table.setRowHeight(20);
																		
																				JScrollPane scrollPane = new JScrollPane();
																				scrollPane.setBounds(10, 33, 1189, 352);
																				panel.add(scrollPane);
				
						table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if (table.getSelectedRowCount()>1) {
									botaoEditarPromocao.setEnabled(false);
									botaoDesativarPromocao.setEnabled(true);
								}
								else if (table.getSelectedRows().length==1) {
									botaoEditarPromocao.setEnabled(true);
									botaoDesativarPromocao.setEnabled(true);
								}
								else if (table.getSelectedRowCount()==0)
								{
									botaoEditarPromocao.setEnabled(false);
									botaoDesativarPromocao.setEnabled(false);
								}
							}
						});
		
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

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
	
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username : " + username);

	}
	
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessão: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}
	
	
	public JButton getBtVoltarGestorPromocao() {
		return btVoltarGestorPromocao;
	}
}
