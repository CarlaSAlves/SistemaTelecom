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
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import guiComponentes.GUI_total;
import historicos.HistoricoOperador;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import java.awt.Color;

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
		inicialize();

	}

	protected void inicialize() {

		// visual look and feel - Nimbus 
		
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

		// -- Campo Pesquisa -- 

		lblCamposPesquisasSetup();
		contentPane.add(lblCamposPesquisas);

		panelPesquisaSetup();
		contentPane.add(panelPesquisa);

		labelIDSetup();
		panelPesquisa.setLayout(null);
		panelPesquisa.add(lblNewLabelID);

		textPesquisaIDSetup();
		panelPesquisa.add(textPesquisaID);

		labelNIFSetup();
		panelPesquisa.add(lblNewLabelNIF);

		textPesquisaNIFSetup();
		panelPesquisa.add(textPesquisaNIF);

		lblNomeSetup();
		panelPesquisa.add(lblNome);

		textFieldNomeSetup();
		panelPesquisa.add(textFieldNome);

		checkBoxAtivoSetup();
		panelPesquisa.add(checkBoxAtivo);

		botaoPesquisaSetup();
		panelPesquisa.add(botaoPesquisa);

		// -- Botões --  

		JButton botaoCriarOperador = botaoCriarOperadorSetup();
		getContentPane().add(botaoCriarOperador);

		botaoEditarOperadorSetup();
		getContentPane().add(botaoEditarOperador);

		botaoDesativarOperadorSetup();
		getContentPane().add(botaoDesativarOperador);

		btVoltarGestorOperadorSetup();
		getContentPane().add(btVoltarGestorOperador);

		//  Visualizar log de histórico

		botaoVisualizarHistoricoSetup();
		contentPane.add(botaoVisualizarHistorico);
		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1219, 709, 159, 18);
		contentPane.add(lblTempoSessao);

		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dialog", Font.PLAIN, 12));

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1219, 690, 159, 16);
		contentPane.add(lblUsernameLogged);

		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 12));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1219, 729, 159, 18);
		contentPane.add(lblHoraSistema);

		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dialog", Font.PLAIN, 12));

		// tabela

		JPanel panelDaTable = panelDaTableSetup();
		getContentPane().add(panelDaTable);

		JScrollPane scrollPane = scrollPaneSetup();
		panelDaTable.add(scrollPane);

		tableSetup();
		lblResultadosSetup();
		panelDaTable.add(lblResultados);

		// footer 

		JLabel lbFooter = lbFooterSetup();
		contentPane.add(lbFooter);

		panelUserESessaoSetup();
		lblUsernameLoggedSetup();
		lblTempoSessaoSetup();
		lblHoraSistemaSetup();


	}

	private void contentPaneSetup() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Operador");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane.setBackground(SystemColor.text);
	}
	
	private void lblCamposPesquisasSetup() {
		lblCamposPesquisas = new JLabel("Campos de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 26, 294, 26);
	}
	
	private void panelPesquisaSetup() {
		panelPesquisa = new JPanel();
		panelPesquisa.setBackground(SystemColor.text);
		panelPesquisa.setBounds(98, 63, 444, 215);
	}
	
	private void labelIDSetup() {
		lblNewLabelID = new JLabel("ID");
		lblNewLabelID.setBounds(10, 13, 12, 18);
		lblNewLabelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}
	
	private void textPesquisaIDSetup() {
		textPesquisaID = new JTextField();
		textPesquisaID.setBounds(84, 11, 350, 27);
		textPesquisaID.setColumns(10);
	}
	
	private void labelNIFSetup() {
		lblNewLabelNIF = new JLabel("NIF");
		lblNewLabelNIF.setBounds(10, 44, 20, 26);
		lblNewLabelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}
	
	private void textPesquisaNIFSetup() {
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setBounds(84, 47, 350, 27);
		textPesquisaNIF.setColumns(10);
	}
	
	private void lblNomeSetup() {
		lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 83, 64, 18);
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
	}
	
	private void textFieldNomeSetup() {
		textFieldNome = new JTextField();
		textFieldNome.setBounds(84, 81, 350, 27);
		textFieldNome.setColumns(10);
	}
	
	private void checkBoxAtivoSetup() {
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBounds(226, 112, 76, 24);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		checkBoxAtivo.setBackground(Color.WHITE);
	}
	
	private void botaoPesquisaSetup() {
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBounds(84, 143, 350, 25);
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
	
	private void botaoEditarOperadorSetup() {
		botaoEditarOperador = new JButton("Editar Operador");
		botaoEditarOperador.setBounds(826, 264, 161, 33);
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
	
	private void botaoDesativarOperadorSetup() {
		botaoDesativarOperador = new JButton("Desativar Operador");
		botaoDesativarOperador.setBounds(997, 264, 161, 33);
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
	
	private JButton botaoCriarOperadorSetup() {
		JButton botaoCriarOperador = new JButton("Criar Operador");
		botaoCriarOperador.setBounds(1168, 264, 152, 32);
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
	
	private void btVoltarGestorOperadorSetup() {
		btVoltarGestorOperador = new JButton("Voltar");
		btVoltarGestorOperador.setBounds(6, 709, 119, 38);
		btVoltarGestorOperador.setFont(font);
		btVoltarGestorOperador.setBackground(SystemColor.activeCaption);
		btVoltarGestorOperador.setFocusPainted(false);
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
		botaoVisualizarHistorico.setBounds(651, 264, 161, 33);
		botaoVisualizarHistorico.setEnabled(false);
	}
	
	private void lblResultadosSetup() {
		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
	}
	

	private void lblHoraSistemaSetup() {
	}

	private void lblTempoSessaoSetup() {
	}

	private void lblUsernameLoggedSetup() {
	}

	private void panelUserESessaoSetup() {
	}

	private void tableSetup() {
	}

	private JScrollPane scrollPaneSetup() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		table = new JTable();
		scrollPane.setViewportView(table);
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
		return scrollPane;
	}

	private JPanel panelDaTableSetup() {
		JPanel panelDaTable = new JPanel();
		panelDaTable.setBackground(SystemColor.window);
		panelDaTable.setBounds(66, 309, 1279, 369);
		panelDaTable.setFont(font);
		panelDaTable.setLayout(null);
		return panelDaTable;
	}
	
	private JLabel lbFooterSetup() {
		JLabel lbFooter = new JLabel();
		lbFooter.setIcon(new ImageIcon(GUI_gestor_operador.class.getResource("/guiComponentes/img/Altran1.1.png")));
		lbFooter.setBounds(599, 690, 367, 65);
		return lbFooter;
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
	
	
	public JTable getTable() {
		return table;
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
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
	
	public JLabel getLblResultados() {
		return lblResultados;
	}
	
}
