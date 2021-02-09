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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class CriarClienteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNIF;
	private JTextField textFieldMorada;
	private JTextField textFieldLogin;
	private JPasswordField textFieldPassword;
	private JTextField textFieldPacote;
	private JCheckBox checkBoxAtivo;
	private GUI_gestor_cliente clientePesquisaApp;
	private Cliente clienteAntigo;
	private boolean modoEditar = false;
	private JTextField textFieldNome;
	private String username;

	private Font font = new Font("Dubai Light", Font.PLAIN, 17);


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
			if (clienteAntigo.isAtivo())
			checkBoxAtivo.setVisible(false);
			
		}
	}

	private void popularTextFields(Cliente clienteAntigo2) {
		textFieldNome.setText(clienteAntigo2.getNome()+ "");
		textFieldNIF.setText(clienteAntigo2.getNif() + "");
		textFieldMorada.setText(clienteAntigo2.getMorada());
		textFieldLogin.setText(clienteAntigo2.getLogin());
		textFieldPassword.setText(clienteAntigo2.getPassword()); // .substring(0,8)
		textFieldPacote.setText(clienteAntigo2.getId_pacote_cliente()+ "");
		checkBoxAtivo.setSelected(clienteAntigo2.isAtivo());

	}

	public CriarClienteDialog() {

		setBounds(500, 300, 417, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.window);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Cliente");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(9, 33, 82, 23);
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNome);
		}
		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setBounds(9, 61, 82, 23);
			lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNif);
		}
		{
			JLabel lblMorada = new JLabel("Morada");
			lblMorada.setBounds(9, 91, 82, 23);
			lblMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblMorada);
		}
		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setBounds(9, 121, 82, 23);
			lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblLogin);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setBounds(101, 31, 290, 27);
			textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome);
		}
		{
			textFieldNIF = new JTextField();
			textFieldNIF.setBounds(101, 61, 290, 27);
			textFieldNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNIF.setColumns(10);
			contentPanel.add(textFieldNIF);
		}
		{
			textFieldMorada = new JTextField();
			textFieldMorada.setBounds(101, 91, 290, 27);
			textFieldMorada.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldMorada.setColumns(10);
			contentPanel.add(textFieldMorada);
		}
		{
			textFieldLogin = new JTextField();
			textFieldLogin.setBounds(101, 121, 290, 27);
			textFieldLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldLogin.setColumns(10);
			contentPanel.add(textFieldLogin);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(9, 153, 82, 23);
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblPassword);
		}
		{
			textFieldPassword = new JPasswordField();
			textFieldPassword.setBounds(101, 153, 290, 27);
			textFieldPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldPassword.setColumns(10);
			contentPanel.add(textFieldPassword);
		}
		{
			JLabel lblIdpacote = new JLabel("ID Pacote");
			lblIdpacote.setBounds(9, 183, 82, 23);
			lblIdpacote.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblIdpacote);
		}
		{
			textFieldPacote = new JTextField();
			textFieldPacote.setBounds(101, 183, 290, 29);
			textFieldPacote.setBackground(SystemColor.textHighlightText);
			textFieldPacote.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldPacote.setColumns(10);
			contentPanel.add(textFieldPacote);
		}
				
						checkBoxAtivo = new JCheckBox("Ativo");
						checkBoxAtivo.setBounds(244, 350, 53, 27);
						checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
						checkBoxAtivo.setFont(new Font("Dialog", Font.PLAIN, 13));
						contentPanel.add(checkBoxAtivo);
						
						JCheckBox chckbxNewCheckBox = new JCheckBox("Ativo");
						chckbxNewCheckBox.setSelected(true);
						chckbxNewCheckBox.setBounds(165, 218, 53, 23);
						chckbxNewCheckBox.setBackground(SystemColor.text);
						chckbxNewCheckBox.setFont(new Font("Dubai Light", Font.PLAIN, 13));
						contentPanel.add(chckbxNewCheckBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 268, 401, 44);
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
		int idPacote = Integer.parseInt(textFieldPacote.getText());


		Cliente cliente = null;

		if (modoEditar) {
			cliente = clienteAntigo;

			cliente.setNif(nif);
			cliente.setNome(nome);
			cliente.setMorada(morada);
			cliente.setLogin(login);
			cliente.setPassword(pass);
			cliente.setAtivo(ativo);
			cliente.setId_pacote_cliente(idPacote);

		} else {
			cliente = new Cliente( nome, nif, morada, login, pass, ativo, idPacote);
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
