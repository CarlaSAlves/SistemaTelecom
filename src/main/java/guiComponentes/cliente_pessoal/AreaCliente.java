package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

public class AreaCliente extends JFrame {


	private static final long serialVersionUID = 1L;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);	
	private JButton btVoltar, btAtualizaDados;
	private JPanel panel;
	private JLabel lblUsernameLogged,lblTempoSessao,lblHoraSistema;
	private JPanel pane;
	private JPanel panelMeusDados;
	private JPanel panelDados2;
	private JPanel panelDados3;
	private JPanel panelDados4;
	private JTextField textFieldNome;
	private JTextField textFieldNIF;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;

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
		
		setupTempoSessao();
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
		
		// Construção JTabbedPane
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(192, 168, 1008, 456);
		panel.add(tabbedPane);
		
		// Separador os meus dados
		
		panelMeusDados = new JPanel();
		tabbedPane.addTab("Meus Dados",null,  panelMeusDados);
		panelMeusDados.setLayout(null);
		panelMeusDados.setForeground(Color.BLUE);
		panelMeusDados.setFont(new Font("Dubai", Font.PLAIN, 12 ));
		
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Dubai", Font.PLAIN, 18));
		lblNome.setBounds(51, 77, 69, 36);
		panelMeusDados.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(145, 77, 246, 29);
		panelMeusDados.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblNIF = new JLabel("NIF:");
		lblNIF.setFont(new Font("Dubai", Font.PLAIN, 18));
		lblNIF.setBounds(51, 138, 69, 36);
		panelMeusDados.add(lblNIF);
		
		textFieldNIF = new JTextField();
		textFieldNIF.setColumns(10);
		textFieldNIF.setBounds(145, 138, 246, 29);
		panelMeusDados.add(textFieldNIF);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dubai", Font.PLAIN, 18));
		lblLogin.setBounds(51, 205, 69, 36);
		panelMeusDados.add(lblLogin);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(145, 205, 246, 29);
		panelMeusDados.add(textFieldLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dubai", Font.PLAIN, 18));
		lblPassword.setBounds(51, 279, 81, 36);
		panelMeusDados.add(lblPassword);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(145, 279, 246, 29);
		panelMeusDados.add(textFieldPassword);
		


		
		
			
		JPanel panelMeusProdutos = new JPanel();
		tabbedPane.addTab("Os Meus produtos",null,  panelMeusProdutos);
		panelMeusProdutos.setLayout(null);
		
		// Separador Produtos
		
		JPanel panelPromoções = new JPanel();
		tabbedPane.addTab("As suas promocoes",null,  panelPromoções);
		panelPromoções.setLayout(null);
		
		// Separador Pacotes
		
		JPanel panelVerPacotes = new JPanel();
		tabbedPane.addTab("Ver todos os pacotes comercial",null,  panelVerPacotes);
		panelVerPacotes.setLayout(null);
		
		// Separador Promoções
		
		JPanel panelVerPromocoes = new JPanel();
		tabbedPane.addTab("Ver todas as promoções",null,  panelVerPromocoes);
		panelVerPromocoes.setLayout(null);
		
		
		
		
		
		// Label portal cliente

		JLabel lblPortalCliente = new JLabel("Portal Do Cliente");
		lblPortalCliente.setBounds(53, 45, 508, 33);
		lblPortalCliente.setForeground(new Color(70,74,101));
		lblPortalCliente.setFont(new Font("Dubai", Font.BOLD, 40));
		getContentPane().add(lblPortalCliente);
		
		JLabel lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(new Color(70,74,101));
		lblBemVindo.setFont(new Font("Dubai Light", Font.BOLD, 17));
		lblBemVindo.setBounds(94, 89, 208, 60);
		panel.add(lblBemVindo);
		
		


	
		
		
		
		// Botão Atualiza dados 

//		btAtualizaDados = new JButton("Atualizar Dados");
//		btAtualizaDados.setBounds(468, 27, 268, 50);
//		btAtualizaDados.setForeground(Color.DARK_GRAY);
//		btAtualizaDados.setToolTipText("Atualize os seus dados aqui.");	
//		btAtualizaDados.setFocusPainted(false);
//		btAtualizaDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
//		getContentPane().add(btAtualizaDados);
		
		
		
		
		/* RUDAPÉ */
		//Botão voltar
		
		btVoltar = new JButton("Terminar Sessão");
		btVoltar.setForeground(Color.DARK_GRAY);
		btVoltar.setBounds(16, 687, 180, 50);
		btVoltar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);
		
		
		// Logotipo no rodapé

		JLabel lblFooter = new JLabel("");
		lblFooter.setForeground(new Color(0, 0, 0));
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);
		
		
		
//		JLabel icon = new JLabel("");
//		icon.setBounds(0, 89, 1394, 586);
//		icon.setBackground(new Color(240, 240, 240));
//		icon.setIcon(new ImageIcon(AreaCliente.class.getResource("/guiComponentes/img/AltranClientes.png")));
//		getContentPane().add(icon);
		

		//TODO criar os action listener Atualizar dados/ Ver Pacotes / Ver Promoções
		
		
		btVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}//end initialize method
	
	

	// TODO  Coloca a lable de boas vindas
	
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


	public void setLableBoasVindas(String name) {
		//Passos a fazer: guitOTAL - SETAR A LBL DE BOAS VINDAS no método labelUsernameNavegaPaginas
		//Funcionario DAO copiar o método pesquisar funcionarioAdmin - Copiar e modificar para pesquisa funcionario por login ()
		//Acrescentar o novo método gestor dao
		//GUItotal altear pesquisafuncionario
		// funcionario f = gestorDeDaoget...pesquisaFuncionario  Admin
	
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
}
