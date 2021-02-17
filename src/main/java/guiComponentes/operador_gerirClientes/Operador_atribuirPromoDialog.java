package guiComponentes.operador_gerirClientes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class Operador_atribuirPromoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 * @param string 
	 * @param operador_gerirClientes 
	 */
	public Operador_atribuirPromoDialog(Operador_gerirClientes operador_gerirClientes) {
		setBounds(500, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		setTitle("Atribuir Promoção");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JComboBox<Promocao> comboBox = new JComboBox<Promocao>();
		comboBox.setBounds(44, 49, 296, 22);
		contentPanel.add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(44, 82, 296, 107);
		contentPanel.add(textArea);
		
		JLabel labelPromo = new JLabel("Promoções");
		labelPromo.setForeground(Color.BLACK);
		labelPromo.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		labelPromo.setBounds(10, 11, 130, 16);
		
		contentPanel.add(labelPromo);
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
