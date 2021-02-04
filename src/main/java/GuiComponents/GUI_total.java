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

		JPanel loginPanel = login.returnPanel();
		loginPanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(loginPanel);

		JPanel homepagePanel = homepage.returnPanel();
		homepagePanel.setVisible(false);
		homepagePanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(homepagePanel);

		JPanel gestor_clientePanel = gestor_cliente.returnPanel();
		gestor_clientePanel.setVisible(false);
		gestor_clientePanel.setBounds(0, 0, 1500, 900);
		getContentPane().add(gestor_clientePanel);

		login.getBtLogin().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loginPanel.setVisible(false);
				homepagePanel.setVisible(true);
			}
		});

		homepage.getBtVoltar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loginPanel.setVisible(true);
				homepagePanel.setVisible(false);
			}
		});
		
		homepage.getBtGerirClientes().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepagePanel.setVisible(false);
				gestor_clientePanel.setVisible(true);
			}
		});

		gestor_cliente.btVoltarGestorCliente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homepagePanel.setVisible(true);
				gestor_clientePanel.setVisible(false);
			}
		});
		
		
	}

}
