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


public class CriarOperadorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextField textFieldNIF;
	private JTextField textFieldLogin;
	private JPasswordField textFieldPassword;
	private JCheckBox checkBoxAtivo;
	private GUI_gestor_operador operadorPesquisaApp;
	private Funcionario funcionarioAntigo;
	private boolean modoEditar = false;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);

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
			if (funcionarioTemp.isAtivo())
				checkBoxAtivo.setVisible(false);
		}
	}

	private void popularTextFields(Funcionario funcionarioTemp) {
		textFieldNome.setText(funcionarioTemp.getNome()+ "");
		textFieldNIF.setText(funcionarioTemp.getNif() + "");
		textFieldLogin.setText(funcionarioTemp.getLogin());
		textFieldPassword.setText(funcionarioTemp.getPassword()); //.substring(0,8)
		checkBoxAtivo.setSelected(funcionarioTemp.isAtivo());

	}

	public CriarOperadorDialog() {
		setBounds(500, 300, 385, 297);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.text);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Operador");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(17, 34, 31, 23);
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNome);
		}

		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setBounds(17, 60, 19, 23);
			lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNif);
		}

		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setBounds(17, 88, 29, 23);
			lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblLogin);
		}

		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(17, 118, 70, 23);
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblPassword);
		}

		{
			textFieldNome = new JTextField();
			textFieldNome.setBounds(99, 34, 264, 23);
			textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome);
		}

		{
			textFieldNIF = new JTextField();
			textFieldNIF.setBounds(99, 60, 264, 23);
			textFieldNIF.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNIF.setColumns(10);
			contentPanel.add(textFieldNIF);
		}

		{
			textFieldLogin = new JTextField();
			textFieldLogin.setBounds(99, 88, 264, 23);
			textFieldLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldLogin.setColumns(10);
			contentPanel.add(textFieldLogin);
		}

		{
			textFieldPassword = new JPasswordField();
			textFieldPassword.setBounds(99, 118, 264, 23);
			textFieldPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldPassword.setColumns(10);
			contentPanel.add(textFieldPassword);
		}

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBounds(208, 163, 50, 23);
		checkBoxAtivo.setSelected(true);
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(checkBoxAtivo);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.text);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);


			{
				JButton okButton = new JButton("Confirmar");
				okButton.setForeground(SystemColor.activeCaptionText);
				okButton.setBackground(SystemColor.activeCaption);
				okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
				okButton.setFocusPainted(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gravarOperador();
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
	private void gravarOperador() {
		String nome = textFieldNome.getText();
		int nif = Integer.parseInt(textFieldNIF.getText());
		String login = textFieldLogin.getText();
		String pass = textFieldPassword.getText();
		boolean ativo = checkBoxAtivo.isSelected();

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
				GestorDeDAO.getGestorDeDAO().editarFuncionario(funcionario, admin);
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
