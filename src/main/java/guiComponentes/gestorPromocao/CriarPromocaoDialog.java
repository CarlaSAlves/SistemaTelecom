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
import standard_value_object.Promocao;
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

public class CriarPromocaoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox checkBoxAtivo;	
	private GUI_gestor_promocao promocaoPesquisaApp;
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
		checkBoxAtivo.setSelected(promocaoAntigo2.isAtiva());

	}

	public CriarPromocaoDialog() {
		setBounds(500, 300, 427, 249);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("94px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("294px"),},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				RowSpec.decode("30px"),
				RowSpec.decode("23px"),
				RowSpec.decode("24px"),
				RowSpec.decode("44px"),}));
		{
			JLabel lblNome = new JLabel("Nome");
			lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblNome, "1, 2, fill, fill");
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome, "3, 2, fill, fill");
		}
		
		{
			JLabel lblDescricao = new JLabel("Descrição");
			lblDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			contentPanel.add(lblDescricao, "1, 4, fill, fill");
		}
		{
			textFieldDescricao = new JTextField();
			textFieldDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldDescricao.setColumns(10);
			contentPanel.add(textFieldDescricao, "3, 4, fill, fill");
		}

		checkBoxAtivo = new JCheckBox("Ativa");
		checkBoxAtivo.setBackground(SystemColor.inactiveCaption);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(checkBoxAtivo, "3, 6, left, fill");
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "1, 8, 3, 1, fill, top");
			buttonPane.setBackground(SystemColor.inactiveCaption);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("Confirmar");
				okButton.setForeground(SystemColor.activeCaptionText);
				okButton.setBackground(SystemColor.inactiveCaption);
				okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
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

