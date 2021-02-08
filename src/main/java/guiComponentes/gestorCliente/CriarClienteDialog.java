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

		setBounds(500, 300, 573, 429);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Cliente");
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("70px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("334px"),},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("21px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setFont(new Font("Dialog", Font.PLAIN, 13));
			contentPanel.add(lblNome, "2, 2, left, fill");
		}
		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setFont(new Font("Dialog", Font.PLAIN, 13));
			contentPanel.add(lblNif, "2, 4, left, fill");
		}
		{
			JLabel lblMorada = new JLabel("Morada");
			lblMorada.setFont(new Font("Dialog", Font.PLAIN, 13));
			contentPanel.add(lblMorada, "2, 6, fill, fill");
		}
		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setFont(new Font("Dialog", Font.PLAIN, 13));
			contentPanel.add(lblLogin, "2, 8, left, fill");
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setFont(font);
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome, "4, 2, fill, fill");
		}
		{
			textFieldNIF = new JTextField();
			textFieldNIF.setFont(font);
			textFieldNIF.setColumns(10);
			contentPanel.add(textFieldNIF, "4, 4, fill, fill");
		}
		{
			textFieldMorada = new JTextField();
			textFieldMorada.setFont(font);
			textFieldMorada.setColumns(10);
			contentPanel.add(textFieldMorada, "4, 6, fill, fill");
		}
		{
			textFieldLogin = new JTextField();
			textFieldLogin.setFont(font);
			textFieldLogin.setColumns(10);
			contentPanel.add(textFieldLogin, "4, 8, fill, fill");
		}
		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
			contentPanel.add(lblPassword, "2, 18, fill, fill");
		}
		{
			textFieldPassword = new JPasswordField();
			textFieldPassword.setFont(font);
			textFieldPassword.setColumns(10);
			contentPanel.add(textFieldPassword, "4, 18, fill, fill");
		}
		{
			JLabel lblIdpacote = new JLabel("ID Pacote");
			lblIdpacote.setFont(new Font("Dialog", Font.PLAIN, 13));
			contentPanel.add(lblIdpacote, "2, 22, fill, fill");
		}
		{
			textFieldPacote = new JTextField();
			textFieldPacote.setBackground(SystemColor.textHighlightText);
			textFieldPacote.setFont(font);
			textFieldPacote.setColumns(10);
			contentPanel.add(textFieldPacote, "4, 22, fill, fill");
		}
				
						checkBoxAtivo = new JCheckBox("Ativo");
						checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
						checkBoxAtivo.setFont(new Font("Dialog", Font.PLAIN, 13));
						contentPanel.add(checkBoxAtivo, "4, 24, center, fill");
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
