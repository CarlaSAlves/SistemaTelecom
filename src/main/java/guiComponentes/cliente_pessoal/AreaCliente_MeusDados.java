package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.GUI_total;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;

public class AreaCliente_MeusDados extends JFrame {

	private JPanel panelMeusDados1;
	private JTextField textFieldDadosNome;
	private JTextField textFieldDadosNIF;
	private JTextField textFieldDadosLogin;
	private JTextField textFieldDadosMorada;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_MeusDados frame = new AreaCliente_MeusDados();
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
	public AreaCliente_MeusDados() {
		initialize();
					
	}
	
	private void initialize() {
		
		ativarNimbusLookAndFeel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		panelMeusDados1 = new JPanel();
		panelMeusDados1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMeusDados1);
		panelMeusDados1.setLayout(null);
		
		JPanel panelMeusDados = new JPanel();
		panelMeusDados.setLayout(null);
		panelMeusDados.setForeground(Color.BLUE);
		panelMeusDados.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelMeusDados.setBounds(0, 11, 1384, 623);
		panelMeusDados1.add(panelMeusDados);

		
		JLabel lblDadosNome = new JLabel("Nome:");
		lblDadosNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNome.setBounds(76, 77, 69, 36);
		panelMeusDados.add(lblDadosNome);
		
		textFieldDadosNome = new JTextField();
		textFieldDadosNome.setColumns(10);
		textFieldDadosNome.setBounds(170, 77, 246, 36);
		panelMeusDados.add(textFieldDadosNome);
		
		JLabel lblDadosNIF = new JLabel("NIF:");
		lblDadosNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNIF.setBounds(76, 138, 69, 36);
		panelMeusDados.add(lblDadosNIF);
		
		textFieldDadosNIF = new JTextField();
		textFieldDadosNIF.setColumns(10);
		textFieldDadosNIF.setBounds(170, 138, 246, 36);
		panelMeusDados.add(textFieldDadosNIF);
		
		JLabel lblDadosLogin = new JLabel("Login");
		lblDadosLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosLogin.setBounds(76, 205, 69, 36);
		panelMeusDados.add(lblDadosLogin);
		
		textFieldDadosLogin = new JTextField();
		textFieldDadosLogin.setColumns(10);
		textFieldDadosLogin.setBounds(170, 205, 246, 36);
		panelMeusDados.add(textFieldDadosLogin);
		
		JLabel lblDadosMorada = new JLabel("Morada");
		lblDadosMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosMorada.setBounds(76, 268, 81, 36);
		panelMeusDados.add(lblDadosMorada);
		
		textFieldDadosMorada = new JTextField();
		textFieldDadosMorada.setColumns(10);
		textFieldDadosMorada.setBounds(170, 272, 246, 36);
		panelMeusDados.add(textFieldDadosMorada);
		
		// Botão Atualiza dados 
		
		JButton btAtualizarDados = new JButton("Atualizar dados");
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btAtualizarDados.setFocusPainted(false);
		btAtualizarDados.setBounds(200, 362, 180, 50);
		panelMeusDados.add(btAtualizarDados);
		
		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, -35, 1384, 622);
		panelMeusDados.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240,240 ));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));
		
	}// end initialize
	

	
	/**
	 * Activa o Nimbus Look and Feel
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
	
	public JPanel returnAreaClienteMeusDados() {
		return panelMeusDados1;
	}
	
	
	
}//end class
