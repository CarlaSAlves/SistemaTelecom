package guiComponentes.operador_PromoPacote;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import guiComponentes.admin_gestorPacoteComercial.PacoteComercialPesquisaModelTable;
import guiComponentes.admin_gestorPromocao.PromocaoPesquisaModelTable;
import servico.GestorDeDAO;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

@SuppressWarnings("serial")
public class Operador_VisualizarPromocoes extends JFrame {

	private JPanel contentPane, painelPesquisa;
	private JButton btVoltarOperadorHomepage, btPesquisar;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextArea textAreaDescricao;
	private JTable table;
	private JLabel lblResultados, labelID, labelNome,  lblUsernameLogged, lblTempoSessao, lblHoraSistema;
	private JTextField textPesquisaID, textFieldNome;
	private int numberRows;
	private JCheckBox checkBoxAtivo;

	/**
	 * Create the frame.
	 */
	public Operador_VisualizarPromocoes() {
		inicialize();
	}

	/**
	 * Settings of the page 
	 */
	protected void inicialize() {

		ativarNimbusLookAndFeel();

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Promoções");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane.setBackground(Color.WHITE);
		setResizable(false);

		// Buttons

		btVoltarOperadorHomepageSetup();
		getContentPane().add(btVoltarOperadorHomepage);

		// textArea 
		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(795, 105, 367, 151);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setEditable(false);
		contentPane.add(textAreaDescricao);

		// Table 

		JPanel panel = panelSetup();
		getContentPane().add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 33, 1224, 330);
		panel.add(scrollPane);

		tableSetup();
		scrollPane.setViewportView(table);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
		panel.add(lblResultados);

		// Campo de pesquisa 

		JLabel lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(98, 50, 294, 26);
		contentPane.add(lblCamposPesquisas);

		painelPesquisa();

		// Footer

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(Operador_VisualizarPromocoes.class.getResource("/guiComponentes/img/AltranOperadorFooter.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		contentPane.add(lbFooter);

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblUsernameLogged);

		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblTempoSessao);

		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		contentPane.add(lblHoraSistema);
	}

	/**
	 * Painel Pesquisa settings
	 */
	private void painelPesquisa() {
		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 104, 453, 175);
		contentPane.add(painelPesquisa);

		labelID = new JLabel("ID");
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelID.setBounds(6, 15, 39, 18);
		painelPesquisa.add(labelID);

		textPesquisaID = new JTextField();
		textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);

		labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelNome.setBounds(6, 44, 56, 27);
		painelPesquisa.add(labelNome);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 44, 371, 27);
		painelPesquisa.add(textFieldNome);
		
		checkBoxAtivo = new JCheckBox("Ativa");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setBounds(232, 78, 69, 24);
		painelPesquisa.add(checkBoxAtivo);

		btPesquisar = new JButton("Pesquisar");
		btPesquisar.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btPesquisar.addActionListener(new ActionListener() {
		
		

			public void actionPerformed(ActionEvent e) {
				try {
				    int id = 0;
					String nome = null;
					int ativo = checkBoxAtivo.isSelected() ? 1 : 0;

					if (!textPesquisaID.getText().isBlank()) {
						id = Integer.parseInt(textPesquisaID.getText().trim());
					}

					if (!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText().trim();
					}

					List<Promocao> promocoes = null;
					
					if ((id != 0) || (nome != null) || (ativo != 0)) {
						promocoes = GestorDeDAO.getGestorDeDAO().pesquisaPromocao(id, nome, ativo);
								
					} else {
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

		
		btPesquisar.setBounds(72, 121, 371, 27);
		painelPesquisa.add(btPesquisar);
	}

	/**
	 * Table setup
	 */
	private void tableSetup() {
		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setFocusable(false);
		table.setSelectionBackground(new Color(250,235,70));
		table.setSelectionForeground(Color.black);
		table.setModel(new DefaultTableModel(
				new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
					if (table.getSelectedRows().length == 1) {
					int row = table.getSelectedRow();
	
					Promocao promocao = (Promocao) table.getValueAt(row, PromocaoPesquisaModelTable.OBJECT_COL);
					textAreaDescricao.setText(promocao.getDescricao());
					
				}
			}
		});

	}

	/**
	 * Table panel setup
	 * @return painelTabela
	 */
	private JPanel panelSetup() {
		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(Color.WHITE);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		return painelTabela;
	}

	/**
	 * Voltar Button settings, to go back to the previous page 
	 */
	private void btVoltarOperadorHomepageSetup() {
		btVoltarOperadorHomepage = new JButton("Voltar");
		btVoltarOperadorHomepage.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		btVoltarOperadorHomepage.setBounds(6, 709, 119, 38);
		btVoltarOperadorHomepage.setFocusPainted(false);

	}

	/**
	 * Look and feel - Nimbus 
	 */
	private void ativarNimbusLookAndFeel() {
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
	}
	
	/**
	 * 
	 * @return table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * 
	 * @return btVoltarOperadorHomepage
	 */
	public JButton btVoltarOperadorHomepage() {
		return btVoltarOperadorHomepage;
	}

	/**
	 * 
	 * @param btVoltar
	 */
	public void setBtVoltarOperadorHomepage(JButton btVoltar) {
		this.btVoltarOperadorHomepage = btVoltar;
	}

	/**
	 * @param username
	 */
	
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}

	/**
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText(
				"Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart());;
	}

	/**
	 * 
	 * @param agora
	 */
	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);
	}

	/**
	 * 
	 * @return panel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
	
	/**
	 * 
	 * @return lblResultados
	 */
	public JLabel getLblResultados() {
		return lblResultados;
	}
	
	/**
	 * 
	 * @return textAreaDescricao
	 */
	public JTextArea getTextAreaDescricao() {
		return textAreaDescricao;
	}

	/**
	 * @return the textPesquisaID
	 */
	public JTextField getTextPesquisaID() {
		return textPesquisaID;
	}

	/**
	 * @return the textFieldNome
	 */
	public JTextField getTextFieldNome() {
		return textFieldNome;
	}

}
