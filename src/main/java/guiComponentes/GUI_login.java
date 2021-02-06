package guiComponentes;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.ImageIcon;


public class GUI_login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldUser;
	private JButton btLogin, btnSair;
	private JLabel labelPass;
	private JLabel icon;
	private JPanel panel;
	private Font font = new Font("Dubai Light", Font.PLAIN, 20);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_login frame = new GUI_login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_login() {

		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setBackground(SystemColor.inactiveCaption);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		getContentPane().setLayout(null);
		
		btnSair = new JButton("Sair");
		btnSair.setToolTipText("Carregue para fazer signout");
		btnSair.setForeground(Color.BLACK);
		btnSair.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnSair.setFocusPainted(false);
		btnSair.setBackground(SystemColor.menu);
		btnSair.setBounds(749, 522, 104, 33);
		panel.add(btnSair);

		JLabel labelLogin = new JLabel("Username");
		labelLogin.setBounds(553, 382, 131, 27);
		labelLogin.setForeground(Color.WHITE);
		labelLogin.setFont(font);
		getContentPane().add(labelLogin);

		labelPass = new JLabel("Password");
		labelPass.setBounds(553, 429, 138, 27);
		labelPass.setForeground(Color.WHITE);
		labelPass.setFont(font);
		getContentPane().add(labelPass);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(700, 428, 193, 27);
		passwordField.setFont(font);
		getContentPane().add(passwordField);

		textFieldUser = new JTextField();
		textFieldUser.setBounds(700, 380, 193, 27);
		textFieldUser.setFont(font);
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);

		JLabel labelConfm = new JLabel("User ou Password incorreta");
		labelConfm.setBounds(674, 575, 410, 18);
		labelConfm.setForeground(new Color(255, 0, 0));
		labelConfm.setFont(font);
		getContentPane().add(labelConfm);

		btLogin = new JButton("Login");
		btLogin.setBounds(749, 478, 104, 33);
		btLogin.setForeground(SystemColor.desktop);
		btLogin.setBackground(new Color(240, 240, 240));
		btLogin.setToolTipText("Carregue para fazer login");
		btLogin.setFocusPainted(false);
		btLogin.setFont(font);
		getContentPane().add(btLogin);

		JLabel lblFooter = new JLabel();
		lblFooter.setBounds(630, 727, 327, 123);
		lblFooter.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/Altran4.png")));
		getContentPane().add(lblFooter);
				
				JLabel lblNewLabel = new JLabel();
				lblNewLabel.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/user5.png")));
				lblNewLabel.setBounds(663, 24, 230, 304);
				panel.add(lblNewLabel);
				
						icon = new JLabel();
						icon.setBounds(-17, -61, 1501, 922);
						icon.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/black.png")));
						getContentPane().add(icon);
	}

	public JButton getBtLogin() {
		return btLogin;
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
