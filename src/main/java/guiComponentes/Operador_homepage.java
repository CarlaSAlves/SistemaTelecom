package guiComponentes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

@SuppressWarnings("serial")
public class Operador_homepage extends JFrame {

	private JPanel pane;
	private JButton btVoltar;
	private JLabel lblUsernameLogged, lblTempoSessao, lblHoraSistema;
	private JButton btClientes, btPacotesPromo;
	private JLabel labelBoasVindas;


	/**
	 * Create the frame.
	 */
	public Operador_homepage(GUI_total guit) {
		inicialize(guit);

	}
	/**
	 * Método inicialize, que contém todos os elementos que compõem a homepage. 
	 * @param guit
	 */

	private void inicialize(GUI_total guit) {
		ativarNimbusLookAndFeel();

		/*
		 * Configuração do painel base
		 * Label com nome da página e boas vindas
		 * Botões da página:
		 *  
		 *  Gerir cliente
		 *  Visualizar pacotes comerciais
		 *  Visualizar promoçoes
		 * 	Terminar sessão
		 */

		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		getContentPane().setFont(new Font("Verdana", Font.PLAIN, 11));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		setResizable(false);

		JLabel lblPoesia = new JLabel("Portal do Operador");
		lblPoesia.setBounds(161, 117, 415, 33);
		lblPoesia.setForeground(new Color(255, 205, 30));
		lblPoesia.setFont(new Font("Dubai Light", Font.BOLD, 40));
		pane.add(lblPoesia);

		labelBoasVindas = new JLabel("");
		labelBoasVindas.setFont(new Font("Dubai Light", Font.BOLD, 18));
		labelBoasVindas.setForeground(new Color(255, 205, 30));
		labelBoasVindas.setBounds(161, 162, 606, 33);
		pane.add(labelBoasVindas);

		// Botões Menu 

		btClientes = new JButton("Gerir Clientes");
		btClientes.setBounds(161, 280, 286, 60);
		btClientes.setForeground(Color.DARK_GRAY);
		btClientes.setToolTipText("Gestão de Clientes");	
		btClientes.setFocusPainted(false);
		btClientes.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		pane.add(btClientes);
		btClientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.gerirOperHomepage();

			}
		});

		btPacotesPromo = new JButton("Visualizar Pacotes Comerciais");
		btPacotesPromo.setBounds(161, 373, 286, 60);
		btPacotesPromo.setToolTipText("Gestão de Pacotes Comerciais e Promoções");
		btPacotesPromo.setForeground(Color.DARK_GRAY);
		btPacotesPromo.setFocusPainted(false);
		btPacotesPromo.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		pane.add(btPacotesPromo);
		btPacotesPromo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.operador_visualizarPacote();

			}
		});

		JButton btnVisualizarPromocoes = new JButton("Visualizar Promoções");
		btnVisualizarPromocoes.setToolTipText("Gestão de Pacotes Comerciais e Promoções");
		btnVisualizarPromocoes.setForeground(Color.DARK_GRAY);
		btnVisualizarPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		btnVisualizarPromocoes.setFocusPainted(false);
		btnVisualizarPromocoes.setBounds(161, 465, 286, 60);
		pane.add(btnVisualizarPromocoes);
		btnVisualizarPromocoes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.operador_visualizarPromo();

			}
		});


		// Botão terminar sessão 

		btVoltar = new JButton("Terminar Sessão");
		btVoltar.setForeground(Color.DARK_GRAY);
		btVoltar.setBounds(16, 687, 180, 50);
		btVoltar.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);
		btVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.voltarBtOperadorHomePage();

			}
		});

		setupTempoSessao();

		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Operador_homepage.class.getResource("/guiComponentes/img/AltranOperadorFooter.png")));
		getContentPane().add(lblFooter);

		// Imagem de Fundo

		JLabel icon = new JLabel("");
		icon.setBounds(0, 83, 1394, 586);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(Operador_homepage.class.getResource("/guiComponentes/img/AltranFundoOperador.png")));
		getContentPane().add(icon);

	}

	/**
	 * Configuração das labels de username e temporização.
	 * @lblUsernameLogged apresenta o username que está logado
	 * @lblTempoSessao apresenta o tempo de sessão desde o momento que faz login
	 * @lblHoraSistema apresenta a hora atual do sistema 
	 */
	protected void setupTempoSessao() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.BLACK);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 699, 166, 16);
		pane.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.BLACK);
		lblTempoSessao.setText("Sessao:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		pane.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.BLACK);
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		pane.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
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
	 * Configuração the labelBoasVindas
	 */
	public void setLabelBoasVindas(String username) {
		labelBoasVindas.setText( username );
	}

	/**
	 * @return o painel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	/**
	 * Configura a label usernameLogged 
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}

	/**
	 * Configura a label de temporizador. 
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * Configura a label de hora de sistema
	 * @param agora
	 */
	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}
}
