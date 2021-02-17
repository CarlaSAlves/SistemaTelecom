package guiComponentes.operador_gerirClientes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import standard_value_object.PacoteComercial;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class Operador_atribuirDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Operador_gerirClientes operador_gerirClientes;
	private boolean modoRemover = false;


	/**
	 * Create the dialog.
	 * @param operador_gerirClientes 
	 * @param string 
	 */
	
	
	public Operador_atribuirDialog( Operador_gerirClientes operador_gerirClientes) {
		this();
		this.operador_gerirClientes = operador_gerirClientes;
	}
	
	public Operador_atribuirDialog( Operador_gerirClientes operador_gerirClientes, boolean modoRemover) {
		this();
		this.operador_gerirClientes = operador_gerirClientes;
		this.modoRemover = modoRemover;
	}
	
	public Operador_atribuirDialog() {
		setBounds(500, 300, 450, 158);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Atribuir Pacote Comercial");
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JComboBox<PacoteComercial> comboBox = new JComboBox<PacoteComercial>();
		comboBox.setBounds(83, 38, 296, 22);
		contentPanel.add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(63, 85, 296, 107);
		contentPanel.add(textArea);
		
		JLabel labelPacote = new JLabel("Pacote Comercial");
		labelPacote.setForeground(Color.BLACK);
		labelPacote.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		labelPacote.setBounds(166, 11, 130, 16);
		
		contentPanel.add(labelPacote);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
