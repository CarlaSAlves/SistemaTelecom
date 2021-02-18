package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;

public class AreaCliente_VerPacotes extends JFrame {

	private JPanel panel;
	private JPanel panelVerTodosPacotes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_VerPacotes frame = new AreaCliente_VerPacotes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AreaCliente_VerPacotes() {

		initialize();

	}

	private void initialize() {

		ativarNimbusLookAndFeel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panelVerTodosPacotes = new JPanel();
		setContentPane(panel);
		panelVerTodosPacotes.setLayout(null);
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		
		JLabel imagemDados = new JLabel("");
		imagemDados.setBackground(SystemColor.menu);
		imagemDados.setBounds(-501, 0, 1358, 537);
		getContentPane().add(imagemDados);





	}//end initialize



	/**
	 * Activa o Nimbus Look and Feel
	 * 
	 */

	private void ativarNimbusLookAndFeel() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}


	public JPanel returnAreaClienteVerPacotes() {
		return  panelVerTodosPacotes;
	}
}// end class
