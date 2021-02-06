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

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Operador");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane.setBackground(SystemColor.inactiveCaption);

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

		getContentPane().add(botaoCriarOperador);

		JPanel panelDaTable = new JPanel();
		panelDaTable.setBackground(SystemColor.inactiveCaption);
		panelDaTable.setBounds(66, 310, 1366, 488);
		panelDaTable.setFont(font);
		getContentPane().add(panelDaTable);
		panelDaTable.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 488);
		panelDaTable.add(scrollPane);

		table = new JTable();
		// defin da table 
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);

		scrollPane.setViewportView(table);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblResultados.setBounds(10, 4, 136, 25);
		panelDaTable.add(lblResultados);

		botaoEditarOperador = new JButton("Editar Operador");
		botaoEditarOperador.setBounds(874, 270, 161, 33);
		botaoEditarOperador.setFont(font);
		botaoEditarOperador.setBackground(SystemColor.activeCaption);
		botaoEditarOperador.setFocusPainted(false);
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
		getContentPane().add(botaoEditarOperador);

		botaoDesativarOperador = new JButton("Desativar Operador");
		botaoDesativarOperador.setBounds(1045, 270, 188, 33);
		botaoDesativarOperador.setFont(font);
		botaoDesativarOperador.setBackground(SystemColor.activeCaption);
		botaoDesativarOperador.setFocusPainted(false);
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
						GestorDeDAO.getGestorDeDAO().desativarFuncionario(funcionarioTemp.getId());
					}

					JOptionPane.showMessageDialog(GUI_gestor_operador.this,
							"Operador desativado com sucesso", "Operador Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshOperadorTable();
				} catch (Exception e1) {

				}

			}

		});

		getContentPane().add(botaoDesativarOperador);

		btVoltarGestorOperador = new JButton("Voltar");
		btVoltarGestorOperador.setBounds(76, 809, 119, 32);
		btVoltarGestorOperador.setFont(font);
		btVoltarGestorOperador.setBackground(SystemColor.activeCaption);
		btVoltarGestorOperador.setFocusPainted(false);
		getContentPane().add(btVoltarGestorOperador);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_operador.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		contentPane.add(lbFooter);
		btVoltarGestorOperador.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					botaoEditarOperador.setEnabled(false);
					botaoDesativarOperador.setEnabled(true);
				}
				else if (table.getSelectedRows().length==1) {
					botaoEditarOperador.setEnabled(true);
					botaoDesativarOperador.setEnabled(true);
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarOperador.setEnabled(false);
					botaoDesativarOperador.setEnabled(false);
				}

			}
		});

		botaoEditarOperador.setEnabled(false);
		botaoDesativarOperador.setEnabled(false);
		
		lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 294, 26);
		contentPane.add(lblCamposPesquisas);
		
		panelPesquisa = new JPanel();
		panelPesquisa.setBackground(SystemColor.inactiveCaption);
		panelPesquisa.setBounds(66, 63, 437, 189);
		contentPane.add(panelPesquisa);
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
		
		lblNewLabelID = new JLabel("ID");
		lblNewLabelID.setFont(new Font("Dialog", Font.PLAIN, 13));
		panelPesquisa.add(lblNewLabelID, "2, 2, left, default");
		
		textPesquisaID = new JTextField();
		panelPesquisa.add(textPesquisaID, "4, 2, fill, default");
		textPesquisaID.setColumns(10);
		
		lblNewLabelNIF = new JLabel("NIF");
		lblNewLabelNIF.setFont(new Font("Dialog", Font.PLAIN, 13));
		panelPesquisa.add(lblNewLabelNIF, "2, 4, left, default");
		
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setColumns(10);
		panelPesquisa.add(textPesquisaNIF, "4, 4, fill, default");
		
		lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dialog", Font.PLAIN, 13));
		panelPesquisa.add(lblNome, "2, 6, left, default");
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		panelPesquisa.add(textFieldNome, "4, 6, fill, default");
		
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		panelPesquisa.add(checkBoxAtivo, "4, 10, center, default");
		
		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dialog", Font.PLAIN, 15));
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		panelPesquisa.add(botaoPesquisa, "4, 12");
		
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsernameLogged.setBounds(1252, 809, 159, 32);
		contentPane.add(lblUsernameLogged);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					String nif = textPesquisaNIF.getText();

					List<Funcionario> operadores = null;

					if (nif != null && nif.trim().length() > 0) {
						operadores = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioOperador(nif);
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
		lblUsernameLogged.setText("Logged in : " + username);

	}
	
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
