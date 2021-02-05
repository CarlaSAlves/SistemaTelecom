package guiComponentes;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import guiComponentes.gestorCliente.GUI_gestor_cliente;
import guiComponentes.gestorOperador.GUI_gestor_operador;
import guiComponentes.gestorPacoteComercial.GUI_gestor_pacotes;
import guiComponentes.gestorPromocao.GUI_gestor_promocoes;


public class GUI_total extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String username;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_total frame = new GUI_total();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_total() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane = new JPanel();
		setResizable(false);
		setContentPane(contentPane);
		setLayout(null);

		GUI_login login = new GUI_login();
		GUI_homepage homepage = new GUI_homepage();
		GUI_gestor_cliente gestor_cliente = new GUI_gestor_cliente();
		GUI_gestor_operador gestor_operador = new GUI_gestor_operador();
		GUI_gestor_pacotes gestor_pacotes = new GUI_gestor_pacotes();
		GUI_gestor_promocoes gestor_promocoes = new GUI_gestor_promocoes();


		JPanel loginPanel = login.returnPanel();
		loginPanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(loginPanel);

		JPanel homepagePanel = homepage.returnPanel();
		homepagePanel.setVisible(false);
		homepagePanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(homepagePanel);

		JPanel gestor_clientePanel = gestor_cliente.returnPanel();
		gestor_clientePanel.setVisible(false);
		gestor_clientePanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(gestor_clientePanel);

		JPanel gestor_operadorPanel = gestor_operador.returnPanel();
		gestor_operadorPanel.setVisible(false);
		gestor_operadorPanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(gestor_operadorPanel);

		JPanel gestor_pacotesPanel = gestor_pacotes.returnPanel();
		gestor_pacotesPanel.setVisible(false);
		gestor_pacotesPanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(gestor_pacotesPanel);


		JPanel gestor_promocoesPanel = gestor_promocoes.returnPanel();
		gestor_promocoes.setVisible(false);
		gestor_promocoes.setBounds(0, 0, 1500, 900);
		getContentPane().add(gestor_promocoesPanel);


		// LOGIN
		login.getBtLogin().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				username = login.getUserText().getText();
				gestor_cliente.setUsernameLoggedIn(username);
				gestor_cliente.recebeUsernameDaPaginaLogin(username);
				homepage.setUsernameLoggedIn(username);
				loginPanel.setVisible(false);
				homepagePanel.setVisible(true);
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
				gestor_promocoesPanel.setVisible(true);
			
			}
		});
		
		homepage.getBtGerirPacotes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepagePanel.setVisible(false);
				gestor_pacotesPanel.setVisible(true);
			}
		});


		// BOTÃO VOLTAR
		homepage.getBtVoltar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
			}
		});
		
//		gestor_promocoes.btVoltarGestorPromocoes().addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				homepagePanel.setVisible(true);
//				gestor_promocoesPanel.setVisible(false);
//				
//			}
//		});


	}

}