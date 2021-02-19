package guiComponentes.cliente_pessoal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.admin_gestorCliente.CriarClienteDialog;
import guiComponentes.admin_gestorCliente.GUI_gestor_cliente;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.PacoteComercial;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

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
	private JPanel panelDados;
	private JLabel lblTitulo;
	private JPanel panelPasswords;
	private JLabel lblTituloPass;
	private JLabel lblDadosLogin;
	private String username;
	private JLabel lblNovoNome;
	private JLabel lblNovoNIF;
	private JLabel lblNovaMorada;
	private JLabel lblNovaPass;
	private JLabel lblNovoLogin;
	private Cliente cliente;
	private boolean nomeAlterado = false;
	private boolean nifAlterado = false;
	private boolean moradaAlterado = false;
	private boolean todosDadosValidos = false;

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
	 * @throws Exception 
	 */
	public AreaCliente_MeusDados( ) {
		initialize();
	}

	private void initialize()   {

		ativarNimbusLookAndFeel();


		/**
		 * Define as caracteristicas dos painel base. 
		 */
		panelMeusDados = new JPanel();
		panelMeusDados.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		setContentPane(panelMeusDados);
		panelMeusDados.setLayout(null);

		panelDados = new JPanel();
		panelDados.setBounds(10, 11, 390, 495);
		panelDados.setBackground(new Color(0, 178, 188));
		panelMeusDados.add(panelDados);
		panelDados.setLayout(null);

		panelPasswords = new JPanel();
		panelPasswords.setBounds(410, 11, 299, 465);
		panelMeusDados.add(panelPasswords);
		panelPasswords.setBackground(new Color(0, 178, 188));
		panelPasswords.setLayout(null);

		lblNovaMorada = new JLabel("Ensira a Nova Morada");
		lblNovaMorada.setForeground(Color.LIGHT_GRAY);
		lblNovaMorada.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNovaMorada.setBounds(66, 304, 224, 14);
		lblNovaMorada.setVisible(false);
		panelDados.add(lblNovaMorada);

		lblNovoNome = new JLabel("Ensira o Novo Nome");
		lblNovoNome.setForeground(Color.LIGHT_GRAY);
		lblNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNovoNome.setBounds(66, 176, 224, 14);
		lblNovoNome.setVisible(false);
		panelDados.add(lblNovoNome);

		lblNovoNIF = new JLabel("Ensira o Novo NIF");
		lblNovoNIF.setForeground(Color.LIGHT_GRAY);
		lblNovoNIF.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNovoNIF.setBounds(66, 239, 224, 14);
		lblNovoNIF.setVisible(false);
		panelDados.add(lblNovoNIF);

		JLabel lblDadosNome = new JLabel("Nome ");
		lblDadosNome.setForeground(Color.DARK_GRAY);
		lblDadosNome.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDadosNome.setBounds(56, 144, 69, 28);
		panelDados.add(lblDadosNome);


		JLabel lblDadosMorada = new JLabel("Morada ");
		lblDadosMorada.setForeground(Color.DARK_GRAY);
		lblDadosMorada.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDadosMorada.setBounds(56, 272, 81, 28);
		panelDados.add(lblDadosMorada);





		textFieldDadosNome = new JTextField();
		textFieldDadosNome.setForeground(Color.BLACK);
		textFieldDadosNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosNome.setEditable(false);
		textFieldDadosNome.setColumns(10);
		textFieldDadosNome.setBounds(54, 168, 252, 30);
		panelDados.add(textFieldDadosNome);

		JLabel lblDadosNIF = new JLabel("NIF ");
		lblDadosNIF.setForeground(Color.DARK_GRAY);
		lblDadosNIF.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDadosNIF.setBounds(56, 207, 69, 28);
		panelDados.add(lblDadosNIF);

		textFieldDadosNIF = new JTextField();
		textFieldDadosNIF.setForeground(Color.BLACK);
		textFieldDadosNIF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosNIF.setEditable(false);
		textFieldDadosNIF.setColumns(10);
		textFieldDadosNIF.setBounds(54, 231, 252, 30);
		panelDados.add(textFieldDadosNIF);

		textFieldDadosMorada = new JTextField();
		textFieldDadosMorada.setForeground(Color.BLACK);
		textFieldDadosMorada.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldDadosMorada.setEditable(false);
		textFieldDadosMorada.setColumns(10);
		textFieldDadosMorada.setBounds(54, 296, 252, 30);
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


		// Botão Atualiza dados 

		btAtualizarDados = new JButton("Atualizar Dados ");
		btAtualizarDados.setBounds(167, 341, 139, 30);
		panelDados.add(btAtualizarDados);
		btAtualizarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDados.setVisible(false);
				btCancelar.setVisible(true);
				btConfirmar.setVisible(true);


				textFieldDadosNome.setEditable(true);
				textFieldDadosNIF.setEditable(true);
				textFieldDadosMorada.setEditable(true);
				nomeAlterado = false;
				nifAlterado = false;
				moradaAlterado = false;
				todosDadosValidos = false;
				popularTextFields(cliente);


				textFieldDadosNome.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(textFieldDadosNome.getText().isBlank()) {
							lblNovoNome.setVisible(true);
						}else {
							lblNovoNome.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovoNome.setVisible(false);

					}
				});

				textFieldDadosNIF.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(textFieldDadosNIF.getText().isBlank()) {
							lblNovoNIF.setVisible(true);
						}else {
							lblNovoNIF.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovoNIF.setVisible(false);

					}
				});

				textFieldDadosMorada.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(textFieldDadosMorada.getText().isBlank()) {
							lblNovaMorada.setVisible(true);
						}else {
							lblNovaMorada.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovaMorada.setVisible(false);

					}
				});


			}
		});
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dialog", Font.PLAIN, 15));
		btAtualizarDados.setFocusPainted(false);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(54, 341, 120, 30);
		panelDados.add(btConfirmar);
		btConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				if(cliente != null) {

					Cliente clienteNovo = cliente;
					String nome = clienteNovo.getNome();
					String morada = clienteNovo.getMorada();
					int nif = (int) clienteNovo.getNif();

					char[] nomeEmArray = new char[textFieldDadosNome.getText().length()]; 

					for(int i = 0; i < nomeEmArray.length; i++) {
						nomeEmArray[i] = textFieldDadosNome.getText().charAt(i);
						System.out.println(nomeEmArray[i]);
						if (nomeEmArray[i]<65 || nomeEmArray[i] > 122) {
							JOptionPane.showMessageDialog(AreaCliente_MeusDados.this,
									"O Nome não pode conter números!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					if(cliente.getNome().equalsIgnoreCase(textFieldDadosNome.getText().trim())) {
						nomeAlterado = false;
					}else {
						nomeAlterado = true;
						nome = textFieldDadosNome.getText().trim();
					}


					try {
						if(cliente.getNif() == Integer.parseInt(textFieldDadosNIF.getText().trim())) {
							nifAlterado = false;
						}else {
							nifAlterado = true;
							nif = Integer.parseInt( textFieldDadosNIF.getText().trim());
						}
					}
					catch( Exception ex ){
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "O NIF tem de ser um inteiro!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if(cliente.getMorada().equalsIgnoreCase(textFieldDadosMorada.getText().trim())) {
						moradaAlterado = false;
					}else {
						moradaAlterado = true;
						morada = textFieldDadosMorada.getText();
					}

					if(textFieldDadosNIF.getText().isBlank() || textFieldDadosNome.getText().isBlank() ||  textFieldDadosNIF.getText().isBlank()) {
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "Tem Campos por preencher!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					todosDadosValidos = true;

					if(todosDadosValidos) {
						if(nomeAlterado) {
							clienteNovo.setNome(nome);
						} else if(nifAlterado) {
							clienteNovo.setNif(nif);
						} else if(moradaAlterado){
							clienteNovo.setMorada(morada);
						}
						try {
							GestorDeDAO.getGestorDeDAO().editarClienteDadosBasicos(clienteNovo);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}

				btAtualizarDados.setVisible(true);
				btCancelar.setVisible(false);
				btConfirmar.setVisible(false);

				textFieldDadosNome.setEditable(false);
				textFieldDadosNIF.setEditable(false);
				textFieldDadosMorada.setEditable(false);

			}
		});
		btConfirmar.setForeground(Color.DARK_GRAY);
		btConfirmar.setVisible(false);
		btConfirmar.setFont(new Font("Dialog", Font.PLAIN, 15));
		btConfirmar.setFocusPainted(false);

		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(186, 341, 120, 30);
		panelDados.add(btCancelar);
		btCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDados.setVisible(true);
				btCancelar.setVisible(false);
				btConfirmar.setVisible(false);

				if(cliente != null && !todosDadosValidos) {
					textFieldDadosNome.setText(cliente.getNome());
					textFieldDadosNIF.setText( "" + cliente.getNif());
					textFieldDadosMorada.setText(cliente.getMorada());
					textFieldID.setText("" +cliente.getId());
				}


				textFieldDadosNome.setEditable(false);
				textFieldDadosNIF.setEditable(false);
				textFieldDadosMorada.setEditable(false);

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

		lblNovoLogin = new JLabel("Ensira o Novo Login");
		lblNovoLogin.setForeground(Color.LIGHT_GRAY);
		lblNovoLogin.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNovoLogin.setVisible(false);
		lblNovoLogin.setBounds(29, 111, 224, 14);
		panelPasswords.add(lblNovoLogin);

		lblNovaPass = new JLabel("Ensira a Nova Password");
		lblNovaPass.setForeground(Color.LIGHT_GRAY);
		lblNovaPass.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNovaPass.setVisible(false);
		lblNovaPass.setBounds(29, 171, 224, 14);
		panelPasswords.add(lblNovaPass);

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
		btAtualizarDadosPass.setBounds(130, 212, 139, 30);
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
		imagemDados.setBounds(10, 0, 1368, 547);
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
			this.cliente = cliente;

			if(cliente != null) {
				textFieldDadosNome.setText(cliente.getNome());
				textFieldDadosNIF.setText( "" + cliente.getNif());
				textFieldDadosLogin.setText(cliente.getLogin());
				textFieldDadosMorada.setText(cliente.getMorada());
				textFieldID.setText("" +cliente.getId());
				passwordField.setText(cliente.getPassword().substring(0, 8));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void popularTextFields(Cliente clienteAntigo) {
		textFieldDadosNome.setText(clienteAntigo.getNome()+ "");
		textFieldDadosNIF.setText(clienteAntigo.getNif() + "");
		textFieldDadosMorada.setText(clienteAntigo.getMorada());

	}

}//end class
