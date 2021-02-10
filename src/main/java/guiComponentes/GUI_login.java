package guiComponentes;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JLabel labelPass;
	private JPasswordField passwordField;
	private JLabel labelConfm;
	
	private GUI_total guit;
	


	private JLabel icon;
	private JPanel panel;
	private Font font = new Font("Dubai Light", Font.PLAIN, 20);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_login frame = new GUI_login(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	public GUI_login(GUI_total guit) {
		
		this.guit = guit;
		
		setBackground(Color.WHITE);
		
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
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		
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

		labelConfm = new JLabel("User ou Password incorreta");
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
				// TODO Auto-generated method stub
				
			}
		});
		
		textFieldUser.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					login(guit);
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
					login(guit);
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
				login(guit);
			}

			
		});
		
		getContentPane().add(btLogin);
				
				JLabel lblNewLabel = new JLabel();
				lblNewLabel.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/user.png")));
				lblNewLabel.setBounds(301, 89, 238, 298);
				panel.add(lblNewLabel);
				
				JLabel lblNewLabel_1 = new JLabel("");
                lblNewLabel_1.setIcon(new ImageIcon(GUI_login.class.getResource("/guiComponentes/img/fundoAltran.png")));
                lblNewLabel_1.setBounds(0, 89, 1394, 586);
                panel.add(lblNewLabel_1);
	}
	private void login(GUI_total guit) {
		//login vai usar metodos e entidades que estao nos dao do package V2
		//primeiro vamos ver se o utilizador � um cliente
		String login = textFieldUser.getText();
		String pass = passwordField.getText();
	
		if(login.isBlank() || pass.isBlank()) {
			JOptionPane.showMessageDialog(null, "Campos não podem estar vazios.");
			return;
		}
		
		//verifica se � um cliente
		Cliente cliente = null;
		try {
			cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLoginPass( login, pass);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if( cliente != null) {
			//linha para abrir a janela do cliente (de preferencia essa janela recebe um cliente no construtor, assim podemos passar a info sobre o cliente atualmente logado)
			//TODO: abrir Janela da area cliente e passar o cliente que loga no seu construtor
			JOptionPane.showMessageDialog(null, "Menu cliente em construção.");
			return;
		}
		
		//se nao é  cliente, é  funcion�rio 
		Funcionario funcionario = null;
		try {
			funcionario = GestorDeDAO.getGestorDeDAO().pesquisarFuncionarioLoginPass( login, pass);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(funcionario != null) {
			//linha para abrir a janela do admin (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o admin atualmente logado)
			//TODO: abrir Janela da area admin e passar o admin que loga no seu construtor
			
			//TODO: arranjar algo melhor que um switch case para tratar da abertura da janela correspondente. Tenho que perceber mais sobre patterns
			switch(funcionario.getId_role()) {
				//role 1 = admin
				case(1):
					//linha para abrir a janela do admin (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o admin atualmente logado)
					//TODO: abrir Janela da area admin e passar o admin que loga no seu construtor
					guit.loginEfetuado();
					return;
				//role 2 = operador	
				case(2):
					//linha para abrir a janela do operador (de preferencia essa janela recebe um funcionario no construtor, assim podemos passar a info sobre o operador atualmente logado)
					//TODO: abrir Janela da area operador e passar o operador que loga no seu construtor
					JOptionPane.showMessageDialog(null, "Menu operador em construção.");
			
					return;
			}
		}
		labelConfm.setVisible(true);
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