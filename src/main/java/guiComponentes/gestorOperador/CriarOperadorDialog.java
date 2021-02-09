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
import java.awt.Color;


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
		setBounds(500, 300, 417, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.text);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Novo Operador");
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
<<<<<<< HEAD
			lblNome.setBounds(15, 29, 34, 18);
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
=======
			lblNome.setBounds(9, 15, 82, 23);
			lblNome.setFont(new Font("Dialog", Font.BOLD, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			contentPanel.add(lblNome);
		}

		{
			JLabel lblNif = new JLabel("NIF");
<<<<<<< HEAD
			lblNif.setBounds(15, 55, 20, 18);
			lblNif.setFont(new Font("Dubai Light", Font.PLAIN, 13));
=======
			lblNif.setBounds(9, 49, 82, 23);
			lblNif.setFont(new Font("Dialog", Font.BOLD, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			contentPanel.add(lblNif);
		}

		{
			JLabel lblLogin = new JLabel("Login");
<<<<<<< HEAD
			lblLogin.setBounds(15, 86, 31, 18);
			lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 13));
=======
			lblLogin.setBounds(9, 85, 82, 23);
			lblLogin.setFont(new Font("Dialog", Font.BOLD, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			contentPanel.add(lblLogin);
		}

		{
			JLabel lblPassword = new JLabel("Password");
<<<<<<< HEAD
			lblPassword.setBounds(15, 112, 70, 18);
			lblPassword.setFont(new Font("Dubai Light", Font.PLAIN, 13));
=======
			lblPassword.setBounds(9, 121, 82, 23);
			lblPassword.setFont(new Font("Dialog", Font.BOLD, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			contentPanel.add(lblPassword);
		}

		{
			textFieldNome = new JTextField();
<<<<<<< HEAD
			textFieldNome.setBounds(95, 29, 268, 23);
			textFieldNome.setFont(font);
=======
			textFieldNome.setBounds(101, 9, 290, 27);
			textFieldNome.setFont(new Font("Dialog", Font.PLAIN, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome);
		}

		{
			textFieldNIF = new JTextField();
<<<<<<< HEAD
			textFieldNIF.setBounds(95, 55, 268, 23);
			textFieldNIF.setFont(font);
=======
			textFieldNIF.setBounds(101, 47, 290, 27);
			textFieldNIF.setFont(new Font("Dialog", Font.PLAIN, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			textFieldNIF.setColumns(10);
			contentPanel.add(textFieldNIF);
		}

		{
			textFieldLogin = new JTextField();
<<<<<<< HEAD
			textFieldLogin.setBounds(95, 83, 268, 23);
			textFieldLogin.setFont(font);
=======
			textFieldLogin.setBounds(101, 83, 290, 27);
			textFieldLogin.setFont(new Font("Dialog", Font.PLAIN, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			textFieldLogin.setColumns(10);
			contentPanel.add(textFieldLogin);
		}

		{
			textFieldPassword = new JPasswordField();
<<<<<<< HEAD
			textFieldPassword.setBounds(95, 112, 268, 23);
			textFieldPassword.setFont(font);
=======
			textFieldPassword.setBounds(101, 119, 290, 27);
			textFieldPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
			textFieldPassword.setColumns(10);
			contentPanel.add(textFieldPassword);
		}

		checkBoxAtivo = new JCheckBox("Ativo");
<<<<<<< HEAD
		checkBoxAtivo.setBounds(235, 157, 53, 23);
		checkBoxAtivo.setSelected(true);
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
=======
		checkBoxAtivo.setBounds(209, 153, 102, 23);
		checkBoxAtivo.setBackground(Color.WHITE);
>>>>>>> e08abd290be096b2ebbb54f61c23ee7c6f8066c0
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
