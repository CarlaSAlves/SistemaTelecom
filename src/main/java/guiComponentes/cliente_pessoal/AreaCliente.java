package guiComponentes.cliente_pessoal;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.GUI_total;

import javax.swing.JTabbedPane;


public class AreaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel lblUsernameLogged,lblTempoSessao,lblHoraSistema;
	private JLabel lblBemVindo;
	private JButton btTerminarSessao;
	private AreaCliente_MeusDados areaClienteDados;
	private AreaCliente_MeusProdutos areaClienteProdutos;
	private AreaCliente_VerPacotes areaClienteVerPacotes;
	private AreaCliente_VerPromocoes areaClienteVerPromo;
	private String username;
	private JTabbedPane tabbedPane;

	/**
	 * Create the frame.
	 * @param guit 
	 */
	public AreaCliente(GUI_total guit) {
		areaClienteDados = new AreaCliente_MeusDados();
		areaClienteProdutos = new AreaCliente_MeusProdutos();
		areaClienteVerPacotes = new AreaCliente_VerPacotes();
		areaClienteVerPromo = new AreaCliente_VerPromocoes();
		
		areaClienteProdutos.getBtnPesquisarPacotes().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
				
			}
		});
		
		areaClienteProdutos.getBtnPesquisarPromocoes().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
				
			}
		});
		
		initialize(guit);
		ativarNimbusLookAndFeel();
	}
	
	/**
	 * Activates the Nimbus Look and Feel
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

	/**
	
	 /* 
	 * Contains the body of the page
	 * @param guit
	 */
	private void initialize(GUI_total guit) {
		
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.okButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Sim");

		ativarNimbusLookAndFeel(); 
		
		/**
		 * Defines the characteristics of the base panel. 
		 */
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Dubai Light", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		setResizable(false);

		/* Header */
		
		// Label customer portal

		JLabel lblPortalCliente = new JLabel("Portal do Cliente");
		lblPortalCliente.setBounds(16, 17, 508, 33);
		lblPortalCliente.setForeground(new Color(253,132,67));
		lblPortalCliente.setFont(new Font("Dubai Light", Font.BOLD, 40));
		getContentPane().add(lblPortalCliente);

		lblBemVindo = new JLabel("");
		lblBemVindo.setForeground(new Color(253,132,67));
		lblBemVindo.setFont(new Font("Dubai Light", Font.BOLD, 17));
		lblBemVindo.setBounds(16, 48, 250, 33);
		panel.add(lblBemVindo);

		// Construction JTabbedPane

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		tabbedPane.setBounds(0, 89, 1384, 586); //180, 50
		panel.add(tabbedPane);

		
		// Link to AreaCliente_MeusDados - My Data tab
		
		JPanel panelMeusDados = areaClienteDados.returnAreaClienteMeusDados();	
		tabbedPane.addTab("Meus Dados",null,  panelMeusDados);
		panelMeusDados.setLayout(null);
		panelMeusDados.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));

		// Link to Class AreaCliente_MeusProdutos- MyProducts tab
		
		JPanel panelMeusProdutos = areaClienteProdutos.returnAreaClienteMeusProdutos();
		tabbedPane.addTab("Meus Produtos",null,  panelMeusProdutos);
		panelMeusProdutos.setLayout(null);
		panelMeusProdutos.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));

		// Link to class - ver Pacotes

		JPanel panelVerTodosPacotes = areaClienteVerPacotes.returnAreaClienteVerPacotes();
		tabbedPane.addTab("Ver todos os Pacotes Comerciais",null,  panelVerTodosPacotes);
		panelVerTodosPacotes.setLayout(null);
		panelMeusProdutos.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));

		// Link to class - ver Promoções

		JPanel panelVerTodasPromo = areaClienteVerPromo.returnAreaClienteVerPromo();
		tabbedPane.addTab("Ver todas as Promoções", null,  panelVerTodasPromo);
		panelVerTodasPromo.setLayout(null);
		panelVerTodasPromo.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));

		/* BACKBOARD */
		
		//Sign out button

		btTerminarSessao = new JButton("Terminar Sessão");
		btTerminarSessao.setForeground(Color.DARK_GRAY);
		btTerminarSessao.setBounds(16, 687, 180, 50);
		btTerminarSessao.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		btTerminarSessao.setFocusPainted(false);
		getContentPane().add(btTerminarSessao);

		// Action listener logout button

		btTerminarSessao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.btTerminarSessaoCliente();

			}
		});


		// Company logo

		JLabel lblFooter = new JLabel("");
		lblFooter.setForeground(new Color(0, 0, 0));
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/AltranClienteFooter.png")));
		getContentPane().add(lblFooter);

		setupTempoSessao();
		tabbedPane.setSelectedIndex(0);

	} //end initialize method
	
	/**
	 * Configuring username labels and timer.
	 * @lblUsernameLogged displays the username that is logged in
	 * @lblTempoSessao displays the session time from the moment you log in
	 * @lblHoraSistema displays the current system time 
	 */
	protected void setupTempoSessao() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.BLACK);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 699, 166, 16);
		panel.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.BLACK);
		lblTempoSessao.setText("Sessao:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		panel.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.BLACK);
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		panel.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));

	}

	/**
	 * Create a Welcome Label
	 * @param name
	 */
	public void setLabelBoasVindas(String name) {
		lblBemVindo.setText(name);

	}
	
	/**
	 * Send the username to the client area
	 * @param username
	 */
	public void enviarUsernameAreaCliente(String username) {
		this.username = username;
		areaClienteDados.enviarUsernameMeusDados(this.username);
		areaClienteProdutos.enviarUsernameMeusProdutos(this.username);
	}

	/**
	 * 
	 * @return tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * 
	 * @return painel
	 */
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Username: " + username);
	}

	/**
	 * 
	 * @param temporizador
	 */
	public void setLblTempoSessao(Duration temporizador) {
		lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
	}

	/**
	 * 
	 * @param agora
	 */
	public void setLblHoraSistema(String agora) {
		lblHoraSistema.setText("Data: " + agora);

	}

}
