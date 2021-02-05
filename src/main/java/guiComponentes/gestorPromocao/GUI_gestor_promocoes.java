
package guiComponentes.gestorPromocao;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import standard_value_object.Promocao;
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
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

public class GUI_gestor_promocoes extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private JTextField textPesquisaId;
	private JTable table;
	private JButton btVoltarGestorPromocao;
	private JPanel contentPane;
	private int numberRows;
	private JCheckBox checkBoxAtivo;
	private JLabel lblResultados;
	private JButton botaoDesativarPromocao;
	private JButton botaoEditarPromocao;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 17);
	private JTextField textField;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_gestor_promocoes frame = new GUI_gestor_promocoes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_gestor_promocoes() {

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Promoções");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane.setBackground(SystemColor.inactiveCaption);

		JLabel lblDeProcura = new JLabel("Id da Promoção: ");
		lblDeProcura.setBounds(66, 68, 134, 26);
		lblDeProcura.setFont(font);
		getContentPane().add(lblDeProcura);

		textPesquisaId = new JTextField();
		textPesquisaId.setBounds(210, 105, 161, 26);
		textPesquisaId.setFont(font);
		getContentPane().add(textPesquisaId);
		textPesquisaId.setColumns(10);


		JButton botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setBounds(210, 196, 139, 32);
		botaoPesquisa.setFont(font);
		botaoPesquisa.setFocusPainted(false);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					String id = textPesquisaId.getText();

					List<Promocao> promocoes = null;


					if (!id.isBlank()) {
						promocoes = GestorDeDAO.getGestorDeDAO().pesquisaPromocao(id);
					} else  {
						promocoes = GestorDeDAO.getGestorDeDAO().getAllPromocoes();
					}

					PromocaoPesquisaModelTable model = new PromocaoPesquisaModelTable(promocoes);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}

			}
		});
		getContentPane().add(botaoPesquisa);

		JButton botaoCriarPromocao = new JButton("Criar Promoção");
		botaoCriarPromocao.setBounds(426, 126, 170, 32);
		botaoCriarPromocao.setFont(font);
		botaoCriarPromocao.setBackground(SystemColor.activeCaption);
		botaoCriarPromocao.setFocusPainted(false);
		botaoCriarPromocao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarPromocaoDialog dialog = new CriarPromocaoDialog(GUI_gestor_promocoes.this);
				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoCriarPromocao);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 296, 1366, 499);
		panel.setFont(font);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 660);
		panel.add(scrollPane);

		table = new JTable();
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

		botaoEditarPromocao = new JButton("Editar Promocao");
		botaoEditarPromocao.setBounds(426, 82, 170, 33);
		botaoEditarPromocao.setFont(font);
		botaoEditarPromocao.setBackground(SystemColor.activeCaption);
		botaoEditarPromocao.setFocusPainted(false);
		botaoEditarPromocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_promocoes.this,
							"Por favor selecione uma Promoção", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Promocao promocaoTemp = (Promocao) table.getValueAt(row, PromocaoPesquisaModelTable.OBJECT_COL);

				CriarPromocaoDialog dialog = new CriarPromocaoDialog(GUI_gestor_promocoes.this, promocaoTemp, true);

				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoEditarPromocao);

		botaoDesativarPromocao = new JButton("Desativar Promoção");
		botaoDesativarPromocao.setBounds(426, 38, 170, 33);
		botaoDesativarPromocao.setFont(font);
		botaoDesativarPromocao.setBackground(SystemColor.activeCaption);
		botaoDesativarPromocao.setFocusPainted(false);
		botaoDesativarPromocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_promocoes.this,
								"Por favor selecione uma Promoção", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_promocoes.this,
							"Desativar esta Promoção?", "Confirmar Desativar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					for(int i = 0; i < indices.length; i++) {
						Promocao promocaoTemp = (Promocao) table.getValueAt(indices[i], PromocaoPesquisaModelTable.OBJECT_COL);
						GestorDeDAO.getGestorDeDAO().desativarPromocao(promocaoTemp.getId());

					}
					JOptionPane.showMessageDialog(GUI_gestor_promocoes.this,
							"Cliente Desativado com sucesso", "Cliente Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshClienteTable();
				} catch (Exception e1) {

				}
			}

		});
		getContentPane().add(botaoDesativarPromocao);

		btVoltarGestorPromocao = new JButton("Voltar");
		btVoltarGestorPromocao.setBounds(26, 818, 119, 32);
		btVoltarGestorPromocao.setFont(font);
		btVoltarGestorPromocao.setBackground(SystemColor.activeCaption);
		btVoltarGestorPromocao.setFocusPainted(false);
		getContentPane().add(btVoltarGestorPromocao);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_promocoes.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		contentPane.add(lbFooter);
		

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

		botaoEditarPromocao.setEnabled(false);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		lblNome.setBounds(66, 105, 134, 26);
		contentPane.add(lblNome);

		textField = new JTextField();
		textField.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		textField.setColumns(10);
		textField.setBounds(210, 68, 161, 26);
		contentPane.add(textField);

		JLabel lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 26, 168, 26);
		contentPane.add(lblCamposPesquisas);
		botaoDesativarPromocao.setEnabled(false);

		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(699, 38, 499, 211);
		contentPane.add(textAreaDescricao);
		
		checkBoxAtivo = new JCheckBox("Ativa");
		checkBoxAtivo.setSelected(true);
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setFont(font);
		checkBoxAtivo.setBounds(210, 153, 97, 23);
		contentPane.add(checkBoxAtivo);
	}

	public void refreshClienteTable() {
		
		try {
			List<Promocao> promocoes = GestorDeDAO.getGestorDeDAO().getAllPromocoes();
			PromocaoPesquisaModelTable model = new PromocaoPesquisaModelTable(promocoes);
			table.setModel(model);

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public JButton btVoltarGestorPromocoes() {
		return btVoltarGestorPromocao;
	}

	public void setBtVoltarGestorPromocoes(JButton botaoVoltar2) {
		this.btVoltarGestorPromocao = botaoVoltar2;
	}

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
