package guiComponentes.Admin_gestorPromocao;

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

	public static void main(String[] args) {
		try {
			CriarPromocaoDialog dialog = new CriarPromocaoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CriarPromocaoDialog(GUI_gestor_promocao promocaoPesquisaApp ) {
		this();
		this.promocaoPesquisaApp = promocaoPesquisaApp;
	}

	public CriarPromocaoDialog(GUI_gestor_promocao promocaoPesquisaApp, Promocao promocaoTemp, boolean modoEditar ) {
		this();
		this.promocaoPesquisaApp = promocaoPesquisaApp;
		this.promocaoAntiga = promocaoTemp;
		this.modoEditar = modoEditar;

		if(modoEditar) {
			setTitle("Editar Promoção");
			popularTextFields(promocaoTemp);
		}
	}

	private void popularTextFields(Promocao promocaoAntigo2) {
		textFieldNome.setText(promocaoAntigo2.getNome());
		textFieldDescricao.setText(promocaoAntigo2.getDescricao());

	}

	public CriarPromocaoDialog() {
		setBounds(500, 300, 465, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textFieldDescricao = new JTextArea();
			textFieldDescricao.setBackground(Color.WHITE);
			textFieldDescricao.setLineWrap(true);
			textFieldDescricao.setBounds(101, 118, 290, 193);
			textFieldDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldDescricao.setColumns(10);
			contentPanel.add(textFieldDescricao);
		}
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(5, 48, 86, 28);
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNome);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setBounds(103, 45, 290, 35);
			textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome);
		}

		{
			JLabel lblDescricao = new JLabel("Descrição");
			lblDescricao.setBounds(5, 117, 86, 51);
			lblDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblDescricao);
		}


		{
			JPanel painelConfirmacao = new JPanel();
			painelConfirmacao.setBounds(5, 322, 444, 44);
			contentPanel.add(painelConfirmacao);
			painelConfirmacao.setBackground(Color.WHITE);
			painelConfirmacao.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
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
			}
			{
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
		}
	}

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

