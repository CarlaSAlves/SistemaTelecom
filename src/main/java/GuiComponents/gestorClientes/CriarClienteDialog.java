package GuiComponents.gestorClientes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import servico.GestorDeDAO;
import standard_value_object.Cliente;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.SystemColor;

public class CriarClienteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNIF;
	private JTextField textFieldMorada;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	private JTextField textFieldPacote;
	private JCheckBox checkBoxAtivo;
	private GUI_gestor_cliente clientePesquisaApp;
	private Cliente clienteAntigo;
	private boolean modoEditar = false;
	private JTextField textFieldNome;
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

	public CriarClienteDialog(GUI_gestor_cliente clientePesquisaApp ) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
	}

	public CriarClienteDialog(GUI_gestor_cliente clientePesquisaApp, Cliente clienteAntigo, boolean modoEditar ) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
		this.clienteAntigo = clienteAntigo;
		this.modoEditar = modoEditar;

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
		textFieldPassword.setText(clienteAntigo2.getPassword());
		textFieldPacote.setText(clienteAntigo2.getId_pacote_cliente()+ "");
		checkBoxAtivo.setSelected(clienteAntigo2.isAtivo());
		
	}

	public CriarClienteDialog() {
		setBounds(500, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Cliente");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setFont(font);
			lblNome.setBounds(10, 14, 46, 14);
			contentPanel.add(lblNome);
		}
		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setFont(font);
			lblNif.setBounds(10, 42, 46, 14);
			contentPanel.add(lblNif);
		}
		{
			JLabel lblMorada = new JLabel("Morada");
			lblMorada.setFont(font);
			lblMorada.setBounds(10, 70, 70, 14);
			contentPanel.add(lblMorada);
		}
		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setFont(font);
			lblLogin.setBounds(10, 95, 46, 20);
			contentPanel.add(lblLogin);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setFont(font);
			lblPassword.setBounds(10, 129, 70, 14);
			contentPanel.add(lblPassword);
		}
		{
			JLabel lblIdpacote = new JLabel("ID Pacote");
			lblIdpacote.setFont(font);
			lblIdpacote.setBounds(10, 158, 70, 14);
			contentPanel.add(lblIdpacote);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setFont(font);
			textFieldNome.setColumns(10);
			textFieldNome.setBounds(90, 11, 334, 20);
			contentPanel.add(textFieldNome);
		}
		{
			textFieldNIF = new JTextField();
			textFieldNIF.setFont(font);
			textFieldNIF.setColumns(10);
			textFieldNIF.setBounds(90, 39, 334, 20);
			contentPanel.add(textFieldNIF);
		}
		{
			textFieldMorada = new JTextField();
			textFieldMorada.setFont(font);
			textFieldMorada.setColumns(10);
			textFieldMorada.setBounds(90, 67, 334, 20);
			contentPanel.add(textFieldMorada);
		}
		{
			textFieldLogin = new JTextField();
			textFieldLogin.setFont(font);
			textFieldLogin.setColumns(10);
			textFieldLogin.setBounds(90, 96, 334, 20);
			contentPanel.add(textFieldLogin);
		}
		{
			textFieldPassword = new JTextField();
			textFieldPassword.setFont(font);
			textFieldPassword.setColumns(10);
			textFieldPassword.setBounds(90, 126, 334, 20);
			contentPanel.add(textFieldPassword);
		}
		{
			textFieldPacote = new JTextField();
			textFieldPacote.setBackground(SystemColor.textHighlightText);
			textFieldPacote.setFont(font);
			textFieldPacote.setColumns(10);
			textFieldPacote.setBounds(90, 155, 334, 20);
			contentPanel.add(textFieldPacote);
		}
		
		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setFont(font);
		checkBoxAtivo.setBounds(191, 182, 97, 23);
		contentPanel.add(checkBoxAtivo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.inactiveCaption);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);


			{
				JButton okButton = new JButton("Confirmar");
				okButton.setForeground(SystemColor.activeCaptionText);
				okButton.setBackground(SystemColor.inactiveCaption);
				okButton.setFont(font);
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
				cancelButton.setBackground(SystemColor.inactiveCaption);
				cancelButton.setFont(font);
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
			if (modoEditar) {
				GestorDeDAO.getGestorDeDAO().editarCliente(cliente);
				clientePesquisaApp.refreshClienteTable();
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Cliente Editado com sucesso!", "Cliente Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				GestorDeDAO.getGestorDeDAO().criarCliente(cliente);
				clientePesquisaApp.refreshClienteTable();
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Cliente Criado com sucesso!", "Cliente Criado",
						JOptionPane.INFORMATION_MESSAGE);
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
