package guiComponentes.gestorCliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

public class CriarClienteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNIF, textFieldMorada, textFieldLogin, textFieldPacote, textFieldNome ;
	private JCheckBox checkBoxAtivo;
	private JPasswordField textFieldPassword;
	private GUI_gestor_cliente clientePesquisaApp;
	private Cliente clienteAntigo;
	private boolean modoEditar = false;
	private String username;



	public static void main(String[] args) {
		try {
			CriarClienteDialog dialog = new CriarClienteDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CriarClienteDialog(GUI_gestor_cliente clientePesquisaApp, String username) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
		this.username = username;
	}

	public CriarClienteDialog(GUI_gestor_cliente clientePesquisaApp, Cliente clienteAntigo, boolean modoEditar, String username) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
		this.clienteAntigo = clienteAntigo;
		this.modoEditar = modoEditar;
		this.username = username;

		if(modoEditar) {
			setTitle("Editar Cliente");
			popularTextFields(clienteAntigo);
		}
	}

	private void popularTextFields(Cliente clienteAntigo2) {
		textFieldNome.setText(clienteAntigo2.getNome()+ "");
		textFieldNIF.setText(clienteAntigo2.getNif() + "");
		textFieldMorada.setText(clienteAntigo2.getMorada());
		textFieldLogin.setText(clienteAntigo2.getLogin());
		textFieldPassword.setText(clienteAntigo2.getPassword()); // .substring(0,8)
		checkBoxAtivo.setSelected(clienteAntigo2.isAtivo());

	}

	public CriarClienteDialog() {

		setBounds(500, 300, 440, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.window);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Cliente");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(9, 11, 82, 27);
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNome);
		}
		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setBounds(9, 51, 82, 27);
			lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNif);
		}
		{
			JLabel lblMorada = new JLabel("Morada");
			lblMorada.setBounds(9, 89, 82, 27);
			lblMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblMorada);
		}
		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setBounds(9, 125, 82, 27);
			lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblLogin);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setBounds(101, 11, 290, 27);
			textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome);
		}
		{
			textFieldNIF = new JTextField();
			textFieldNIF.setBounds(101, 49, 290, 27);
			textFieldNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNIF.setColumns(10);
			contentPanel.add(textFieldNIF);
		}
		{
			textFieldMorada = new JTextField();
			textFieldMorada.setBounds(101, 87, 290, 27);
			textFieldMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldMorada.setColumns(10);
			contentPanel.add(textFieldMorada);
		}
		{
			textFieldLogin = new JTextField();
			textFieldLogin.setBounds(101, 125, 290, 27);
			textFieldLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldLogin.setColumns(10);
			contentPanel.add(textFieldLogin);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(9, 163, 82, 27);
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblPassword);
		}
		{
			textFieldPassword = new JPasswordField();
			textFieldPassword.setBounds(101, 163, 290, 27);
			textFieldPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldPassword.setColumns(10);
			contentPanel.add(textFieldPassword);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 245, 424, 41);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(SystemColor.window);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));


			{
				JButton okButton = new JButton("Confirmar");
				okButton.setForeground(SystemColor.activeCaptionText);
				okButton.setBackground(SystemColor.activeCaption);
				okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
				okButton.setFocusPainted(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if (textFieldNome.getText().isBlank() || textFieldMorada.getText().isBlank() || textFieldLogin.getText().isBlank() || textFieldPassword.getPassword().length == 0) {
							JOptionPane.showMessageDialog( CriarClienteDialog.this, "Todos os dados têm de ser preenchidos!");
							return;
						}
						if( textFieldNIF.getText().isBlank()) {
							JOptionPane.showMessageDialog( CriarClienteDialog.this, "O NIF tem que estar preenchido!");
							return;
						}
						try {
							Integer.parseInt( textFieldNIF.getText() );
						}
						catch( Exception ex ){
							JOptionPane.showMessageDialog( CriarClienteDialog.this, "A NIF tem de ser um inteiro!");
							return;
						}

						gravarCliente();

					}
				});
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setBackground(SystemColor.activeCaption);
				cancelButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
				cancelButton.setFocusPainted(false);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				checkBoxAtivo = new JCheckBox("Ativo");
				checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
				checkBoxAtivo.setBackground(Color.WHITE);
				checkBoxAtivo.setBounds(159, 212, 86, 23);
				contentPanel.add(checkBoxAtivo);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();

					}
				});
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void gravarCliente() {
		String nome = textFieldNome.getText();
		int nif = Integer.parseInt(textFieldNIF.getText());
		String morada = textFieldMorada.getText();
		String login = textFieldLogin.getText();
		String pass = textFieldPassword.getText();
		boolean ativo = checkBoxAtivo.isSelected();


		Cliente cliente = null;

		if (modoEditar) {
			cliente = clienteAntigo;

			cliente.setNif(nif);
			cliente.setNome(nome);
			cliente.setMorada(morada);
			cliente.setLogin(login);
			cliente.setPassword(pass);
			cliente.setAtivo(ativo);

		} else {
			cliente = new Cliente( nome, nif, morada, login, pass, ativo);
		}

		try {
			Funcionario funcionario = null;
			if (modoEditar) {
				funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(username);
				GestorDeDAO.getGestorDeDAO().editarCliente(cliente, funcionario);
				clientePesquisaApp.refreshClienteTable();
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Cliente Editado com sucesso!", "Cliente Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				try {

					funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(username);
					GestorDeDAO.getGestorDeDAO().criarCliente(cliente, funcionario);
					clientePesquisaApp.refreshClienteTable();
					JOptionPane.showMessageDialog(clientePesquisaApp,
							"Cliente Criado com sucesso!", "Cliente Criado",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {

				}
			}
			setVisible(false);
			dispose();


		} catch (Exception exc) {
			JOptionPane.showMessageDialog(clientePesquisaApp,
					"Error a criar cliente " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
