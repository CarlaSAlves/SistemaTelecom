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
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Operador_visualizarPromocaoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;


	/**
	 * Create the dialog.
	 * @param operador_gerirClientes 
	 * @param string 
	 */
	public Operador_visualizarPromocaoDialog(Operador_gerirClientes operador_gerirClientes) {
		setBounds(500, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Atribuir Pacote Comercial");
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(63, 85, 296, 107);
		contentPanel.add(textArea);
		
		JLabel labelPromo = new JLabel("Promoção");
		labelPromo.setForeground(Color.BLACK);
		labelPromo.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		labelPromo.setBounds(17, 16, 130, 16);
		
		contentPanel.add(labelPromo);
		
		textField = new JTextField();
		textField.setBounds(63, 52, 298, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
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
