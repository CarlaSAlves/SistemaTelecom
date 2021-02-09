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
import guiComponentes.gestorCliente.GUI_gestor_cliente;
import guiComponentes.gestorOperador.GUI_gestor_operador;
import guiComponentes.gestorPacoteComercial.GUI_gestor_pacotes;
import guiComponentes.gestorPromocao.GUI_gestor_promocao;


public class GUI_total extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static String username;
	private Instant inicio;
	private GUI_gestor_cliente gestor_cliente;
	private GUI_homepage homepage;
	private	GUI_gestor_operador gestor_operador;
	private GUI_gestor_pacotes gestor_pacotes;
	private	GUI_gestor_promocao gestor_promocao;
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		contentPane = new JPanel();
		setResizable(false);
		setContentPane(contentPane);
		setLayout(null);

		GUI_login login = new GUI_login();
		homepage = new GUI_homepage();
		gestor_cliente = new GUI_gestor_cliente();
		gestor_operador = new GUI_gestor_operador();
		gestor_pacotes = new GUI_gestor_pacotes();
		gestor_promocao = new GUI_gestor_promocao();

		JPanel loginPanel = login.returnPanel();
		loginPanel.setBounds(0, 0, 1400, 800);
		getContentPane().add(loginPanel);

		JPanel homepagePanel = homepage.returnPanel();
		homepagePanel.setVisible(false);
		homepagePanel.setBounds(0, 0, 1400, 800);
		getContentPane().add(homepagePanel);

		JPanel gestor_clientePanel = gestor_cliente.returnPanel();
		gestor_clientePanel.setVisible(false);
		gestor_clientePanel.setBounds(0, 0, 1400, 800);
		getContentPane().add(gestor_clientePanel);

		JPanel gestor_operadorPanel = gestor_operador.returnPanel();
		gestor_operadorPanel.setVisible(false);
		gestor_operadorPanel.setBounds(0, 0, 1400, 800);
		getContentPane().add(gestor_operadorPanel);

		JPanel gestor_pacotesPanel = gestor_pacotes.returnPanel();
		gestor_pacotesPanel.setVisible(false);
		gestor_pacotesPanel.setBounds(0, 0, 1400, 800);
		getContentPane().add(gestor_pacotesPanel);

		JPanel gestor_promocaoPanel = gestor_promocao.returnPanel();
		gestor_promocaoPanel.setVisible(false);
		gestor_promocaoPanel.setBounds(0, 0, 1400, 800);
		getContentPane().add(gestor_promocaoPanel);

		// LOGIN
		login.getBtnSair().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				dispose();

			}
		});

		login.getBtLogin().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				inicio = Instant.now();
				labelUsernameNavegaPaginas(login, homepage, gestor_cliente, gestor_operador, gestor_pacotes,
						gestor_promocao);
				loginPanel.setVisible(false);
				homepagePanel.setVisible(true);
				comecarTemporizador();
				gestor_cliente.getTable().setModel(new DefaultTableModel());	
				gestor_operador.getTable().setModel(new DefaultTableModel());
				gestor_pacotes.getTable().setModel(new DefaultTableModel());
				gestor_promocao.getTable().setModel(new DefaultTableModel());
			}

		});

		homepage.getBtGerirClientes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepagePanel.setVisible(false);
				gestor_clientePanel.setVisible(true);
			}
		});

		homepage.getBtGerirOperadores().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(false);
				gestor_operadorPanel.setVisible(true);				
			}
		});


		homepage.getBtGerirPromocoes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				homepagePanel.setVisible(false);				
				gestor_promocaoPanel.setVisible(true);

			}
		});

		homepage.getBtGerirPacotes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(false);
				gestor_promocaoPanel.setVisible(true);
			}
		});

		// BOTÃO VOLTAR
		homepage.getBtVoltar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gravarFicheiro(username, temporizador, dataEHoraDeLog, "sessaolog.txt");
				loginPanel.setVisible(true);
				homepagePanel.setVisible(false);
			
			}
		});

		gestor_cliente.btVoltarGestorCliente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepagePanel.setVisible(true);
				gestor_clientePanel.setVisible(false);
				
		
			}
		});

		gestor_operador.btVoltarGestorOperador().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(true);
				gestor_operadorPanel.setVisible(false);

			}
		});

		gestor_pacotes.btVoltarGestorPacotes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(true);
				gestor_pacotesPanel.setVisible(false);

				System.out.println("Teste botão voltar !!!!");
			}
		});


		gestor_promocao.getBtVoltarGestorPromocao().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homepagePanel.setVisible(true);
				gestor_promocaoPanel.setVisible(false);
			}
		});

	}

	private void labelUsernameNavegaPaginas(GUI_login login, GUI_homepage homepage,
			GUI_gestor_cliente gestor_cliente, GUI_gestor_operador gestor_operador,
			GUI_gestor_pacotes gestor_pacotes, GUI_gestor_promocao gestor_promocao) {
		username = login.getUserText().getText();
		gestor_cliente.setUsernameLoggedIn(username);
		gestor_operador.setUsernameLoggedIn(username);
		gestor_pacotes.setUsernameLoggedIn(username);
		gestor_promocao.setUsernameLoggedIn(username);
		homepage.setUsernameLoggedIn(username);
	}


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
					try {
						sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		t.start();
	}

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

	public static String getUsername() {
		return username;
	}
}


