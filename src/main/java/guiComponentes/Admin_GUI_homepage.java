package guiComponentes;

import javax.swing.JFrame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.Font;
import java.awt.Color;

public class Admin_GUI_homepage extends JFrame {

	private JButton btVoltar,btGerirClientes,btGerirOperadores,btGerirPacotes,btGerirPromocoes;

	private JPanel panel;
	private static final long serialVersionUID = 1L;
	private JLabel lblUsernameLogged, lblTempoSessao, lblHoraSistema;


	/**
	 * Construtor que configura a página com o método inicialize, 
	 * configura os action listeners dos botões da página  
	 * @param guit - de forma a aceder aos métodos do GUI Total para gerir os paineis
	 */
	public Admin_GUI_homepage(GUI_total guit) {

		ativarNimbusLookAndFeel();

		panel = new JPanel();
		setBounds(100, 30, 1400, 800);
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Verdana", Font.PLAIN, 11));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		inicialize();

		//action Listeners dos botões 

		actionListenersBotoes(guit);

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
	 * Configura o corpo da página 
	 */
	private void inicialize() {
		/*
		 * Titulo 
		 */

		JLabel lblTitulo = new JLabel("Portal do Administrador");
		lblTitulo.setBounds(161, 117, 508, 33);
		lblTitulo.setForeground(new Color(70,74,101));
		lblTitulo.setFont(new Font("Dubai", Font.BOLD, 40));
		getContentPane().add(lblTitulo);
		setResizable(false);
		
		/*
		 * Botões:
		 * 
		 * Gerir Clientes
		 * Gerir Operadores 
		 * Gerir Promoções 
		 * Gerir Pacotes 
		 */

		btGerirClientes = new JButton("Gerir Clientes");
		btGerirClientes.setBounds(239, 207, 286, 60);
		btGerirClientes.setForeground(Color.DARK_GRAY);
		btGerirClientes.setToolTipText("Gestão de Clientes");	
		btGerirClientes.setFocusPainted(false);
		btGerirClientes.setFont(new Font("Dialog", Font.PLAIN, 15));
		getContentPane().add(btGerirClientes);

		btGerirOperadores = new JButton("Gerir Operadores");
		btGerirOperadores.setBounds(239, 300, 286, 60);
		btGerirOperadores.setToolTipText("Gestão de Operadores");
		btGerirOperadores.setForeground(Color.DARK_GRAY);
		btGerirOperadores.setFocusPainted(false);
		btGerirOperadores.setFont(new Font("Dialog", Font.PLAIN, 15));
		getContentPane().add(btGerirOperadores);

		btGerirPacotes = new JButton("Gerir Pacotes Comerciais");
		btGerirPacotes.setBounds(239, 395, 286, 60);
		btGerirPacotes.setToolTipText("Gestão de Pacotes de Clientes");
		btGerirPacotes.setForeground(Color.DARK_GRAY);
		btGerirPacotes.setFont(new Font("Dialog", Font.PLAIN, 15));
		btGerirPacotes.setFocusPainted(false);
		getContentPane().add(btGerirPacotes);

		btGerirPromocoes = new JButton("Gerir Promoções");
		btGerirPromocoes.setBounds(239, 483, 286, 60);
		btGerirPromocoes.setToolTipText("Gerir Promoções");
		btGerirPromocoes.setForeground(Color.DARK_GRAY);
		btGerirPromocoes.setFont(new Font("Dialog", Font.PLAIN, 15));
		btGerirPromocoes.setFocusPainted(false);
		getContentPane().add(btGerirPromocoes);

		/*
		 * Footer
		 * 
		 * Botão Voltar
		 * Imagem Empresa
		 * Imagem de Fundo 
		 * Temporizador
		 */

		btVoltar = new JButton("Terminar Sessão");
		btVoltar.setForeground(Color.DARK_GRAY);
		btVoltar.setBounds(16, 687, 180, 50);
		btVoltar.setFont(new Font("Dialog", Font.PLAIN, 15));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);

		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);

		JLabel icon = new JLabel("");
		icon.setBounds(0, 89, 1394, 586);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));
		getContentPane().add(icon);

		timerSetup();

	}
	
	/**
	 * ActionListeners dos botões da página 
	 * Conectam-se com o gui total para gestão dos paineis 
	 * @param guit - de forma a aceder aos métodos do GUI Total para gerir os paineis
	 */
	private void actionListenersBotoes(GUI_total guit) {

		// botão Gerir Clientes 

		btGerirClientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				guit.gerirAdminClientes();
			}
		});

		// Botão Gerir Operadores 

		btGerirOperadores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.gerirAdminOperadores();				
			}
		});

		//Botão Gerir Pacotes Comerciais 

		btGerirPacotes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.gerirAdminPacotes();
			}
		});

		// Botão Gerir Promoções 

		btGerirPromocoes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				guit.gerirAdminPromocoes();

			}
		});

		// Botão Voltar

		btVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.voltarBtAdminHomePage();

			}
		});

	}

	/**
	 * Configuração das labels de username e temporização.
	 * @lblUsernameLogged apresenta o username que está logado
	 * @lblTempoSessao apresenta o tempo de sessão desde o momento que faz login
	 * @lblHoraSistema apresenta a hora atual do sistema 
	 */
	protected void timerSetup() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.BLACK);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 699, 166, 16);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		panel.add(lblUsernameLogged);

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.BLACK);
		lblTempoSessao.setText("Sessao:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		panel.add(lblTempoSessao);

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.BLACK);
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		panel.add(lblHoraSistema);
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
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}

	/**
	 * 
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * 
	 * @param agora
	 */
	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}

}
