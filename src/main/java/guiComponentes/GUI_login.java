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
	private JPanel panel;
	private Font font = new Font("Dubai Light", Font.PLAIN, 20);
	private JLabel lblNewLabel_1;

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
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		
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

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(329, 430, 193, 27);
		passwordField.setFont(font);
		getContentPane().add(passwordField);

		textFieldUser = new JTextField();
		textFieldUser.setBounds(329, 383, 193, 27);
		textFieldUser.setFont(font);
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);

		JLabel labelConfm = new JLabel("User ou Password incorreta");
		labelConfm.setBounds(359, 578, 251, 18);
		labelConfm.setForeground(new Color(70,74,101));
		labelConfm.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		
		//labelConfirm devia estar escondida de origem, n�o ?
		labelConfm.setVisible(false);
		getContentPane().add(labelConfm);

		btLogin = new JButton("Login");
		btLogin.setBounds(375, 476, 104, 33);
		btLogin.setForeground(SystemColor.desktop);
		btLogin.setBackground(new Color(240, 240, 240));
		btLogin.setToolTipText("Carregue para fazer login");
		btLogin.setFocusPainted(false);
		btLogin.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//login vai usar metodos e entidades que estao nos dao do package V2
				//primeiro vamos ver se o utilizador � um cliente
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
				
				//se nao � cliente, � funcion�rio (entidade funcionario vem da package standard_value_objects_v2)
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
		lblFooter.setBounds(621, 674, 320, 87);
		lblFooter.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);
				
				JLabel lblNewLabel = new JLabel();
				lblNewLabel.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/user.png")));
				lblNewLabel.setBounds(302, 73, 238, 298);
				panel.add(lblNewLabel);
				
				lblNewLabel_1 = new JLabel("New label");
				lblNewLabel_1.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/1-3_what-we-do_1600.png")));
				lblNewLabel_1.setBounds(0, 0, 1484, 717);
				panel.add(lblNewLabel_1);
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
