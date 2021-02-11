package guiComponentes.gestorOperador;

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

import guiComponentes.GUI_total;
import guiComponentes.gestorCliente.CriarClienteDialog;
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

	public static void main(String[] args) {
		try {
			CriarOperadorDialog dialog = new CriarOperadorDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CriarOperadorDialog(GUI_gestor_operador operadorPesquisaApp ) {
		this();
		this.operadorPesquisaApp = operadorPesquisaApp;
	}

	public CriarOperadorDialog(GUI_gestor_operador operadorPesquisaApp, Funcionario funcionarioTemp, boolean modoEditar ) {
		this();
		this.operadorPesquisaApp = operadorPesquisaApp;
		this.funcionarioAntigo = funcionarioTemp;
		this.modoEditar = modoEditar;

		if(modoEditar) {
			setTitle("Editar Operador");
			popularTextFields(funcionarioTemp);

		}
	}

	private void popularTextFields(Funcionario funcionarioTemp) {
		textFieldNome.setText(funcionarioTemp.getNome()+ "");
		textFieldNIF.setText(funcionarioTemp.getNif() + "");
		textFieldLogin.setText(funcionarioTemp.getLogin());
		textFieldPassword.setText(funcionarioTemp.getPassword()); //.substring(0,8)
	}

	public CriarOperadorDialog() {
		setBounds(500, 300, 418, 275);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.text);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Operador");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(17, 11, 70, 27);
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNome);
		}

		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setBounds(17, 49, 70, 27);
			lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNif);
		}

		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setBounds(17, 87, 70, 27);
			lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblLogin);
		}

		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(17, 125, 70, 27);
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblPassword);
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
			textFieldLogin = new JTextField();
			textFieldLogin.setBounds(101, 87, 290, 27);
			textFieldLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldLogin.setColumns(10);
			contentPanel.add(textFieldLogin);
		}

		{
			textFieldPassword = new JPasswordField();
			textFieldPassword.setBounds(101, 125, 290, 27);
			textFieldPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldPassword.setColumns(10);
			contentPanel.add(textFieldPassword);
		}



		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 189, 401, 44);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(SystemColor.text);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));


			{
				JButton okButton = new JButton("Confirmar");
				okButton.setForeground(SystemColor.activeCaptionText);
				okButton.setBackground(SystemColor.activeCaption);
				okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
				okButton.setFocusPainted(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if (textFieldNome.getText().isBlank() || textFieldLogin.getText().isBlank() || textFieldPassword.getPassword().length == 0) {
							JOptionPane.showMessageDialog( CriarOperadorDialog.this, "Todos os dados têm de ser preenchidos!");
							return;
						}
						if( textFieldNIF.getText().isBlank()) {
							JOptionPane.showMessageDialog( CriarOperadorDialog.this, "O NIF tem que estar preenchido!");
							return;
						}
						try {
							Integer.parseInt( textFieldNIF.getText() );
						}
						catch( Exception ex ){
							JOptionPane.showMessageDialog( CriarOperadorDialog.this, "A NIF tem de ser um inteiro!");
							return;
						}


						gravarOperador();
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
		}
	}

	@SuppressWarnings("deprecation")
	private void gravarOperador() {
		String nome = textFieldNome.getText();
		int nif = Integer.parseInt(textFieldNIF.getText());
		String login = textFieldLogin.getText();
		String pass = textFieldPassword.getText();
		boolean ativo = true; 

		Funcionario funcionario = null;

		if (modoEditar) {
			funcionario = funcionarioAntigo;

			funcionario.setNif(nif);
			funcionario.setNome(nome);
			funcionario.setLogin(login);
			funcionario.setPassword(pass);
			funcionario.setAtivo(ativo);

		} else {
			funcionario = new Funcionario(nome, nif, login, pass, ativo, 2);
		}

		try {
			if (modoEditar) {
				Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(GUI_total.getUsername());
				GestorDeDAO.getGestorDeDAO().editarFuncionario(funcionario, admin, pass);
				operadorPesquisaApp.refreshOperadorTable();
				JOptionPane.showMessageDialog(operadorPesquisaApp,
						"Operador Editado com sucesso!", "Operador Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				Funcionario admin = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioAdmin(GUI_total.getUsername());
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
}
