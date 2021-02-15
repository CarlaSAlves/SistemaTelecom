package guiComponentes.operador_gerirClientes;


import java.awt.EventQueue;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Operador_areaPessoal extends JFrame {

	private JPanel pane = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operador_areaPessoal frame = new Operador_areaPessoal();
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
	public Operador_areaPessoal() {
		inicialize();
		
		
	}

	private void inicialize() {
		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		setTitle("Área Pessoal Operador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		pane.setBackground(SystemColor.text);
		
	}
	
	

}
