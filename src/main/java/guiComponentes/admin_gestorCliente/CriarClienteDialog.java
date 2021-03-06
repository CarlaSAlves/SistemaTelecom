package guiComponentes.admin_gestorCliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import guiComponentes.cliente_pessoal.AreaCliente_MeusDados;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

public class CriarClienteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNIF, textFieldMorada, textFieldLogin, textFieldNome ;
	private JPasswordField textFieldPassword;
	private GUI_gestor_cliente clientePesquisaApp;
	private Cliente clienteAntigo;
	private boolean modoEditar = false;
	private String username;
	private JLabel lblPassword, lblAuxiliar;

	/**
	 * 
	 * Contructor create client dialog
	 * @param clientePesquisaApp
	 * @param username
	 */

	public CriarClienteDialog(GUI_gestor_cliente clientePesquisaApp, String username) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
		this.username = username;
	}

	/**
	 *
	 * fills the textfields with previous values from client before editing
	 * @param clienteAntigo2
	 */
	private void popularTextFields(Cliente clienteAntigo2) {
		textFieldNome.setText(clienteAntigo2.getNome()+ "");
		textFieldNIF.setText(clienteAntigo2.getNif() + "");
		textFieldMorada.setText(clienteAntigo2.getMorada());
		textFieldLogin.setText(clienteAntigo2.getLogin());
	}

	/**
	 * 
	 * constructor that initializes with a method that sets up the window and the initilize method
	 * which contains all the methods and elements that compose the page
	 */
	public CriarClienteDialog() {

		setTitle("Novo Cliente");
		setBounds(500, 300, 440, 329);
		contentPanel.setLayout(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.window);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		inicialize();

	}

	/**
	 * 
	 * contains the body of the page
	 */
	private void inicialize() {

		/**
		 *  Labels e textField of the page:
		 *  Nome
		 *  NIF
		 *  Morada
		 *  Login
		 *  Password
		 */

		labelsSetup();
		textFieldSetup();

		/**
		 *
		 * confirmation panel
		 * confirmation button
		 * cancellation button
		 */
		painelConfirmacaoSetup();

	}

	/**
	 *
	 * configs the window labels
	 * @lblNome
	 * @lblNIF
	 * @lblMorada
	 * @lblLogin
	 * @lblPassword
	 * @lblAuxiliar - label inserida no textField da password // label inserted in the password textfield
	 * 
	 */
	private void labelsSetup() {
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(9, 11, 82, 27);
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblNome);

		JLabel lblNif = new JLabel("NIF");
		lblNif.setBounds(9, 51, 82, 27);
		lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblNif);

		JLabel lblMorada = new JLabel("Morada");
		lblMorada.setBounds(9, 89, 82, 27);
		lblMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblMorada);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(9, 125, 82, 27);
		lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblLogin);

		lblPassword = new JLabel( "Password");
		lblPassword.setBounds(9, 163, 82, 27);
		lblPassword.setFont(new Font("Arial Nova Light", Font.PLAIN, 13));
		contentPanel.add(lblPassword);

		lblAuxiliar = new JLabel();
		lblAuxiliar.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		lblAuxiliar.setForeground(Color.LIGHT_GRAY);
		lblAuxiliar.setBounds(111, 163, 280, 27);
		lblAuxiliar.setVisible(false);
		contentPanel.add(lblAuxiliar);

	}

	/**
	 * Configuração das textFields 
	 * textfield configs
	 * @textFieldNome
	 * @textFieldNIF
	 * @textFieldMorada
	 * @textFieldLogin
	 * @textFieldPassword
	 * 
	 */
	private void textFieldSetup() {
		textFieldNome = new JTextField();
		textFieldNome.setBounds(101, 11, 290, 27);
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldNome.setColumns(10);
		contentPanel.add(textFieldNome);

		textFieldNIF = new JTextField();
		textFieldNIF.setBounds(101, 49, 290, 27);
		textFieldNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldNIF.setColumns(10);
		contentPanel.add(textFieldNIF);

		textFieldMorada = new JTextField();
		textFieldMorada.setBounds(101, 87, 290, 27);
		textFieldMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldMorada.setColumns(10);
		contentPanel.add(textFieldMorada);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(101, 125, 290, 27);
		textFieldLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldLogin.setColumns(10);
		contentPanel.add(textFieldLogin);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(101, 163, 290, 27);
		textFieldPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldPassword.setColumns(10);
		contentPanel.add(textFieldPassword);

	}

	/**
	 * 
	 * configs the buttons at the bottom of the page
	 * 
	 * @buttonPane - confirmation panel
	 * 
	 * @okButton - confirmation button that starts the validations of the fields and saves
	 *  the client in the DB
	 * @cancelButton -  cancellation button, cancel the action and closes the window
	 * 
	 */
	protected void painelConfirmacaoSetup() {

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 245, 424, 41);
		contentPanel.add(buttonPane);
		buttonPane.setBackground(SystemColor.window);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton okButton = new JButton("Confirmar");
		okButton.setForeground(SystemColor.activeCaptionText);
		okButton.setBackground(SystemColor.activeCaption);
		okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		okButton.setFocusPainted(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Validações dos campos de texto no modo editar
				if(modoEditar) {

					if (textFieldNome.getText().isBlank() || textFieldMorada.getText().isBlank() || textFieldLogin.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "Todos os dados têm de ser preenchidos!");
						return;
					}
					if( textFieldNIF.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "O NIF tem que estar preenchido!");
						return;
					}
					char[] nomeEmArray = new char[textFieldNome.getText().length()]; 

					for(int i = 0; i < nomeEmArray.length; i++) {
						nomeEmArray[i] = textFieldNome.getText().charAt(i);
						if(nomeEmArray[i]==32) {
							continue;
						}
						if (nomeEmArray[i]>= 48 && nomeEmArray[i] <= 57 ) {
							JOptionPane.showMessageDialog(CriarClienteDialog.this,
									"O Nome não pode conter números!", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					try {
						Integer.parseInt( textFieldNIF.getText() );
					}
					catch( Exception ex ){
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "O NIF só pode conter números!");
						return;
					}

					List<Cliente> listaClientes = null;
					try {
						listaClientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
					} catch (Exception e) {
						e.printStackTrace();
					}

					gravarCliente();

					// Validações dos campos de texto no modo criar
				}else {

					if (textFieldNome.getText().isBlank() || textFieldMorada.getText().isBlank() || textFieldLogin.getText().isBlank() || textFieldPassword.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "Todos os dados têm de ser preenchidos!");
						return;
					}
					if( textFieldNIF.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "O NIF tem que estar preenchido!");
						return;
					}
					char[] nomeEmArray = new char[textFieldNome.getText().length()]; 

					for(int i = 0; i < nomeEmArray.length; i++) {
						nomeEmArray[i] = textFieldNome.getText().charAt(i);
						if(nomeEmArray[i]==32) {
							continue;
						}
						if (nomeEmArray[i]>= 48 && nomeEmArray[i] <= 57 ) {
							JOptionPane.showMessageDialog(CriarClienteDialog.this,
									"O Nome não pode conter números!", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					try {
						Integer.parseInt( textFieldNIF.getText() );
					}
					catch( Exception ex ){
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "O NIF só pode conter números!");
						return;
					}
					
					if(textFieldNIF.getText().length() != 9) {
						JOptionPane.showMessageDialog( CriarClienteDialog.this, "O NIF deve conter 9 digitos!");
						return;
					}
					// verifica se já existe um cliente com o mesmo login
					List<Cliente> listaClientes = null;
					try {
						listaClientes = GestorDeDAO.getGestorDeDAO().getAllClientes();
					} catch (Exception e) {

					}
					for( Cliente c : listaClientes) {
						if(c.getLogin().equalsIgnoreCase(textFieldLogin.getText())) {
							JOptionPane.showMessageDialog( CriarClienteDialog.this, "Login Invalido, já em uso!");
							return;
						}
					}							

					gravarCliente();
				}
			}
		});

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setBackground(SystemColor.activeCaption);
		cancelButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		cancelButton.setFocusPainted(false);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);


		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
	}



	/**
	 * configs the window if actioned by the edit button - presents the editable fields
	 * @param clientePesquisaApp
	 * @param clienteAntigo -  retrieves the updated values from the client
	 * @param modoEditar - actions by the edit button
	 * @param username
	 */
	public CriarClienteDialog(GUI_gestor_cliente clientePesquisaApp, Cliente clienteAntigo, boolean modoEditar, String username) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
		this.clienteAntigo = clienteAntigo;
		this.modoEditar = modoEditar;
		this.username = username;

		//edit mode, actioned by the edit button

		if(modoEditar) {
			setTitle("Editar Cliente");
			lblPassword.setText("Nova Password");
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 11));
			popularTextFields(clienteAntigo);
			lblAuxiliar.setText("Não preencher se desejar manter a password atual");
			lblAuxiliar.setVisible(true);
			textFieldPassword.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {
					if(textFieldPassword.getText().isBlank()) {
						lblAuxiliar.setVisible(true);
					}else {
						lblAuxiliar.setVisible(false);
					}
				}

				@Override
				public void focusGained(FocusEvent e) {
					lblAuxiliar.setVisible(false);

				}
			});
		}
	}

	/**
	
	 * Method that connects gestorDAO and saves the data of the client in the DB
	 */
	@SuppressWarnings("deprecation")
	private void gravarCliente() {
		String pass;
		String nome = textFieldNome.getText();
		int nif = Integer.parseInt(textFieldNIF.getText());
		String morada = textFieldMorada.getText();
		String login = textFieldLogin.getText();
		if(textFieldPassword.getText().isBlank()) {
			pass = null;
		}else {
			pass = textFieldPassword.getText();
		}
		boolean ativo = true;

		Cliente cliente = null;

		if (modoEditar) {
			cliente = clienteAntigo;

			cliente.setNif(nif);
			cliente.setNome(nome);
			cliente.setMorada(morada);
			cliente.setLogin(login);
			if(textFieldPassword.getText().isBlank()) {
				cliente.setPassword(clienteAntigo.getPassword());
			}else {
				cliente.setPassword(pass);
			}
			cliente.setAtivo(clienteAntigo.isAtivo());

		} else {
			cliente = new Cliente( nome, nif, morada, login, pass, ativo);
		}

		try {
			Funcionario funcionario = null;
			if (modoEditar) {

				funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
				GestorDeDAO.getGestorDeDAO().editarCliente(cliente, funcionario, pass);
				clientePesquisaApp.refreshClienteTable();
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Cliente Editado com sucesso!", "Cliente Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(username);
				GestorDeDAO.getGestorDeDAO().criarCliente(cliente, funcionario);
				clientePesquisaApp.refreshClienteTable();
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Cliente Criado com sucesso!", "Cliente Criado",
						JOptionPane.INFORMATION_MESSAGE);		
			}
			setVisible(false);
			dispose();


		} catch (Exception exc) {

			if (modoEditar) {
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Erro a editar cliente " + exc.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Erro a criar cliente " + exc.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}
} // end class
