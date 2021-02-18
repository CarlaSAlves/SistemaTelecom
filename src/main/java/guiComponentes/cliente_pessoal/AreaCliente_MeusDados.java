package guiComponentes.cliente_pessoal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.GUI_total;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")

public class AreaCliente_MeusDados extends JFrame {
	private JPanel panelMeusDados1;
	private JTextField textFieldDadosNome;
	private JTextField textFieldDadosNIF;
	private JTextField textFieldDadosLogin;
	private JTextField textFieldDadosMorada;
	private String userName;
	
	



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
		
		try {
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initialize() throws Exception {

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
		
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.setForeground(Color.DARK_GRAY);
		btCancelar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btCancelar.setFocusPainted(false);
		btCancelar.setBounds(459, 389, 180, 50);
		panelMeusDados.add(btCancelar);
		
		JButton btConfirmar = new JButton("Confirmar");
		btConfirmar.setForeground(Color.DARK_GRAY);
		btConfirmar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btConfirmar.setFocusPainted(false);
		btConfirmar.setBounds(269, 389, 180, 50);
		panelMeusDados.add(btConfirmar);


		JLabel lblDadosNome = new JLabel("Nome ");
		lblDadosNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNome.setBounds(158, 93, 69, 36);
		panelMeusDados.add(lblDadosNome);

		
		//TODO 
		
		userName = GUI_total.getUsername();
		Cliente cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLogin(userName);		
		System.out.println("AQU!!!!");
		textFieldDadosNome = new JTextField();
		textFieldDadosNome.setColumns(10);
		textFieldDadosNome.setBounds(252, 93, 246, 36);		
		panelMeusDados.add(textFieldDadosNome);
		
		if(cliente != null) {
			textFieldDadosNome.setText(cliente.getNome());
		}
		
		
	
			
		JLabel lblDadosNIF = new JLabel("NIF ");
		lblDadosNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNIF.setBounds(158, 154, 69, 36);
		panelMeusDados.add(lblDadosNIF);

		textFieldDadosNIF = new JTextField();
		textFieldDadosNIF.setColumns(10);
		textFieldDadosNIF.setBounds(252, 154, 246, 36);
		panelMeusDados.add(textFieldDadosNIF);

		JLabel lblDadosLogin = new JLabel("Login ");
		lblDadosLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosLogin.setBounds(158, 221, 69, 36);
		panelMeusDados.add(lblDadosLogin);

		textFieldDadosLogin = new JTextField();
		textFieldDadosLogin.setColumns(10);
		textFieldDadosLogin.setBounds(252, 221, 246, 36);
		panelMeusDados.add(textFieldDadosLogin);

		JLabel lblDadosMorada = new JLabel("Morada ");
		lblDadosMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosMorada.setBounds(158, 284, 81, 36);
		panelMeusDados.add(lblDadosMorada);

		textFieldDadosMorada = new JTextField();
		textFieldDadosMorada.setColumns(10);
		textFieldDadosMorada.setBounds(252, 288, 246, 36);
		panelMeusDados.add(textFieldDadosMorada);

		// Botão Atualiza dados 

		JButton btAtualizarDados = new JButton("Atualizar Dados ");
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btAtualizarDados.setFocusPainted(false);
		btAtualizarDados.setBounds(79, 389, 180, 50);
		panelMeusDados.add(btAtualizarDados);
		
		

		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, 0, 1368, 547);
		panelMeusDados.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240,240 ));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/AltranClientes.png")));

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
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}//end class
