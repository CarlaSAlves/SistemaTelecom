package guiComponentes.cliente_pessoal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
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
	private JPanel panelMeusDados;
	private JTextField textFieldDadosNome;
	private JTextField textFieldDadosNIF;
	private JTextField textFieldDadosLogin;
	private JTextField textFieldDadosMorada;
	private Cliente cliente;


	/**
	 * Launch the application.
//	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					//AreaCliente_MeusDados frame = new AreaCliente_MeusDados();
	//					frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the frame.
	 */
	public AreaCliente_MeusDados( ) {

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
		panelMeusDados = new JPanel();
		panelMeusDados.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMeusDados);
		panelMeusDados.setLayout(null);


		JLabel lblDadosNome = new JLabel("Nome ");
		lblDadosNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNome.setBounds(158, 93, 69, 36);
		panelMeusDados.add(lblDadosNome);

		JLabel lblDadosLogin = new JLabel("Login ");
		lblDadosLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosLogin.setBounds(158, 221, 69, 36);
		panelMeusDados.add(lblDadosLogin);

		
		JLabel lblDadosMorada = new JLabel("Morada ");
		lblDadosMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosMorada.setBounds(158, 284, 81, 36);
		panelMeusDados.add(lblDadosMorada);

		JButton btConfirmar = new JButton("Confirmar");
		btConfirmar.setForeground(Color.DARK_GRAY);
		btConfirmar.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btConfirmar.setFocusPainted(false);
		btConfirmar.setBounds(269, 389, 180, 50);

		panelMeusDados.add(btConfirmar);
		textFieldDadosNome = new JTextField();
		textFieldDadosNome.setEditable(false);
		textFieldDadosNome.setColumns(10);
		textFieldDadosNome.setBounds(252, 93, 246, 36);		
		panelMeusDados.add(textFieldDadosNome);

		JLabel lblDadosNIF = new JLabel("NIF ");
		lblDadosNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNIF.setBounds(158, 154, 69, 36);
		panelMeusDados.add(lblDadosNIF);

		textFieldDadosNIF = new JTextField();
		textFieldDadosNIF.setEditable(false);
		textFieldDadosNIF.setColumns(10);
		textFieldDadosNIF.setBounds(252, 154, 246, 36);
		panelMeusDados.add(textFieldDadosNIF);

		textFieldDadosLogin = new JTextField();
		textFieldDadosLogin.setEditable(false);
		textFieldDadosLogin.setColumns(10);
		textFieldDadosLogin.setBounds(252, 221, 246, 36);
		panelMeusDados.add(textFieldDadosLogin);
		
		textFieldDadosMorada = new JTextField();
		textFieldDadosMorada.setEditable(false);
		textFieldDadosMorada.setColumns(10);
		textFieldDadosMorada.setBounds(252, 288, 246, 36);
		panelMeusDados.add(textFieldDadosMorada);


		// Botão Atualiza dados 

		JButton btAtualizarDados = new JButton("Atualizar Dados ");
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btAtualizarDados.setFocusPainted(false);
		btAtualizarDados.setBounds(79, 389, 180, 50);
		panelMeusDados.add(btAtualizarDados);

		JButton btCancelar = new JButton("Cancelar");
		btCancelar.setForeground(Color.DARK_GRAY);
		btCancelar.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btCancelar.setFocusPainted(false);
		btCancelar.setBounds(459, 389, 180, 50);
		panelMeusDados.add(btCancelar);


		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, 0, 1432, 547);
		panelMeusDados.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240,240 ));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/AltranClientes.png")));

		
//		textFieldDadosNome.setText(cliente.getNome());
//		textFieldDadosNIF.setText(cliente.getNif() + "");
//		textFieldDadosLogin.setText(cliente.getLogin());
//		textFieldDadosMorada.setText(cliente.getMorada());
//		

	}// end initialize

	
	public JTextField getTextFieldDadosNome() {
		return textFieldDadosNome;
	}


	public JTextField getTextFieldDadosNIF() {
		return textFieldDadosNIF;
	}

	

	public JTextField getTextFieldDadosLogin() {
		return textFieldDadosLogin;
	}

	

	public JTextField getTextFieldDadosMorada() {
		return textFieldDadosMorada;
	}



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
		return (JPanel) getContentPane();
	}


}//end class
