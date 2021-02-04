package guiComponentes.gestorOperador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import servico.GestorDeDAO;
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
	private JTextField textFieldNome;
	private JTextField textFieldNIF;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
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
		}
	}

	private void popularTextFields(Funcionario funcionarioTemp) {
		textFieldNome.setText(funcionarioTemp.getNome()+ "");
		textFieldNIF.setText(funcionarioTemp.getNif() + "");
		textFieldLogin.setText(funcionarioTemp.getLogin());
		textFieldPassword.setText(funcionarioTemp.getPassword());
		checkBoxAtivo.setSelected(funcionarioTemp.isAtivo());

	}

	public CriarOperadorDialog() {
		setBounds(500, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Operador");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setFont(font);
			lblNome.setBounds(10, 27, 46, 14);
			contentPanel.add(lblNome);
		}

		{
			JLabel lblNif = new JLabel("NIF");
			lblNif.setFont(font);
			lblNif.setBounds(10, 55, 46, 14);
			contentPanel.add(lblNif);
		}

		{
			JLabel lblLogin = new JLabel("Login");
			lblLogin.setFont(font);
			lblLogin.setBounds(10, 83, 46, 20);
			contentPanel.add(lblLogin);
		}

		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setFont(font);
			lblPassword.setBounds(10, 114, 70, 14);
			contentPanel.add(lblPassword);
		}

		{
			textFieldNome = new JTextField();
			textFieldNome.setFont(font);
			textFieldNome.setColumns(10);
			textFieldNome.setBounds(90, 24, 334, 20);
			contentPanel.add(textFieldNome);
		}

		{
			textFieldNIF = new JTextField();
			textFieldNIF.setFont(font);
			textFieldNIF.setColumns(10);
			textFieldNIF.setBounds(90, 52, 334, 20);
			contentPanel.add(textFieldNIF);
		}

		{
			textFieldLogin = new JTextField();
			textFieldLogin.setFont(font);
			textFieldLogin.setColumns(10);
			textFieldLogin.setBounds(90, 83, 334, 20);
			contentPanel.add(textFieldLogin);
		}

		{
			textFieldPassword = new JTextField();
			textFieldPassword.setFont(font);
			textFieldPassword.setColumns(10);
			textFieldPassword.setBounds(90, 111, 334, 20);
			contentPanel.add(textFieldPassword);
		}

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setSelected(true);
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setFont(font);
		checkBoxAtivo.setBounds(170, 156, 97, 23);
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
						gravarOperador();
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
				GestorDeDAO.getGestorDeDAO().editarFuncionario(funcionario);
				operadorPesquisaApp.refreshOperadorTable();
				JOptionPane.showMessageDialog(operadorPesquisaApp,
						"Operador Editado com sucesso!", "Operador Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				GestorDeDAO.getGestorDeDAO().criarFuncionario(funcionario);
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
