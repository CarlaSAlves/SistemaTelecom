package guiComponentes;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager.*;
import guiComponentes.admin_gestorCliente.GUI_gestor_cliente;
import guiComponentes.admin_gestorOperador.GUI_gestor_operador;
import guiComponentes.admin_gestorPacoteComercial.GUI_gestor_pacotes;
import guiComponentes.admin_gestorPromocao.GUI_gestor_promocao;
import guiComponentes.cliente_pessoal.AreaCliente;
import guiComponentes.operador_PromoPacote.Operador_VisualizarPacote;
import guiComponentes.operador_PromoPacote.Operador_VisualizarPromocoes;
import guiComponentes.operador_gerirClientes.Operador_gerirClientes;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;



public class GUI_total extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pane, loginPanel, homepagePanel, gestor_clientePanel, gestor_operadorPanel, gestor_pacotesPanel, gestor_promocaoPanel, 
	operador_homepagePanel, operador_gerirClientesPanel, operador_visualizarPromoPanel, areaClientePanel, operador_visualizarPacotePanel;
	private static String username;
	private Instant inicio;
	private GUI_gestor_cliente gestor_cliente;
	private GUI_login login;
	private Admin_GUI_homepage homepage;
	private	GUI_gestor_operador gestor_operador;
	private GUI_gestor_pacotes gestor_pacotes;
	private	GUI_gestor_promocao gestor_promocao;
	private Operador_homepage operador_homepage;
	private Operador_gerirClientes operador_gerirClientes;
	private Operador_VisualizarPacote operador_visualizarPacote;
	private Operador_VisualizarPromocoes operador_visualizarPromo;
	private AreaCliente areaCliente;
	private Duration temporizador;
	private String dataEHoraDeLog;
	private SimpleDateFormat dateFormat ;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_total frame = new GUI_total();
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public GUI_total() {

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");

		ativarNimbusLookAndFeel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		pane = new JPanel();
		setResizable(false);
		setContentPane(pane);
		setLayout(null);

		// criação dos objectos referentes às páginas

		login = new GUI_login(this);
		homepage = new Admin_GUI_homepage(this);
		gestor_cliente = new GUI_gestor_cliente();
		gestor_operador = new GUI_gestor_operador();
		gestor_pacotes = new GUI_gestor_pacotes();
		gestor_promocao = new GUI_gestor_promocao();
		operador_homepage = new Operador_homepage(this);
		operador_gerirClientes = new Operador_gerirClientes();
		operador_visualizarPacote = new Operador_VisualizarPacote();
		operador_visualizarPromo = new Operador_VisualizarPromocoes();
		areaCliente = new AreaCliente(this);
		
		// ligação - login 

		loginPanel = login.returnPanel();
		loginPanel.setBounds(0, 0, 1400, 800);
		pane.add(loginPanel);

		login.getBtnSair().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				dispose();

			}
		});


		// ligação homepage

		homepagePanel = homepage.returnPanel();
		homepagePanel.setVisible(false);
		homepagePanel.setBounds(0, 0, 1400, 800);
		pane.add(homepagePanel);

		// ligação Admin gestor Cliente

		gestor_clientePanel = gestor_cliente.returnPanel();
		gestor_clientePanel.setVisible(false);
		gestor_clientePanel.setBounds(0, 0, 1400, 800);
		pane.add(gestor_clientePanel);
		gestor_cliente.btVoltarGestorCliente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepagePanel.setVisible(true);
				gestor_clientePanel.setVisible(false);
			}
		});

		// ligação Admin gestor Operador

		gestor_operadorPanel = gestor_operador.returnPanel();
		gestor_operadorPanel.setVisible(false);
		gestor_operadorPanel.setBounds(0, 0, 1400, 800);
		pane.add(gestor_operadorPanel);
		gestor_operador.btVoltarGestorOperador().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(true);
				gestor_operadorPanel.setVisible(false);
			}
		});

		// ligação Admin gestor Pacotes Comerciais

		gestor_pacotesPanel = gestor_pacotes.returnPanel();
		gestor_pacotesPanel.setVisible(false);
		gestor_pacotesPanel.setBounds(0, 0, 1400, 800);
		pane.add(gestor_pacotesPanel);
		gestor_pacotes.btVoltarGestorPacotes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(true);
				gestor_pacotesPanel.setVisible(false);
			}
		});

		// ligação Admin gestor Promoção

		gestor_promocaoPanel = gestor_promocao.returnPanel();
		gestor_promocaoPanel.setVisible(false);
		gestor_promocaoPanel.setBounds(0, 0, 1400, 800);
		pane.add(gestor_promocaoPanel);
		gestor_promocao.getBtVoltarGestorPromocao().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(true);
				gestor_promocaoPanel.setVisible(false);
			}
		});

		// ligação Operador homepage

		operador_homepagePanel = operador_homepage.returnPanel();
		operador_homepagePanel.setVisible(false);
		operador_homepagePanel.setBounds(0, 0, 1400, 800);
		pane.add(operador_homepagePanel);

		// ligação Operador gerir clientes

		operador_gerirClientesPanel = operador_gerirClientes.returnPanel();
		operador_gerirClientesPanel.setVisible(false);
		operador_gerirClientesPanel.setBounds(0, 0, 1400, 800);
		pane.add(operador_gerirClientesPanel);
		operador_gerirClientes.btVoltarOperador().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				operador_gerirClientesPanel.setVisible(false);
				operador_homepagePanel.setVisible(true);

			}
		});

		// ligação Operador - Visualizar Pacotes Comerciais

		operador_visualizarPacotePanel = operador_visualizarPacote.returnPanel();
		operador_visualizarPacotePanel.setVisible(false);
		operador_visualizarPacotePanel.setBounds(0, 0, 1400, 800);
		pane.add(operador_visualizarPacotePanel);
		operador_visualizarPacote.btVoltarOperadorHomepage().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				operador_visualizarPacotePanel.setVisible(false);
				operador_homepagePanel.setVisible(true);

			}
		});

		// ligação Operador - Visualizar Promocoes

		operador_visualizarPromoPanel = operador_visualizarPromo.returnPanel();
		operador_visualizarPromoPanel.setVisible(false);
		operador_visualizarPromoPanel.setBounds(0, 0, 1400, 800);
		pane.add(operador_visualizarPromoPanel);
		operador_visualizarPromo.btVoltarOperadorHomepage().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				operador_visualizarPromoPanel.setVisible(false);
				operador_homepagePanel.setVisible(true);

			}
		});

		//ligação ao painel do cliente

		areaClientePanel = areaCliente.returnPanel();
		areaClientePanel.setVisible(false);
		areaClientePanel.setBounds(0, 0, 1400, 800);
		pane.add(areaClientePanel);


	}

	/**
	 * Configurar interface, look and feel Nimbus 
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
	 * Método que transporta o username pelos paineis da aplicação
	 * @param login
	 * @param homepage
	 * @param gestor_cliente
	 * @param gestor_operador
	 * @param gestor_pacotes
	 * @param gestor_promocao
	 * @param operador_homepage
	 * @param operador_visualizarPacotes
	 * @param operador_visualizarPromocoes
	 * @param areaCliente
	 * @param operador_gerirClientes
	 * @throws Exception
	 */
	private void labelUsernameNavegaPaginas(GUI_login login, Admin_GUI_homepage homepage,
			GUI_gestor_cliente gestor_cliente, GUI_gestor_operador gestor_operador,
			GUI_gestor_pacotes gestor_pacotes, GUI_gestor_promocao gestor_promocao, Operador_homepage operador_homepage, 
			Operador_VisualizarPacote operador_visualizarPacotes, Operador_VisualizarPromocoes operador_visualizarPromocoes, AreaCliente areaCliente, Operador_gerirClientes operador_gerirClientes) throws Exception {
		username = login.getUserText().getText();
		gestor_cliente.setUsernameLoggedIn(username);
		gestor_operador.setUsernameLoggedIn(username);
		gestor_pacotes.setUsernameLoggedIn(username);
		gestor_promocao.setUsernameLoggedIn(username);
		homepage.setUsernameLoggedIn(username);
		operador_homepage.setUsernameLoggedIn(username);
		operador_visualizarPacotes.setUsernameLoggedIn(username);
		operador_visualizarPromocoes.setUsernameLoggedIn(username);		
		operador_gerirClientes.setUsername(username);
		operador_gerirClientes.setUsernameLoggedIn(username);
		areaCliente.setUsernameLoggedIn(username);
		areaCliente.enviarUsernameAreaCliente(username);
		areaCliente.getTabbedPane().setSelectedIndex(0);
		
		Funcionario func = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
		Cliente cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLogin(username);

		if (func != null && func.getId_role() == 2) {
			operador_homepage.setLabelBoasVindas(func.getNome());
		} else if (cliente != null) {
			areaCliente.setLabelBoasVindas(cliente.getNome());
		}
	}

	
	/**
	 * Configura o temporizador e insere-o nos paineis
	 */
	private void comecarTemporizador(){

		long data1 = System.currentTimeMillis();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(data1);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		dataEHoraDeLog = dateFormat.format(cal2.getTime());

		Thread t = new Thread(){
			public void run(){
				while (true) {

					long data = System.currentTimeMillis();
					Instant agora = Instant.now();
					Calendar cal1 = Calendar.getInstance();
					temporizador = Duration.between(inicio, agora);
					cal1.setTimeInMillis(data);
					dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
					String dataEHora = dateFormat.format(cal1.getTime());

					homepage.setLblTempoSessao(temporizador);
					homepage.setLblHoraSistema(dataEHora);

					gestor_cliente.setLblTempoSessao(temporizador);
					gestor_cliente.setLblHoraSistema(dataEHora);

					gestor_operador.setLblTempoSessao(temporizador);
					gestor_operador.setLblHoraSistema(dataEHora);

					gestor_pacotes.setLblTempoSessao(temporizador);
					gestor_pacotes.setLblHoraSistema(dataEHora);

					gestor_promocao.setLblTempoSessao(temporizador); 
					gestor_promocao.setLblHoraSistema(dataEHora);

					operador_homepage.setLblTempoSessao(temporizador);
					operador_homepage.setLblHoraSistema(dataEHora);

					operador_visualizarPacote.setLblTempoSessao(temporizador);
					operador_visualizarPacote.setLblHoraSistema(dataEHora);

					operador_visualizarPromo.setLblTempoSessao(temporizador);
					operador_visualizarPromo.setLblHoraSistema(dataEHora);

					areaCliente.setLblTempoSessao(temporizador);
					areaCliente.setLblHoraSistema(dataEHora);

					operador_gerirClientes.setLblTempoSessao(temporizador);
					operador_gerirClientes.setLblHoraSistema(dataEHora);
					
					try {
						sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		t.start();
	}

	/**
	 * Método que grava no ficheiro sessaolog o registo das sessões, username, tempo de sessão, data e hora
	 * @param username
	 * @param temporizador
	 * @param dataEHora
	 * @param ficheiro
	 */
	private void gravarFicheiro(String username, Duration temporizador, String dataEHora, String ficheiro) {

		try {
			PrintWriter guarda = new PrintWriter(new BufferedWriter (new FileWriter(ficheiro, true)));
			guarda.println("<sessao>");
			guarda.println("<username:	" + username + ">");
			guarda.println("<data e hora do login:	" + dataEHora +  ">" );
			guarda.println("<duração da sessão:	" + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart() +  ">" );
			guarda.println("</sessao>\n");

			guarda.close();
		}	 catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 
	 * @return username
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * Este método comunica com o login, configura os paineis que se escondem e aparecem conforme 
	 * o username que fez login
	 * @param role
	 * @throws Exception
	 */
	public void loginEfetuado(int role) throws Exception {
		inicio = Instant.now();
		labelUsernameNavegaPaginas(login, homepage, gestor_cliente, gestor_operador, gestor_pacotes,
				gestor_promocao, operador_homepage, operador_visualizarPacote, operador_visualizarPromo, areaCliente ,operador_gerirClientes);
		loginPanel.setVisible(false);
		if (role == 1) {
			homepagePanel.setVisible(true);
		} else if(role == 2) {
			operador_homepagePanel.setVisible(true);
		} else if (role == 0) {
			areaClientePanel.setVisible(true);
		}
		comecarTemporizador();
	}

	/**
	 * Faz reset às tabelas e ao campo de pesquisa do administrador quando o botão voltar é premido 
	 */
	public void voltarBtAdminHomePage() {
		gravarFicheiro(username, temporizador, dataEHoraDeLog, "sessaolog.txt");
		
		loginPanel.setVisible(true);
		homepagePanel.setVisible(false);

		gestor_cliente.getTable().setModel(new DefaultTableModel());	
		gestor_cliente.getLblResultados().setText("Resultados: ");
		gestor_cliente.getTextFieldID().setText("");
		gestor_cliente.getTextFieldNome().setText("");
		gestor_cliente.getTextFieldNIF().setText("");
		gestor_cliente.getTextFieldMorada().setText("");
		
		gestor_operador.getTable().setModel(new DefaultTableModel());
		gestor_operador.getLblResultados().setText("Resultados: ");
		gestor_operador.getTextPesquisaID().setText("");
		gestor_operador.getTextFieldNome().setText("");
		gestor_operador.getTextPesquisaNIF().setText("");

		gestor_pacotes.getTable().setModel(new DefaultTableModel());
		gestor_pacotes.getLblResultados().setText("Resultados: ");
		gestor_pacotes.getTextAreaDescricao().setText(" ");	
		gestor_pacotes.getTextPesquisaID().setText("");
		gestor_pacotes.getTextFieldNome().setText("");
		gestor_pacotes.getTextAreaDescricao().setText("");

		gestor_promocao.getTable().setModel(new DefaultTableModel());
		gestor_promocao.getLblResultados().setText("Resultados: ");
		gestor_promocao.getTextAreaDescricao().setText("");	
		gestor_promocao.getTextPesquisaID().setText("");
		gestor_promocao.getTextFieldNome().setText("");
		gestor_promocao.getTextAreaDescricao().setText("");
		
		
	}

	/**
	 * Configura quando o gerir clientes, na homepage Administrador é clicado. 
	 * A homepage é escondida e o gestor cliente é mostrado.
	 */
	public void gerirAdminClientes() {
		homepagePanel.setVisible(false);
		gestor_clientePanel.setVisible(true);
	}

	/**
	 * Configura quando o gerir operadores, na homepage Administrador é clicado. 
	 * A homepage é escondida e o gestor operadores é mostrado.
	 */
	public void gerirAdminOperadores() {
		homepagePanel.setVisible(false);
		gestor_operadorPanel.setVisible(true);
	}

	/**
	 * Configura quando o gerir promoções, na homepage Administrador é clicado. 
	 * A homepage é escondida e o gestor de promoções é mostrado. 
	 */
	public void gerirAdminPromocoes() {
		homepagePanel.setVisible(false);				
		gestor_promocaoPanel.setVisible(true);
	}

	/**
	 * Configura quando o gerir pacotes comerciais, na homepage Administrador é clicado. 
	 * A homepage é escondida e o gestor de pacotes comerciais é mostrado. 
	 */
	public void gerirAdminPacotes() {
		homepagePanel.setVisible(false);
		gestor_pacotesPanel.setVisible(true);
	}

	/**
	 * Faz reset às tabelas e ao campo de pesquisa do operador quando o botão voltar é premido 
	 * A homepage é escondida e a janela de login é mostrada. 
	 */
	public void voltarBtOperadorHomePage() {
		gravarFicheiro(username, temporizador, dataEHoraDeLog, "sessaolog.txt");
		loginPanel.setVisible(true);
		operador_homepagePanel.setVisible(false);

		operador_visualizarPacote.getTable().setModel(new DefaultTableModel());
		operador_visualizarPacote.getLblResultados().setText("Resultados: ");
		operador_visualizarPacote.getTextAreaDescricao().setText(" ");
		operador_visualizarPacote.getTextFieldNome().setText("");
		operador_visualizarPacote.getTextPesquisaID().setText("");

		operador_visualizarPromo.getTable().setModel(new DefaultTableModel());
		operador_visualizarPromo.getLblResultados().setText("Resultados: ");
		operador_visualizarPromo.getTextAreaDescricao().setText(" ");
		operador_visualizarPromo.getTextFieldNome().setText("");
		operador_visualizarPromo.getTextPesquisaID().setText("");
		
		operador_gerirClientes.getTable().setModel(new DefaultTableModel());
		operador_gerirClientes.getLblResultados().setText("Resultados: ");
		operador_gerirClientes.getTextFieldMorada().setText(" ");
		operador_gerirClientes.getTextFieldNome().setText("");
		operador_gerirClientes.getTextPesquisaNIF().setText("");
		operador_gerirClientes.getTextPesquisaID().setText("");
	}

	/**
	 * Configura quando o gerir clientes, na homepage Operador é clicado. 
	 * A homepage é escondida e o gestor de clientes é mostrado. 
	 */
	public void gerirOperHomepage() {
		operador_homepagePanel.setVisible(false);
		operador_gerirClientesPanel.setVisible(true);
	}

	/**
	 * Configura quando o gerir pacotes comerciais, na homepage Operador é clicado. 
	 * A homepage é escondida e o gestor de pacotes comerciais é mostrado.  
	 */
	public void operador_visualizarPacote() {
		operador_homepagePanel.setVisible(false);
		operador_visualizarPacotePanel.setVisible(true);
	}

	/**
	 * Configura quando o gerir promoções, na homepage Operador é clicado. 
	 * A homepage é escondida e o gestor de promoções é mostrado. 
	 */
	public void operador_visualizarPromo() {
		operador_homepagePanel.setVisible(false);
		operador_visualizarPromoPanel.setVisible(true);
	}

	/**
	 * @return username
	 */
	public String mandarUsername() {
		return username;
	}


	/**
	 * Botão terminar sessão de cliente
	 */
	public void btTerminarSessaoCliente() {
		gravarFicheiro(username, temporizador, dataEHoraDeLog, "sessaolog.txt");
		loginPanel.setVisible(true);
		areaClientePanel.setVisible(false);
	}	
}


