package GuiComponents.gestorOperador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Servico.SistemaTeleServico;
import standard_value_object.Cliente;
import standard_value_object.Funcionario;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.SystemColor;

public class CriarOperadorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNIF;
	private JTextField textFieldMorada;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	private JTextField textFieldPacote;
	private JCheckBox checkBoxAtivo;
	
	private Font font = new Font("Dubai Light", Font.PLAIN, 17);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CriarOperadorDialog dialog = new CriarOperadorDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private GUI_gestor_operador clientePesquisaApp;
	private Cliente clienteAntigo;
	private boolean modoEditar = false;
	private JTextField textFieldNome;

	public CriarOperadorDialog(GUI_gestor_operador clientePesquisaApp ) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
	}

	public CriarOperadorDialog(GUI_gestor_operador clientePesquisaApp, Funcionario funcionarioTemp, boolean modoEditar ) {
		this();
		this.clientePesquisaApp = clientePesquisaApp;
		this.clienteAntigo = funcionarioTemp;
		this.modoEditar = modoEditar;

		if(modoEditar) {
			setTitle("Editar Cliente");
			popularTextFields(funcionarioTemp);
		}
	}

	private void popularTextFields(Funcionario funcionarioTemp) {
		textFieldNome.setText(funcionarioTemp.getNome()+ "");
		textFieldNIF.setText(funcionarioTemp.getNif() + "");
		textFieldMorada.setText(funcionarioTemp.getMorada());
		textFieldLogin.setText(funcionarioTemp.getLogin());
		textFieldPassword.setText(funcionarioTemp.getPassword());
		textFieldPacote.setText(funcionarioTemp.getId_pacote_cliente()+ "");
		checkBoxAtivo.setSelected(funcionarioTemp.isAtivo());
		
	}

	public CriarOperadorDialog() {
		setBounds(500, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("NIF");
			lblNome.setFont(font);
			lblNome.setBounds(10, 42, 46, 14);
			contentPanel.add(lblNome);
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
		{
			JLabel lblNome_1 = new JLabel("Nome");
			lblNome_1.setFont(font);
			lblNome_1.setBounds(10, 14, 46, 14);
			contentPanel.add(lblNome_1);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setFont(font);
			textFieldNome.setColumns(10);
			textFieldNome.setBounds(90, 11, 334, 20);
			contentPanel.add(textFieldNome);
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
			// save to the database
			if (modoEditar) {
				SistemaTeleServico.getSistemaTeleServicoInstance().editarCliente(cliente);
				clientePesquisaApp.refreshOperadorTable();
				JOptionPane.showMessageDialog(clientePesquisaApp,
						"Cliente Editado com sucesso!", "Cliente Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				SistemaTeleServico.getSistemaTeleServicoInstance().criarCliente(cliente);
				clientePesquisaApp.refreshOperadorTable();
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
