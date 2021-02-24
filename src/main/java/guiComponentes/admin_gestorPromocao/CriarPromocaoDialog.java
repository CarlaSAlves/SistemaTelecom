package guiComponentes.admin_gestorPromocao;

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
import standard_value_object.Promocao;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.Color;

public class CriarPromocaoDialog extends JDialog {
	private GUI_gestor_promocao promocaoPesquisaApp;
	private static final long serialVersionUID = 1L;
	private Promocao promocaoAntiga;
	private boolean modoEditar = false;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextArea textFieldDescricao;


	public CriarPromocaoDialog(GUI_gestor_promocao promocaoPesquisaApp ) {
		this();
		this.promocaoPesquisaApp = promocaoPesquisaApp;
	}

	/**
	 * Preenche as textFields com os valores anteriores da promoção, antes de editar.
	 * @param promocaoAntigo2
	 */
	private void popularTextFields(Promocao promocaoAntigo2) {
		textFieldNome.setText(promocaoAntigo2.getNome());
		textFieldDescricao.setText(promocaoAntigo2.getDescricao());

	}

	/**
	 * Construtor que inicia com o método que configura a janela e o método inicialize, 
	 * que contém todos os métodos e elementos que compõem a página 
	 */
	public CriarPromocaoDialog() {

		setTitle("Nova Promoção");
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
	 * Contém o corpo da página
	 */
	private void inicialize() {
		
		/**
		 *  Labels e textField da página:
		 *  Nome
		 *  Descrição
		 */

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(5, 48, 86, 28);
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
		textFieldDescricao.setBackground(Color.WHITE);
		textFieldDescricao.setLineWrap(true);
		textFieldDescricao.setBounds(101, 118, 290, 193);
		textFieldDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textFieldDescricao.setColumns(10);
		contentPanel.add(textFieldDescricao);

		/**
		 * Painel de Confirmação 
		 * Botão Confirmar
		 * Botão Cancelar
		 */

		painelConfirmacaoSetup();
	}
	
	/**
	 * Configura os botões do rodapé da página 
	 * 
	 * @buttonPane - painel de confirmação
	 * @okButton - botão de confirmação, aciona as validações de campos e 
	 * o método que grava os dados da promoção na base de dados.
	 * @cancelButton - botão cancelar, cancela a ação e fecha a janela.
	 * 
	 */
	protected void painelConfirmacaoSetup() {
		JPanel painelConfirmacao = new JPanel();
		painelConfirmacao.setBounds(5, 322, 444, 44);
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
					JOptionPane.showMessageDialog( CriarPromocaoDialog.this, "Todos os dados têm de ser preenchidos!");
					return;
				}
				gravarPromocao();
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
	 * Configuração da janela se for acionada pelo botão editar - apresenta os campos editáveis.
	 * @param promocaoPesquisaApp
	 * @param promocaoTemp - vaio buscar os valores atuais da promoção
	 * @param modoEditar - accionado pelo botão editar
	 */
	public CriarPromocaoDialog(GUI_gestor_promocao promocaoPesquisaApp, Promocao promocaoTemp, boolean modoEditar ) {
		this();
		this.promocaoPesquisaApp = promocaoPesquisaApp;
		this.promocaoAntiga = promocaoTemp;
		this.modoEditar = modoEditar;

		// modo editar, accionado pelo clique no botão "editar"
		
		if(modoEditar) {
			setTitle("Editar Promoção");
			popularTextFields(promocaoTemp);
		}
	}

	/**
	 * Método que conecta ao gestor DAO e grava os dados da promoção na base de dados
	 */
	private void gravarPromocao() {
		String nome = textFieldNome.getText();
		String descricao = textFieldDescricao.getText();
		boolean ativo = true;

		Promocao promocao = null;

		if (modoEditar) {
			promocao = promocaoAntiga;

			promocao.setNome(nome);
			promocao.setDescricao(descricao);			
			promocao.setAtiva(ativo);

		} else {
			promocao = new Promocao( nome, descricao, ativo);
		}

		try {
			if (modoEditar) {
				GestorDeDAO.getGestorDeDAO().editarPromocao(promocao);

				promocaoPesquisaApp.refreshPromocaoTable();
				JOptionPane.showMessageDialog(promocaoPesquisaApp,
						"Promoção editada com sucesso!", "Promoção Editada",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				GestorDeDAO.getGestorDeDAO().criarPromocao(promocao);
				promocaoPesquisaApp.refreshPromocaoTable();
				JOptionPane.showMessageDialog(promocaoPesquisaApp,
						"Promoção criada com sucesso!", "Promoção Criada",
						JOptionPane.INFORMATION_MESSAGE);
			}

			setVisible(false);
			dispose();


		} catch (Exception exc) {
			JOptionPane.showMessageDialog(promocaoPesquisaApp,
					"Error a criar a Promoção " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}

