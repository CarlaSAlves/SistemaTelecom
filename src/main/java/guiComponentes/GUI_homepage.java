package guiComponentes;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.SystemColor;
import java.time.Duration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

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
//					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				        if ("Nimbus".equals(info.getName())) {
//				            UIManager.setLookAndFeel(info.getClassName());
//				            break;
//				        }
//					}
					
					GUI_homepage frame = new GUI_homepage();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_homepage() {
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
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Verdana", Font.PLAIN, 11));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		getContentPane().setLayout(null);

		
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.WHITE);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1297, 805, 166, 16);
		panel.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 13));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.WHITE);
		lblTempoSessao.setText("Sessão:");
		lblTempoSessao.setBounds(1297, 820, 166, 16);
		panel.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dialog", Font.PLAIN, 13));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.WHITE);
		lblHoraSistema.setBounds(1297, 835, 166, 16);
		panel.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dialog", Font.PLAIN, 13));

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GUI_homepage.class.getResource("/guiComponentes/img/user3.png")));
		lblNewLabel.setBounds(1100, 62, 318, 328);
		panel.add(lblNewLabel);

		JLabel lblPoesia = new JLabel("Portal Do Administrador");
		lblPoesia.setBounds(40, 62, 513, 33);
		lblPoesia.setForeground(Color.WHITE);
		lblPoesia.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblPoesia.setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().add(lblPoesia);

		btGerirClientes = new JButton("Gerir Clientes");
		btGerirClientes.setBounds(593, 326, 318, 59);
		btGerirClientes.setForeground(Color.DARK_GRAY);
		btGerirClientes.setToolTipText("Gestão de Clientes");	
		btGerirClientes.setFocusPainted(false);
		btGerirClientes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		getContentPane().add(btGerirClientes);

		btVoltar = new JButton("Terminar Sessão");
		btVoltar.setBounds(40, 807, 222, 33);
		btVoltar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);

		btGerirOperadores = new JButton("Gerir Operadores");
		btGerirOperadores.setBounds(593, 399, 318, 59);
		btGerirOperadores.setToolTipText("Gestão de Operadores");
		btGerirOperadores.setForeground(Color.DARK_GRAY);
		btGerirOperadores.setFocusPainted(false);
		btGerirOperadores.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		getContentPane().add(btGerirOperadores);

		btGerirPromocoes = new JButton("Gerir Promoções");
		btGerirPromocoes.setBounds(593, 469, 318, 59);
		btGerirPromocoes.setToolTipText("Gerir Promoções");
		btGerirPromocoes.setForeground(Color.DARK_GRAY);
		btGerirPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		btGerirPromocoes.setFocusPainted(false);
		getContentPane().add(btGerirPromocoes);

		btGerirPacotes = new JButton("Gerir Pacotes Comerciais");
		btGerirPacotes.setBounds(593, 539, 318, 59);
		btGerirPacotes.setToolTipText("Gestão de Pacotes de Clientes");
		btGerirPacotes.setForeground(Color.DARK_GRAY);
		btGerirPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		btGerirPacotes.setFocusPainted(false);
		getContentPane().add(btGerirPacotes);

		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(593, 740, 313, 100);
		lblFooter.setIcon(new ImageIcon(GUI_homepage.class.getResource("/guiComponentes/img/Altran4.png")));
		getContentPane().add(lblFooter);

		JLabel icon = new JLabel("");
		icon.setBounds(0, 0, 1484, 872);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(GUI_homepage.class.getResource("/guiComponentes/img/black.png")));
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
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}
	
}

