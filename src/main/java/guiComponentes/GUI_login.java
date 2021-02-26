package guiComponentes;


import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;

public class GUI_login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField textFieldUser;
	private JButton btLogin, btnSair;
	private JLabel labelPass, labelConfm;
	private JPasswordField passwordField;



	public GUI_login(GUI_total guit) {

		ativarNimbusLookAndFeel();

		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		setBackground(Color.WHITE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		inicialize(guit);

	}

	private void inicialize(GUI_total guit) {
		/**
		 * Login
		 * 
		 * Username
		 * Password 
		 * 
		 */

		JLabel labelLogin = new JLabel("Username");
		labelLogin.setBounds(209, 383, 131, 27);
		labelLogin.setForeground(Color.WHITE);
		labelLogin.setFont(new Font("Dubai Light", Font.BOLD, 17));
		getContentPane().add(labelLogin);
		setResizable(false);
		labelPass = new JLabel("Password");
		labelPass.setBounds(209, 430, 138, 27);
		labelPass.setForeground(Color.WHITE);
		labelPass.setFont(new Font("Dubai Light", Font.BOLD, 17));
		getContentPane().add(labelPass);

		// textField de username 

		textFieldUser = new JTextField();
		textFieldUser.setBounds(329, 383, 193, 27);
		textFieldUser.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		textFieldUser.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				passwordField.setText("");

			}
		});

		textFieldUser.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						login(guit);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		// textField da password 

		passwordField = new JPasswordField();
		passwordField.setBounds(329, 430, 193, 27);
		passwordField.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		getContentPane().add(passwordField);	
		passwordField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						login(guit);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		// label de confirmação de dados 

		labelConfm = new JLabel("User ou Password incorreta");
		labelConfm.setBounds(342, 578, 251, 18);
		labelConfm.setForeground(new Color(70,74,101));
		labelConfm.setVisible(false);
		labelConfm.setFont(new Font("Dubai Light", Font.BOLD, 16));
		getContentPane().add(labelConfm);

		// icon de user 

		JLabel iconUser = new JLabel();
		iconUser.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/user.png")));
		iconUser.setBounds(301, 89, 238, 298);
		panel.add(iconUser);

		// botão login 

		btLogin = new JButton("Login");
		btLogin.setBounds(375, 476, 104, 33);
		btLogin.setForeground(SystemColor.desktop);
		btLogin.setBackground(Color.LIGHT_GRAY);
		btLogin.setToolTipText("Carregue para fazer login");
		btLogin.setFocusPainted(false);
		btLogin.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		btLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					login(guit);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		getContentPane().add(btLogin);

		/**
		 * Footer 
		 * 
		 * Botão Sair
		 * Logotipo Empresa
		 * Imagem de fundo
		 */

		JLabel lblFooter = new JLabel();
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);

		// botão sair 

		btnSair = new JButton("Sair");
		btnSair.setToolTipText("Carregue para fazer signout");
		btnSair.setBounds(375, 520, 104, 33);
		btnSair.setForeground(Color.BLACK);
		btnSair.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		btnSair.setFocusPainted(false);
		btnSair.setBackground(Color.LIGHT_GRAY);
		panel.add(btnSair);

		// imagem de fundo 

		JLabel icon = new JLabel("");
		icon.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		icon.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/fundoAltran.png")));
		icon.setBounds(0, 89, 1394, 586);
		panel.add(icon);

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
	 * Método de validação do login 
	 * Este método verifica se os dados introduzidos pertencem a um cliente, administrador ou operador. 
	 * Dependendo a qual pertença é encaminhado para a homepage correspondente. 
	 * @param guit
	 * @throws Exception
	 */
	private void login(GUI_total guit) throws Exception {

		//login vai usar metodos e entidades que estao nos dao do package V2
		//primeiro vemos se o utilizador é um cliente

		String login = textFieldUser.getText();
		@SuppressWarnings("deprecation")
		String pass = passwordField.getText();

		if(login.isBlank() || pass.isBlank()) {
			JOptionPane.showMessageDialog(null, "Campos não podem estar vazios.");
			return;
		}

		//verifica se é um cliente
		Cliente cliente = null;

		//se não é cliente, é funcionário 
		Funcionario funcionario = null;

		try {
			// no método gui total, o cliente trata-se como role 0
			cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLoginPass( login, pass);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		// Caso o cliente seja diferente de null, faz o login e encaminha para a área cliente.
		if( cliente != null) {
			guit.loginEfetuado(0);
			labelConfm.setVisible(false);
		}

		try {
			funcionario = GestorDeDAO.getGestorDeDAO().pesquisarFuncionarioLoginPass( login, pass);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Caso o funcionario seja diferente de null, faz o login e encaminha para a área de gestão do administrador ou operador
		if(funcionario != null) {
		//role 1 = administrador 
			switch(funcionario.getId_role()) {
				//role 1 = admin
				case(1):
		
					guit.loginEfetuado(1);
				labelConfm.setVisible(false);
				return;
		//role 2 = operador	
				case(2):
					guit.loginEfetuado(2);
				labelConfm.setVisible(false);
				return;
				default:
					break;
			}
		}

		if(cliente == null && funcionario == null) {
			labelConfm.setVisible(true);
		}
	}

	/**
	 * @return panel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
	
	/**
	 * @return textFieldUser
	 */
	public JTextField getUserText() {
		return textFieldUser;
	}
	
	/**
	 * @return btnSair
	 */
	public JButton getBtnSair() {
		return btnSair;
	}
}