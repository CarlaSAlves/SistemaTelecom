package GuiComponents.gestorOperador;

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
import Servico.SistemaTeleServico;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GUI_gestor_operador extends JFrame {
	private JTextField textPesquisaNIF;
	private JTable table;
	private JButton btVoltarGestorOperador;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados;
	private JButton botaoDesativarOperador;
	private JButton botaoEditarOperador;
	private int indices[];

	private Font font = new Font("Dubai Light", Font.PLAIN, 17);

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

		JLabel lblDeProcura = new JLabel("Introduza o NIF: ");
		lblDeProcura.setBounds(66, 18, 134, 26);
		lblDeProcura.setFont(font);
		getContentPane().add(lblDeProcura);

		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setBounds(210, 18, 161, 26);
		textPesquisaNIF.setFont(font);
		getContentPane().add(textPesquisaNIF);
		textPesquisaNIF.setColumns(10);

		JButton botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setBounds(411, 15, 119, 32);
		botaoPesquisa.setFont(font);
		botaoPesquisa.setFocusPainted(false);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String nif = textPesquisaNIF.getText();

					List<Funcionario> operadores = null;

					if (!nif.isBlank()) {
						operadores = SistemaTeleServico.getSistemaTeleServicoInstance().pesquisaFuncionarioOperador(nif);
					} else {
						operadores = SistemaTeleServico.getSistemaTeleServicoInstance().getAllFuncionarioOperador();

					}

					OperadorPesquisaModelTable model = new OperadorPesquisaModelTable(operadores);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {
				}
			}
		});
		getContentPane().add(botaoPesquisa);

		JButton botaoCriarOperador = new JButton("Criar Operador");
		botaoCriarOperador.setBounds(1250, 15, 161, 32);
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

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 74, 1366, 721);
		panel.setFont(font);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 660);
		panel.add(scrollPane);

		table = new JTable();
		// defin da table 
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		table.setRowHeight(20);

		scrollPane.setViewportView(table);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblResultados.setBounds(10, 4, 136, 25);
		panel.add(lblResultados);

		botaoEditarOperador = new JButton("Editar Operador");
		botaoEditarOperador.setBounds(849, 15, 161, 33);
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
		botaoDesativarOperador.setBounds(1038, 15, 188, 33);
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
						SistemaTeleServico.getSistemaTeleServicoInstance().desativarFuncionario(funcionarioTemp.getId());
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
		btVoltarGestorOperador.setBounds(26, 818, 119, 32);
		btVoltarGestorOperador.setFont(font);
		btVoltarGestorOperador.setBackground(SystemColor.activeCaption);
		btVoltarGestorOperador.setFocusPainted(false);
		getContentPane().add(btVoltarGestorOperador);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_operador.class.getResource("/img/footer2.png")));
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
	}

	public void refreshOperadorTable() {

		try {
			List<Funcionario> funcionarios = SistemaTeleServico.getSistemaTeleServicoInstance().getAllFuncionarioOperador();
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

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
