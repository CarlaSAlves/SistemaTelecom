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
	JLabel lblBemVindo;
	private JButton btTerminarSessao;
	private AreaCliente_MeusDados areaClienteDados = new AreaCliente_MeusDados();
	private AreaCliente_MeusProdutos areaClienteProdutos = new AreaCliente_MeusProdutos();
	private AreaCliente_VerPacotes areaClienteVerPacotes = new AreaCliente_VerPacotes();
	private AreaCliente_VerPromocoes areaClienteVerPromo = new AreaCliente_VerPromocoes();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//GUI_total guit = new GUI_total();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente frame = new AreaCliente(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 * @param guit 
	 */
	public AreaCliente(GUI_total guit) {
		initialize(guit);
		ativarNimbusLookAndFeel();

	}

	private void initialize(GUI_total guit) {

		ativarNimbusLookAndFeel(); 


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);

		/* Cabeçalho */
		//  Label portal cliente

		JLabel lblPortalCliente = new JLabel("Portal do Cliente");
		lblPortalCliente.setBounds(16, 6, 508, 33);
		lblPortalCliente.setForeground(new Color(255,165,0));
		lblPortalCliente.setFont(new Font("Dubai", Font.BOLD, 40));
		getContentPane().add(lblPortalCliente);

		lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(new Color(255,165,0));
		lblBemVindo.setFont(new Font("Dubai Light", Font.BOLD, 17));
		lblBemVindo.setBounds(16, 44, 104, 33);
		panel.add(lblBemVindo);


		// Construção JTabbedPane

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		tabbedPane.setBounds(0, 89, 1384, 586); //180, 50
		panel.add(tabbedPane);
		

		// Ligação a Classe AreaCliente_MeusDados -  Separador os meus dados
		
		JPanel panelMeusDados = areaClienteDados.returnAreaClienteMeusDados();	
		tabbedPane.addTab("Meus Dados",null,  panelMeusDados);
		panelMeusDados.setLayout(null);
	//	panelMeusDados.setForeground(Color.BLUE);
		panelMeusDados.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));


		// Ligação a Classe AreaCliente_MeusProdutos-  Separador MeusProdutos

		JPanel panelMeusProdutos = areaClienteProdutos.returnAreaClienteMeusProdutos();
		tabbedPane.addTab("Meus Produtos",null,  panelMeusProdutos);
		panelMeusProdutos.setLayout(null);
	//	panelMeusProdutos.setForeground(Color.BLUE));
		panelMeusProdutos.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));


		// Ligação a classe ver Pacotes

		JPanel panelVerTodosPacotes = areaClienteVerPacotes.returnAreaClienteVerPacotes();
		tabbedPane.addTab("Ver todos os Pacotes Comerciais",null,  panelVerTodosPacotes);
		panelVerTodosPacotes.setLayout(null);
	//	panelVerTodosPacotes.setForeground(Color.BLUE);
		panelMeusProdutos.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));
		
		// Ligação a classe ver Promoções

		JPanel panelVerTodasPromo = areaClienteVerPromo.returnAreaClienteVerPromo();
		tabbedPane.addTab("Ver todas as Promoções",null,  panelVerTodasPromo);
		panelVerTodasPromo.setLayout(null);
	//	panelVerTodasPromo.setForeground(Color.BLACK);
		panelVerTodasPromo.setFont(new Font("Dubai Light", Font.PLAIN, 12 ));
		
		/* RODAPÉ */
		//Botão Termina sessão

		btTerminarSessao = new JButton("Terminar Sessão");
		btTerminarSessao.setForeground(Color.DARK_GRAY);
		btTerminarSessao.setBounds(16, 687, 180, 50);
		btTerminarSessao.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btTerminarSessao.setFocusPainted(false);
		getContentPane().add(btTerminarSessao);


		// Action listener botão terminar sessão
		
		btTerminarSessao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guit.btTerminarSessaoCliente();

			}
		});


		// Logotipo no rodapé

		JLabel lblFooter = new JLabel("");
		lblFooter.setForeground(new Color(0, 0, 0));
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/AltranClienteFooter.png")));
		getContentPane().add(lblFooter);

		setupTempoSessao();


	}//end initialize method


	/**
	 * Definição da lable do tempo de sessão
	 * 
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
	 * Cria uma Lable de Boas Vindas
	 * @param name
	 */
	public void setLabelBoasVindas(String name) {
		lblBemVindo.setText(name);

	}
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
