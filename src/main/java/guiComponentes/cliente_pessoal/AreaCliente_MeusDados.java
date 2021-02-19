package guiComponentes.cliente_pessoal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")

public class AreaCliente_MeusDados extends JFrame {
	private JPanel panelMeusDados;
	private JTextField textFieldDadosNome;
	private JTextField textFieldDadosNIF;
	private JTextField textFieldDadosLogin;
	private JTextField textFieldDadosMorada;
	private JButton btAtualizarDados, btConfirmar, btCancelar, btAtualizarDadosPass, btConfirmarPass, btCancelarPass;
	private JTextField textFieldID;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JLabel lblPacoteCliente;
	private JTextField textFieldPacoteComercial;
	private JPanel panelDados;
	private JLabel lblTitulo;
	private JPanel panelPasswords;
	private JLabel lblTituloPass;
	private JLabel lblDadosLogin;
	private String username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_MeusProdutos frame = new AreaCliente_MeusProdutos();
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
	public AreaCliente_MeusDados( ) {
		initialize();
	}

	private void initialize()   {

		ativarNimbusLookAndFeel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		panelMeusDados = new JPanel();
		panelMeusDados.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMeusDados);
		panelMeusDados.setLayout(null);

		panelDados = new JPanel();
		panelDados.setBounds(10, 11, 390, 465);
		panelDados.setBackground(new Color(0, 178, 188));
		panelMeusDados.add(panelDados);
		panelDados.setLayout(null);

		panelPasswords = new JPanel();
		panelPasswords.setBounds(410, 11, 299, 429);
		panelMeusDados.add(panelPasswords);
		panelPasswords.setBackground(new Color(0, 178, 188));
		panelPasswords.setLayout(null);

		JLabel lblDadosNome = new JLabel("Nome ");
		lblDadosNome.setForeground(Color.DARK_GRAY);
		lblDadosNome.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDadosNome.setBounds(56, 199, 69, 28);
		panelDados.add(lblDadosNome);


		JLabel lblDadosMorada = new JLabel("Morada ");
		lblDadosMorada.setForeground(Color.DARK_GRAY);
		lblDadosMorada.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDadosMorada.setBounds(56, 319, 81, 28);
		panelDados.add(lblDadosMorada);





		textFieldDadosNome = new JTextField();
		textFieldDadosNome.setForeground(Color.BLACK);
		textFieldDadosNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosNome.setEditable(false);
		textFieldDadosNome.setColumns(10);
		textFieldDadosNome.setBounds(54, 223, 252, 30);
		panelDados.add(textFieldDadosNome);

		JLabel lblDadosNIF = new JLabel("NIF ");
		lblDadosNIF.setForeground(Color.DARK_GRAY);
		lblDadosNIF.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDadosNIF.setBounds(56, 259, 69, 28);
		panelDados.add(lblDadosNIF);

		textFieldDadosNIF = new JTextField();
		textFieldDadosNIF.setForeground(Color.BLACK);
		textFieldDadosNIF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosNIF.setEditable(false);
		textFieldDadosNIF.setColumns(10);
		textFieldDadosNIF.setBounds(54, 283, 252, 30);
		panelDados.add(textFieldDadosNIF);

		textFieldDadosMorada = new JTextField();
		textFieldDadosMorada.setForeground(Color.BLACK);
		textFieldDadosMorada.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosMorada.setEditable(false);
		textFieldDadosMorada.setColumns(10);
		textFieldDadosMorada.setBounds(54, 343, 252, 30);
		panelDados.add(textFieldDadosMorada);

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(Color.DARK_GRAY);
		lblID.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblID.setBounds(56, 79, 69, 28);
		panelDados.add(lblID);

		textFieldID = new JTextField();
		textFieldID.setForeground(Color.BLACK);
		textFieldID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldID.setEditable(false);
		textFieldID.setColumns(10);
		textFieldID.setBounds(54, 103, 252, 30);
		panelDados.add(textFieldID);

		lblPacoteCliente = new JLabel("Pacote Comercial ID");
		lblPacoteCliente.setForeground(Color.DARK_GRAY);
		lblPacoteCliente.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblPacoteCliente.setBounds(56, 139, 223, 28);
		panelDados.add(lblPacoteCliente);

		textFieldPacoteComercial = new JTextField();
		textFieldPacoteComercial.setForeground(Color.BLACK);
		textFieldPacoteComercial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldPacoteComercial.setEditable(false);
		textFieldPacoteComercial.setColumns(10);
		textFieldPacoteComercial.setBounds(54, 163, 252, 30);
		panelDados.add(textFieldPacoteComercial);


		// Botão Atualiza dados 

		btAtualizarDados = new JButton("Atualizar Dados ");
		btAtualizarDados.setBounds(169, 392, 139, 30);
		panelDados.add(btAtualizarDados);
		btAtualizarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDados.setVisible(false);
				btCancelar.setVisible(true);
				btConfirmar.setVisible(true);
			}
		});
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dialog", Font.PLAIN, 15));
		btAtualizarDados.setFocusPainted(false);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(56, 392, 120, 30);
		panelDados.add(btConfirmar);
		btConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDados.setVisible(true);
				btCancelar.setVisible(false);
				btConfirmar.setVisible(false);
			}
		});
		btConfirmar.setForeground(Color.DARK_GRAY);
		btConfirmar.setVisible(false);
		btConfirmar.setFont(new Font("Dialog", Font.PLAIN, 15));
		btConfirmar.setFocusPainted(false);

		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(188, 392, 120, 30);
		panelDados.add(btCancelar);
		btCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDados.setVisible(true);
				btCancelar.setVisible(false);
				btConfirmar.setVisible(false);
			}
		});
		btCancelar.setForeground(Color.DARK_GRAY);
		btCancelar.setVisible(false);
		btCancelar.setFont(new Font("Dialog", Font.PLAIN, 15));
		btCancelar.setFocusPainted(false);

		lblTitulo = new JLabel("Meus Dados");
		lblTitulo.setForeground(Color.DARK_GRAY);
		lblTitulo.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTitulo.setBounds(56, 43, 224, 28);
		panelDados.add(lblTitulo);

		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBounds(17, 163, 252, 30);
		passwordField.setEditable(false);
		panelPasswords.add(passwordField);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblDadosLogin = new JLabel("Login ");
		lblDadosLogin.setForeground(Color.DARK_GRAY);
		lblDadosLogin.setBounds(19, 79, 69, 28);
		panelPasswords.add(lblDadosLogin);
		lblDadosLogin.setFont(new Font("Dialog", Font.PLAIN, 15));

		textFieldDadosLogin = new JTextField();
		textFieldDadosLogin.setForeground(Color.BLACK);
		textFieldDadosLogin.setBounds(17, 103, 252, 30);
		panelPasswords.add(textFieldDadosLogin);
		textFieldDadosLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosLogin.setEditable(false);
		textFieldDadosLogin.setColumns(10);

		lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.DARK_GRAY);
		lblPassword.setBounds(19, 139, 69, 28);
		panelPasswords.add(lblPassword);
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 15));

		lblTituloPass = new JLabel("Login & Password");
		lblTituloPass.setForeground(Color.DARK_GRAY);
		lblTituloPass.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTituloPass.setBounds(19, 43, 224, 28);
		panelPasswords.add(lblTituloPass);

		btAtualizarDadosPass = new JButton("Atualizar Login");
		btAtualizarDadosPass.setForeground(Color.DARK_GRAY);
		btAtualizarDadosPass.setFont(new Font("Dialog", Font.PLAIN, 15));
		btAtualizarDadosPass.setFocusPainted(false);
		btAtualizarDadosPass.setBounds(132, 212, 139, 30);
		btAtualizarDadosPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDadosPass.setVisible(false);
				btCancelarPass.setVisible(true);
				btConfirmarPass.setVisible(true);
			}
		});
		panelPasswords.add(btAtualizarDadosPass);

		btConfirmarPass = new JButton("Confirmar");
		btConfirmarPass.setForeground(Color.DARK_GRAY);
		btConfirmarPass.setFont(new Font("Dialog", Font.PLAIN, 15));
		btConfirmarPass.setFocusPainted(false);
		btConfirmarPass.setBounds(17, 212, 120, 30);
		btConfirmarPass.setVisible(false);
		btConfirmarPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDadosPass.setVisible(true);
				btCancelarPass.setVisible(false);
				btConfirmarPass.setVisible(false);
			}
		});
		panelPasswords.add(btConfirmarPass);

		btCancelarPass = new JButton("Cancelar");
		btCancelarPass.setForeground(Color.DARK_GRAY);
		btCancelarPass.setFont(new Font("Dialog", Font.PLAIN, 15));
		btCancelarPass.setFocusPainted(false);
		btCancelarPass.setVisible(false);
		btCancelarPass.setBounds(149, 212, 120, 30);
		btCancelarPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDadosPass.setVisible(true);
				btCancelarPass.setVisible(false);
				btConfirmarPass.setVisible(false);
			}
		});
		panelPasswords.add(btCancelarPass);

		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, 0, 1432, 547);
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
		return (JPanel) getContentPane();
	}

	public void enviarUsernameMeusDados(String username) {
		this.username = username;

		try {

			Cliente cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLogin(username);
			if (cliente != null) {
			textFieldDadosNome.setText(cliente.getNome());
			textFieldDadosNIF.setText( "" + cliente.getNif());
			textFieldDadosLogin.setText(cliente.getLogin());
			textFieldDadosMorada.setText(cliente.getMorada());
			textFieldPacoteComercial.setText(cliente.getId_pacote_cliente()== 0? "Não Atribuido" : "" + cliente.getId_pacote_cliente() );
			textFieldID.setText("" +cliente.getId());
			passwordField.setText(cliente.getPassword().substring(0, 8));
			}
		} catch (Exception e) {

			e.printStackTrace();

		}


	}


}//end class
