package guiComponentes;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.SystemColor;
import java.time.Duration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;

public class GUI_homepage extends JFrame {

	private JButton btVoltar;
	private JButton btGerirClientes;
	private JButton btGerirOperadores;
	private JButton btGerirPacotes;
	private JButton btGerirPromocoes;
	private JPanel panel;
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JLabel lblUsernameLogged;
	private JLabel lblTempoSessao;
	private JLabel lblHoraSistema;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_homepage frame = new GUI_homepage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_homepage() {
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Verdana", Font.PLAIN, 11));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		
				btVoltar = new JButton("Terminar Sessão");
				btVoltar.setForeground(Color.DARK_GRAY);
				btVoltar.setBounds(16, 687, 180, 50);
				btVoltar.setFont(new Font("SansSerif", Font.PLAIN, 15));
				btVoltar.setFocusPainted(false);
				getContentPane().add(btVoltar);

		
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.WHITE);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1297, 794, 166, 16);
		panel.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 15));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.WHITE);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setBounds(1297, 809, 166, 15);
		panel.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 15));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.WHITE);
		lblHoraSistema.setBounds(1297, 824, 166, 16);
		panel.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 15));

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GUI_homepage.class.getResource("/guiComponentes/img/user3.png")));
		lblNewLabel.setBounds(1018, 71, 318, 328);
		panel.add(lblNewLabel);

		JLabel lblPoesia = new JLabel("Portal Do Administrador");
		lblPoesia.setBounds(133, 71, 508, 33);
		lblPoesia.setForeground(SystemColor.text);
		lblPoesia.setFont(new Font("SansSerif", Font.BOLD, 40));
		getContentPane().add(lblPoesia);

		btGerirClientes = new JButton("Gerir Clientes");
		btGerirClientes.setBounds(239, 207, 286, 60);
		btGerirClientes.setForeground(Color.DARK_GRAY);
		btGerirClientes.setToolTipText("Gestão de Clientes");	
		btGerirClientes.setFocusPainted(false);
		btGerirClientes.setFont(new Font("SansSerif", Font.PLAIN, 15));
		getContentPane().add(btGerirClientes);

		btGerirOperadores = new JButton("Gerir Operadores");
		btGerirOperadores.setBounds(239, 300, 286, 60);
		btGerirOperadores.setToolTipText("Gestão de Operadores");
		btGerirOperadores.setForeground(Color.DARK_GRAY);
		btGerirOperadores.setFocusPainted(false);
		btGerirOperadores.setFont(new Font("SansSerif", Font.PLAIN, 15));
		getContentPane().add(btGerirOperadores);

		btGerirPromocoes = new JButton("Gerir Promoções");
		btGerirPromocoes.setBounds(239, 396, 286, 60);
		btGerirPromocoes.setToolTipText("Gerir Promoções");
		btGerirPromocoes.setForeground(Color.DARK_GRAY);
		btGerirPromocoes.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btGerirPromocoes.setFocusPainted(false);
		getContentPane().add(btGerirPromocoes);

		btGerirPacotes = new JButton("Gerir Pacotes Comerciais");
		btGerirPacotes.setBounds(239, 489, 286, 60);
		btGerirPacotes.setToolTipText("Gestão de Pacotes de Clientes");
		btGerirPacotes.setForeground(Color.DARK_GRAY);
		btGerirPacotes.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btGerirPacotes.setFocusPainted(false);
		getContentPane().add(btGerirPacotes);

		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(348, 655, 226, 100);
		lblFooter.setIcon(new ImageIcon(GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);

		JLabel icon = new JLabel("");
		icon.setBounds(0, -52, 1400, 807);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));
		getContentPane().add(icon);
	}

	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	public JButton getBtVoltar() {
		return btVoltar;
	}


	public JButton getBtGerirClientes() {
		return btGerirClientes;
	}

	public JButton getBtGerirOperadores() {
		return btGerirOperadores;
	}

	public JButton getBtGerirPacotes() {
		return btGerirPacotes;
	}

	public JButton getBtGerirPromocoes() {
		return btGerirPromocoes;
	}
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}

	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessão: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}
	
}

