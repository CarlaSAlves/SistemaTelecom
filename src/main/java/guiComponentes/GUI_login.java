package guiComponentes;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data_acess_object_dao_v2.PasswordEncryption;
import servico.GestorDeDAO;
import standard_value_object_v2.Funcionario;
import standard_value_object_v2.Cliente;


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
		
		//labelConfirm devia estar escondida de origem, não ?
		labelConfm.setVisible(false);
		getContentPane().add(labelConfm);

		btLogin = new JButton("Login");
		btLogin.setBounds(749, 478, 104, 33);
		btLogin.setForeground(SystemColor.desktop);
		btLogin.setBackground(new Color(240, 240, 240));
		btLogin.setToolTipText("Carregue para fazer login");
		btLogin.setFocusPainted(false);
		btLogin.setFont(font);
		btLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//login vai usar metodos e entidades que estao nos dao do package V2
				//primeiro vamos ver se o utilizador é um cliente
				String login = textFieldUser.getText();
				String pass = passwordField.getSelectedText();
				
				if(login.isBlank() || pass.isBlank()) {
					JOptionPane.showMessageDialog(null, "Campos não podem estar vazios.");
					return;
				}
				
				//verifica se é um cliente (entidade cliente vem da package standard_value_objects_v2)
				//searchClienteByLoginPass vem dos dao V2
				Cliente cliente = GestorDeDAO.getGestorDeDAO().searchClienteByLoginPass( login, PasswordEncryption.get_SHA_512_SecurePassword(pass) );
				if( cliente != null) {
					//linha para abrir a janela do cliente (de preferencia essa janela recebe um cliente no construtor, assim podemos passar a info sobre o cliente atualmente logado)
					//TODO: abrir Janela da area cliente e passar o cliente que loga no seu construtor
					
					//fecha o menu login
					GUI_login.this.setVisible(false);
					GUI_login.this.dispose();
					return;
				}
				
				//se nao é cliente, é funcionário (entidade funcionario vem da package standard_value_objects_v2)
				//searchFuncionarioByLoginPass vem dos dao V2
				Funcionario funcionario = GestorDeDAO.getGestorDeDAO().searchFuncionarioByLoginPass( login, PasswordEncryption.get_SHA_512_SecurePassword(pass) );
				if( funcionario != null) {
					//linha para abrir a janela do admin (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o admin atualmente logado)
					//TODO: abrir Janela da area admin e passar o admin que loga no seu construtor
					
					//TODO: arranjar algo melhor que um switch case para tratar da abertura da janela correspondente. Tenho que perceber mais sobre patterns
					switch(funcionario.getId_role()) {
						//role 1 = admin
						case(1):
							//linha para abrir a janela do admin (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o admin atualmente logado)
							//TODO: abrir Janela da area admin e passar o admin que loga no seu construtor
							
							GUI_login.this.setVisible(false);
							GUI_login.this.dispose();
							return;
						//role 2 = operador	
						case(2):
							//linha para abrir a janela do operador (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o operador atualmente logado)
							//TODO: abrir Janela da area operador e passar o operador que loga no seu construtor
							
							GUI_login.this.setVisible(false);
							GUI_login.this.dispose();
							return;
					}
				}
				labelConfm.setVisible(true);
			}
		});
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
