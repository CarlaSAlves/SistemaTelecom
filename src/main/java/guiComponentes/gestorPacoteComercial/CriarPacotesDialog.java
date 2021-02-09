package guiComponentes.gestorPacoteComercial;

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
import java.awt.Color;

public class CriarPacotesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox checkBoxAtivo;	
	private GUI_gestor_pacotes pacoteComercialPesquisaApp;
	private PacoteComercial pacoteComercialAntigo;
	private boolean modoEditar = false;
	private JTextField textFieldNome;
	private JTextField textFieldDescricao;
	private Font font = new Font("Dubai Light", Font.PLAIN, 17);


	public static void main(String[] args) {
		try {
			CriarPacotesDialog dialog = new CriarPacotesDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CriarPacotesDialog(GUI_gestor_pacotes pacoteComercialPesquisaApp ) {
		this();
		this.pacoteComercialPesquisaApp = pacoteComercialPesquisaApp;
	}

	public CriarPacotesDialog(GUI_gestor_pacotes pacoteComercialPesquisaApp, PacoteComercial pacoteComercialAntigo, boolean modoEditar ) {
		this();
		this.pacoteComercialPesquisaApp = pacoteComercialPesquisaApp;
		this.pacoteComercialAntigo = pacoteComercialAntigo;
		this.modoEditar = modoEditar;

		if(modoEditar) {
			setTitle("Editar Pacote Comercial");
			popularTextFields(pacoteComercialAntigo);
		}
	}

	private void popularTextFields(PacoteComercial pacoteComercialAntigo2) {
		textFieldNome.setText(pacoteComercialAntigo2.getNome()+ "");
		textFieldDescricao.setText(pacoteComercialAntigo2.getDescricao());
		checkBoxAtivo.setSelected(pacoteComercialAntigo2.isAtivo());

	}

	public CriarPacotesDialog() {
		setBounds(500, 300, 417, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		{
			JLabel lblNome_1 = new JLabel("Nome");
			lblNome_1.setBounds(9, 15, 82, 23);
			lblNome_1.setFont(new Font("Dialog", Font.BOLD, 13));
			contentPanel.add(lblNome_1);
		}
		{
			textFieldNome = new JTextField();
			textFieldNome.setBounds(101, 9, 290, 27);
			textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldNome.setColumns(10);
			contentPanel.add(textFieldNome);
		}
		{
			JLabel lblMorada = new JLabel("Descrição");
			lblMorada.setBounds(9, 49, 82, 23);
			lblMorada.setFont(new Font("Dialog", Font.BOLD, 13));
			contentPanel.add(lblMorada);
		}
		{
			textFieldDescricao = new JTextField();
			textFieldDescricao.setBounds(101, 47, 290, 27);
			textFieldDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 13));
			textFieldDescricao.setColumns(10);
			contentPanel.add(textFieldDescricao);
		}

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setBounds(211, 81, 75, 23);
		checkBoxAtivo.setBackground(Color.WHITE);
		checkBoxAtivo.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		contentPanel.add(checkBoxAtivo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(9, 274, 392, 38);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("Confirmar");
				okButton.setForeground(SystemColor.activeCaptionText);
				okButton.setBackground(SystemColor.inactiveCaption);
				okButton.setFont(new Font("Dubai Light", Font.PLAIN, 15));
				okButton.setFocusPainted(false);
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						gravarPacoteComercial();
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

	private void gravarPacoteComercial() {
		String nome = textFieldNome.getText();
		String descricao = textFieldDescricao.getText();
		boolean ativo = checkBoxAtivo.isSelected();

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
			if (modoEditar) {
				GestorDeDAO.getGestorDeDAO().editarPacoteComercial(pacoteComercial);

				pacoteComercialPesquisaApp.refreshPacotesTable();
				JOptionPane.showMessageDialog(pacoteComercialPesquisaApp,
						"Pacote Comercial Editado com sucesso!", "Pacote Comercial Editado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				GestorDeDAO.getGestorDeDAO().criarPacoteComercial(pacoteComercial);
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

