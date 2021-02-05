package guiComponentes;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.SystemColor;
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
	private JPanel panel;
	private JLabel lblUsernameLogged;
	private static final long serialVersionUID = 1L;


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
		getContentPane().setBackground(SystemColor.inactiveCaption);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);

		JLabel icon = new JLabel("");
		icon.setBounds(429, 0, 626, 220);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(GUI_homepage.class.getResource("/img/mundo.png")));
		getContentPane().setLayout(null);

		JLabel labelTitulo = new JLabel("Gestão Telecom");
		labelTitulo.setBounds(439, 103, 183, 47);
		labelTitulo.setForeground(SystemColor.window);
		labelTitulo.setFont(new Font("Dubai Light", Font.BOLD | Font.ITALIC, 27));
		labelTitulo.setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().add(labelTitulo);

		JLabel lblPoesia = new JLabel("Bem Vindo Portal Do Administrador");
		lblPoesia.setBounds(442, 180, 513, 33);
		lblPoesia.setForeground(Color.WHITE);
		lblPoesia.setFont(new Font("Dubai Light", Font.ITALIC, 19));
		lblPoesia.setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().add(lblPoesia);
		getContentPane().add(icon);

		btGerirClientes = new JButton("Gerir Clientes");
		btGerirClientes.setBounds(633, 321, 222, 59);
		btGerirClientes.setForeground(Color.DARK_GRAY);
		btGerirClientes.setToolTipText("Gestão de Clientes");	
		btGerirClientes.setFocusPainted(false);
		btGerirClientes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		getContentPane().add(btGerirClientes);

		btVoltar = new JButton("Voltar");
		btVoltar.setBounds(40, 807, 125, 33);
		btVoltar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);

		btGerirOperadores = new JButton("Gerir Operadores");
		btGerirOperadores.setBounds(633, 406, 223, 59);
		btGerirOperadores.setToolTipText("Gestão de Operadores");
		btGerirOperadores.setForeground(Color.DARK_GRAY);
		btGerirOperadores.setFocusPainted(false);
		btGerirOperadores.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		getContentPane().add(btGerirOperadores);

		JButton btGerirPromocoes = new JButton("Gerir Promoções");
		btGerirPromocoes.setBounds(633, 489, 222, 59);
		btGerirPromocoes.setToolTipText("Gerir Promoções");
		btGerirPromocoes.setForeground(Color.DARK_GRAY);
		btGerirPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		btGerirPromocoes.setFocusPainted(false);
		getContentPane().add(btGerirPromocoes);

		JButton btGerirPacotes = new JButton("Gerir Pacotes");
		btGerirPacotes.setBounds(633, 575, 222, 59);
		btGerirPacotes.setToolTipText("Gestão de Pacotes de Clientes");
		btGerirPacotes.setForeground(Color.DARK_GRAY);
		btGerirPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		btGerirPacotes.setFocusPainted(false);
		getContentPane().add(btGerirPacotes);

		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(316, 740, 600, 100);
		lblFooter.setIcon(new ImageIcon(GUI_login.class.getResource("/img/footer.png")));
		getContentPane().add(lblFooter);
		
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsernameLogged.setBounds(1211, 817, 263, 33);
		panel.add(lblUsernameLogged);
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
	
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Logged in : " + username);
	}
	
}

