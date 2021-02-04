package GuiComponents;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import GuiComponents.gestorClientes.GUI_gestor_cliente;

@SuppressWarnings("serial")
public class GUI_total extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_total frame = new GUI_total();
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
	public GUI_total() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane = new JPanel();
		setResizable(false);
		setContentPane(contentPane);
		setLayout(null);


		GUI_login login = new GUI_login();
		GUI_homepage homepage = new GUI_homepage();
		GUI_gestor_cliente gestor_cliente = new GUI_gestor_cliente();

		JPanel login1 = login.returnPanel();
		login1.setBounds(0, 0, 1500, 900);
		getContentPane().add(login1);

		JPanel homepage1 = homepage.returnPanel();
		homepage1.setVisible(false);
		homepage1.setBounds(0, 0, 1500, 900);
		getContentPane().add(homepage1);

		JPanel gestor_cliente1 = gestor_cliente.returnPanel();
		gestor_cliente1.setVisible(false);
		gestor_cliente1.setBounds(0, 0, 1500, 900);
		getContentPane().add(gestor_cliente1);



		login.getBtLogin().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				login1.setVisible(false);
				homepage1.setVisible(true);
			}
		});

		homepage.getBtVoltar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				login1.setVisible(true);
				homepage1.setVisible(false);
			}
		});
		
		homepage.getBtGerirClientes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepage1.setVisible(false);
				gestor_cliente1.setVisible(true);
			}
		});

		gestor_cliente.btVoltarGestorCliente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepage1.setVisible(true);
				gestor_cliente1.setVisible(false);
			}
		});
		
		
	}

}
