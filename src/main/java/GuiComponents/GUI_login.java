package GuiComponents;


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
import javax.swing.SpringLayout;


@SuppressWarnings("serial")
public class GUI_login extends JFrame {

	private JTextField textFieldUser;
	private JButton btLogin;
	
	private JLabel labelPass;
	
	JLabel icon;
	
	private JPanel panel;
	
	private Font font = new Font("Dubai Light", Font.PLAIN, 20);

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public GUI_login() {
		
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setBackground(SystemColor.inactiveCaption);
		//getContentPane().setBackground(SystemColor.inactiveCaption);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		
		
		icon = new JLabel("");
		icon.setBounds(429, 0, 626, 220);
		icon.setIcon(new ImageIcon(GUI_login.class.getResource("/img/mundo.png")));
		
		
		JLabel labelTitulo = new JLabel("Gestão Telecom");
		labelTitulo.setBounds(439, 103, 183, 47);
		labelTitulo.setForeground(SystemColor.window);
		labelTitulo.setFont(new Font("Dubai Light", Font.BOLD | Font.ITALIC, 27));
		labelTitulo.setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().setLayout(null);
		
		
		JLabel lblPoesia = new JLabel("O mundo, mais perto de si");
		lblPoesia.setBounds(442, 180, 513, 33);
		lblPoesia.setForeground(Color.WHITE);
		lblPoesia.setFont(new Font("Dubai Light", Font.ITALIC, 19));
		lblPoesia.setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().add(lblPoesia);
		getContentPane().add(labelTitulo);
		getContentPane().add(icon);
		
		JLabel labelLogin = new JLabel("Username");
		labelLogin.setBounds(563, 358, 80, 64);
		labelLogin.setForeground(new Color(0,37,80));
		labelLogin.setFont(font);
		getContentPane().add(labelLogin);
		
		labelPass = new JLabel("Password");
		labelPass.setBounds(563, 428, 87, 27);
		labelPass.setForeground(new Color(0,37,80));
		labelPass.setFont(font);
		getContentPane().add(labelPass);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(666, 428, 193, 27);
		passwordField.setFont(font);
		getContentPane().add(passwordField);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(666, 380, 193, 27);
		textFieldUser.setFont(font);
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel labelConfm = new JLabel("User ou Password incorreta");
		labelConfm.setBounds(635, 550, 643, 18);
		labelConfm.setForeground(new Color(255, 0, 0));
		labelConfm.setFont(font);
		getContentPane().add(labelConfm);
		
		btLogin = new JButton("Login");
		btLogin.setBounds(697, 483, 104, 33);
		btLogin.setForeground(SystemColor.desktop);
		btLogin.setBackground(new Color(240, 240, 240));
		btLogin.setToolTipText("Carregue para fazer login");
		btLogin.setFocusPainted(false);
		btLogin.setFont(font);
		getContentPane().add(btLogin);
		
		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(329, 676, 600, 100);
		//springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblFooter, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
		lblFooter.setIcon(new ImageIcon(GUI_login.class.getResource("/img/footer.png")));
		getContentPane().add(lblFooter);
	}
	
	 public JButton getBtLogin() {
			return btLogin;
		}

		public JPanel returnPanel() {
		        return (JPanel) getContentPane();
		    }
}
