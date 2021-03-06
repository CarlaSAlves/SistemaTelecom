package guiComponentes.admin_gestorPacoteComercial;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import servico.GestorDeDAO;
import standard_value_object.Funcionario;
import standard_value_object.PacoteComercial;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;


import guiComponentes.GUI_total;
import java.awt.Color;

public class CriarPacotesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private GUI_gestor_pacotes pacoteComercialPesquisaApp;
	private PacoteComercial pacoteComercialAntigo;
	private boolean modoEditar = false;
	private JTextField textFieldNome;
	private JTextArea textFieldDescricao;


	public CriarPacotesDialog(GUI_gestor_pacotes pacoteComercialPesquisaApp ) {
		this();
		this.pacoteComercialPesquisaApp = pacoteComercialPesquisaApp;
	}

	/**
	 * 
	 * Fills the textfields with values of the selected package before edition
	 * @param pacoteComercialAntigo2
	 */
	private void popularTextFields(PacoteComercial pacoteComercialAntigo2) {
		textFieldNome.setText(pacoteComercialAntigo2.getNome()+ "");
		textFieldDescricao.setText(pacoteComercialAntigo2.getDescricao());

	}

	/**
	 * constructor that initializes with a method that sets up the window and the initilize method
	 * which contains all the methods and elements that compose the page
	 */
	public CriarPacotesDialog() {

		setTitle("Novo Pacote Comercial");
		setBounds(500, 300, 465, 412);
		contentPanel.setLayout(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setResizable(false);
		inicialize(); 

	}

	/**
	 *  * contains the body of the page
	 */
	private void inicialize() {

		/**
		 *  Labels e textField of the page:
		 *  Nome
		 *  Descrição
		 */

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(5, 40, 86, 35);
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblNome);

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setBounds(5, 117, 86, 51);
		lblDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(lblDescricao);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(103, 45, 290, 35);
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldNome.setColumns(10);
		contentPanel.add(textFieldNome);

		textFieldDescricao = new JTextArea();
		textFieldDescricao.setBounds(101, 118, 290, 174);
		textFieldDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldDescricao.setColumns(10);
		contentPanel.add(textFieldDescricao);


		/**
		 *confirmation panel
		 * confirmation button
		 * cancellation button
		 */

		painelConfirmacaoSetup();

	}

	/**
	 * configs the footer buttons of the page
	 * 
	 * @buttonPane - confirmation panel
	 * @okButton -  confirmation button that starts the validations of the fields
	 *  and saves the client in the DB
	 * @cancelButton - cancellation button, cancel the action and closes the window
	 * 
	 */
	protected void painelConfirmacaoSetup() {

		JPanel painelConfirmacao = new JPanel();
		painelConfirmacao.setBounds(5, 323, 438, 44);
		contentPanel.add(painelConfirmacao);
		painelConfirmacao.setBackground(Color.WHITE);
		painelConfirmacao.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton okButton = new JButton("Confirmar");
		okButton.setForeground(SystemColor.activeCaptionText);
		okButton.setBackground(SystemColor.inactiveCaption);
		okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		okButton.setFocusPainted(false);
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (textFieldNome.getText().isBlank() || textFieldDescricao.getText().isBlank()) {
					JOptionPane.showMessageDialog( CriarPacotesDialog.this, "Todos os dados têm de ser preenchidos!");
					return;
				}
				gravarPacoteComercial();
			}
		});
		
		okButton.setActionCommand("OK");
		painelConfirmacao.add(okButton);
		getRootPane().setDefaultButton(okButton);


		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setBackground(SystemColor.inactiveCaption);
		cancelButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		cancelButton.setFocusPainted(false);
		cancelButton.setActionCommand("Cancel");
		painelConfirmacao.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
	}

	/**
	 * configs the window if actioned by the edit button - presents the editable fields * @param pacoteComercialPesquisaApp
	 * @param pacoteComercialAntigo - retrieves the current info of the package
	 * @param modoEditar - actioned by the edit button
	 */
	public CriarPacotesDialog(GUI_gestor_pacotes pacoteComercialPesquisaApp, PacoteComercial pacoteComercialAntigo, boolean modoEditar ) {
		this();
		this.pacoteComercialPesquisaApp = pacoteComercialPesquisaApp;
		this.pacoteComercialAntigo = pacoteComercialAntigo;
		this.modoEditar = modoEditar;

		//edit mode, actioned by the edit button
		
		if(modoEditar) {
			setTitle("Editar Pacote Comercial");
			popularTextFields(pacoteComercialAntigo);
		}
	}

	/**
	 * Method that connects gestorDAO and saves the data of the client in the DB
	 */
	private void gravarPacoteComercial() {
		String nome = textFieldNome.getText();
		String descricao = textFieldDescricao.getText();
		boolean ativo = true;

		PacoteComercial pacoteComercial = null;

		if (modoEditar) {
			pacoteComercial = pacoteComercialAntigo;
			pacoteComercial.setNome(nome);
			pacoteComercial.setDescricao(descricao);            
			pacoteComercial.setAtivo(ativo);	 

		} else {
			pacoteComercial = new PacoteComercial( nome, descricao, ativo);
		}

		try {
			Funcionario funcionario = null;
			if (modoEditar) {
				funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
				GestorDeDAO.getGestorDeDAO().editarPacoteComercial(pacoteComercial,funcionario );
				pacoteComercialPesquisaApp.refreshPacotesTable();
				JOptionPane.showMessageDialog(pacoteComercialPesquisaApp,
						"Pacote Comercial Editado com sucesso!", "Pacote Comercial Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				funcionario = GestorDeDAO.getGestorDeDAO().pesquisaFuncionarioLogin(GUI_total.getUsername());
				GestorDeDAO.getGestorDeDAO().criarPacoteComercial(pacoteComercial, funcionario);
				pacoteComercialPesquisaApp.refreshPacotesTable();
				JOptionPane.showMessageDialog(pacoteComercialPesquisaApp,
						"Pacote comercial criado com sucesso!", "Pacote Comercial Criado",
						JOptionPane.INFORMATION_MESSAGE);
			}

			setVisible(false);
			dispose();

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(pacoteComercialPesquisaApp,
					"Error a criar o Pacote Comercial " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}

