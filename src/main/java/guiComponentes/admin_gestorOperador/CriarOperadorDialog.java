package guiComponentes.admin_gestorOperador;

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

import servico.GestorDeDAO;
import standard_value_object.Funcionario;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;

import guiComponentes.GUI_total;
import guiComponentes.admin_gestorCliente.CriarClienteDialog;

import java.awt.Color;


public class CriarOperadorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome, textFieldNIF, textFieldLogin;
	private JPasswordField textFieldPassword;
	private GUI_gestor_operador operadorPesquisaApp;
	private Funcionario funcionarioAntigo;
	private boolean modoEditar = false;
	private JLabel lblPassword, lblAuxiliar;



	public CriarOperadorDialog(GUI_gestor_operador operadorPesquisaApp ) {
		this();
		this.operadorPesquisaApp = operadorPesquisaApp;
	}
	/**
	 * Preenche as textFields com os valores anteriores do operador, antes de editar.
	 * @param funcionarioTemp
	 */
	private void popularTextFields(Funcionario funcionarioTemp) {
		textFieldNome.setText(funcionarioTemp.getNome()+ "");
		textFieldNIF.setText(funcionarioTemp.getNif() + "");
		textFieldLogin.setText(funcionarioTemp.getLogin());
	}

	/**
	 * Construtor que inicia com o método que configura a janela e o método inicialize, 
	 * que contém todos os métodos e elementos que compõem a página 
	 */
	public CriarOperadorDialog() {

		setTitle("Novo Operador");
		contentPanel.setLayout(null);
		setBounds(500, 300, 418, 275);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.text);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		inicialize();

	}


	/**
	 * Contém o corpo da página
	 */
	private void inicialize() {

		/**
		 *  Labels e textField da página:
		 * 
		 *  Nome
		 *  NIF
		 *  Login
		 *  Password
		 */
		labelsSetup();
		textFieldSetup();

		/**
		 * Painel de Confirmação 
		 * Botão Confirmar
		 * Botão Cancelar
		 */

		painelConfirmacaoSetup();

	}

	/**
	 * Configuração das labels da janela 
	 * @lblNome
	 * @lblNIF
	 * @lblLogin
	 * @lblPassword
	 * @lblAuxiliar - label inserida no textField da password
	 */
	private void labelsSetup() {
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(17, 11, 70, 27);
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblNome);

		JLabel lblNif = new JLabel("NIF");
		lblNif.setBounds(17, 49, 70, 27);
		lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblNif);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(17, 87, 70, 27);
		lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblLogin);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(17, 125, 70, 27);
		lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblPassword);

		lblAuxiliar = new JLabel();
		lblAuxiliar.setFont(new Font("Dubai Light", Font.PLAIN, 10));
		lblAuxiliar.setForeground(Color.LIGHT_GRAY);
		lblAuxiliar.setBounds(130, 132, 261, 14);
		lblAuxiliar.setVisible(false);
		contentPanel.add(lblAuxiliar);

	}
	
	/**
	 * Configuração das textFields 
	 * @textFieldNome
	 * @textFieldNIF
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

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(101, 87, 290, 27);
		textFieldLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldLogin.setColumns(10);
		contentPanel.add(textFieldLogin);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(101, 125, 290, 27);
		textFieldPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldPassword.setColumns(10);
		contentPanel.add(textFieldPassword);

	}
	/**
	 * Configura os botões do rodapé da página 
	 * 
	 * @buttonPane - painel de confirmação
	 * @okButton - botão de confirmação, aciona as validações de campos e 
	 * o método que grava os dados do operador na base de dados.
	 * @cancelButton - botão cancelar, cancela a ação e fecha a janela.
	 */
	private void painelConfirmacaoSetup() {
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 189, 401, 44);
		contentPanel.add(buttonPane);
		buttonPane.setBackground(SystemColor.text);
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
					if (textFieldNome.getText().isBlank() || textFieldLogin.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarOperadorDialog.this, "Todos os dados têm de ser preenchidos!");
						return;
					}
					if( textFieldNIF.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarOperadorDialog.this, "O NIF tem que estar preenchido!");
						return;
					}
					char[] nomeEmArray = new char[textFieldNome.getText().length()]; 

					for(int i = 0; i < nomeEmArray.length; i++) {
						nomeEmArray[i] = textFieldNome.getText().charAt(i);
						if(nomeEmArray[i]==32) {
							continue;
						}
						if (nomeEmArray[i]<65 || nomeEmArray[i] > 122 ) {
							JOptionPane.showMessageDialog(CriarOperadorDialog.this,
									"O Nome não pode conter números!", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					try {
						Integer.parseInt( textFieldNIF.getText() );
					}
					catch( Exception ex ){
						JOptionPane.showMessageDialog( CriarOperadorDialog.this, "O NIF tem de ser um inteiro!");
						return;
					}


					gravarOperador();
				}
				
				// Validações dos campos de texto no modo criar
				else {
					if (textFieldNome.getText().isBlank() || textFieldLogin.getText().isBlank() || textFieldPassword.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarOperadorDialog.this, "Todos os dados têm de ser preenchidos!");
						return;
					}
					if( textFieldNIF.getText().isBlank()) {
						JOptionPane.showMessageDialog( CriarOperadorDialog.this, "O NIF tem que estar preenchido!");
						return;
					}
					char[] nomeEmArray = new char[textFieldNome.getText().length()]; 

					for(int i = 0; i < nomeEmArray.length; i++) {
						nomeEmArray[i] = textFieldNome.getText().charAt(i);
						if(nomeEmArray[i]==32) {
							continue;
						}
						if (nomeEmArray[i]<65 || nomeEmArray[i] > 122 ) {
							JOptionPane.showMessageDialog(CriarOperadorDialog.this,
									"O Nome não pode conter números!", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					try {
						Integer.parseInt( textFieldNIF.getText() );
					}
					catch( Exception ex ){
						JOptionPane.showMessageDialog( CriarOperadorDialog.this, "O NIF tem de ser um inteiro!");
						return;
					}

					List<Funcionario> listaOperadores = null;
					try {
						listaOperadores = GestorDeDAO.getGestorDeDAO().getAllFuncionarioOperador();
					} catch (Exception e) {
						e.printStackTrace();
					}
					// verifica se já existe um operador com o mesmo login
					for( Funcionario f : listaOperadores) {
						if(f.getLogin().equalsIgnoreCase(textFieldLogin.getText())) {
							JOptionPane.showMessageDialog( CriarOperadorDialog.this, "Login Invalido, ja em uso!");
							return;
						}
					}	

					gravarOperador();

				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		{
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

	}

	/**
	 * Configuração da janela se for acionada pelo botão editar - apresenta os campos editáveis.
	 * @param operadorPesquisaApp
	 * @param funcionarioTemp - vai buscar os valores atuais do operador
	 * @param modoEditar - acionado pelo botão editar
	 */
	public CriarOperadorDialog(GUI_gestor_operador operadorPesquisaApp, Funcionario funcionarioTemp, boolean modoEditar ) {
		this();
		this.operadorPesquisaApp = operadorPesquisaApp;
		this.funcionarioAntigo = funcionarioTemp;
		this.modoEditar = modoEditar;

		// modo editar, accionado pelo clique no botão "editar"
		if(modoEditar) {
			setTitle("Editar Operador");
			popularTextFields(funcionarioTemp);
			lblPassword.setText("Nova Password");
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 11));
			lblAuxiliar.setText("Nao preencher se desejar manter a password atual");
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
	 * Método que conecta ao gestor DAO e grava os dados do operador na base de dados
	 */
	@SuppressWarnings("deprecation")
	private void gravarOperador() {
		String pass;
		String nome = textFieldNome.getText();
		int nif = Integer.parseInt(textFieldNIF.getText());
		String login = textFieldLogin.getText();
		if(textFieldPassword.getText().isBlank()) {
			pass = null;
		}else {
			pass = textFieldPassword.getText();
		}
		boolean ativo = true; 

		Funcionario funcionario = null;

		if (modoEditar) {
			funcionario = funcionarioAntigo;

			funcionario.setNif(nif);
			funcionario.setNome(nome);
			funcionario.setLogin(login);
			if(textFieldPassword.getText().isBlank()) {
				funcionario.setPassword(funcionarioAntigo.getPassword());
			}else {
				funcionario.setPassword(pass);
			}
			funcionario.setAtivo(funcionarioAntigo.isAtivo());

		} else {
			funcionario = new Funcionario(nome, nif, login, pass, ativo, 2);
		}

		try {
			if (modoEditar) {
				Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
				GestorDeDAO.getGestorDeDAO().editarFuncionario(funcionario, admin, pass);
				operadorPesquisaApp.refreshOperadorTable();
				JOptionPane.showMessageDialog(operadorPesquisaApp,
						"Operador Editado com sucesso!", "Operador Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
				GestorDeDAO.getGestorDeDAO().criarFuncionario(funcionario, admin);
				operadorPesquisaApp.refreshOperadorTable();
				JOptionPane.showMessageDialog(operadorPesquisaApp,
						"Operador Criado com sucesso!", "Operador Criado",
						JOptionPane.INFORMATION_MESSAGE);
			}

			setVisible(false);
			dispose();


		} catch (Exception exc) {
			JOptionPane.showMessageDialog(operadorPesquisaApp,
					"Error a criar operador " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
} // end class
