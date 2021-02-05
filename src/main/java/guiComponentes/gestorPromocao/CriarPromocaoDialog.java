package guiComponentes.gestorPromocao;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import servico.GestorDeDAO;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.SystemColor;

public class CriarPromocaoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox checkBoxAtivo;	
	private GUI_gestor_promocoes promocaoPesquisaApp;
	private Promocao promocaoAntiga;
	private boolean modoEditar = false;
	private JTextField textFieldNome;
	private JTextField textFieldDescricao;
	private Font font = new Font("Dubai Light", Font.PLAIN, 17);

	public static void main(String[] args) {
		try {
			CriarPromocaoDialog dialog = new CriarPromocaoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CriarPromocaoDialog(GUI_gestor_promocoes promocaoPesquisaApp ) {
		this();
		this.promocaoPesquisaApp = promocaoPesquisaApp;
	}

	public CriarPromocaoDialog(GUI_gestor_promocoes promocaoPesquisaApp, Promocao promocaoTemp, boolean modoEditar ) {
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
		textFieldNome.setText(promocaoAntigo2.getNome()+ "");
		textFieldDescricao.setText(promocaoAntigo2.getDescricao());
		checkBoxAtivo.setSelected(promocaoAntigo2.isAtiva());

	}

	public CriarPromocaoDialog() {
		setBounds(500, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setFont(font);
			lblNome.setBounds(10, 14, 55, 14);
			contentPanel.add(lblNome);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setFont(font);
			textFieldNome.setColumns(10);
			textFieldNome.setBounds(90, 11, 334, 20);
			contentPanel.add(textFieldNome);
		}
		
		{
			JLabel lblDescricao = new JLabel("Descrição");
			lblDescricao.setFont(font);
			lblDescricao.setBounds(10, 54, 70, 14);
			contentPanel.add(lblDescricao);
		}
		{
			textFieldDescricao = new JTextField();
			textFieldDescricao.setFont(font);
			textFieldDescricao.setColumns(10);
			textFieldDescricao.setBounds(90, 51, 334, 20);
			contentPanel.add(textFieldDescricao);
		}

		checkBoxAtivo = new JCheckBox("Ativa");
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setFont(font);
		checkBoxAtivo.setBounds(10, 116, 97, 23);
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
						gravarPromocao();
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

	private void gravarPromocao() {
		String nome = textFieldNome.getText();
		String descricao = textFieldDescricao.getText();
		boolean ativo = checkBoxAtivo.isSelected();

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

				promocaoPesquisaApp.refreshClienteTable();
				JOptionPane.showMessageDialog(promocaoPesquisaApp,
						"Promoção editada com sucesso!", "Promoção Editada",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				GestorDeDAO.getGestorDeDAO().criarPromocao(promocao);
				promocaoPesquisaApp.refreshClienteTable();
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

