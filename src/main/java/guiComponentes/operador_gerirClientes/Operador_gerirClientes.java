package guiComponentes.operador_gerirClientes;


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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import guiComponentes.GUI_total;
import guiComponentes.admin_gestorCliente.ClientePesquisaModelTable;
import guiComponentes.admin_gestorCliente.CriarClienteDialog;
import guiComponentes.admin_gestorCliente.GUI_gestor_cliente;
import guiComponentes.admin_gestorOperador.GUI_gestor_operador;
import guiComponentes.admin_gestorOperador.HistoricoOperadorDialog;
import guiComponentes.admin_gestorOperador.OperadorPesquisaModelTable;
import historicos.HistoricoOperador;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

@SuppressWarnings("serial")
public class Operador_gerirClientes extends JFrame {

	private int numberRows;
	private JPanel pane;
	private JLabel lblCampoPesquisas, lblTempoSessao, lblUsernameLogged, lblHoraSistema, lblResultados;
	private JButton btAtribuirPacote, btAtribuirPromocao, btVisualizarPromocao, btHistorico, btVoltarOperador;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTable table;
	private JTextField textPesquisaID;
	private JTextField textPesquisaNIF;
	private JTextField textFieldNome;
	private JTextField textFieldMorada;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operador_gerirClientes frame = new Operador_gerirClientes();
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
	public Operador_gerirClientes() {
		inicialize();

	}

	private void inicialize() {

		ativarNimbusLookAndFeel();

		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		setTitle("Operador - Gerir Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		pane.setBackground(SystemColor.text);

		// -- Campo Pesquisa -- 

		lblCamposPesquisasSetup();

		pane.add(lblCampoPesquisas);

		painelPesquisa();

		// -- Botões --  

		btAtribuirPacote = new JButton("Atribuir Pacote Comercial");
		btAtribuirPacote.setBounds(1135, 231, 187, 40);
		pane.add(btAtribuirPacote);
		btAtribuirPacote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Operador_atribuirPacoteDialog dialog = new Operador_atribuirPacoteDialog(Operador_gerirClientes.this);
				dialog.setVisible(true);
				dialog.setResizable(false);
				
			}
		});

		btAtribuirPromocao = new JButton("Atribuir Promoção");
		btAtribuirPromocao.setBounds(947, 231, 157, 40);
		pane.add(btAtribuirPromocao);
		btAtribuirPromocao.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

					Operador_atribuirPacoteDialog dialog = new Operador_atribuirPacoteDialog(Operador_gerirClientes.this);
					dialog.setVisible(true);
					dialog.setResizable(false);

				}
			});

		btVisualizarPromocao = new JButton("Visualizar Promoções");
		btVisualizarPromocao.setBounds(947, 179, 157, 40);
		pane.add(btVisualizarPromocao);
		btVisualizarPromocao.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Operador_visualizarPacoteDialog dialog = new Operador_visualizarPacoteDialog(Operador_gerirClientes.this);
				dialog.setVisible(true);
				dialog.setResizable(false);
				
			}
		});
		
		JButton btnVisualizarPacote = new JButton("Visualizar Pacote Comercial");
		btnVisualizarPacote.setBounds(1135, 179, 187, 40);
		pane.add(btnVisualizarPacote);
		btnVisualizarPacote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Operador_visualizarPacoteDialog dialog = new Operador_visualizarPacoteDialog(Operador_gerirClientes.this);
				dialog.setVisible(true);
				dialog.setResizable(false);
				
			}
		});

		btHistorico = new JButton("Histórico");
		btHistorico.setBounds(764, 232, 154, 40);
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
		lbFooter.setIcon(new ImageIcon(Operador_gerirClientes.class.getResource("/guiComponentes/img/AltranOperadorFooter.png")));
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
		
		JPanel painelPesquisa = new JPanel();
		painelPesquisa.setLayout(null);
		painelPesquisa.setBackground(Color.WHITE);
		painelPesquisa.setBounds(96, 77, 453, 221);
		pane.add(painelPesquisa);
		
		JLabel labelID = new JLabel("ID");
		labelID.setFont(new Font("Dialog", Font.PLAIN, 13));
		labelID.setBounds(6, 15, 39, 18);
		painelPesquisa.add(labelID);
		
		textPesquisaID = new JTextField();
		textPesquisaID.setColumns(10);
		textPesquisaID.setBounds(72, 6, 371, 27);
		painelPesquisa.add(textPesquisaID);
		
		JLabel labelNIF = new JLabel("NIF");
		labelNIF.setFont(new Font("Dialog", Font.PLAIN, 13));
		labelNIF.setBounds(6, 49, 56, 18);
		painelPesquisa.add(labelNIF);
		
		textPesquisaNIF = new JTextField();
		textPesquisaNIF.setColumns(10);
		textPesquisaNIF.setBounds(72, 40, 371, 27);
		painelPesquisa.add(textPesquisaNIF);
		
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Dialog", Font.PLAIN, 13));
		labelNome.setBounds(6, 87, 56, 18);
		painelPesquisa.add(labelNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(72, 78, 371, 27);
		painelPesquisa.add(textFieldNome);
		
		JLabel labelMorada = new JLabel("Morada");
		labelMorada.setFont(new Font("Dialog", Font.PLAIN, 13));
		labelMorada.setBounds(6, 116, 56, 27);
		painelPesquisa.add(labelMorada);
		
		textFieldMorada = new JTextField();
		textFieldMorada.setColumns(10);
		textFieldMorada.setBounds(72, 116, 371, 27);
		painelPesquisa.add(textFieldMorada);
		
		JCheckBox checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setFont(new Font("Dialog", Font.PLAIN, 13));
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setBounds(234, 150, 69, 24);
		painelPesquisa.add(checkBoxAtivo);
		
		JButton botaoPesquisa = new JButton("Pesquisar");
		botaoPesquisa.setFont(new Font("Dialog", Font.PLAIN, 13));
		botaoPesquisa.setBackground(SystemColor.activeCaption);
		botaoPesquisa.setBounds(72, 181, 371, 27);
		botaoPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int id = 0;
					String nif = null;
					String nome = null;
					String morada = null;
					int ativo = checkBoxAtivo.isSelected()? 1:0;

					if(!textPesquisaID.getText().isBlank()) {
						id = Integer.parseInt(textPesquisaID.getText().trim());
					}

					if(!textPesquisaNIF.getText().isBlank()) {
						nif = textPesquisaNIF.getText().trim();
					}

					if(!textFieldNome.getText().isBlank()) {
						nome = textFieldNome.getText().trim();
					}

					if(!textFieldMorada.getText().isBlank()) {
						morada = textFieldMorada.getText().trim();
					}


					List<Cliente> clientes = null;

					if ((id != 0) || (nif != null) || (nome != null) || (morada != null) || (ativo!=0) ) {
						clientes = GestorDeDAO.getGestorDeDAO().pesquisaCliente(id, nif, nome, morada, ativo);
					} else {
						clientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
					}

					ClientePesquisaModelTableOP model = new ClientePesquisaModelTableOP(clientes);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}



			}
		});
		painelPesquisa.add(botaoPesquisa);
		
	

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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					btAtribuirPacote.setEnabled(false);
					btAtribuirPromocao.setEnabled(false);
					btVisualizarPromocao.setEnabled(false);
					btHistorico.setEnabled(false);
				}
				else if (table.getSelectedRows().length==1) {
					btAtribuirPacote.setEnabled(true);
					btAtribuirPromocao.setEnabled(true);
					btVisualizarPromocao.setEnabled(true);
					btHistorico.setEnabled(true);
				}
				else if (table.getSelectedRowCount()==0)
				{
					btAtribuirPacote.setEnabled(false);
					btAtribuirPromocao.setEnabled(false);
					btVisualizarPromocao.setEnabled(false);
					btHistorico.setEnabled(false);
				}
				
			}	
		});
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
		lblCampoPesquisas = new JLabel("Campo de Pesquisa");
		lblCampoPesquisas.setFont(new Font("Dubai Light", Font.BOLD, 20));
		lblCampoPesquisas.setBounds(96, 39, 294, 26);

	}

	protected void painelPesquisa() {
	}

	public JTable getTable() {
		return table;
	}

	public JButton btVoltarOperador() {
		return btVoltarOperador;
	}

	public void setBtVoltarOperador(JButton botaoVoltar2) {
		this.btVoltarOperador = botaoVoltar2;
	}

	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);

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
