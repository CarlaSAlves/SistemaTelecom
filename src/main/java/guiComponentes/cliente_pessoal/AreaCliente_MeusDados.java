package guiComponentes.cliente_pessoal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import data_acess_object_dao.PasswordEncryption;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.admin_gestorCliente.CriarClienteDialog;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")

public class AreaCliente_MeusDados extends JFrame {
	private JPanel panelMeusDados, panelDados, panelPasswords;
	private JTextField textFieldID, textFieldDadosNome, textFieldDadosNIF, textFieldDadosLogin, textFieldDadosMorada ;
	private JButton btAtualizarDados, btConfirmar, btCancelar, btAtualizarDadosPass, btConfirmarPass, btCancelarPass;	
	private JLabel lblPassword, lblTitulo, lblTituloPass, lblDadosLogin, lblNovoNome, lblNovoNIF,
	lblNovaMorada, lblNovaPass, lblNovoLogin, lblNovaPassword_1, lblNovaPassword_2, lblNovaPass_2, lblNovaPassConfirm;
	private JPasswordField passwordField, passwordField_1, passwordField_2;
	private Cliente cliente;
	private boolean nomeAlterado = false, nifAlterado = false, 
			moradaAlterado = false, todosDadosValidos = false, 
			loginAlterado = false, passwordCorreta = false, passwordNovaIgual = false;
	private String username;

		/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AreaCliente_MeusDados( ) {
		initialize();
	}

	/**
	 * Interface, look and feel Nimbus setup
	 */
	private void initialize()   {

		ativarNimbusLookAndFeel();

		/**
		 * Defines the characteristics of the base panels. 
		 */

		panelMeusDados = new JPanel();
		panelMeusDados.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		setContentPane(panelMeusDados);
		panelMeusDados.setLayout(null);

		setResizable(false);
		
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

		/**
		 * Labels 
		 * 
		 * ID
		 * Nome 
		 * NIF
		 * Morada
		 * 
		 * Novo Nome
		 * Novo NIF
		 * Nova Morada
		 * Novo Login
		 * Nova Password
		 */

		labelsSetup();

		/**
		 * TextFields 
		 * 
		 * ID
		 * Nome 
		 * NIF
		 * Morada
		 * Login
		 * Password
		 */

		textFieldsSetup();

		/**
		 * Buttons
		 * 
		 * Atualiza dados
		 * Confirma dados
		 * Cancelar
		 * Atualiza dados Password
		 * Confirma Password
		 * Cancelar Password 
		 */

		botaoAtualizaDados();
		botaoConfirmarDados();
		botaoCancelar();
		botaoAtualizaDadosPassword();
		botaoConfirmarPassword();
		botaoCancelarPassword();

		//Background immage

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, -37, 1408, 586);
		panelMeusDados.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240,240 ));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/AltranClientes.png")));

	}

	/**
	 * Activates Nimbus Look and Feel UI
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

	/**
	 * labels for viewing and entering data 
	 */
	private void labelsSetup() {

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(new Color(255, 255, 255));
		lblID.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblID.setBounds(56, 79, 69, 28);
		panelDados.add(lblID);

		JLabel lblDadosNome = new JLabel("Nome ");
		lblDadosNome.setForeground(new Color(255, 255, 255));
		lblDadosNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNome.setBounds(56, 144, 69, 28);
		panelDados.add(lblDadosNome);

		JLabel lblDadosNIF = new JLabel("NIF ");
		lblDadosNIF.setForeground(new Color(255, 255, 255));
		lblDadosNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosNIF.setBounds(56, 207, 69, 28);
		panelDados.add(lblDadosNIF);

		JLabel lblDadosMorada = new JLabel("Morada ");
		lblDadosMorada.setForeground(new Color(255, 255, 255));
		lblDadosMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblDadosMorada.setBounds(56, 272, 81, 28);
		panelDados.add(lblDadosMorada);

		lblNovoNome = new JLabel("Insira o novo Nome");
		lblNovoNome.setForeground(Color.LIGHT_GRAY);
		lblNovoNome.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovoNome.setBounds(66, 176, 224, 14);
		panelDados.add(lblNovoNome);

		lblNovaMorada = new JLabel("Insira a nova Morada");
		lblNovaMorada.setForeground(Color.LIGHT_GRAY);
		lblNovaMorada.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovaMorada.setBounds(66, 304, 224, 14);
		panelDados.add(lblNovaMorada);

		lblNovoNIF = new JLabel("Insira o novo NIF");
		lblNovoNIF.setForeground(Color.LIGHT_GRAY);
		lblNovoNIF.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovoNIF.setBounds(66, 239, 224, 14);
		panelDados.add(lblNovoNIF);

		lblTitulo = new JLabel("Os seus Dados");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Dubai Light", Font.BOLD, 22));
		lblTitulo.setBounds(56, 43, 234, 28);
		panelDados.add(lblTitulo);

		lblNovoLogin = new JLabel("Insira o novo Username");
		lblNovoLogin.setForeground(Color.LIGHT_GRAY);
		lblNovoLogin.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovoLogin.setBounds(29, 111, 224, 14);
		panelPasswords.add(lblNovoLogin);

		lblNovaPass_2 = new JLabel("Insira a nova Password");
		lblNovaPass_2.setForeground(Color.LIGHT_GRAY);
		lblNovaPass_2.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovaPass_2.setBounds(29, 239, 224, 14);
		panelPasswords.add(lblNovaPass_2);

		lblNovaPassConfirm = new JLabel("Insira a nova Password");
		lblNovaPassConfirm.setForeground(Color.LIGHT_GRAY);
		lblNovaPassConfirm.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovaPassConfirm.setBounds(29, 304, 224, 14);
		panelPasswords.add(lblNovaPassConfirm);

		lblNovaPass = new JLabel("Insira a Password Atual");
		lblNovaPass.setForeground(Color.LIGHT_GRAY);
		lblNovaPass.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNovaPass.setBounds(29, 171, 224, 14);
		panelPasswords.add(lblNovaPass);

		lblDadosLogin = new JLabel("Username ");
		lblDadosLogin.setForeground(Color.WHITE);
		lblDadosLogin.setBounds(19, 79, 234, 28);
		panelPasswords.add(lblDadosLogin);
		lblDadosLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));

		lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(19, 139, 234, 28);
		panelPasswords.add(lblPassword);
		lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 18));

		lblTituloPass = new JLabel("O seu Username & Password");
		lblTituloPass.setForeground(Color.WHITE);
		lblTituloPass.setFont(new Font("Dubai Light", Font.BOLD, 22));
		lblTituloPass.setBounds(19, 43, 300, 28);
		panelPasswords.add(lblTituloPass);

		lblNovaPassword_1 = new JLabel("Nova Password");
		lblNovaPassword_1.setForeground(Color.WHITE);
		lblNovaPassword_1.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNovaPassword_1.setVisible(false);
		lblNovaPassword_1.setBounds(19, 207, 234, 28);
		panelPasswords.add(lblNovaPassword_1);

		lblNovaPassword_2 = new JLabel("Confirmar nova Password");
		lblNovaPassword_2.setForeground(Color.WHITE);
		lblNovaPassword_2.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNovaPassword_2.setVisible(false);
		lblNovaPassword_2.setBounds(19, 272, 250, 28);
		panelPasswords.add(lblNovaPassword_2);

	}

	/**
	 * textFields for viewing and entering data 
	 */
	private void textFieldsSetup() {

		textFieldID = new JTextField();
		textFieldID.setForeground(Color.BLACK);
		textFieldID.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldID.setEditable(false);
		textFieldID.setColumns(10);
		textFieldID.setBounds(54, 103, 252, 30);
		panelDados.add(textFieldID);

		textFieldDadosNome = new JTextField();
		textFieldDadosNome.setForeground(Color.BLACK);
		textFieldDadosNome.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldDadosNome.setEditable(false);
		textFieldDadosNome.setColumns(10);
		textFieldDadosNome.setBounds(54, 168, 252, 30);
		panelDados.add(textFieldDadosNome);

		textFieldDadosNIF = new JTextField();
		textFieldDadosNIF.setForeground(Color.BLACK);
		textFieldDadosNIF.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldDadosNIF.setEditable(false);
		textFieldDadosNIF.setColumns(10);
		textFieldDadosNIF.setBounds(54, 231, 252, 30);
		panelDados.add(textFieldDadosNIF);

		textFieldDadosMorada = new JTextField();
		textFieldDadosMorada.setForeground(Color.BLACK);
		textFieldDadosMorada.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldDadosMorada.setEditable(false);
		textFieldDadosMorada.setColumns(10);
		textFieldDadosMorada.setBounds(54, 296, 252, 30);
		panelDados.add(textFieldDadosMorada);

		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBounds(17, 163, 252, 30);
		passwordField.setEditable(false);
		panelPasswords.add(passwordField);
		passwordField.setFont(new Font("Dubai Light", Font.PLAIN, 14));

		textFieldDadosLogin = new JTextField();
		textFieldDadosLogin.setForeground(Color.BLACK);
		textFieldDadosLogin.setBounds(17, 103, 252, 30);
		panelPasswords.add(textFieldDadosLogin);
		textFieldDadosLogin.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldDadosLogin.setEditable(false);
		textFieldDadosLogin.setColumns(10);

		passwordField_1 = new JPasswordField();
		passwordField_1.setForeground(Color.BLACK);
		passwordField_1.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		passwordField_1.setEditable(true);
		passwordField_1.setBounds(17, 231, 252, 30);
		panelPasswords.add(passwordField_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.setForeground(Color.BLACK);
		passwordField_2.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		passwordField_2.setEditable(true);
		passwordField_2.setBounds(17, 296, 252, 30);
		panelPasswords.add(passwordField_2);

	}

	/**
	 * Configures the interface for when the "Atualizar Dados" button is clicked. 
	 * The data fields are editable, the confirm and cancel buttons are visible
	 * the refresh button is hidden 
	 * @btAtualizarDados 
	 */
	protected void botaoAtualizaDados() {
		
		btAtualizarDados = new JButton("Atualizar Dados ");
		btAtualizarDados.setBounds(167, 341, 139, 30);
		panelDados.add(btAtualizarDados);
		btAtualizarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDados.setVisible(false);
				btCancelar.setVisible(true);
				btConfirmar.setVisible(true);

				textFieldID.setEnabled(false);
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
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btAtualizarDados.setFocusPainted(false);
	}

	/**
	 * Sets to when the change is confirmed. The data fields become non-editable again. 
	 * The confirm and cancel buttons are hidden and the Update Data button appears again.
	 * Validations for the data fields. 
	 */
	protected void botaoConfirmarDados() {
		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(54, 341, 120, 30);
		panelDados.add(btConfirmar);
		btConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				if(cliente != null) {

					Cliente clienteNovo = cliente;
					List<Cliente> todosClientes = null;

					try {
						todosClientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					String nome = clienteNovo.getNome();
					String morada = clienteNovo.getMorada();
					int nif = (int) clienteNovo.getNif();

					if(textFieldDadosNIF.getText().isBlank() || textFieldDadosNome.getText().isBlank() ||  textFieldDadosMorada.getText().isBlank()) {
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "Tem Campos por preencher!", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}

					char[] nomeEmArray = new char[textFieldDadosNome.getText().length()]; 

					for(int i = 0; i < nomeEmArray.length; i++) {
						nomeEmArray[i] = textFieldDadosNome.getText().charAt(i);
						if(nomeEmArray[i]==32) {
							continue;
						}
						if (nomeEmArray[i]>= 48 && nomeEmArray[i] <= 57 ) {
							JOptionPane.showMessageDialog(AreaCliente_MeusDados.this,
									"O Nome não pode conter números!", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}

					if(cliente.getNome().equalsIgnoreCase(textFieldDadosNome.getText())) {
						nomeAlterado = false;
					}else {
						nomeAlterado = true;
						nome = textFieldDadosNome.getText();
					}


					try {

						for(Cliente c : todosClientes) {
							if(cliente.getNif() != Integer.parseInt(textFieldDadosNIF.getText().trim())) {
								if(c.getNif() == Integer.parseInt(textFieldDadosNIF.getText().trim())){
									JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "NIF já se encontra em uso!", "Erro", JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
						}

						if(cliente.getNif() == Integer.parseInt(textFieldDadosNIF.getText().trim())) {
							nifAlterado = false;
						}else {
							nifAlterado = true;
							nif = Integer.parseInt( textFieldDadosNIF.getText().trim());
						}


					}catch( Exception ex ){
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "O NIF só pode conter números!", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(textFieldDadosNIF.getText().length() != 9) {
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "O NIF deve conter 9 digitos!");
						return;
					}

					if(cliente.getMorada().equalsIgnoreCase(textFieldDadosMorada.getText())) {
						moradaAlterado = false;
					}else {
						moradaAlterado = true;
						morada = textFieldDadosMorada.getText();
					}

					todosDadosValidos = true;

					if(todosDadosValidos) {

						if(nomeAlterado) {
							clienteNovo.setNome(nome);
						}

						if(nifAlterado) {
							clienteNovo.setNif(nif);
						} 

						if(moradaAlterado){
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

				textFieldID.setEnabled(true);
				textFieldDadosNome.setEditable(false);
				textFieldDadosNIF.setEditable(false);
				textFieldDadosMorada.setEditable(false);

				if(nomeAlterado || nifAlterado || moradaAlterado) {
					JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "Dados alterados com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
				}

				lblNovoNome.setVisible(false);
				lblNovaMorada.setVisible(false);
				lblNovoNIF.setVisible(false);

			}
		});

		btConfirmar.setForeground(Color.DARK_GRAY);
		btConfirmar.setVisible(false);
		btConfirmar.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btConfirmar.setFocusPainted(false);
	}

	/**
	 * Configuration for the cancel button. 
	 * The confirm and cancel buttons are hidden and the Update Data button reappears.
	 * Does not make any changes.
	 */
	protected void botaoCancelar() {
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

				textFieldID.setEnabled(true);
				textFieldDadosNome.setEditable(false);
				textFieldDadosNIF.setEditable(false);
				textFieldDadosMorada.setEditable(false);

				lblNovoNome.setVisible(false);
				lblNovaMorada.setVisible(false);
				lblNovoNIF.setVisible(false);

			}
		});
		btCancelar.setForeground(Color.DARK_GRAY);
		btCancelar.setVisible(false);
		btCancelar.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btCancelar.setFocusPainted(false);
	}

	/**
	 * Updates the password. When clicked, 4 labels are visible for data entry and two buttons, confirm and cancel. 
	 */
	protected void botaoAtualizaDadosPassword() {
		btAtualizarDadosPass = new JButton("Atualizar Login");
		btAtualizarDadosPass.setForeground(Color.DARK_GRAY);
		btAtualizarDadosPass.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btAtualizarDadosPass.setFocusPainted(false);
		btAtualizarDadosPass.setBounds(130, 206, 139, 30);
		btAtualizarDadosPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDadosPass.setVisible(false);
				btCancelarPass.setVisible(true);
				btConfirmarPass.setVisible(true);

				textFieldDadosLogin.setEditable(true);
				passwordField.setEditable(true);

				lblNovaPassword_1.setVisible(true);
				passwordField_1.setVisible(true);
				lblNovaPassword_2.setVisible(true);
				passwordField_2.setVisible(true);

				lblPassword.setText("Password Atual");
				lblDadosLogin.setText("Novo Username");

				lblNovaPass.setVisible(true);
				lblNovaPass_2.setVisible(true);
				lblNovaPassConfirm.setVisible(true);

				textFieldDadosLogin.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(textFieldDadosLogin.getText().isBlank()) {
							lblNovoLogin.setVisible(true);
						}else {
							lblNovoLogin.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovoLogin.setVisible(false);
					}
				});

				passwordField.setText("");
				passwordField.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(passwordField.getText().isBlank()) {
							lblNovaPass.setVisible(true);
						}else {
							lblNovaPass.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovaPass.setVisible(false);

					}
				});

				passwordField_1.setText("");
				passwordField_1.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(passwordField_1.getText().isBlank()) {
							lblNovaPass_2.setVisible(true);
						}else {
							lblNovaPass_2.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovaPass_2.setVisible(false);

					}
				});

				passwordField_2.setText("");
				passwordField_2.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if(passwordField_2.getText().isBlank()) {
							lblNovaPassConfirm.setVisible(true);
						}else {
							lblNovaPass_2.setVisible(false);
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						lblNovaPassConfirm.setVisible(false);

					}
				});

			}
		});
		panelPasswords.add(btAtualizarDadosPass);
	}

	/**
	 * Cancels the password change.
	 * The labels for entering new data and the Confirm and Cancel buttons are hidden. 
	 * The labels with the username and password are visible, as well as the Update Login button
	 */
	protected void botaoCancelarPassword() {
		btCancelarPass = new JButton("Cancelar");
		btCancelarPass.setForeground(Color.DARK_GRAY);
		btCancelarPass.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btCancelarPass.setFocusPainted(false);
		btCancelarPass.setVisible(false);
		btCancelarPass.setBounds(149, 341, 120, 30);
		btCancelarPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAtualizarDadosPass.setVisible(true);
				btCancelarPass.setVisible(false);
				btConfirmarPass.setVisible(false);

				lblNovaPassword_1.setVisible(false);
				passwordField_1.setVisible(false);
				lblNovaPassword_2.setVisible(false);
				passwordField_2.setVisible(false);

				lblNovaPass_2.setVisible(false);
				lblNovaPassConfirm.setVisible(false);
				lblNovoLogin.setVisible(false);
				lblNovaPass.setVisible(false);

				lblPassword.setText("Password");
				lblDadosLogin.setText("Username");

				if(cliente != null) {
					textFieldDadosLogin.setText(cliente.getLogin());
					passwordField.setText(PasswordEncryption.get_SHA_512_SecurePassword(cliente.getPassword()).substring(0, 8));
				}
				textFieldDadosLogin.setEditable(false);
				passwordField.setEditable(false);
			}


		});
		panelPasswords.add(btCancelarPass);
	}

	/**
	 * Confirms the password change. 
	 * Validates the entered data.
	 * The labels for entering new data and the Confirm and Cancel buttons are hidden. 
	 * The labels with the username and password are visible, as well as the Update Login button
	 * 
	 */
	protected void botaoConfirmarPassword() {
		btConfirmarPass = new JButton("Confirmar");
		btConfirmarPass.setForeground(Color.DARK_GRAY);
		btConfirmarPass.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btConfirmarPass.setFocusPainted(false);
		btConfirmarPass.setBounds(17, 341, 120, 30);
		btConfirmarPass.setVisible(false);
		btConfirmarPass.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {


				if(cliente != null) {

					Cliente clienteNovo = cliente;
					List<Cliente> todosClientes = null;

					try {
						todosClientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					String login = clienteNovo.getLogin();
					String password = clienteNovo.getPassword();

					if(textFieldDadosLogin.getText().isBlank() || passwordField.getText().isBlank() ||  passwordField_1.getText().isBlank() || passwordField_2.getText().isBlank() ) {
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "Tem Campos por preencher!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					for(Cliente c : todosClientes) {
						if(!login.equalsIgnoreCase(textFieldDadosLogin.getText().trim())) {
							if(c.getLogin().equalsIgnoreCase(textFieldDadosLogin.getText().trim())){
								JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "Login já se encontra em uso!", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}

					if(login.equalsIgnoreCase(textFieldDadosLogin.getText().trim())) {
						loginAlterado = false;
					}else {
						loginAlterado = true;
						login = textFieldDadosLogin.getText().trim();
					}

					if(password.equals(PasswordEncryption.get_SHA_512_SecurePassword(passwordField.getText()))) {
						passwordCorreta = true;
					}else {
						passwordCorreta = false;
						JOptionPane.showMessageDialog(AreaCliente_MeusDados.this, "Password Incorreta!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if(passwordField_1.getText().equals(passwordField_2.getText())) {
						passwordNovaIgual = true;
					}else {
						passwordNovaIgual = false;
						JOptionPane.showMessageDialog(AreaCliente_MeusDados.this, "Nova Password e Confirmar Password não estão iguais!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					todosDadosValidos = true;

					if(todosDadosValidos) {

						if(loginAlterado) {
							clienteNovo.setLogin(login);
						} 

						if(passwordCorreta && passwordNovaIgual) {
							password = passwordField_1.getText();
						}

						try {
							GestorDeDAO.getGestorDeDAO().editarClienteDadosLoginEPassword(clienteNovo, password);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

					if(loginAlterado || (passwordCorreta && passwordNovaIgual)) {
						JOptionPane.showMessageDialog( AreaCliente_MeusDados.this, "Dados alterados com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
					}
				}

				btAtualizarDadosPass.setVisible(true);
				btCancelarPass.setVisible(false);
				btConfirmarPass.setVisible(false);

				lblNovaPassword_1.setVisible(false);
				passwordField_1.setVisible(false);
				lblNovaPassword_2.setVisible(false);
				passwordField_2.setVisible(false);

				lblNovaPass_2.setVisible(false);
				lblNovaPassConfirm.setVisible(false);
				lblNovoLogin.setVisible(false);
				lblNovaPass.setVisible(false);

				lblPassword.setText("Password");
				lblDadosLogin.setText("Username");

				passwordField.setText(PasswordEncryption.get_SHA_512_SecurePassword(cliente.getPassword()).substring(0, 8));

				textFieldDadosLogin.setEditable(false);
				passwordField.setEditable(false);
			}

		});
		panelPasswords.add(btConfirmarPass);
	}

	/**
	 * 
	 * @return main panel
	 */
	public JPanel returnAreaClienteMeusDados() {
		return (JPanel) getContentPane();
	}

	/**
	 * Method that sends the username across the various panels.
	 * @param username
	 */
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
				passwordField.setText(PasswordEncryption.get_SHA_512_SecurePassword(cliente.getPassword()).substring(0, 8));
			}

			btAtualizarDadosPass.setVisible(true);
			btCancelarPass.setVisible(false);
			btConfirmarPass.setVisible(false);
			btAtualizarDados.setVisible(true);
			btCancelar.setVisible(false);
			btConfirmar.setVisible(false);

			lblNovaPassword_1.setVisible(false);
			passwordField_1.setVisible(false);
			lblNovaPassword_2.setVisible(false);
			passwordField_2.setVisible(false);

			lblNovaPass_2.setVisible(false);
			lblNovaPassConfirm.setVisible(false);
			lblNovoLogin.setVisible(false);
			lblNovaPass.setVisible(false);

			lblPassword.setText("Password");
			lblDadosLogin.setText("Username");

			textFieldDadosNome.setEditable(false);
			textFieldDadosNIF.setEditable(false);
			textFieldDadosMorada.setEditable(false);

			lblNovoNome.setVisible(false);
			lblNovaMorada.setVisible(false);
			lblNovoNIF.setVisible(false);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills in the fields with the customer's previous data
	 * @param clienteAntigo
	 */
	private void popularTextFields(Cliente clienteAntigo) {
		textFieldDadosNome.setText(clienteAntigo.getNome()+ "");
		textFieldDadosNIF.setText(clienteAntigo.getNif() + "");
		textFieldDadosMorada.setText(clienteAntigo.getMorada());
	}

}//end class
