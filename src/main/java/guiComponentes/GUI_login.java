package guiComponentes;


import java.awt.Color;
import java.awt.EventQueue;
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
	private JTextField textFieldUser;
	private JButton btLogin, btnSair;
	private JLabel labelPass, labelConfm;
	private JPasswordField passwordField;
	private JPanel panel;


	public GUI_login(GUI_total guit) {

		setBackground(Color.WHITE);

		ativarNimbusLookAndFeel();

		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);

		// imagem user e de fundo

		JLabel iconUser = new JLabel();
		iconUser.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/user.png")));
		iconUser.setBounds(301, 89, 238, 298);
		panel.add(iconUser);

		JLabel lblFooter = new JLabel();
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);

		btnSair = new JButton("Sair");
		btnSair.setToolTipText("Carregue para fazer signout");
		btnSair.setForeground(Color.BLACK);
		btnSair.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSair.setFocusPainted(false);
		btnSair.setBackground(SystemColor.menu);
		btnSair.setBounds(375, 520, 104, 33);
		panel.add(btnSair);

		JLabel labelLogin = new JLabel("Username");
		labelLogin.setBounds(209, 383, 131, 27);
		labelLogin.setForeground(Color.WHITE);
		labelLogin.setFont(new Font("SansSerif", Font.BOLD, 20));
		getContentPane().add(labelLogin);

		labelPass = new JLabel("Password");
		labelPass.setBounds(209, 430, 138, 27);
		labelPass.setForeground(Color.WHITE);
		labelPass.setFont(new Font("SansSerif", Font.BOLD, 20));
		getContentPane().add(labelPass);

		passwordField = new JPasswordField();
		passwordField.setBounds(329, 430, 193, 27);
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 13));
		getContentPane().add(passwordField);

		textFieldUser = new JTextField();
		textFieldUser.setBounds(329, 383, 193, 27);
		textFieldUser.setFont(new Font("Dialog", Font.PLAIN, 13));
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		textFieldUser.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusGained(FocusEvent e) {
				passwordField.setText("");

			}
		});

		labelConfm = new JLabel("User ou Password incorreta");
		labelConfm.setBounds(359, 578, 251, 18);
		labelConfm.setForeground(new Color(70,74,101));
		labelConfm.setVisible(false);
		labelConfm.setFont(new Font("SansSerif", Font.PLAIN, 12));
		getContentPane().add(labelConfm);

		btLogin = new JButton("Login");
		btLogin.setBounds(375, 476, 104, 33);
		btLogin.setForeground(SystemColor.desktop);
		btLogin.setBackground(new Color(240, 240, 240));
		btLogin.setToolTipText("Carregue para fazer login");
		btLogin.setFocusPainted(false);
		btLogin.setFont(new Font("SansSerif", Font.PLAIN, 14));


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
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

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
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
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

		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/fundoAltran.png")));
		icon.setBounds(0, 89, 1394, 586);
		panel.add(icon);


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

	private void login(GUI_total guit) throws Exception {
		//login vai usar metodos e entidades que estao nos dao do package V2
		//primeiro vamos ver se o utilizador � um cliente
		String login = textFieldUser.getText();
		@SuppressWarnings("deprecation")
		String pass = passwordField.getText();

		if(login.isBlank() || pass.isBlank()) {
			JOptionPane.showMessageDialog(null, "Campos não podem estar vazios.");
			return;
		}

		//verifica se é um cliente
		Cliente cliente = null;
		//se nao é  cliente, é  funcion�rio 
		Funcionario funcionario = null;

		try {
			cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLoginPass( login, pass);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if( cliente != null) {
			guit.loginEfetuado(0);
			labelConfm.setVisible(false);
		}


		try {
			funcionario = GestorDeDAO.getGestorDeDAO().pesquisarFuncionarioLoginPass( login, pass);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(funcionario != null) {
			//linha para abrir a janela do admin (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o admin atualmente logado)

			switch(funcionario.getId_role()) {
			//role 1 = admin
			case(1):
				//linha para abrir a janela do admin (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o admin atualmente logado)
				guit.loginEfetuado(1);
			labelConfm.setVisible(false);
			return;
			//role 2 = operador	
			case(2):
				guit.loginEfetuado(2);
			labelConfm.setVisible(false);
			//linha para abrir a janela do operador (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o operador atualmente logado)
			//JOptionPane.showMessageDialog(null, "Menu operador em construção.");
			return;
			}
		}
		
		if(cliente == null && funcionario == null) {
			labelConfm.setVisible(true);
		}
	}





	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	public JTextField getUserText() {
		return textFieldUser;
	}

	public JButton getBtnSair() {
		return btnSair;
	}
}