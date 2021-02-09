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
import standard_value_object.Promocao;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;


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
	private JLabel lblTempoSessao;
	private JLabel lblHoraSistema;
	JCheckBox checkBoxAtivo;


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
		
		contentPaneSetup();

		/* Lbl e botão de Procura */

		JButton botaoCriarPromocao = botaoCriarPromocaoSetup();
		getContentPane().add(botaoCriarPromocao);

		JPanel panel = panelSetup();
		getContentPane().add(panel);

		JScrollPane scrollPane = scrollPaneSetup();
		panel.add(scrollPane);

		tableSetup();


		scrollPane.setViewportView(table);


		lblResultadosSetup();
		panel.add(lblResultados);

		botaoEditarPromocaoSetup();
		getContentPane().add(botaoEditarPromocao);

		botaoDesativarPromocaoSetup();
		getContentPane().add(botaoDesativarPromocao);

		btVoltarGestorPromocaoSetup();
		getContentPane().add(btVoltarGestorPromocao);

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);	

		

		JLabel lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 294, 26);
		contentPane.add(lblCamposPesquisas);
		

		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(938, 71, 470, 151);
		contentPane.add(textAreaDescricao);

		JPanel panel_1 = panel_1Setup();
		panel_1ContentSetup(panel_1);
		contentPane.add(panel_1);
	
		panelUserESessaoSetup();
		panelUserESessaoContentSetup();
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1297, 820, 159, 15);
		contentPane.add(lblTempoSessao);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1297, 805, 159, 16);
		contentPane.add(lblUsernameLogged);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1297, 835, 159, 16);
		contentPane.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dialog", Font.PLAIN, 13));

		
		
	}

	private void panelUserESessaoContentSetup() {
		lblUsernameLoggedSetup();

		lblTempoSessaoSetup();

		lblHoraSistemaSetup();
	}

	private void lblHoraSistemaSetup() {
	}

	private void lblTempoSessaoSetup() {
	}

	private void lblUsernameLoggedSetup() {
	}

	private void panel_1ContentSetup(JPanel panel_1) {
		JLabel lblDeProcura = lblDeProcuraSetup();
		panel_1.add(lblDeProcura, "2, 2, left, center");

		textPesquisaIDSetup();
		panel_1.add(textPesquisaID, "4, 2, fill, default");

		JLabel lblNome = lblNomeSetup();
		panel_1.add(lblNome, "2, 4, left, center");

		textFieldNomeSetup();
		panel_1.add(textFieldNome, "4, 4, fill, default");

		JCheckBox checkBoxAtivo = checkBoxAtivoSetup();
		panel_1.add(checkBoxAtivo, "4, 6, center, default");


		JButton botaoPesquisa = botaoPesquisaSetup();
		panel_1.add(botaoPesquisa, "4, 9");
	}

	private void panelUserESessaoSetup() {
	}

	private JButton botaoPesquisaSetup() {
		JButton botaoPesquisa = new JButton("Pesquisar");
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
		return botaoPesquisa;
	}

	private JCheckBox checkBoxAtivoSetup() {
		checkBoxAtivo = new JCheckBox("Ativa");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		return checkBoxAtivo;
	}

	private void textFieldNomeSetup() {
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
	}

	private JLabel lblNomeSetup() {
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		return lblNome;
	}

	private void textPesquisaIDSetup() {
		textPesquisaID = new JTextField();
		textPesquisaID.setColumns(10);
	}

	private JLabel lblDeProcuraSetup() {
		JLabel lblDeProcura = new JLabel("ID");
		lblDeProcura.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		return lblDeProcura;
	}

	private JPanel panel_1Setup() {
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setForeground(SystemColor.desktop);
		panel_1.setBounds(66, 63, 380, 177);
		
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
		return panel_1;
	}

	private JLabel lbFooterSetup() {
		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_promocao.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		return lbFooter;
	}

	private void btVoltarGestorPromocaoSetup() {
		btVoltarGestorPromocao = new JButton("Voltar");
		btVoltarGestorPromocao.setBounds(76, 806, 119, 32);
		btVoltarGestorPromocao.setFont(font);
		btVoltarGestorPromocao.setBackground(SystemColor.activeCaption);
		btVoltarGestorPromocao.setFocusPainted(false);
	}

	private void botaoDesativarPromocaoSetup() {
		botaoDesativarPromocao = new JButton("Desativar Promoção");
		botaoDesativarPromocao.setBounds(697, 71, 231, 43);
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
		botaoEditarPromocao.setBounds(697, 125, 231, 43);
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
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblResultados.setBounds(10, 4, 136, 25);
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

	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 660);
		return scrollPane;
	}

	private JPanel panelSetup() {
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 296, 1366, 499);
		panel.setFont(font);
		
		panel.setLayout(null);
		return panel;
	}

	private JButton botaoCriarPromocaoSetup() {
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
		contentPane.setBackground(SystemColor.inactiveCaption);
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
