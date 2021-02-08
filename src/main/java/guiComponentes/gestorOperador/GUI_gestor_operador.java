package guiComponentes.gestorOperador;

import java.awt.EventQueue;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import guiComponentes.GUI_total;
import historicos.HistoricoOperador;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class GUI_gestor_operador extends JFrame {
	private JTable table;
	private JButton btVoltarGestorOperador;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados;
	private JButton botaoDesativarOperador;
	private JButton botaoEditarOperador;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JLabel lblCamposPesquisas;
	private JPanel panelPesquisa;
	private JLabel lblNewLabelID;
	private JTextField textPesquisaID;
	private JTextField textPesquisaNIF;
	private JTextField textFieldNome;
	private JLabel lblNewLabelNIF;
	private JLabel lblNome;
	private JCheckBox checkBoxAtivo;
	private JButton botaoPesquisa;
	private JLabel lblUsernameLogged;
	private JPanel panelUserESessao;
	private JLabel lblTempoSessao;
	private JLabel lblHoraSistema;
	private JButton botaoVisualizarHistorico;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_gestor_operador frame = new GUI_gestor_operador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_gestor_operador() {

		contentPaneSetup();

		JButton botaoCriarOperador = botaoCriarOperadorSetup();
		getContentPane().add(botaoCriarOperador);

		JPanel panelDaTable = panelDaTableSetup();
		getContentPane().add(panelDaTable);

		JScrollPane scrollPane = scrollPaneSetup();
		panelDaTable.add(scrollPane);

		tableSetup();
		scrollPane.setViewportView(table);

		lblResultadosSetup();
		panelDaTable.add(lblResultados);

		botaoEditarOperadorSetup();
		getContentPane().add(botaoEditarOperador);

		botaoDesativarOperadorSetup();
		getContentPane().add(botaoDesativarOperador);

		btVoltarGestorOperadorSetup();
		getContentPane().add(btVoltarGestorOperador);

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);




		lblCamposPesquisasSetup();
		contentPane.add(lblCamposPesquisas);

		panelPesquisaSetup();
		contentPane.add(panelPesquisa);

		lblNewLabelIDSetup();
		panelPesquisa.add(lblNewLabelID, "2, 2, left, default");

		textPesquisaIDSetup();
		panelPesquisa.add(textPesquisaID, "4, 2, fill, default");

		lblNewLabelNIFSetup();
		panelPesquisa.add(lblNewLabelNIF, "2, 4, left, default");

		textPesquisaNIFSetup();
		panelPesquisa.add(textPesquisaNIF, "4, 4, fill, default");

		lblNomeSetup();
		panelPesquisa.add(lblNome, "2, 6, left, default");

		textFieldNomeSetup();
		panelPesquisa.add(textFieldNome, "4, 6, fill, default");

		checkBoxAtivoSetup();
		panelPesquisa.add(checkBoxAtivo, "4, 10, center, default");

		botaoPesquisaSetup();
		panelPesquisa.add(botaoPesquisa, "4, 12");

		panelUserESessaoSetup();
		contentPane.add(panelUserESessao);

		lblUsernameLoggedSetup();
		panelUserESessao.add(lblUsernameLogged);

		lblTempoSessaoSetup();
		panelUserESessao.add(lblTempoSessao);

		lblHoraSistemaSetup();
		panelUserESessao.add(lblHoraSistema);

		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);

	}

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

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		botaoVisualizarHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoVisualizarHistorico.setBackground(SystemColor.activeCaption);
		botaoVisualizarHistorico.setBounds(703, 270, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
	}

	private void lblHoraSistemaSetup() {
		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(0, 29, 159, 16);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 15));
	}

	private void lblTempoSessaoSetup() {
		lblTempoSessao = new JLabel();
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setBounds(0, 15, 159, 15);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 15));
	}

	private void lblUsernameLoggedSetup() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(0, 0, 159, 16);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 15));
	}

	private void panelUserESessaoSetup() {
		panelUserESessao = new JPanel();
		panelUserESessao.setBackground(SystemColor.inactiveCaption);
		panelUserESessao.setBounds(1222, 11, 252, 51);
		panelUserESessao.setLayout(null);
	}

	private void botaoPesquisaSetup() {
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int id = 0;
					String nif = null;
					String nome = null;
					int ativo = checkBoxAtivo.isSelected()? 1:0;

					if(!textPesquisaID.getText().isBlank()) {
						id = Integer.parseInt(textPesquisaID.getText());
					}

					if(!textPesquisaNIF.getText().isBlank()) {
						nif = textPesquisaNIF.getText();
					}

					if(!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText();
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
	}

	private void checkBoxAtivoSetup() {
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
	}

	private void textFieldNomeSetup() {
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
	}

	private void lblNomeSetup() {
		lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textPesquisaNIFSetup() {
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setColumns(10);
	}

	private void lblNewLabelNIFSetup() {
		lblNewLabelNIF = new JLabel("NIF");
		lblNewLabelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void textPesquisaIDSetup() {
		textPesquisaID = new JTextField();
		textPesquisaID.setColumns(10);
	}

	private void lblNewLabelIDSetup() {
		lblNewLabelID = new JLabel("ID");
		lblNewLabelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}

	private void panelPesquisaSetup() {
		panelPesquisa = new JPanel();
		panelPesquisa.setBackground(SystemColor.inactiveCaption);
		panelPesquisa.setBounds(66, 63, 437, 189);
		panelPesquisa.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));
	}

	private void lblCamposPesquisasSetup() {
		lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 294, 26);
	}

	private JLabel lbFooterSetup() {
		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_operador.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		return lbFooter;
	}

	private void btVoltarGestorOperadorSetup() {
		btVoltarGestorOperador = new JButton("Voltar");
		btVoltarGestorOperador.setBounds(76, 809, 119, 32);
		btVoltarGestorOperador.setFont(font);
		btVoltarGestorOperador.setBackground(SystemColor.activeCaption);
		btVoltarGestorOperador.setFocusPainted(false);
	}

	private void botaoDesativarOperadorSetup() {
		botaoDesativarOperador = new JButton("Desativar Operador");
		botaoDesativarOperador.setBounds(1045, 270, 188, 33);
		botaoDesativarOperador.setFont(font);
		botaoDesativarOperador.setBackground(SystemColor.activeCaption);
		botaoDesativarOperador.setFocusPainted(false);
		botaoDesativarOperador.setEnabled(false);
		botaoDesativarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_operador.this,
								"Por favor selecione um Operador", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_operador.this,
							"Desativar este Operador?", "Confirmar Desactivar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					for(int i = 0; i < indices.length; i++) {
						Funcionario funcionarioTemp = (Funcionario) table.getValueAt(indices[i], OperadorPesquisaModelTable.OBJECT_COL);
						Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(GUI_total.getUsername());
						GestorDeDAO.getGestorDeDAO().desativarFuncionario(funcionarioTemp.getId(),admin );
					}

					JOptionPane.showMessageDialog(GUI_gestor_operador.this,
							"Operador desativado com sucesso", "Operador Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshOperadorTable();
				} catch (Exception e1) {

				}

			}

		});
	}

	private void botaoEditarOperadorSetup() {
		botaoEditarOperador = new JButton("Editar Operador");
		botaoEditarOperador.setBounds(874, 270, 161, 33);
		botaoEditarOperador.setFont(font);
		botaoEditarOperador.setBackground(SystemColor.activeCaption);
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

				dialog.setVisible(true);

			}
		});
	}

	private void lblResultadosSetup() {
		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
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
					botaoEditarOperador.setEnabled(false);
					botaoDesativarOperador.setEnabled(true);
					botaoVisualizarHistorico.setEnabled(false);
				}
				else if (table.getSelectedRows().length==1) {
					botaoEditarOperador.setEnabled(true);
					botaoDesativarOperador.setEnabled(true);
					botaoVisualizarHistorico.setEnabled(true);
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

	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 488);
		return scrollPane;
	}

	private JPanel panelDaTableSetup() {
		JPanel panelDaTable = new JPanel();
		panelDaTable.setBackground(SystemColor.inactiveCaption);
		panelDaTable.setBounds(66, 310, 1366, 488);
		panelDaTable.setFont(font);
		panelDaTable.setLayout(null);
		return panelDaTable;
	}

	private JButton botaoCriarOperadorSetup() {
		JButton botaoCriarOperador = new JButton("Criar Operador");
		botaoCriarOperador.setBounds(1243, 270, 161, 32);
		botaoCriarOperador.setFont(font);
		botaoCriarOperador.setBackground(SystemColor.activeCaption);
		botaoCriarOperador.setFocusPainted(false);
		botaoCriarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CriarOperadorDialog dialog = new CriarOperadorDialog(GUI_gestor_operador.this);
				dialog.setVisible(true);
			}
		});
		return botaoCriarOperador;
	}

	private void contentPaneSetup() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Operador");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane.setBackground(SystemColor.inactiveCaption);
	}

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

	public JButton btVoltarGestorOperador() {
		return btVoltarGestorOperador;
	}

	public void setBtVoltarGestorOperador(JButton botaoVoltar2) {
		this.btVoltarGestorOperador = botaoVoltar2;
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

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
