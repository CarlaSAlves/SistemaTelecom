package guiComponentes.operador_gerirClientes;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import guiComponentes.admin_gestorOperador.GUI_gestor_operador;
import guiComponentes.admin_gestorOperador.HistoricoOperadorDialog;
import guiComponentes.admin_gestorOperador.OperadorPesquisaModelTable;
import historicos.HistoricoOperador;
import servico.GestorDeDAO;
import standard_value_object.Funcionario;

@SuppressWarnings("serial")
public class Operador_areaPessoal extends JFrame {

	private JPanel pane, painelPesquisa;
	private JLabel lblCamposPesquisas, labelID, labelNIF, labelNome, lblTempoSessao, lblUsernameLogged, lblHoraSistema, lblResultados;
	private JTextField textPesquisaID, textFieldNome, textPesquisaNIF;
	private JCheckBox checkBoxAtivo;
	private JButton botaoPesquisa, btAtribuirPacote, btAtribuirPromocao, visualizarPromocao, btHistorico, btVoltarOperador;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operador_areaPessoal frame = new Operador_areaPessoal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Operador_areaPessoal() {
		inicialize();

	}

	private void inicialize() {

		ativarNimbusLookAndFeel();

		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		setTitle("Área Pessoal Operador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		pane.setBackground(SystemColor.text);

		// -- Campo Pesquisa -- 

		lblCamposPesquisasSetup();

		pane.add(lblCamposPesquisas);

		painelPesquisa();

		// -- Botões --  

		btAtribuirPacote = new JButton("Atribuir Pacote");
		btAtribuirPacote.setBounds(1160, 231, 154, 40);
		pane.add(btAtribuirPacote);

		btAtribuirPromocao = new JButton("Atribuir Promoção");
		btAtribuirPromocao.setBounds(994, 231, 154, 40);
		pane.add(btAtribuirPromocao);

		visualizarPromocao = new JButton("Visualizar Promoção");
		visualizarPromocao.setBounds(833, 231, 154, 40);
		pane.add(visualizarPromocao);

		btHistorico = new JButton("Histórico");
		btHistorico.setBounds(667, 232, 154, 40);
		btHistorico.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		btHistorico.setBackground(SystemColor.activeCaption);
		btHistorico.setEnabled(false);
		pane.add(btHistorico);
		
		// tabela

		JPanel painelTabela = new JPanel();
		painelTabela.setBackground(SystemColor.window);
		painelTabela.setBounds(66, 309, 1279, 369);
		painelTabela.setFont(font);
		painelTabela.setLayout(null);
		pane.add(painelTabela);

		JScrollPane scrollPane = scrollPaneSetup();
		painelTabela.add(scrollPane);

		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		lblResultados.setBounds(33, 6, 136, 25);
		painelTabela.add(lblResultados);

		// Footer 

		btVoltarOperador = new JButton("Voltar");
		btVoltarOperador.setBounds(6, 709, 119, 38);
		btVoltarOperador.setFont(font);
		btVoltarOperador.setBackground(SystemColor.activeCaption);
		btVoltarOperador.setFocusPainted(false);
		pane.add(btVoltarOperador);

		JLabel lbFooter = new JLabel();
		lbFooter.setIcon(new ImageIcon(Operador_areaPessoal.class.getResource("/guiComponentes/img/AltranOperador.png")));
		lbFooter.setBounds(599, 690, 213, 65);
		pane.add(lbFooter);


		//  Visualizar log de histórico

		lblTempoSessao = new JLabel();
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		pane.add(lblTempoSessao);

		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setBounds(1215, 698, 159, 18);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		pane.add(lblUsernameLogged);

		lblHoraSistema = new JLabel();
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		pane.add(lblHoraSistema);

	}

	// Look Nimbus 

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

		return scrollPane;
	}

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

	private void lblCamposPesquisasSetup() {
		lblCamposPesquisas = new JLabel("Campo de Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(96, 39, 294, 26);

	}

	protected void painelPesquisa() {

		painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(98, 87, 453, 184);
		pane.add(painelPesquisa);

		labelID = new JLabel("ID");
		labelID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelID.setBounds(6, 15, 39, 18);
		painelPesquisa.add(labelID);

		textPesquisaID = new JTextField();
		textPesquisaID.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);

		labelNIF = new JLabel("NIF");
		labelNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelNIF.setBounds(6, 49, 56, 18);
		painelPesquisa.add(labelNIF);

		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textPesquisaNIF.setColumns(10);
		textPesquisaNIF.setBounds(72, 40, 371, 27);
		painelPesquisa.add(textPesquisaNIF);

		labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		labelNome.setBounds(6, 78, 56, 27);
		painelPesquisa.add(labelNome);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 78, 371, 27);
		painelPesquisa.add(textFieldNome);

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setBounds(235, 112, 69, 24);
		painelPesquisa.add(checkBoxAtivo);

		botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setBounds(72, 143, 371, 27);	

		painelPesquisa.add(botaoPesquisa);
	}


	








}
