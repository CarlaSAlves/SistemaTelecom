package guiComponentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.time.Duration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

public class Operador_homepage extends JFrame {

	private JPanel pane;
	private JButton btVoltar;
	private JLabel lblUsernameLogged, lblTempoSessao, lblHoraSistema;
	private JButton btClientes, btPacotesPromo;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operador_homepage frame = new Operador_homepage(null);
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
	public Operador_homepage(GUI_total guit) {
		
		inicialize();
		
	}

	private void inicialize() {
		
		ativarNimbusLookAndFeel();
		
		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		getContentPane().setFont(new Font("Verdana", Font.PLAIN, 11));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		
		JLabel lblPoesia = new JLabel("Portal do Operador");
		lblPoesia.setBounds(161, 117, 508, 33);
		lblPoesia.setForeground(new Color(70,74,101));
		lblPoesia.setFont(new Font("Dubai", Font.BOLD, 40));
		pane.add(lblPoesia);
		
		// Botões Menu 
		
		btClientes = new JButton("Clientes");
		btClientes.setBounds(235, 334, 286, 60);
		btClientes.setForeground(Color.DARK_GRAY);
		btClientes.setToolTipText("Gestão de Clientes");	
		btClientes.setFocusPainted(false);
		btClientes.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		pane.add(btClientes);

		btPacotesPromo = new JButton("Pacotes Comerciais e Promoções");
		btPacotesPromo.setBounds(235, 427, 286, 60);
		btPacotesPromo.setToolTipText("Gestão de Pacotes Comerciais e Promoções");
		btPacotesPromo.setForeground(Color.DARK_GRAY);
		btPacotesPromo.setFocusPainted(false);
		btPacotesPromo.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		pane.add(btPacotesPromo);

		// Footer 
		
		btVoltar = new JButton("Terminar Sessão");
		btVoltar.setForeground(Color.DARK_GRAY);
		btVoltar.setBounds(16, 687, 180, 50);
		btVoltar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);
		
		setupTempoSessao();
		
		JLabel lblFooter = new JLabel("");
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);
		
		// Imagem de Fundo
		
		JLabel icon = new JLabel("");
		icon.setBounds(0, 89, 1394, 586);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));
		getContentPane().add(icon);
		
	}

	/**
	 * Define as caracteristicas das labels usernameLogged, tempoSessao e horaSistema
	 */
	protected void setupTempoSessao() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.BLACK);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 699, 166, 16);
		pane.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.BLACK);
		lblTempoSessao.setText("Sessao:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		pane.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.BLACK);
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		pane.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));
	}

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
	
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}

	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}

}
